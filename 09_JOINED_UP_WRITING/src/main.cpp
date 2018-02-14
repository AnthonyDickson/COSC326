#include <iostream>

#include "Node.h"
#include "JoinUp.h"

int main(int argc, char** argv) {
    if (argc != 3) {
        std::cout << "Usage: ./JoinUp <start> <end>" << std::endl;
        return 0;
    } 

    JoinUp jup;
    jup.search(argv[1], argv[2], LinkType::Single);
    jup.search(argv[1], argv[2], LinkType::Double);
}