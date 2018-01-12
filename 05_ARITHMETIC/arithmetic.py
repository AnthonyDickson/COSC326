"""arithmetic.py
Given a set of numbers to use, a target value, and the order to use;
try find a combination of addition and multiplication operations
that once performed upon the given numbers, results in the target number.

Author: Anthony Dickson
Date: 13 January 2018
"""

class Arithmetic:
    """Given a set of numbers to use, a target value, and the order to use;
    try find a combination of addition and multiplication operations
    that once performed upon the given numbers, results in the target number.
    """
    @staticmethod
    def parse_nums(nums):
        """Parse a string of space-separated numbers and return the 
        array of numbers.
        """
        return nums

    def __init__(self, nums, target):
        self.nums = self.parse_nums(nums)
        self.target = target.split()[0]
        self.order = target.split()[1]


    def solve(self):
        """Solve the problem. """
        return ''

def main():
    pass

if __name__ == '__main__':
    main()