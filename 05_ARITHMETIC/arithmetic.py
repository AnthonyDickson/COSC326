#!/usr/bin/env python

"""arithmetic.py
Application module for the Arithmetic class. 

Read in a sequence of scenarios and print the solution. A scenario 
should consist of the numbers to use (e.g. 1 2 3) on one line, with each number
separated by a space; and the target number plus a letter representing which 
order to use (e.g. 7 N where N=normal order, 9 L where L=left to right order, 
if the order is not specified left to right order is assumed) on the next line, 
also separated with spaces. 

Author: Anthony Dickson
Date: 13 January 2018
"""

import fileinput
from collections import deque

class BST:
    """A simple binary search tree implementation.
    
    Does not support deletion.
    """
    def __init__(self):
        self.key = ''
        self.left = None
        self.right = None

    def is_empty(self):
        """Return whether or not this tree is empty."""
        return self.key == ''

    def has_child(self):
        """Return whether or not this node has at least one child."""
        return self.left or self.right

    def add(self, key):
        """Add a new node to this sub-tree."""
        if self.is_empty():
            self.key = key
        if key < self.key:
            if self.left:
                self.left.add(key)
            else:                
                self.left = BST()
                self.left.key = key
        elif key > self.key:
            if self.right:
                self.right.add(key)
            else:                
                self.right = BST()
                self.right.key = key

    def height(self):
        """Return the height of this node (sub-tree)."""
        if self.is_empty():
            return 0

        left = 0
        right = 0

        if self.left:
            left = self.left.height()
        if self.right:
            right = self.right.height()
        
        return 1 + (left if left > right else right)

    def depth_of(self, key):
        """Return the depth of the node whose key matches the given key.
        
        Return -1 if not found.
        """
        if self.key == key:
            return 0
        elif key < self.key and self.left:
            return 1 + self.left.depth_of(key)
        elif key > self.key and self.right:
            return 1 + self.right.depth_of(key)
        else:
            return -1

    def bft(self, f=None):
        """Performs a breadth-first traversal of a tree and either:
        a) applies the given function at each node.
        OR
        b) returns a generator of the node keys in breadth-first order.
        """
        if self.is_empty():
            return

        q = deque()
        q.append(self)

        while len(q) > 0:
            node = q.popleft()

            if f:
                f(node.key)
            else:
                yield node.key

            if node.left:
                q.append(node.left)
            
            if node.right:
                q.append(node.right)

    def __str__(self):
        """Return string of nodes in breadth-first order."""
        result = ''

        for i in self.bft():
            result += i

        return result


class Arithmetic:
    """Given a set of numbers to use, a target value, and the order to use;
    try find a combination of addition and multiplication operations
    that once performed upon the given numbers, results in the target number.

    The solution shows the ordering used, the numbers used and which operations
    where used for the solution. In the case no solution was found, the order
    followed by impossible will be output (e.g. N impossible).

    Author: Anthony Dickson
    Date: 13 January 2018

    >>> Arithmetic('1 2 3', '7 N')
    N 1 + 2 * 3

    >>> Arithmetic('1 2 3', '9 L')
    L 1 + 2 * 3

    >>> Arithmetic('1 2 3', '100 N')
    N impossible
    """

    def __init__(self, nums, target):        
        self.nums = list(map(int, nums.split()))
        self.target, self.order = target.split()
        self.target = int(self.target)        
        self.ops = []
        

    def solve(self):
        """Solve the problem and return the solution."""
        # Run through all permutations of addition and multiplication
        # operations.
        # for ops in product('+*', repeat=len(self.nums) - 1):
        #     self.ops = ops
            
        #     if self.is_solution():
        #         return self.get_solution()

        # Create an empty binary tree
        # Perform a depth first traversal
        # At each step 
        # 
        # # Evaluate the result of nums and ops up to step #
        # # If result is greater than target we stop searching this branch and try sibling branch
        # 

        return self.order + ' impossible'

    def is_solution(self):
        """Return True if the current solution (nums and ops) produces the 
        target value, False otherwise.
        """
        if self.order == 'L':
            return self.compute() == self.target

        return eval(self.merge_nums_ops()) == self.target

    def compute(self):
        """Use nums and ops to compute the result using left to right ordering 
        and return the result.
        """
        result = self.nums[0]

        for i in range(len(self.ops)):
            if self.ops[i] == '*':
                result *= self.nums[i + 1]
            else:
                result += self.nums[i + 1]

        return result

    def get_solution(self):
        """Return the solution as a string."""
        return self.order + ' ' +  self.merge_nums_ops()

    def merge_nums_ops(self):
        """Merge, or zip, nums and ops and return as string."""
        result = str(self.nums[0])

        for i in range(len(self.ops)):
            result += ' ' + str(self.ops[i]) + ' ' + str(self.nums[i + 1])

        return result

    def __str__(self):
        """Return the solution as a string."""
        return self.get_solution()

def main():
    """Read two lines then print the solution to that given scenario.

    Usage: python arithmetic.py < <file>"""
    lines = []

    for line in fileinput.input():
        lines.append(line)
        
        if len(lines) == 2:
            try:
                print(Arithmetic(lines[0], lines[1]).solve())
            except ValueError:
                # skip over any scenarios with invalid input.
                pass
                
            lines = []

if __name__ == '__main__':
    main()
