"""arithmetic.py
Application module for the Arithmetic class. 

Read in a sequence of scenarios and print the solution. A scenario 
should consist of the numbers to use (e.g. 1 2 3) on one line, with each number
separated by a space; and the target number plus a letter representing which 
order to use (e.g. 7 N where N=normal order, 9 L where L=left to right order)
on the next line, also separated with spaces. 

Author: Anthony Dickson
Date: 13 January 2018
"""

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
        list of numbers.
        """
        return list(map(int, nums.split()))

    def __init__(self, nums, target):
        self.nums = self.parse_nums(nums)
        self.target, self.order = target.split()
        self.target = int(self.target)
        self.solution = None

        self.solve()

    def solve(self):
        """Solve the problem. """
        return self.order + ' impossible'

    def __str__(self):
        """Return the solution as a string."""
        return self.solution

def main():
    pass

if __name__ == '__main__':
    main()