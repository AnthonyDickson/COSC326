#include "Node.h"

Node::Node(std::string value="noname") {
    this->value = value;
    this->parent = nullptr;
}