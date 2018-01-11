#!/usr/bin/env python

"""telephone.py
Read in a list of telephone numbers from stdin, check whether each number is valid,
and output the formatted numbers in the order they were added.

Author: Anthony Dickson
"""

import fileinput
import re

class TelephoneNumber:
    """Represents a telephone number and handles validation and 
    formatting.
    """

    """Keep track of unqiue phone numbers."""
    phonebook = set() 

    """List valid codes."""
    initial_codes = ['0508', '0800', '0900']
    mobile_codes = ['021', '022', '025', '027']
    area_codes = ['02', '03', '04', '06', '07', '09']
    
    valid_codes = initial_codes + mobile_codes + area_codes

    """Valid phone number length grouped by code."""
    valid_lengths = {
        '0508'  : [ 6 ], '0800' : [ 6, 7 ], 
        '0900'  : [ 5 ], '021'  : [ 6, 7, 8 ], 
        '022'   : [ 7 ], '025'  : [ 6 ], 
        '027'   : [ 7 ], '02'   : [ 7 ], 
        '03'    : [ 7 ], '04'   : [ 7 ], 
        '06'    : [ 7 ], '07'   : [ 7 ], 
        '09'    : [ 7 ]
    }

    keypad_letter_groupings = ['ABC', 'DEF', 'GHI', 'JKL', 'MNO', 'PQRS', 'TUV', 
                               'WXYZ']

    letter_num_conversion_chart = {
        'ABC'   : 2, 'DEF'  : 3,  'GHI' : 4,  'JKL' : 5,  'MNO' : 6, 
        'PQRS'  : 7, 'TUV'  : 8,  'WXYZ': 9
    }

    @staticmethod
    def replace_letters_with_nums(s):
        """Replace uppercase letters with the corresponding numbers."""
        if s == '':
            return ''

        # We do not need to do anything to the string if it does not contain 
        # any uppercase letters or if there is a mixture of 
        # lowercase/uppercase (invalid).
        if not s.isupper():
            return s

        c = s[0]

        for group in TelephoneNumber.keypad_letter_groupings:
            if c in group:
                c = TelephoneNumber.letter_num_conversion_chart[group]
                break

        return str(c) + TelephoneNumber.replace_letters_with_nums(s[1:])


    @staticmethod
    def digitify(s):
        """Returns the string stripped of non-numeric values, and uppercase letters
        replaced with their corresponding numbers."""
        if (s.isdigit()):
            return s

        s = TelephoneNumber.replace_letters_with_nums(s)

        return re.sub("[^0-9]", "", s) # Strip non-numerical values.

    def __init__(self, ph_num):
        self.original = ph_num.strip()
        self.formatted = ''
        self.digits = ''
        self.code = ''
        self.number = '' # The phone number without the code.        

        self.process()
   
    def process(self):
        """Pocess phone number"""
        # Get just the digits of the phone number.
        self.digits = TelephoneNumber.digitify(self.original)
        
        # Get the code and number parts of the phone number.
        for code in TelephoneNumber.valid_codes:
            front_numbers = self.original.split(' ')[0]
            # Strip paranthesis so we can check the code easily.
            front_numbers = re.sub("[()]", '', front_numbers)
            if front_numbers[:len(code)] == code:
                self.code = code
                self.number = self.digits[len(code):]
                break

        # Get the formatted string.
        if self.is_valid():
            if (self.is_duplicate()):
                self.formatted = self.duplicate_format()
            else:
                self.formatted = self.standard_format()
        else:
            self.formatted = self.invalid_format()

        # Keep track of added phone numbers so we can check for duplicates later.
        TelephoneNumber.phonebook.add(self.digits)

    def is_valid(self):
        """Check if phone number is valid and return True if valid, false
        otherwise.
        """       
        if (not self.has_valid_code() or not self.has_valid_length()):
            return False

        # Handle the special cases
        if self.code == '02':
            return self.number[:3] == '409'
        elif self.code in ['03', '04', '06', '07', '09']:
            return (int(self.number[0]) in range(2, 10) and 
                    not self.number[:3] in '900 911 999')

        return True

    def has_valid_code(self):
        """Check if the phone number has a valid code."""
        # Check if number has been input with formatting or not.
        if not self.original.isdigit():
            # Check that the code and number are space separated.
            split = self.original.split(' ')
            # If there are no spaces or there is a hyphen in the first portion
            # of the phone number (where the code should be) the code is invalid.
            if len(split) == 1 or re.search("-", split[0]): 
                return False

        # Check if number starts with a valid code
        code_is_valid = False

        for code in TelephoneNumber.valid_codes:
            if self.digits[:len(code)] == code:
                code_is_valid = True
        
        return code_is_valid

    def has_valid_length(self):
        """Check if the phone number has a valid length"""  
        # Handle case where number has letters in it for numbers that have an 
        # intial code (not an area code or mobile code).
        if self.code in TelephoneNumber.initial_codes and self.original.isupper():
            return len(self.number) <= 9 # Numbers with lett

        return len(self.number) in TelephoneNumber.valid_lengths[self.code]

    def is_duplicate(self):
        """Check if phone number is a duplicate (already processed) 
        and return True if duplicate, False otherwise.
        """
        return self.digits in TelephoneNumber.phonebook

    def standard_format(self):
        """Return phone number in the standard format."""
        # Handle special case for 025 prefixed numbers.
        if self.code == '025':
            self.code = '027' # Convert code to 027.
            self.number = '4' + self.number # Put a 4 infront of the number.
            self.digits = self.code + self.number

        # Phone numbers that have an initial code and contains letters must be
        # be truncated if they exceed the maximum allowed length.
        if self.code in TelephoneNumber.initial_codes:
            self.number = self.number[:TelephoneNumber.valid_lengths[self.code][-1]]
            self.digits = self.code + self.number

        # Insert correct spacing
        if len(self.number) == 5:
            # no spaces
            return self.code + ' ' + self.number 
        elif len(self.number) == 6 or len(self.number) == 7:
            # space after third digit
            return self.code + ' ' + self.number[:3] + ' ' + self.number[3:]
        elif len(self.number) == 8:
            # space after fourth digit
            return self.code + ' ' + self.number[:4] + ' ' + self.number[4:]
        
        return self.code + ' ' + self.number

    def duplicate_format(self):
        """Return phone number in the format for duplicate entries."""
        return self.standard_format() + ' DUP'

    def invalid_format(self):
        """Return phone number in the format for invalid entries."""
        return self.original + ' INV'

    def __str__(self):
        return self.formatted

def main():
    """Read a file of telephone numbers from stdin and process it 
    line-by-line.
    """
    telephone_numbers = []

    for line in fileinput.input():
        telephone_numbers.append(TelephoneNumber(line))
    
    for tn in telephone_numbers:
        print(tn)

if __name__ == "__main__":
    main()
