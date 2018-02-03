#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "mylib.h"
#include "dictionary.h"


//typedef struct Dictionary* dic;

struct Dictionary{
	
	long sorted, size, cap;//size & capacity 
	char** words;

};


dic dic_new(long size){
	dic d;
	d = malloc(sizeof *d);
	d->sorted = 0;
	d->cap = 2;
	d->size = size;
	d->words = malloc(sizeof(*d->words) * d->cap);
	return d;
}



void dic_add(dic d, char* word){
	//adds a word into the dictionary
	size_t len;
	d->sorted = 0;
	if(d->size == d->cap){
		//make more space for an other word
		d->cap *= 2;
		d->words = realloc(d->words, sizeof(*d->words)*d->cap); 
	}

	len = strlen(word);
	d->words[d->size] = malloc((len + 1) * sizeof(*d->words));
	strcpy(d->words[d->size++], word);
}

static int compare (const void * a, const void * b){
	return strcmp (*(const char **) a, *(const char **) b);
}



void dic_sort(dic d){
	//sorts the given dictionary using merege sort;
	//merge_sort(d, 
	qsort(d->words, d->size, sizeof (const char *), compare);
	d->sorted = 1;

}


long dic_getsize(dic d){
	//get the size of dictionary
	return d->size;
}

long dic_getindex(dic d, char* word){
	int i, lower, upper; 
	//int count=0;	
	/*return the given index that belongs to the word
		if the array is sorted already then use binayr seach */
	if(d->sorted){
		lower = 0;
		upper = d->size - 1;
		while(lower <= upper){
	
			i = lower + (upper - lower)/2;
			if(strcmp(word, d->words[i]) == 0) return i;
			else if(strcmp(word, d->words[i]) > 0) lower = i + 1;
			else upper = i - 1;	
		}
	}else{
		for(i = 0; i < d->size; i++) 
			if(d->words[i] == word) return i;
	}
	
return EXIT_FAILURE;
}


char* dic_getword(dic d, long index){
	//return the word that belongs to the given index	
	return d->words[index];
}


void dic_print(dic d){
	//print to std the given dictinary
	int i; 
	printf("array size: %d\n", d->size);
	for(i = 0; i < d->size; i++){
		printf("%s", d->words[i]);
		if(i < d->size -1) printf(" -> "); 
	}
	printf("\n");
}


int dic_free(dic d){
	//frees the memory associated to a given dic
	long i;
	
	for(i = 0; i < d->size; i++)
	  free(d->words[i]);	

	free(d->words);
	free(d);	
}
