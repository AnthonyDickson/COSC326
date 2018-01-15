import unittest
from arithmetic import BST

class ArithmeticTests(unittest.TestCase):
    """Tests for BST from `arithmetic.py`."""

    def test_create_empty_tree(self):
        """Create an empty tree."""
        bst = BST()
        self.assertTrue(bst.is_empty())

    def test_add_node_to_empty(self):
        """Add a node to an empty bst."""
        bst = BST()
        bst.add('a')
        self.assertFalse(bst.is_empty())

    def test_node_has_child(self):
        """Check if a node has child nodes."""
        bst = BST()
        bst.add('a')
        self.assertFalse(bst.has_child())

    def test_node_add_left_child(self):
        """Add a left child to a node."""
        bst = BST()
        bst.add('b')
        bst.add('a')
        self.assertTrue(bst.has_child())
        self.assertTrue(bst.left)

    def test_node_add_right_child(self):
        """Add a right child to a node."""
        bst = BST()
        bst.add('a')
        bst.add('b')
        self.assertTrue(bst.has_child())
        self.assertTrue(bst.right)

    def test_bft(self):
        """Print breadth-first traversal of a tree."""
        bst = BST()
        bst.add('b')
        bst.add('a')
        bst.add('c')
        self.assertEqual(str(bst), 'bac')

    def test_height(self):
        """Get the height of a tree."""
        bst = BST()
        self.assertEqual(bst.height(), 0)

        bst.add('b')
        self.assertEqual(bst.height(), 1)

        bst.add('a')
        bst.add('c')
        self.assertEqual(bst.height(), 2)

    def test_depth_of_nonexistent(self):
        """Get the depth of a non-existent node."""
        bst = BST()
        self.assertEqual(bst.depth_of('a'), -1)

        bst.add('b')
        self.assertEqual(bst.depth_of('a'), -1)

    def test_depth_of(self):
        """Get the depth of a node."""
        bst = BST()
        bst.add('a')
        bst.add('b')
        bst.add('c')
        self.assertEqual(bst.depth_of('a'), 0)
        self.assertEqual(bst.depth_of('b'), 1)
        self.assertEqual(bst.depth_of('c'), 2)

if __name__ == '__main__':
    unittest.main(verbosity=1)
