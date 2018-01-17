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
    DEBUG = False

    def __init__(self, nums, target):        
        self.nums = list(map(int, nums.split()))
        self.target, self.order = target.split()
        self.target = int(self.target)        
        self.ops = []
        self.target_depth = len(self.nums) - 1

    def solve(self):
        """Try to find a solution and return it."""
        if self.search():
            return str(self)
        
        return self.order + ' impossible'

    def search(self, value=0, pending=0):
        """Perform a depth-first search."""
        depth = len(self.ops)
        
        if self.order == 'L':
            if self.ops == []:
                value = self.nums[depth]
            elif self.ops[depth - 1] == '*':
                value *= self.nums[depth]
            else:
                value += self.nums[depth]
        else:
            if self.ops == []:
                pending = self.nums[depth]
            elif self.ops[depth - 1] == '*':
                pending *= self.nums[depth]
            else:
                value += pending
                pending = self.nums[depth]

        if depth == self.target_depth:
            if value + pending == self.target:
                return True
            else:
                # This path done.
                self.ops.pop()
                return False
        
        
        if value + pending >= self.target:
            # This node done.
            self.ops.pop()
            return False
        else:
            # Search left.
            self.ops.append('*')            
            if self.search(value, pending):
                return True

            # Search right.
            self.ops.append('+')
            if self.search(value, pending):
                return True

            # This node done.
            if len(self.ops) > 0:
                self.ops.pop()

            return False         
            

    def __str__(self):
        """Return the solution as a string."""
        result = self.order + ' ' + str(self.nums[0])

        for i, op in enumerate(self.ops):
            result += ' ' + op + ' ' + str(self.nums[i + 1])

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
