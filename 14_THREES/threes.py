from fractions import gcd
import math
import Queue

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
        x = 1

        results = Queue.PriorityQueue()

        try:
            while results.qsize() < 70: 
                for z in range(int(math.sqrt(x)) + 1, x):
                    y = math.sqrt(1 + z**4 - x**2)
                    
                    if y.is_integer() and Threes.is_valid(x, int(y), z):
                        results.put((x, (x, int(y), z)))

                x += 2
        except KeyboardInterrupt:
            pass
        finally:
            i = 0
            
            while not results.empty():
                i += 1
                x, y, z = results.get()[-1]
                print("{} {} {} {}".format(i, x, y, z))
                

    @staticmethod
    def incrZ():
        x = 1
        z = 1

        results = Queue.PriorityQueue()

        try:
            while results.qsize() < 70: 
                for x in range(z, z**2, 2):
                    y = math.sqrt(1 + z**4 - x**2)
                    
                    if y.is_integer() and Threes.is_valid(x, int(y), z):
                        results.put((x, (x, int(y), z)))

                z += 2
        except KeyboardInterrupt:
            pass
        finally:
            i = 0
            
            while not results.empty():
                i += 1
                x, y, z = results.get()[-1]
                print("{} {} {} {}".format(i, x, y, z))
                
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

def main():
    #import doctest
    #doctest.testmod()
    Threes.incrX()
    print()
    Threes.incrZ()

if __name__ == '__main__':
    main()
