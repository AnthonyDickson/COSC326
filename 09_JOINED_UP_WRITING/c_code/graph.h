#ifndef GRAPH_H_
#define GRAPH_H_
#include <stdio.h>


typedef struct Graphrec* graph;

extern void graph_free(graph g);
extern int graph_add_edge (graph g, int vertice1, int vertice2, int is_bidirectonal);
extern graph graph_new(long vertices);
extern void graph_print(graph g);
extern int graph_search(graph g, char *str);
extern int* graph_dijkstraspath(graph g, int s, int t);
extern int graph_bfs(graph g, int source);
extern int graph_dfs(graph g);
extern void graph_print_bfs(graph g);

#endif
