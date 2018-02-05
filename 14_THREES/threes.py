from fractions import gcd
from math import sqrt, ceil
from queue import PriorityQueue

class Threes:
    """Finds the sets of positive integers that satisfy the following 
    conditions:
        x^2 + y^2 = 1 + z^4        
    where z < x < y, and x, y, and z have no factors in common.

    Author: Anthony Dickson
    """

    @staticmethod
    def incrX():
        """Finds the first 70 sets ordered by increasing x, for x, y and z 
        which satisfy the aformentioned conditions.
        """
        x = z = 1

        results = PriorityQueue()

        while results.qsize() < 70: 
            zLow = int(ceil((x**2 - 1)**(1/4)))
            
            if zLow % 2 == 0:
                zLow += 1

            for z in range(zLow, x, 2):
                y = sqrt(1 + z**4 - x**2)
                
                if y.is_integer() and Threes.is_valid(x, int(y), z):
                    results.put((x, (x, int(y), z)))

                if results.qsize() == 70:
                    break

            x += 2
        i = 0
        
        Threes.print_results(results)

    @staticmethod
    def incrZ():
        """Finds the first 70 sets ordered by increasing z, for x, y and z 
        which satisfy the aformentioned conditions.
        """
        x = z = 1

        results = PriorityQueue()

        while results.qsize() < 70: 
            for x in range(z, z**2, 2):
                y = sqrt(1 + z**4 - x**2)
                
                if y.is_integer() and Threes.is_valid(x, int(y), z):
                    results.put((z, (x, int(y), z)))

                if results.qsize() == 70:
                    break

            z += 2
            
        Threes.print_results(results)

    @staticmethod
    def print_results(results):
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
        >>> Threes.is_valid(169, 319, 19)
        True
        """
        if not (z < x and x < y):
            return False        
            
        if x**2 + y**2 != 1 + z**4:
            return False

        return gcd(x, y) == 1 and gcd(x, z) == 1 and gcd(y, z) == 1

def main():
    Threes.incrX()
    print('')
    Threes.incrZ()

if __name__ == '__main__':
    main()
