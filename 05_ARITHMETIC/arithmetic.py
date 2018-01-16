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

class Operations(Enum):
    ADD = '+'
    MULT = '*'

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
        self.target_depth = 0

    def search(self, value, pending):
        depth = len(self.ops)
        
        if depth == self.target_depth:
            return value == self.target
        
        if self.ops[depth] == Operations.MULT:
            value *= self.nums[depth]
        else:
            value += self.nums[depth]
        
        if value >= self.target:
            self.ops.pop()
            return False
        else:
            self.ops.append(Operations.MULT)
            
            if self.search(value):
                return True
           
            self.ops.append(Operations.ADD)
            return self.search(value)   
        

    def compute_value_of(self, value, pending):
        """Compute node value and store the value in that node."""
        # Compute left to right.
        if self.order == 'L':
            if self.ops[-1] == Operations.MULT:
                value = value * 
            else:
                node.value = parent.value + node.num
        # Or compute_value_of using the normal order.
        else:
            if self.ops[-1] == Operations.ADD:
                node.pending = node.num * parent.pending
                node.value = parent.value
            else:
                node.value = parent.value + parent.pending
                node.pending = node.num

    def solve(self):
        """Solve the problem and return the solution."""
        if ((self.order == 'N' and self.search(0, self.nums[0])) or
            (self.order == 'L' and self.search(self.nums[0], 0)):
            return str(self)
        
        return self.order + ' impossible'

    def __str__(self):
        """Return the solution as a string."""
        result = self.order + ' ' + str(self.nums[0])

        for i, op in enumerate(self.ops):
            result += ' ' + str(op) + ' ' + str(self.nums[i + 1])

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
