#ifndef DICTIONARY_H_
#define DICTINARY_H_

typedef struct Dictionary* dic;

extern dic dic_new(long size);
extern void dic_add(dic d, char* word);
extern void dic_sort(dic d);
extern long dic_getsize(dic d);
extern long dic_getindex(dic d, char* word);
extern char* dic_getword(dic d, long index);
extern void dic_print(dic d);
extern int dic_free(dic d);

#endif
 
