#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "graph.h"
#include "dictionary.h"
#include "queue.h"
#include "joinedup.h"
	

int main(void){
	dic d;
	graph g;
	jpfinder jp;
	int* ans; 
	int i, j, dicsize;
	int MAXWORD = 150;
	char* word = malloc(sizeof(*word)*MAXWORD);
	char* word1 = "sufix",* word2 = "fixisdwe";
	int isjoined;
	
	//isjoined = isjoinup(word1, word2);
	//printf("are '%s' and '%s' joined: %d\n", word1, word2, isjoined);
	

	d =  dic_new(0);	
		
	while(1 == (scanf("%s",  word)))
		//read words from std
		dic_add(d, word);
	
	dic_sort(d); 
	
	//dic_print(d);
	
	
	jp = jpfinder_new(d);
	strcpy(word, "physical");
		
	jp_find(jp, d, word);
	jp_print(jp);
	jp_clear(jp);
	
	//printf("get_word(dic_getsize()-1): %s\n", dic_getword(d, dic_getsize(d)-1));
	//printf("get_index(zoo): %d\n", dic_getindex(d,"zoo"));
	//dicsize = dic_getsize(d);
	//dicsize
	//g = graph_new(dicsize);

	/*

	for(i = 0; i < 2; i++){
		word = dic_getword(d, i);
		printf("-------word%d/%d %s------\n",i, dicsize, word);
		jp_find(jp, d, word);
		jp_print(jp);
		jp_clear(jp);
	}
	//printf("hello\n");
*/
/*
 //add edges to to graph if they are joined up 
	for(i = 0; i < dicsize; i++){
		word1 = dic_getword(d, i);
		for(j = 0; j < dicsize; j++){
			word2 = dic_getword(d, j);
			printf("%s,  %s\n", word1, word2);
			if(isjoinup(word1, word2)){
				if(i != j) graph_add_edge(g, i, j, 0);	
			}
		}
	}
	
	//printf("hello\n");
	//graph_print(g);
	ans = graph_dijkstraspath(g, dic_getindex(d,"zoo"), dic_getindex(d,"fixture"));
	i = 0;
	
	if(ans != NULL) while(ans[i] != '\0'){
		printf("%s -> ", dic_getword( d, ans[i]));
		i++;
	} 
	*/
	//graph_free(g);
	
	jp_free(jp);
	dic_free(d);
	free(word);

	return EXIT_SUCCESS;

}
