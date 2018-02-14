#ifndef JOINUP_H_
#define JOINUP_H_

#include <string>
#include <vector>

using namespace std;

const int NOT_FOUND = -1;
enum class LinkType { Single, Double };

class JoinUp {
    public:
        vector<Node> dict;

    public:
        JoinUp();
        ~JoinUp();
        // Perform a BFS for a sequence of words that link start and end.
        bool search(string start, string end, LinkType type);
        // Find the words in dict that are linked with word.
        vector<Node*> findLinked(string word, LinkType type);
        // Find the index of the first word that starts with the prefix.
        int findPrefix(string prefix);
        // Check if str starts with the given prefix.
        bool startsWith(string str, string prefix);
        // Check if two words are singly linked by a common string.
        bool isSinglyLinked(string a, string b, string commonPart);
        // Check if two words are doubly linked by a common string.
        bool isDoublyLinked(string a, string b, string commonPart);
        // Get the path that ends with endNode.
        vector<Node*> path(Node* endNode);
        // Print the solution path.
        void printSolution(vector<Node*> *path);
};

#endif