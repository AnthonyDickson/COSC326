#ifndef NODE_H_
#define NODE_H_

#include <string>

class Node {
    public:
        std::string value;
        Node* parent;

    public:
        Node(std::string value);
};

#endif