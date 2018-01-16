# arithmetic.py

Author: Anthony Dickson
January 2018

Given an input of numbers, arithmetic.py tries to find a combination of addition and multiplication operations that produce the given target value.
The order in which the operands are evaluated can either be normal order (represented by 'N') or left to right order (represented by 'L').
Input can be read in from the terminal or from a file that contains input similar to what is shown in the examples below.

The main issue I faced when writing this program was the rapid growth of the problem size. The number of possible solutions is about 2^(n - 1),
 where n is the count of numbers in the input, meaning that the problem size increases twicefold for each extra number in the input.
For example, a simple input with 3 numbers would have 4 different possible solutions. However, an input with 4 numbers would have 8 solutions, an input of 5 numbers 16 solutions and so on. An input of 30 numbers has rougly one billion different possible solutions.

The solution to the rapidly growing problem size is to perform a depth-first search of the possible solutions, 'pruning' parts of the tree that have been dtermined to be not worth looking at (e.g. a sub-tree that can only produce values larger than the target value). This greatly reduced the run time of my application from over an hour on a 30 number input (when performing an exhaustive search) to ~11 seconds when using this strategy for the same length input.

## Usage

```shell
python arithmetic.py <filename>
```

## Example

Input:  
1 2 3  
7 N  
1 2 3  
9 L  
1 2 3  
100 N

Output:  
N 1 + 2 * 3  
L 1 + 2 * 3  
N impossible