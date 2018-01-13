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

import re

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
    @staticmethod
    def parse_nums(nums):
        """Parse a string of space-separated numbers and return the 
        numbers as a list of integers.
        """
        return list(map(int, nums.split()))

    def __init__(self, nums, target):
        if not re.match("^[\d+\s]*\d+$", nums):
            raise ValueError(repr(nums) + ' is invalid input.')

        self.nums = Arithmetic.parse_nums(nums)

        if re.match("^\d+\s[NL]$", target):
            self.target, self.order = target.split()
            self.target = int(self.target)
        elif re.match("^\d+$", target):
            self.target = int(target)
            self.order = 'L'
        else:
            raise ValueError(repr(target) + ' is invalid input.')
        
        self.ops = []

    def solve(self):
        """Solve the problem and return the solution."""
        # Try all ops equal to '+'.
        self.ops = ['+'] * len(self.nums)
        
        if self.is_solution():
            return self.get_solution()
            
        # Try all ops equal to '*'.
        self.ops = ['*'] * len(self.nums)
        
        if self.is_solution() == self.target:
            return self.get_solution()
        
        # Reset ops to all '+' and...
        self.ops = ['+'] * len(self.nums)

        # try all other permetations.
        for i in range(1, len(self.ops)):
            for j in range(i, len(self.ops)):
                # Try changing the current op to a '*'.
                self.ops[j] = '*'
                
                if self.is_solution():
                    return self.get_solution()
                
                # Try this permetation with the ops flipped
                self.flip_ops()

                if self.is_solution():
                    return self.get_solution()

                # Flip ops back and...
                self.flip_ops()
                # and reset the current op.
                self.ops[j] = '+'

            # Try changing a '+' to a '*'
            self.ops[i] = '*'

        return self.order + ' impossible'

    def is_solution(self):
        """Return True if the current solution (nums and ops) produces the target
        value, False otherwise.
        """
        if self.order == 'L':
            return self.compute() == self.target

        return eval(self.merge_nums_ops()) == self.target

    def flip_ops(self):
        """Flip all '+' ops to '*' ops in self.ops and vice versa.

        The op in position 0 will always be '+' (having the first
        op as '*' is invalid).
        
        >>> a = Arithmetic('1 2 3', '6 N')
        >>> a.ops = ['+', '*', '+']
        >>> a.flip_ops()
        >>> a.ops
        ['+', '+', '*']
        """
        ops = list(map(lambda op: '+' if op == '*' else '*', self.ops))
        ops[0] = '+'
        self.ops = ops

    def compute(self, i=0, a=0):
        """Use nums and ops to compute the result using left to right ordering 
        and return the result.
        
        @param i The index used for recursion.
        @param a The running total used for recursion.
        """
        if i == len(self.nums):
            return a

        if self.ops[i] == '+':
            return self.compute(i + 1, a + self.nums[i])

        return self.compute(i + 1, a * self.nums[i])

    def get_solution(self):
        """Return the solution as a string."""
        return self.order + ' ' +  self.merge_nums_ops()

    def merge_nums_ops(self):
        """Merge, or zip, nums and ops and return as string."""
        result = str(self.nums[0])

        for i in range(1, len(self.nums)):
            result += ' ' + str(self.ops[i]) + ' ' + str(self.nums[i])

        return result

    def __str__(self):
        """Return the solution as a string."""

        return self.get_solution()

def main():
    pass

if __name__ == '__main__':
    main()