pkg = quiltingbee
source_dir = src/$(pkg)
source = $(source_dir)/*.java
output_dir = $(pkg)
output = $(pkg)/*.class
main = $(pkg)/QuiltingBeeApp

build: $(source)
	mkdir -p $(output_dir)
	javac $(source) -d .

test: $(output)
	java $(main) < sample_input

clean:
	rm -rf $(output_dir)
	rm -f output.png