import fileinput

class TelephoneNumber:
    """Represents a telephone number and handles validation and 
    formatting.
    """

    phonebook = set() # Keeps track of original phone numbers.

    def __init__(self, ph_num):
        self.original = ph_num.rstrip('\n') # strip trailing carriage return
        self.formatted = ''
        self.area_code = 0 # Area code part of the phone number
        self.number = 0 # The rest of the phone number

        self.process()
   
    def process(self):
        """Pocess phone number"""        
        if (self.isValid()):
            if (self.isDupe()):
                self.formatted = self.duplicate_format()
            else:
                self.formatted = self.standard_format()
        else:
            self.formatted = self.invalid_format()

        TelephoneNumber.phonebook.add(self.original)

    def isValid(self):
        """Check if phone number is valid and return True if valid, false
        otherwise.
        """
        return True

    def isDupe(self):
        """Check if phone number is a duplicate (already processed) 
        and return True if duplicate, False otherwise.
        """
        return self.original in TelephoneNumber.phonebook

    def standard_format(self):
        """Return phone number in the standard format."""
        return self.original

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
        print (tn)

if __name__ == "__main__":
    main()
