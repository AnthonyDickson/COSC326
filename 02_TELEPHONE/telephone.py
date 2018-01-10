#!/usr/bin/env python

"""Telephone.py
Read in a list of telephone numbers, check whether each number is valid,
and output the (correctly) formatted numbers.

Author: Anthony Dickson

Tests
***** Test length checking ***** 
>>> print(TelephoneNumber('0508 123 456'))
0508 123 456

>>> print(TelephoneNumber('0508 123 4567'))
0508 123 4567 INV

>>> print(TelephoneNumber('0800 123 456'))
0800 123 456

>>> print(TelephoneNumber('0800 123 4567'))
0800 123 4567

>>> print(TelephoneNumber('0800 1234 5678'))
0800 1234 5678 INV

>>> print(TelephoneNumber('0900 12345'))
0900 12345

>>> print(TelephoneNumber('0900 123 456'))
0900 123 456 INV

>>> print(TelephoneNumber('02 409 1234'))
02 409 1234

>>> print(TelephoneNumber('02 409 12345'))
02 409 12345 INV

***** Test 02 area code special case checking ***** 
>>> print(TelephoneNumber('02 309-1234'))
02 309-1234 INV

>>> print(TelephoneNumber('03 923 4567'))
03 923 4567

>>> print(TelephoneNumber('04 9234 5678'))
04 9234 5678 INV

>>> print(TelephoneNumber('06 900 4567'))
06 900 4567 INV

>>> print(TelephoneNumber('07 911 4567'))
07 911 4567 INV

>>> print(TelephoneNumber('09 999 4567'))
09 999 4567 INV

>>> print(TelephoneNumber('021 123 456'))
021 123 456

>>> print(TelephoneNumber('021 123 4567'))
021 123 4567

>>> print(TelephoneNumber('021 1234 5678'))
021 1234 5678

>>> print(TelephoneNumber('021 1234 56789'))
021 1234 56789 INV

>>> print(TelephoneNumber('022 123 4567'))
022 123 4567

>>> print(TelephoneNumber('022 123 456'))
022 123 456 INV

>>> print(TelephoneNumber('027 123 4567'))
027 123 4567

>>> print(TelephoneNumber('027 123 456'))
027 123 456 INV

>>> print(TelephoneNumber('025 123 456'))
027 412 3456

>>> print(TelephoneNumber('025 123 4567'))
025 123 4567 INV

***** Test spacing checking ***** 
>>> print(TelephoneNumber('021097777'))
021 097 777

>>> print(TelephoneNumber('0900 1234 6'))
0900 12346

>>> print(TelephoneNumber('0900 1234-7'))
0900 12347

>>> print(TelephoneNumber('0508 123457'))
0508 123 457

>>> print(TelephoneNumber('0800 123457'))
0800 123 457

>>> print(TelephoneNumber('021 123 45679'))
021 1234 5679

***** Test numbers with paranthesis ***** 
>>> print(TelephoneNumber('(021) 123 45670'))
021 1234 5670

>>> print(TelephoneNumber('(0800) 4BRACKETS'))
0800 427225387

***** Test numbers with letters ***** 
>>> print(TelephoneNumber('0800 4PIPELINE'))
0800 474735463

>>> print(TelephoneNumber('0800 TOGOGO'))
0800 864 646

>>> print(TelephoneNumber('0800 IMWAYTOOLONG'))
0800 IMWAYTOOLONG INV

***** Test duplicates ***** 
>>> print(TelephoneNumber('03 456 7890'))
03 456 7890

>>> print(TelephoneNumber('03 456 7890'))
03 456 7890 DUP

>>> print(TelephoneNumber('0800 TWOOFME'))
0800 896 6363

>>> print(TelephoneNumber('0800 TWOOFME'))
0800 896 6363 DUP
"""

import fileinput
import re

class TelephoneNumber:
    """Represents a telephone number and handles validation and 
    formatting.
    """

    # Keeps track of original phone numbers across all instances.
    phonebook = set() 

    valid_codes = ['0508', '0800', '0900', '021', '022', '025', '027', 
                   '02', '03', '04', '06', '07', '09']

    valid_lengths = {
        '0508'  : [ 6 ], '0800' : [ 6, 7 ], 
        '0900'  : [ 5 ], '021'  : [ 6, 7, 8 ], 
        '022'   : [ 7 ], '025'  : [ 6 ], 
        '027'   : [ 7 ], '02'   : [ 7 ], 
        '03'    : [ 7 ], '04'   : [ 7 ], 
        '06'    : [ 7 ], '07'   : [ 7 ], 
        '09'    : [ 7 ]
    }

    initial_codes = valid_codes[:3]
    area_codes = valid_codes[3:7]
    mobile_codes = valid_codes[7:]

    keypad_letter_groupings = ['ABC', 'DEF', 'GHI', 'JKL', 'MNO', 'PQRS', 'TUV', 
                               'WXYZ']

    letter_num_conversion_chart = {
        'ABC'   : 2,  'DEF' : 3,  'GHI' : 4,  'JKL' : 5,  'MNO' : 6, 
        'PQRS'  : 7, 'TUV'  : 8,  'WXYZ': 9
    }

    @staticmethod
    def replace_letters_with_nums(s):
        """Replace uppercase letters with the corresponding numbers."""
        if s == '': # Stop case
            return ''

        if not s.isupper(): # Only uppercase letters are valid to be converted.
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

        return re.sub("[^0-9]", "", s) # Strip non-numbers and return.

    def __init__(self, ph_num):
        self.original = ph_num.rstrip()
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
            if self.digits[:len(code)] == code:
                self.code = code
                self.number = self.digits[len(code):]
                break

        # Get the formatted string.
        if (self.isValid()):
            if (self.is_duplicate()):
                self.formatted = self.duplicate_format()
            else:
                self.formatted = self.standard_format()
        else:
            self.formatted = self.invalid_format()

        # Add the phone number to the phonebook so we can check for duplicates later.
        TelephoneNumber.phonebook.add(self.digits)

    def isValid(self):
        """Check if phone number is valid and return True if valid, false
        otherwise.

        Side effects: area_code and number will be set if number is valid.
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
        """Check if the phone number has a valid code.
        """
        # Check if number has been input with formatting, e.g 03 444-1234.
        if self.original.isdigit():
            self.digits = self.original
        else:
            # Check that the code and number are space separated.
            split = self.original.split(' ')

            if len(split) == 1: 
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
        # return ', '.join([self.original, self.digits, self.code, self.number, self.formatted])
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
    import doctest
    doctest.testmod()
