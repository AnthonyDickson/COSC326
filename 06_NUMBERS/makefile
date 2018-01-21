source = src/numbers
output = numbers

build: $(source)/*.java
	mkdir -p $(output)
	javac $(source)/*.java -d $(output)

run:
	java $(output)/NumbersApp

clean:
	rm -rf bin
	rm -r *~
