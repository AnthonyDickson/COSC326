from collections import deque
from math import gcd

class Threes:
    """We are looking for sets of positive integers that satisfy the following 
    conditions:
        x^2 + y^2 = 1 + z^4        
    where z < x < y, and x, y, and z have no factors in common.

    This class:
    1) Finds the first 70 sets ordered by increasing x, for x, y and z which 
    satisfy the above.
    2) Finds the first 70 sets ordered by increasing z, for x, y and z which 
    satisfy the above.

    Author: Anthony Dickson
    """

    @staticmethod
    def incrX():
        """Find the first 70 sets ordered by increasing x, for x, y and z
        and print them out"""
        n = 0
        for y, z in Threes.coprimes():
            if n > 100000:
                break
            x = 0
            while x < y:
                if Threes.is_valid(x, y, z):
                    print("z=%d x=%d y=%d" % (z, x, y))
                elif Threes.is_valid(z, y, x):
                    print("z=%d x=%d y=%d" % (x, z, y))
                x += 1
            n += 1            
            

    @staticmethod
    def is_valid(x, y, z):
        """Check if the x, y and z values satisfy the following 
        conditions:
            x^2 + y^2 = 1 + z^4        
        where z < x < y, and x, y, and z have no factors in common.

        >>> Threes.is_valid(5, 13, 4)
        False
        
        >>> Threes.is_valid(11, 8, 13) 
        False
        """
        if not (z < x and x < y):
            return False

        if x**2 + y**2 != 1 + z**4:
            return False

        if gcd(x, y) != 1: 
            return False
        
        if gcd(x, z) != 1:
            return False

        if gcd(y, z) != 1:
            return False

        return True

    @staticmethod
    def coprimes():
        """Generates coprime pairs.
        
        >>> i = 0
        >>> for m, n in Threes.coprimes():
        ...     print(m, n)
        ...     i += 1
        ...     if i == 8:
        ...         break
        2 1
        3 1
        3 2
        5 2
        4 1
        5 3
        7 3
        5 1
        """
        queue = deque([[2, 1], [3, 1]])

        while True:
            m, n = queue.popleft()
            
            yield m, n

            queue.append([2 * m - n, m])
            queue.append([2 * m + n, m])
            queue.append([m + 2 * n, n])

def main():
    # import doctest
    # doctest.testmod()
    Threes.incrX()

if __name__ == '__main__':
    main()