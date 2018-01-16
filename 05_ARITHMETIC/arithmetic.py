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

class BTNode:
    """A binary-tree node."""
    key = 0
    val = 0
    op = ''
    nums = []
    ops = []
    left = None
    right = None
    done = False

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
        
    def solve(self):
        """Solve the problem and return the solution."""
        root = BTNode()
        root.key = self.nums[0]
        stack = []
        stack.append(root)
        target_depth = len(self.nums) - 1
        nums_copy = self.nums[:]

        # Perform a depth-first search of the possible solutions.
        while not root.done:
            # Calculate current tree depth.
            depth = len(stack) - 1
            # Peek at node on top of the stack.
            node = stack[-1]
            # Set the node key to corresponding number from nums.
            node.key = nums_copy[depth]

            # Set the node value if not done already.
            if node.val == 0:
                if depth == 0:
                    node.nums = [ node.key ]
                else:
                    parent = stack[depth - 1]                
                    node.nums = parent.nums[:] + [ node.key ]
                    node.ops = parent.ops[:] + [ node.op ]

                self.nums = node.nums
                self.ops = node.ops

                # Get and store the value of this node
                node.val = self.compute()

            # If we are not at the target depth yet...
            if depth < target_depth:
                # and we have overshot the target value...
                if node.val > self.target:
                    # We are done with this sub-tree, and we back up one level.
                    node.done = True
                    stack.pop()
                # Otherwise explore further down this sub-tree.
                else:
                    # Expand the tree if necessary.
                    if not node.left:
                        node.left = BTNode()
                        node.left.op = '*'

                        node.right = BTNode()
                        node.right.op = '+'
                    # If left branch has not been explored...
                    if not node.left.done:
                        # Explore left branch.
                        stack.append(node.left)
                    # Or if the right branch has not been explored...
                    elif node.left.done and not node.right.done:
                        # Explore right branch.
                        stack.append(node.right)
                    else:
                        # Otherwise both branches have been explored.
                        # We are done with this sub-tree, and we back up one level.
                        node.done = True
                        stack.pop()
            # Other if we have reached the target depth...
            else:  
                # and this node has the correct value...
                if node.val == self.target:
                    # We have found the solution and can stop.
                    self.solution = self.get_solution()
                    self.solution_path = stack
                    break
                # otherwise...
                else:
                    # We are done with this sub-tree, and we back up one level.
                    node.done = True
                    stack.pop()

        return self.solution if self.solution else (self.order + ' impossible')

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
        return self.solution

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
