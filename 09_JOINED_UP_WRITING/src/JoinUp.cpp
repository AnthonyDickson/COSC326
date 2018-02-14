#include "Node.h"
#include "JoinUp.h"

#include <iostream>
#include <unordered_set>
#include <algorithm>
#include <queue>

JoinUp::JoinUp() {
    vector<string> words;

    for (string line; getline(cin, line);) {
        words.push_back(line);
    }

    sort(words.begin(), words.end());

    for (string word : words) {
        dict.push_back(Node(word));
    }
}

bool JoinUp::search(string start, string end, LinkType type) {
    Node source(start);
    Node goal(end);
    dict.push_back(goal);

    queue<Node*> q;
    unordered_set<Node*> visited;
    unordered_set<Node*> explored;
    
    q.push(&source);

    while (!q.empty()) {
        Node* curr = q.front();
        q.pop();

        if (isLinkedWith(curr->value, goal.value, type)) {
            goal.parent = curr;
            vector<Node*> solutionPath = path(&goal);
            printSolution(&solutionPath);
            return true;
        }

        for (Node* child : findLinked(curr->value, type)) {
            if (explored.find(child) == explored.end() && 
                visited.find(child) == visited.end()) {
                child->parent = curr;
                q.push(child);
                visited.insert(child);
            }
        }

        explored.insert(curr);
    }
    
    cout << 0 << endl;
    return false;
}

bool JoinUp::isLinkedWith(string a, string b, LinkType type) {
    for (unsigned int i = 1; i < a.length(); i++) {
        if (startsWith(b, a.substr(i))) {
            if (type == LinkType::Single && isSinglyLinked(a, b, a.substr(i))) return true;
            else if (type == LinkType::Double && isDoublyLinked(a, b, a.substr(i))) return true;
        }
    }

    return false;
}

vector<Node*> JoinUp::findLinked(string word, LinkType type) {
    vector<Node*> linked;

    for (unsigned int i = 0; i < word.length(); i++) {
        string prefix = word.substr(i);
        int index = findPrefix(prefix);
        
        if (index > -1) {
            string other = dict[index].value;

            while (startsWith(other, prefix) && index < (signed int) dict.size() - 1) {
                if (type == LinkType::Single && 
                    isSinglyLinked(word, other, prefix)) {
                    linked.push_back(&dict[index]);
                } else if (type == LinkType::Double && 
                           isDoublyLinked(word, other, prefix)) {
                    linked.push_back(&dict[index]);
                } 
                
                other = dict[++index].value;
            }
        }
    }

    return linked;
}

int JoinUp::findPrefix(string prefix) {
    int lower = 0;
    int upper = dict.size() - 1;
    int i = NOT_FOUND;

    while (lower <= upper) {
        int mid = (lower + upper) / 2;
        string entry = dict[mid].value;
        string target = prefix;

        if (entry.length() > target.length()) {
            entry = entry.substr(0, target.length());
        } else if (target.length() > entry.length()) {
            target = target.substr(0, entry.length());
        }

        if (entry < target) {
            lower = mid + 1;
        } else if (entry > target) {
            upper = mid - 1;
        } else {
            i = mid;
            break;
        }
    }

    if (i < 0) return NOT_FOUND;

    // Scan dictionary until we find the first word that starts with 
    // the same letter as the prefix.
    while (dict[i].value > prefix && i > 0) i--;
    // Just incase we went too far.
    while (dict[i].value < prefix && i < (signed int) dict.size() - 1) i++;

    return i;
}

bool JoinUp::startsWith(string str, string prefix) {
    if (prefix.length() > str.length()) return false;

    return str.substr(0, prefix.length()) == prefix;
}

bool JoinUp::isSinglyLinked(string a, string b, string commonPart) {
    return commonPart.length() * 2 >= a.length() || commonPart.length() * 2 >= b.length();
}

bool JoinUp::isDoublyLinked(string a, string b, string commonPart) {
    return commonPart.length() * 2 >= a.length() && commonPart.length() * 2 >= b.length();
}

vector<Node*> JoinUp::path(Node* endNode) {
    vector<Node*> result;

    for (Node* curr = endNode; curr != nullptr; curr = curr->parent) {
        result.push_back(curr);
    }

    reverse(result.begin(), result.end());

    return result;
}

void JoinUp::printSolution(vector<Node*>* path) {
    cout << path->size();
    
    for (Node* n : *path) {
        cout << " " << n->value;
    }

    cout << endl;
}
