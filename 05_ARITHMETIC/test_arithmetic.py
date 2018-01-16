import unittest

from arithmetic import Arithmetic

class ArithmeticTests(unittest.TestCase):
    """Tests for `arithmetic.py`."""

    N_NP = 'N impossible'
    L_NP = 'L impossible'

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

    def test_solve_N(self):
        """Are N ordered problems solved correctly?"""
        self.assertFalse(Arithmetic('1 2 3', '6 N').solve() == self.N_NP)
        self.assertFalse(Arithmetic('1 2 3', '5 N').solve() == self.N_NP)
        self.assertFalse(Arithmetic('1 2 3', '7 N').solve() == self.N_NP)
        self.assertFalse(Arithmetic('1 2 3 4', '10 N').solve() == self.N_NP)
        self.assertFalse(Arithmetic('1 2 3 4', '9 N').solve() == self.N_NP)
        self.assertFalse(Arithmetic('1 2 3 4', '11 N').solve() == self.N_NP)
        self.assertFalse(Arithmetic('1 2 3 4', '15 N').solve() == self.N_NP)
        self.assertFalse(Arithmetic('1 2 3 4', '14 N').solve() == self.N_NP)
        self.assertFalse(Arithmetic('1 2 3 4', '24 N').solve() == self.N_NP)
        self.assertFalse(Arithmetic('1 2 3 4 5', '30 N').solve() == self.N_NP)
        self.assertFalse(Arithmetic('1 2 3 4 5 6', '33 N').solve() == self.N_NP)
        self.assertFalse(Arithmetic('1 2 3 4 5 6', '127 N').solve() == self.N_NP)

        self.assertFalse(Arithmetic('2 11 8 15', '45 N').solve() == self.N_NP)

        # Case from submission.
        self.assertTrue(Arithmetic('1 2 3 4 5', '10 N').solve() == self.N_NP)

    def test_solve_L(self):
        """Are L ordered problems solved correctly?"""
        self.assertFalse(Arithmetic('1 2 3', '6 L').solve() == self.L_NP)
        self.assertFalse(Arithmetic('1 2 3', '5 L').solve() == self.L_NP)
        self.assertFalse(Arithmetic('1 2 3', '9 L').solve() == self.L_NP)
        self.assertFalse(Arithmetic('1 2 3 4', '36 L').solve() == self.L_NP)
        self.assertFalse(Arithmetic('1 2 3 4 5 6', '71 L').solve() == self.L_NP)
        
        # Case from submission.
        self.assertTrue(Arithmetic('1 2 3 4 5 1 2 3 4 5', '100 L').solve() == self.L_NP)
        # Failed case from submission.
        self.assertFalse(Arithmetic('1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5', '2134 L').solve() == self.L_NP)
        # Reaallllyy long case.
        self.assertFalse(Arithmetic('3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3', '2016 L').solve() == self.L_NP)
    
    def test_ouput_is_valid(self):
        """Is the program output valid?"""
        self.assertEqual(Arithmetic('1 2 3', '7 N').solve(), 'N 1 + 2 * 3')
        self.assertEqual(Arithmetic('1 2 3', '9 L').solve(), 'L 1 + 2 * 3')
        self.assertEqual(Arithmetic('1 2 3', '100 N').solve(), self.N_NP)

if __name__ == '__main__':
    unittest.main(verbosity=1)
