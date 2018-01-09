import fileinput

telephoneNums = []

def isValid(line):
    return True

def isDupe(line):
    return True

def standardFormat(line):
    return line

def duplicateFormat(line):
    return standardFormat(line) + "DUP"

def invalidFormat(line):
    return line + "INV"

def process(line):
    telephoneNums.push(line)
    
    if (isValid(line)):
        if (isDupe(line)):
            print duplicateFormat(line)
        print standardFormat(line)
    else:
        print invalidFormat(line)

def main():
    for line in fileinput.input():
        process(line)
    

if __name__ == "__main__":
    main()
