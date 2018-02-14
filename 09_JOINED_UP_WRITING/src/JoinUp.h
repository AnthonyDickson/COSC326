#ifndef JOINUP_H_
#define JOINUP_H_

#include <string>
#include <vector>

using namespace std;

enum class LinkType { Single, Double };

class JoinUp {
    private:
        /** Inidcate unsuccesful termination when searching for an index. */
        const int NOT_FOUND = -1;
        vector<Node> dict;

    public:
        /** Initialises the dictionary with input from stdin. */
        JoinUp();

        /**
         * Perform a BFS for a sequence of words that link start and end.
         * Also prints the solution.
         * 
         * @param start The word to start linking to.
         * @param end The word to link up to.
         * @param type The linking type to use. 
         * @return true if a sequence was found, otherwise false.
         */
        bool search(string start, string end, LinkType type);

    private:
        /**
         * Check if string a and b are linked together.
         * 
         * @param a the first string to check.
         * @param b the string we want to link with.
         * @param type the linking type to use.
         * @return true is a links to b, otherwise false.
         */
        bool isLinkedWith(string a, string b, LinkType type);

        /** 
         * Find the words in dict that are linked with word.
         * 
         * @param word the word to link with.
         * @param type the linking type to use.
         * @return a vector containing pointers to the matching entries in dict.
         */
        vector<Node*> findLinked(string word, LinkType type);

        /**
         * Find the index of the first word that starts with the prefix.
         * 
         * @param prefix the prefix to locate in the dictionary.
         * @return the index of the first word found in the dictionary that 
         * starts with prefix.
         */
        int findPrefix(string prefix);

        /** 
         * Check if str starts with the given prefix.
         * 
         * @param str the string to check.
         * @param prefix the prefix that str should start with.
         * @return true if str starts with prefix, false otherwise.
         * Will return false if prefix is longer than str.
         */
        bool startsWith(string str, string prefix);

        /** 
         * Check if two words are singly linked by a common string.
         * 
         * @param a the first word to check.
         * @param b the other word to check.
         * @param commonPart the string that links a and b.
         * @return true if the common string is at least half as long as a or 
         * b, otherwise false.
         */
        bool isSinglyLinked(string a, string b, string commonPart);

        /** 
         * Check if two words are doubly linked by a common string.
         * 
         * @param a the first word to check.
         * @param b the other word to check.
         * @param commonPart the string that links a and b.
         * @return true if the common string is at least half as long as a and 
         * b, otherwise false.
         */
        bool isDoublyLinked(string a, string b, string commonPart);

        /**
         * Get the path from the start to the given end node.
         * 
         * @param  Node* the pointer to the node at the end of the path.
         * @return A list of Node pointers that make up the path.
         */
        vector<Node*> path(Node* endNode);
        
        /**
         * Print the solution path to stdout.
         * 
         * @param path the pointer to the path returned from the method path().
         */
        void printSolution(vector<Node*>* path);
};

#endif