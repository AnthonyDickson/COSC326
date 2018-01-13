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
        # Strip input of spaces.
        nums = nums.strip()
        target = target.strip()

        # Ensure numbers are input correctly (space separated).
        if not re.match("^[\d+\s]*\d+$", nums):
            raise ValueError(repr(nums) + ' is invalid input.')

        self.nums = Arithmetic.parse_nums(nums)

        # Ensure input numbers are between 1 and 10.
        if min(self.nums) < 1 or max(self.nums) > 10:
            raise ValueError(repr(nums) + ' is invalid input.')
        
        # Ensure input numbers are in order.
        for i in range(1, len(self.nums)):
            if not self.nums[i] - self.nums[i - 1] > 0: 
                raise ValueError(repr(nums) + ' is invalid input.')

        # Ensure the target number and the order are input correctly.
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
        self.ops = ['+'] * (len(self.nums) - 1)
        
        if self.is_solution():
            return self.get_solution()
            
        # Try all ops equal to '*'.
        self.ops = ['*'] * (len(self.nums) - 1)
        
        if self.is_solution() == self.target:
            return self.get_solution()
        
        # Reset ops to all '+' and...
        self.ops = ['+'] * (len(self.nums) - 1)

        # try all other permutations.
        for i in range(len(self.ops)):
            for j in range(i, len(self.ops)):
                for k in range(j, len(self.ops)):
                    # Try changing the current op to a '*'.
                    self.ops[k] = '*'
                    
                    if self.is_solution():
                        return self.get_solution()
                    
                    # Try this permetation with the ops flipped
                    self.flip_ops()

                    if self.is_solution():
                        return self.get_solution()

                    # Flip ops back and...
                    self.flip_ops()
                    # and reset the current op.
                    self.ops[k] = '+'

                # Try changing a '+' to a '*'
                self.ops[j] = '*'
            
            # Fill in ops with pluses from the 'left' side. (Undoes the changes
            # made in the above line).
            self.ops[i] = '+'

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
        
        >>> a = Arithmetic('1 2 3', '6 N')
        >>> a.ops = ['*', '+']
        >>> a.flip_ops()
        >>> a.ops
        ['+', '*']
        """
        self.ops = list(map(lambda op: '+' if op == '*' else '*', self.ops))

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
    """Read two lines then print the solution to that given scenario."""
    lines = []
    a = None

    for line in fileinput.input():
        if not line.strip() == '':
            lines.append(line)

        if len(lines) == 2:
            a = Arithmetic(lines[0], lines[1])
            print(a.solve())
            lines = []


if __name__ == '__main__':
    main()