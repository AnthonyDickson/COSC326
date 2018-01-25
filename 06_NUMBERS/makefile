src = src/numbers
out = numbers

build: $(src)/*.java
	javac $(src)/*.java -d .

harnum:
	java $(out)/HarmonicNumbers < harnum_test.txt > harnum_results.txt

stddev:
	java $(out)/StandardDeviation < stddev_test.txt > stddev_results.txt

identity:
	java $(out)/Identity < identity_test.txt > identity_results.txt

tests: harnum stddev identity

clean:
	rm -rf numbers
	rm -f *_results.txt
