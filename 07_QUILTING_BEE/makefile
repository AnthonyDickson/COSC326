pkg = quiltingbee
source_dir = src/$(pkg)
source = $(source_dir)/*.java
output_dir = $(pkg)
output = $(pkg)/*.class
main = $(pkg)/QuiltingBeeApp

build: $(source)
	mkdir -p $(output_dir)
	javac $(source) -d .

samples: $(output)
	java $(main) < sample1
	mv output.png sample1.png
	java $(main) < sample2
	mv output.png sample2.png
	java $(main) < sample3
	mv output.png sample3.png
	
clean:
	rm -rf $(output_dir)
	rm -f *.png