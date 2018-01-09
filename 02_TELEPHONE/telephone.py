import fileinput

def process(line):
    print line

def main():
    for line in fileinput.input():
        process(line)
    

if __name__ == "__main__":
    main()
