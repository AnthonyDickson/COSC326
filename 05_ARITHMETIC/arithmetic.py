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
    num = 0
    value = 0
    pending = 0
    op = ''
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
        self.solution_path = []
        self.depth = 0
        self.target_depth = 0

    def compute_value_of(self, node, parent):
        """Compute node value and store the value in that node."""
        # Compute left to right.
        if self.order == 'L':
            if node.op == '*':
                node.value = parent.value * node.num
            else:
                node.value = parent.value + node.num
        # Or compute_value_of using the normal order.
        else:
            if node.op == '*':
                node.pending = node.num * parent.pending
                node.value = parent.value
            else:
                node.value = parent.value + parent.pending
                node.pending = node.num

    def expand(self, node):
        """Expand a node by creating two new branches."""
        node.left = BTNode()
        node.left.op = '*'

        node.right = BTNode()
        node.right.op = '+'

    def explore(self, node):
        """Explore a node and its children."""
        # Expand the tree if necessary.
        if not node.left:
            self.expand(node)

        # If left branch has not been explored...
        if not node.left.done:
            self.solution_path.append(node.left)
            self.depth += 1
        # Or if the right branch has not been explored...
        elif node.left.done and not node.right.done:
            self.solution_path.append(node.right)
            self.depth += 1
        # Otherwise if both branches have been explored...
        else:
            self.back_out_from(node)

    def back_out_from(self, node):
        """Back up the solution path from this node."""
        node.done = True
        self.solution_path.pop()
        self.depth -= 1

    def solve(self):
        """Solve the problem and return the solution."""
        root = BTNode()
        root.num = self.nums[0]
        self.solution_path = [ root ]
        self.depth = 0
        self.target_depth = len(self.nums) - 1

        # Perform a depth-first search of the possible solutions.
        while not root.done:
            node = self.solution_path[-1]
            node.num = self.nums[self.depth]

            # Set the node value if not done already.
            if node.value + node.pending == 0:
                if self.depth == 0:
                    if self.order == 'L':
                        node.value = node.num
                    else:
                        node.pending = node.num
                else:
                    self.compute_value_of(node, parent=self.solution_path[self.depth - 1])

            if self.depth < self.target_depth:
                if node.value + node.pending > self.target:
                    self.back_out_from(node)
                else:
                    self.explore(node)
            # Otherwise if we have reached the target depth...
            else:  
                if node.value + node.pending == self.target:
                    return str(self)
                else:
                    self.back_out_from(node)

        return self.order + ' impossible'

    def __str__(self):
        """Return the solution as a string."""
        result = self.order + ' ' + str(self.solution_path[0].num)

        for n in self.solution_path[1:]:
            result += ' ' + n.op + ' ' + str(n.num)

        return result

def main():
    """Read two lines then print the solution to that given scenario.

    Usage: python arithmetic.py <file>"""
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
