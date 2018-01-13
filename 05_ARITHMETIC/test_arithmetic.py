import unittest
from arithmetic import Arithmetic

class ArithmeticTests(unittest.TestCase):
    """Tests for `arithmetic.py`."""
    
    def test_parse_input(self):
        """Is input parsed correctly?"""
        self.assertEqual(Arithmetic('1 2 3', '7 N').nums, [1, 2, 3])
    
    def test_parse_target(self):
        """Is the target number parsed correctly?"""
        self.assertEqual(Arithmetic('1 2 3', '7 N').target, 7)
    
    def test_parse_order(self):
        """Is the order parsed correctly?"""
        self.assertEqual(Arithmetic('1 2 3', '7 N').order, 'N')
        self.assertEqual(Arithmetic('1 2 3', '9 L').order, 'L')
    
    def test_ouput_is_valid(self):
        """Is the program output valid?"""
        self.assertEqual(Arithmetic('1 2 3', '7 N').solve(), 'N 1 + 2 * 3')
        self.assertEqual(Arithmetic('1 2 3', '9 L').solve(), 'L 1 + 2 * 3')
        self.assertEqual(Arithmetic('1 2 3', '100 N').solve(), 'N impossible')

if __name__ == '__main__':
    unittest.main()
