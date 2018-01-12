import unittest
from telephone_numbers import TelephoneNumber

class TelephoneNumberTestCase(unittest.TestCase):
    """Tests for `telephone_numbers.py`."""

    def test_validity(self):
        """Ensure phone numbers are being correctly classified as (in)valid."""
        self.assertTrue(TelephoneNumber('02 409 1234').is_valid())
        self.assertFalse(TelephoneNumber('02 4091234').is_valid())

    def test_letter_split(self):
        """Ensure you can't split the number block with letters."""
        self.assertTrue(TelephoneNumber('(0508) BANANAS').is_valid())
        self.assertTrue(TelephoneNumber('0900 4HELP').is_valid())
        self.assertTrue(TelephoneNumber('0900 4HELPERS').is_valid())
        self.assertFalse(TelephoneNumber('(0508) BAN-ANAS').is_valid())
        self.assertFalse(TelephoneNumber('(0508) BAN ANAS').is_valid())
        self.assertFalse(TelephoneNumber('0900 4HELPER5').is_valid())

    def test_number_after_letter(self):
        """Ensure you can't put numbers in the 'extra' slots."""
        self.assertFalse(TelephoneNumber('0508 BANANAS2').is_valid())

    def test_space_checking(self):
        """Numbers with too many spaces, or spaces in the wrong place are invalid."""
        # Check 1+ spaces.
        self.assertTrue(TelephoneNumber('03 442 1234').has_valid_spacing())
        self.assertFalse(TelephoneNumber('03 442  1234').has_valid_spacing())
        self.assertFalse(TelephoneNumber('03   442 1234').has_valid_spacing())
        # Check spacing for numbers with 5 digits (excl. code).
        self.assertTrue(TelephoneNumber('0900 12345').has_valid_spacing())
        self.assertFalse(TelephoneNumber('0900 12 345').has_valid_spacing())
        # Check spacing for numbers with 6 digits (excl. code).
        self.assertTrue(TelephoneNumber('021 123 456').has_valid_spacing())
        self.assertFalse(TelephoneNumber('021 1234 56').has_valid_spacing())
        self.assertFalse(TelephoneNumber('021 123456').has_valid_spacing())
        # Check spacing for numbers with 7 digits (excl. code).
        self.assertTrue(TelephoneNumber('021 123 4567').has_valid_spacing())
        self.assertFalse(TelephoneNumber('021 1234 567').has_valid_spacing())
        self.assertFalse(TelephoneNumber('03 4421 234').has_valid_spacing())
        # Check spacing for numbers with 8 digits (excl. code).
        self.assertTrue(TelephoneNumber('021 1234 5678').has_valid_spacing())
        self.assertFalse(TelephoneNumber('021 1 2345 678').has_valid_spacing())
        self.assertFalse(TelephoneNumber('021 12345 678').has_valid_spacing())

    def test_trailing_characters(self):
        """Numbers with any trailing non-letter characters are invalid."""
        self.assertFalse(TelephoneNumber('03 443 1234$').is_valid())
        self.assertFalse(TelephoneNumber('03 443 1234132').is_valid())
        self.assertFalse(TelephoneNumber('03 443 1234}_#@.,').is_valid())

    def test_duplicates(self):
        """Are duplicate numbers classified properly?"""
        TelephoneNumber('0800 UNIQUE')       
        TelephoneNumber('0800 123 2287')       
        self.assertTrue(TelephoneNumber('0800 UNIQUE').is_duplicate())     
        self.assertTrue(TelephoneNumber('(0800) UNIQUE').is_duplicate())    
        self.assertTrue(TelephoneNumber('0800 123 2287').is_duplicate())     

    def test_code_checking(self):
        """Are codes being checked correctly?"""
        # Try valid codes.
        for code in TelephoneNumber.valid_codes:            
            self.assertTrue(TelephoneNumber(code + ' 123456').has_valid_code())
        
        self.assertTrue(TelephoneNumber('0274123456').has_valid_code())

        # Try invalid codes.
        self.assertFalse(TelephoneNumber('05 123 4567').has_valid_code())
        self.assertFalse(TelephoneNumber('0274 123 456').has_valid_code())

if __name__ == '__main__':
    unittest.main()