import unittest
from arithmetic import Arithmetic

class ArithmeticTests(unittest.TestCase):
    """Tests for `arithmetic.py`."""

    def test_parse_input(self):
        """Is input parsed correctly?"""
        self.assertEqual(Arithmetic('1 2 3', '7 N').nums, [1, 2, 3])
        self.assertEqual(Arithmetic('  2  6   1 ', '8 N').nums, [2, 6, 1])
    
    def test_parse_target(self):
        """Is the target number parsed correctly?"""
        self.assertEqual(Arithmetic('1 2 3', '7 N').target, 7)
        self.assertEqual(Arithmetic('1 2 3', '7    N').target, 7)
    
    def test_parse_order(self):
        """Is the order parsed correctly?"""
        self.assertEqual(Arithmetic('1 2 3', '7 N').order, 'N')
        self.assertEqual(Arithmetic('1 2 3', '9 L').order, 'L')
        self.assertEqual(Arithmetic('1 2 3', '9 L').order, 'L')

    def test_flip_ops(self):
        """Are ops flipped correctly?"""
        a = Arithmetic('1 2 3', '6 N')
        a.ops = [ '*', '+']
        a.flip_ops()
        self.assertEqual(a.ops, ['+', '*'])

    def test_compute(self):
        """Does compute work correctly?"""
        a = Arithmetic('1 2 3', '7 N')
        a.ops = ['+'] * 2
        self.assertEqual(a.compute(), 6)

        a.ops = ['*'] * 2
        self.assertEqual(a.compute(), 6)

        a.ops = ['+', '*']
        self.assertEqual(a.compute(), 9)

        a.ops = ['*', '+']
        self.assertEqual(a.compute(), 5)

        a.ops = ['*', '*']
        self.assertEqual(a.compute(), 6)

    def test_string_formatting(self):
        """Do the string formatting functions work correctly?"""
        a = Arithmetic('1 2 3', '6 L')
        a.ops = ['+'] * 2
        self.assertEqual(a.merge_nums_ops(), '1 + 2 + 3')
        self.assertEqual(a.get_solution(), 'L 1 + 2 + 3')

    def test_solve_N(self):
        """Are N ordered problems solved correctly?"""
        self.assertEqual(Arithmetic('1 2 3', '6 N').solve(), 'N 1 + 2 + 3')
        self.assertEqual(Arithmetic('1 2 3', '5 N').solve(), 'N 1 * 2 + 3')
        self.assertEqual(Arithmetic('1 2 3', '7 N').solve(), 'N 1 + 2 * 3')
        self.assertEqual(Arithmetic('1 2 3 4', '10 N').solve(), 'N 1 + 2 + 3 + 4')
        self.assertEqual(Arithmetic('1 2 3 4', '9 N').solve(), 'N 1 * 2 + 3 + 4')
        self.assertEqual(Arithmetic('1 2 3 4', '11 N').solve(), 'N 1 + 2 * 3 + 4')
        self.assertEqual(Arithmetic('1 2 3 4', '15 N').solve(), 'N 1 + 2 + 3 * 4')
        self.assertEqual(Arithmetic('1 2 3 4', '14 N').solve(), 'N 1 * 2 + 3 * 4')
        self.assertEqual(Arithmetic('1 2 3 4', '24 N').solve(), 'N 1 * 2 * 3 * 4')
        self.assertEqual(Arithmetic('1 2 3 4 5', '30 N').solve(), 'N 1 + 2 * 3 * 4 + 5')
        self.assertEqual(Arithmetic('1 2 3 4 5 6', '33 N').solve(), 'N 1 + 2 * 3 + 4 * 5 + 6')
        self.assertEqual(Arithmetic('1 2 3 4 5 6', '127 N').solve(), 'N 1 + 2 * 3 + 4 * 5 * 6')

        self.assertTrue(Arithmetic('2 11 8 15', '45 N').solve() != 'N impossible')

    def test_solve_L(self):
        """Are L ordered problems solved correctly?"""
        self.assertEqual(Arithmetic('1 2 3', '6 L').solve(), 'L 1 + 2 + 3')
        self.assertEqual(Arithmetic('1 2 3', '5 L').solve(), 'L 1 * 2 + 3')
        self.assertEqual(Arithmetic('1 2 3', '9 L').solve(), 'L 1 + 2 * 3')
        self.assertEqual(Arithmetic('1 2 3 4', '36 L').solve(), 'L 1 + 2 * 3 * 4')
        self.assertEqual(Arithmetic('1 2 3 4 5 6', '71 L').solve(), 'L 1 + 2 * 3 + 4 * 5 + 6')
    
    def test_ouput_is_valid(self):
        """Is the program output valid?"""
        self.assertEqual(Arithmetic('1 2 3', '7 N').solve(), 'N 1 + 2 * 3')
        self.assertEqual(Arithmetic('1 2 3', '9 L').solve(), 'L 1 + 2 * 3')
        self.assertEqual(Arithmetic('1 2 3', '100 N').solve(), 'N impossible')

if __name__ == '__main__':
    unittest.main(verbosity=1)
