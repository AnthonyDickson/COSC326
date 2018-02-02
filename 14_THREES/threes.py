from collections import deque
from math import gcd    
import queue

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
    D = {}

    @staticmethod
    def incrZ():
        """Find the first 70 sets ordered by increasing z, for x, y and z
        and print them out
        
        Sample of Valid Sets
        x   y   z
        169 319 19
        281 919 31
        599 1231 37
        871 1631 43
        911 1609 43
        991 1561 43
        """
        results = queue.PriorityQueue()

        try:
            for y, x in Threes.coprimes():
                z = (x**2 + y**2 - 1)**(1/4)

                if z.is_integer():
                    Z = int(z)

                    if gcd(x, Z) == 1 and gcd(y, Z) == 1:
                        results.put((Z, (x, y, Z)))
                    # break
                
                if results.qsize() == 70:
                    break
        except KeyboardInterrupt:
            print("Terminated early.")
        finally:
            while not results.empty():
                nums = results.get()[1]
                print(nums[0], nums[1], nums[2])
            

    @staticmethod
    def is_valid(x, y, z):
        """Check if the x, y and z values satisfy the following 
        conditions:
            x^2 + y^2 = 1 + z^4        
        where z < x < y, and x, y, and z have no factors in common.

        >>> Threes.is_valid(5, 13, 4)
        False      
        >>> Threes.is_valid(169, 319, 19)
        True
        >>> Threes.is_valid(281, 919, 31)
        True
        >>> Threes.is_valid(599, 1231, 37)
        True
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
    def primes(q=2):
        """ Generate an infinite sequence of prime numbers.

        Sieve of Eratosthenes
        Code by David Eppstein, UC Irvine, 28 Feb 2002
        http://code.activestate.com/recipes/117119/
        """
        # Maps composites to primes witnessing their compositeness.
        # This is memory efficient, as the sieve is not "run forward"
        # indefinitely, but only as long as required by the current
        # number being tested.
        #
        # D = {}
        
        # The running integer that's checked for primeness
        # q = 2

        while True:
            if q not in Threes.D:
                # q is a new prime.
                # Yield it and mark its first multiple that isn't
                # already marked in previous iterations
                # 
                yield q
                Threes.D[q * q] = [q]
            else:
                # q is composite. D[q] is the list of primes that
                # divide it. Since we've reached q, we no longer
                # need it in the map, but we'll mark the next 
                # multiples of its witnesses to prepare for larger
                # numbers
                # 
                for p in Threes.D[q]:
                    Threes.D.setdefault(p + q, []).append(p)
                del Threes.D[q]
            
            q += 1


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
        yield (2, 1)
        yield (3, 1)

        for m, n in Threes.coprimes():
            yield (2 * m - n, m)
            yield (2 * m + n, m)
            yield (m + 2 * n, n)

def main():
    # import doctest
    # doctest.testmod()
    Threes.incrZ()

if __name__ == '__main__':
    main()