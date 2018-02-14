#ifndef NODE_H_
#define NODE_H_

#include <string>

using namespace std;

class Node {
    public:
        string value;
        Node* parent;

    public:
        Node(string value);
};

#endif
