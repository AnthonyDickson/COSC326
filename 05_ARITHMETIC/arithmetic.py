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
    def __init__(self):
        self.parent = None
        self.key = 0
        self.val = 0
        self.op = ''
        self.left = None
        self.right = None
        self.done = False

    def __str__(self):
        """Return string of key, val and op."""
        return '({}, {}, {})'.format(str(self.key), str(self.val), self.op)

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
        # Make a binary tree
        root = BTNode()
        root.key = self.nums[0]
        stack = []
        stack.append(root)
        
        depth = 0
        target_depth = len(self.nums) - 1

        # Keep a copy of the original nums.
        nums_copy = self.nums[:]

        while not root.done:
            # Peek at node on top of the stack.
            node = stack[-1]
            # Set the node key to corresponding number from nums.
            node.key = nums_copy[depth]
            # Set the node val if not done already.
            if node.val == 0:
                self.ops = []
                self.nums = []

                # Get the nums and ops for this path.
                for n in stack:
                    if n.op != '':
                        self.ops.append(n.op)
                    
                    self.nums.append(n.key)

                # Get and store the value of this node
                node.val = self.compute()


            print('At node: ' + str(node))
            if node.parent:
                if node == node.parent.left:
                    print('In left branch of node: ' + str(node.parent))
                else:
                    print('In left branch of node: ' + str(node.parent))
            print('At depth: ' + str(depth))
            print('Stack :' + str(list(map(lambda n: str(n), stack))))

            # If we are not at the target depth yet.
            if depth < target_depth:
                print('Overshot target value at node ' + str(node))
                # If we have overshot the target value...
                if node.val > self.target:
                    # Remove this node from the solution path.
                    node.done = True
                    stack.pop()
                    depth -= 1
                # Otherwise explore further down the branch.
                else:
                    print('Exploring further...')
                    # If left branch has not been explored.
                    if not node.left:
                        # Expand the tree.
                        print('Planting tree...')
                        node.left = BTNode()
                        node.left.op = '*'
                        node.left.parent = node

                        node.right = BTNode()
                        node.right.op = '+'
                        node.right.parent = node
                    
                    if not node.left.done:
                        # Explore left branch.
                        print('Exploring left branch.')
                        stack.append(node.left)
                        depth += 1
                    elif node.left.done and not node.right.done:
                        # Explore right branch.
                        print('Exploring right branch.')
                        stack.append(node.right)
                        depth += 1
                    else:
                        # Both branches explored.
                        # Remove this node from solution path.
                        node.done = True
                        stack.pop()
                        depth += 1
            elif depth == target_depth:  
                # If node val is target value and we have the correct depth.
                if node.val == self.target:
                    print('Found solution.')
                    self.solution = self.get_solution()
                    break
                else:
                    node.done = True
                    stack.pop()
                    depth -= 1
                
            print('\n')
        

        if self.solution:
            return self.solution

        return self.order + ' impossible'


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
