#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "joinedup.h"
#include "dictionary.h"
#include "mylib.h"

struct Joinedupfinderrec{

	long rstart; //range start
	long rend;  //range end
	size_t fcount;
	size_t cap;
	long* found;
	dic d;
};


jpfinder jpfinder_new(dic dictionary){
	jpfinder jp;
	jp = emalloc(sizeof *jp);
	jp->rstart = -1;
	jp->rend = -1;
	jp->fcount = 0;
	jp->cap = 2;
	jp->d = dictionary;
	jp->found = emalloc(sizeof(*jp->found) * jp->cap);
	return jp;
}



int starts_with(char* prefix, char* word){
	//printf("prefix: %s, word: %s\n", prefix, word);
	int i, prelen, wordlen;
	prelen = strlen(prefix);
	char* wordcpy[prelen +1]; // = malloc(sizeof(*wordcpy)*(prelen + 1));
	//wordlen = strlen(word);

	//printf("all good so far \n");		
	for(i = 0; i < prelen; i++){
			//printf("%d\n", i);
			wordcpy[i] = word[i];
	}
	wordcpy[i] = '\0';
	//printf("%d\n", i);
	
	//printf("%s\n", wordcpy);

	return strcmp(prefix, wordcpy);
}

long dic_getmidpoint(jpfinder jp, char* range){
   long i, lower, upper;  
   //int count=0;  
   /*return the given index that belongs to the word
     if the array is sorted already then use binayr seach */
		 lower = 0;
     upper = dic_getsize(jp->d) - 1;
     while(lower <= upper){
       i = lower + (upper - lower)/2;
       if(starts_with(range, dic_getword(jp->d, i)) == 0){
				 return i;
       }else if(starts_with(range, dic_getword(jp->d, i)) > 0){
					lower = i + 1;
       }else{
			 upper = i - 1;  
			}
     } 

	return EXIT_FAILURE;
}


jpfinder find_range(jpfinder jp, char* range){
	long index, i;
	
	i = 0;
	//printf("range: %s\n", range);
	index = dic_getmidpoint(jp, range);
	//printf("midpoint index: %ld\n", index);
	while(starts_with(range, dic_getword(jp->d, index + i)) == 0){
		if(index + i + 1 < dic_getsize(jp->d)) i++;
		else break;
	}
	jp->rend = index + i - 1;
	i = 0;
	while(starts_with(range, dic_getword(jp->d, index + i)) == 0){
		if(index + i -1  > 0) i--;
		else break;
	}

	jp->rstart = index + i + 1;

	return jp;
}


void jp_found_add(jpfinder jp, long pos){
	if(jp->fcount == jp->cap){
		jp->cap *= 2;
		jp->found = realloc(jp->found, sizeof(*jp->found) * jp->cap);
	}
	
	jp->found[jp->fcount++] = pos; 
	//printf("pos: %ld added\n", pos); 

}


char* get_lastchars(char* word, int len){
	size_t wordlen = strlen(word);	
	char* last[len + 1]; //=  malloc(sizeof(*last) * (len + 1));
	int i, j;
	i = wordlen - len;
	j = 0;

	while(word[i] != '\0')
		last[j++] = word[i++];
	
	last[j] = '\0';

 return last;
}


long* jp_find(jpfinder jp, dic d, char* word){
	size_t len;
	long i, ii;
	char* common_part;
	len = strlen(word);
	
	for(i = 1; i < len; i++){
		common_part = get_lastchars(word, i);
		//printf("common part: %s\n", common_part);
		jp = find_range(jp, common_part);
		//printf("rstart: %ld, rend %ld\n", jp->rstart, jp->rend);

		for(ii = jp->rstart; ii < jp->rend; ii++){
			//printf("is joinedup %s : %s \n",word,  dic_getword(jp->d, ii));
			if(isjoinup(word, dic_getword(jp->d, ii))) jp_found_add(jp, ii);
		}
	}

	//printf("%s: %c\n", word,  word[len-1]);
	//printf("dic size: %ld\n", dic_getsize(jp->d)); 
	//printf("word: %s\n", dic_getword(jp->d, 0));
	//if(starts_with("aba", word) == 0) printf("starts with\n");	
	
	//printf("passed\n");
	return jp->found;
}


size_t jp_getfoundcount(jpfinder jp){
	return jp->fcount;
}


void jp_free(jpfinder jp){
	free(jp->found);
	free(jp);

}

void jp_print(jpfinder jp){
	size_t i;
	for( i = 0; i < jp->fcount; i++){
		printf("%s  ", dic_getword(jp->d, jp->found[i])); 
	}
	printf("\n");
}

void jp_clear(jpfinder jp){
	size_t i;
	jp->rstart = -1;
	jp->rend = -1;
	jp->fcount = 0;
	for( i = 0; i < jp->fcount; i++){
		jp->found[i] = -1;
	}
}



int isjoinup(char* word1, char* word2){
  /*fuction that returns 1 if words and singly joined up or 2 if doubly joined up;
 	0 is it i snot joined up 
    the way that it works is that it looks for the ocurrance of the last letter in word1 in word2
    if found, it read the previous letter in both word1 and word2 raising the count if the are the s    ame.
    if it reaches the end of word2 then word1 most be joied up to word2*/
  int i = 0, e, commonc = 0, len1, len2;
  len1 = strlen(word1);
  len2 = strlen(word2);
  e = len1 - 1;

  for(i = 0; i < len2; i++){
    //iterate the second word until the last char of the first is found
    if(word1[e] == word2[i]){
      while(word1[e] == word2[i]){
        commonc++;//increment count
        if(i == 0){//if has reached the begining of word2
          if(commonc >= len1/2 && commonc >= len2/2) return 2;
          else if(commonc >= len1/2 || commonc >= len2/2) return 1;
          else return 0;
        }
        e--;
        i--;
        
      }
    } 
  }
  return 0; // if not pathern found;
}



