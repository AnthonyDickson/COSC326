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
        self.open = True

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

        i = 0
        max_i = 2 ** len(self.nums)
        depth = 0
        max_depth = len(self.nums) - 1

        # Keep a copy of the original nums.
        nums_copy = self.nums[:]

        while i < max_i:
            # Peek at node on top of the stack.
            node = stack[-1]
            # Set the node key to corresponding number from nums.
            node.key = nums_copy[depth]

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
            print('Stack :' + str(list(map(lambda n: str(n), stack))))
            print()

            # Node val is target value and we have the correct depth.
            if node.val == self.target and depth == max_depth:
                self.solution = self.get_solution()
                break
            # Node val has overshot target value and we are not at the 
            # correct depth.
            if node.val >= self.target and depth < max_depth:
                # Set this node to 'closed'.
                node.open = False
                # Remove this node from path.
                stack.pop()
                depth -= 1
            
            # If left branch has not been explored yet and we have not reached
            # the max depth yet and this node is 'open'.
            if node.open and node.left == None and depth < max_depth:
                left = BTNode()
                left.op = '*'
                left.parent = node
                node.left = left
                stack.append(left)
                depth += 1
            # Otherwise explore the right branch.
            elif node.open and node.right == None and depth < max_depth:
                right = BTNode()
                right.op = '+'
                right.parent = node
                node.right = right
                stack.append(right)
                depth += 1
            # Both left and right branches have been explored.
            elif node.open and depth < max_depth:
                if not node.left.open and not node.right.open:
                    node.open = False
                    stack.pop() 
                    depth -= 1
                elif not node.left.open:
                    

            i += 1
        

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
