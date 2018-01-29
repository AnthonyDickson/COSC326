from collections import deque

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
    def gcd(a, b):
        """Find the greatest common denominator of two integers.
        
        >>> Threes.gcd(8, 24)
        8

        >>> Threes.gcd(13, 27)
        1
        
        """
        while not b == 0:
            if a > b:
                a = a - b
            else:
                b = b - a
        return a

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
        if x**2 + y**2 != 1 + z**4:
            return False

        if not (z < x and x < y):
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
        """Generates coprime pairs."""
        queue = deque([[2, 1], [3, 1]])

        while True:
            m, n = queue.popleft()
            
            yield m, n

            queue.append([2 * m - n, m])
            queue.append([2 * m + n, m])
            queue.append([m + 2 * n, n])

def main():
    import doctest
    doctest.testmod()

if __name__ == '__main__':
    main()