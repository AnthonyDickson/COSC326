#ifndef JOINEDUP_H_
#define JOINEDUP_H_
#include <stdio.h>
#include "dictionary.h"


typedef struct Joinedupfinderrec* jpfinder;

extern jpfinder jpfinder_new();
extern int isjoinup(char* word1, char* word2);
extern long* jp_find(jpfinder jp, dic d, char* inputword);
extern void jp_clear(jpfinder jp);
extern void jp_print(jpfinder jp);
extern size_t jp_getfoundcount(jpfinder jp);
extern void jp_free(jpfinder jp);
#endif
