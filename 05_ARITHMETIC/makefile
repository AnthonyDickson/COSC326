bst: test_bst.py
	python3 test_bst.py

main: test_arithmetic.py 
	python3 test_arithmetic.py

input: arithmetic.py valid_input valid_output
	python3 arithmetic.py < valid_input > test_output
	diff valid_output test_output