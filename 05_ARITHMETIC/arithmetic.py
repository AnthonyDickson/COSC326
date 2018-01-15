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
    """A binary search tree implementation where each path in the tree
    represents a combination of operations.

    Each node keeps track of:
        its key
        a number
        an opertaion (either + or *)
    
    Does not support deletion.
    """    

    @staticmethod
    def from_sorted_list(a, start, end):
        """Construct a bst from a list of sorted numbers.
        
        Method adapted from: 
        https://www.geeksforgeeks.org/sorted-array-to-balanced-bst/
        """
        if start > end:
            return

        mid = int((start + end) / 2)        
        node = BST() 
        node.key = a[mid]
        node.left = BST.from_sorted_list(a, start, mid - 1)
        node.right = BST.from_sorted_list(a, mid + 1, end)

        if node.left:
            node.left.op = '+'
        if node.right:
            node.right.op = '*'
         
        return node

    def __init__(self):
        self.key = None
        self.num = 0
        self.op = ''
        self.left = None
        self.right = None
        self.open = True

    def is_empty(self):
        """Return whether or not this tree is empty."""
        return self.key == None or self.key == ''

    def has_child(self):
        """Return whether or not this node has at least one child."""
        return self.left or self.right

    def add(self, key, num=0, op=''):
        """Add a new node to this sub-tree."""

        if self.is_empty():
            self.key = key
            self.num = num
            self.op = op

        node = BST()
        node.key = key
        node.num = num
        node.op = op

        if key < self.key:
            if self.left:
                self.left.add(key, num, op)
            else:                
                self.left = node
        elif key > self.key: 
            if self.right:
                self.right.add(key, num, op)
            else:                
                self.right = node

    def contains(self, key):
        """Return whether or not the bst contains a node with the given key."""
        if self.is_empty():
            return False

        if key == self.key:
            return True
        elif self.left and key < self.key:
            return self.left.contains(key)
        elif self.right and key > self.key:
            return self.right.contains(key)
        else:
            return False

    def get(self, key):
        """Return the node with the given key, or None if not found."""
        if self.is_empty():
            return None

        if key == self.key:
            return self
        elif self.left and key < self.key:
            return self.left.get(key)
        elif self.right and key > self.key:
            return self.right.get(key)
        else:
            return None

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
        elif self.left and key < self.key:
            return 1 + self.left.depth_of(key)
        elif self.right and key > self.key:
            return 1 + self.right.depth_of(key)
        else:
            return -1

    def path_to(self, key):
        """Return the path to the node with the give key."""
        if self.is_empty():       
            return []
        
        if key < self.key and self.left:
            return [ self ] + self.left.path_to(key)
        elif key > self.key and self.right:
            return [ self ] + self.right.path_to(key)
        elif key == self.key:
            return [ self ]
        else:
            return None

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
                f(node)
            else:
                yield node

            if node.left:# and node.left.open:
                q.append(node.left)
            
            if node.right:# and node.right.open:
                q.append(node.right)

    def set_num(self, num):
        """Set this node's num."""
        self.num = num

    def close(self):
        """Close this node and any desendents."""
        if self.is_empty():
            return

        self.open = False

        if self.left:
            self.left.close()
        
        if self.right:
            self.right.close()

    def __str__(self):
        """Return string of nodes in breadth-first order."""
        result = ''

        for bst in self.bft():
            result += str([bst.key, bst.num, bst.op]) + ', '

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
        self.solution = ''
        self.solution_tree = None
        self.solution_path = []
        

    def solve(self):
        """Solve the problem and return the solution."""
        # Run through all permutations of addition and multiplication
        # operations.
        # for ops in product('+*', repeat=len(self.nums) - 1):
        #     self.ops = ops
            
        #     if self.is_solution():
        #         return self.get_solution()

        # Create an empty binary tree
        indicies = list(range(len(self.nums) ** 2 - 2))
        self.solution_tree = BST.from_sorted_list(indicies, 0, len(indicies))
        # At each step 
        # 
        # # Perform a breadth first traversal
        # # Evaluate the result of nums and ops up to step #
        # # If result is greater than target we stop searching this branch and try sibling branch
        print(self.solution_tree)
        for step in self.solution_tree.bft(self.step):
            step()

        if self.solution:
            return self.solution

        return self.order + ' impossible'

    def step(self, node):

        print('At node ' + str(node.key))
        print('Setting node to: ' + str(self.nums[self.solution_tree.depth_of(node.key)]))
        node.set_num(self.nums[self.solution_tree.depth_of(node.key)])

        if not node.open:
            return

        path = self.solution_tree.path_to(node.key)

        # Get the nums and ops from this path
        nums = []
        ops = []

        for n in path:
            nums.append(n.num)

            if n.op in '*+':
                ops.append(n.op)

        # Make a backup copy of self.nums and self.ops
        nums_copy = self.nums[:]
        ops_copy = self.ops[:]

        self.nums = nums
        self.ops = ops[1:]


        z = ''

        if node.left:
            z += str(node.left.key)

        if node.right:
            z += str(node.right.key)
            
        print('Node children: ' + z)
        print('Path: ' + ' -> '.join(list(map(lambda x: str(x.key), path))) )
        print('Nums: ' + str(self.nums))
        print('ops: ' + str(self.ops))

        # Compute the value of this path.
        path_value = self.compute()

        print('Current solution: ' + self.get_solution())
        print('Path value: ' + str(path_value) + '\n')

        # If we have found the target value and we are at a leaf.
        if path_value == self.target and not node.has_child():
            self.solution = self.get_solution()
            self.solution_path = path
        # Otherwise, if have overshot the target value and we are not yet at a 
        # leaf.
        elif path_value >= self.target:
            # 'Close' this node and its children nodes so that this node and
            # any descendants are ignored in the future.
            node.close()

        # Restore ops and nums
        self.ops = ops_copy
        self.nums = nums_copy


    def is_solution(self):
        """Return True if the current solution (nums and ops) produces the 
        target value, False otherwise.
        """
        return self.compute() == self.target

    def compute(self):
        """Use nums and ops to compute the result using left to right ordering 
        and return the result.
        """
        if self.order == 'N':
            return eval(self.merge_nums_ops())

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
