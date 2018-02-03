#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "graph.h"
#include "queue.h"
#include "mylib.h"
#include <limits.h>
/*
Code for making a graph structure, It uses mylib.h and queue.h
	It creates a structure for revry Vertex which contains the the state and the distance.
	it contains a structure for the graph, which contains the structe for vertices and the edges represented by a 2d array of 1 for an existing edge and 0 for an non- existing one, and the size as the number of vetices the graph has.
*/
 

#define IS_UNVISITED(x) (UNVISITED == (x).state)

#define IS_VISITED_SELF(x) (VISITED_SELF == (x).state)

typedef enum { UNVISITED, VISITED_SELF, VISITED_DESCENDANTS } state_t;

typedef struct Vertex* vertex;

typedef struct Queued_Vertex* qver;	

typedef struct Vertex_Queue* vqueue;

int step;
	 // varibale steps for the implemetation of bfs and dfs
struct Vertex{
	int dis; /*distance*/
	state_t state;
	int pre; /*predicesor*/
	int finish;
};


struct Graphrec {
 
	vertex ver; 
	int size; /*number of vertices*/
	int** edges;
};

struct Queued_Vertex{
	//structure for implemeting a elemete in the linked list queue of vertices;
	vertex ver;
	vertex next;
};

struct Vertex_Queue{
	//a structure for implemeting a queue of verices use in dfs,  bfs anddijkstra's algorithm 
	qver first;
	qver last;
	int len; //length of the queue	
};

/* ----- Internal Queue fuctions start ----- */

vqueue new_queue(void){
	//makes a instance of the vertex queue struct 
	vqueue vq;
	vq = emalloc(sizeof *vq);
	vq->first = NULL;
	vq->last = NULL;
	vq->len = 0;
	return vq;
}

void venqueue(vqueue vq, vertex v){
	qver current;
	current = emalloc(sizeof *current);
	current->ver = v;
	current->next = NULL;
	
	if(vq->first == NULL){
		vq->first = current;	
		vq->last = current; 	
	}else{
		vq->last->next = current;
		vq->last = current;
	}
	vq->len++;
} 

int prio_venqueue(vqueue vq, vertex ver){
	//adds a vertex to the queue but mantains the priority by the order
	qver previous, current, toqueue;
	toqueue = emalloc(sizeof *toqueue);
	toqueue->ver = ver;
	toqueue->next = NULL;
	previous = NULL;
	current = vq->first;
			
	while(1){
		if(current == NULL){
			if(previous == NULL){
				//it is the first vertex in the queue
				vq->first = toqueue;
				vq->last = toqueue;
				vq->len++;
				return EXIT_SUCCESS;
			}else{
				//if it is enqueued in the last elemet
				current = toqueue;
				previous->next = toqueue;
				vq->len++;
			}
		}else if(current->ver->dis < toqueue->ver->dis){
			if(previous == NULL){
				//if it is to be queued at the beinging of the queue
				vq->first = toqueue;
				toqueue->next = current;
				vq->len++;
				return EXIT_SUCCESS;
			}else{
				//enqueue at the middle of the queue
				previous->next = toqueue;
				toqueue->next = current;
				vq->len++;
				return EXIT_SUCCESS;
			}
		}else{
			//keep going down the queue;
			previous = current;
			current = current->next;
		} 
	}
}

vertex vdequeue(vqueue vq){
	vertex popped;
	
	if(vq->first == NULL){
	fprintf(stderr, "There are no vertices in the vq to dequeue\n");
	return NULL;
	}else{
		qver tmp;
		tmp = vq->first;
		popped = vq->first->ver;
		vq->first = vq->first->next;
		free(tmp);
		vq->len--;
	}	
	return popped;
}


void vqueue_print(vqueue vq){

	qver current;
	current = vq->first;
	while(current != NULL){
		printf(" v.dis: %d ->", current->ver->dis); 
		current = current->next;
	}
	printf(" NULL\n");

}

/* ------- Graph fuctions start ---------- */

graph graph_new(long num_vertices){
	/*a fuctuion that crates a new graph.*/

	long i, j;

	/*allocate memory for graph*/	
	graph g =  emalloc(sizeof *g);
	g->size = num_vertices;
	g->edges = emalloc(g->size * sizeof(*g->edges));
	for(i = 0; i < g->size; i++)
	  g->edges[i] = emalloc(g->size * sizeof(**g->edges));
 	g->ver = emalloc(g->size * sizeof(*g->ver));

	/*initilize var to 0, if the size of the graph is too big 
 	 *then putting everythin to Zer becomes too expencive */
		
		for(i = 0; i < g->size; i++){
	  	//for(j = 0; j < g->size; j++){
			 	//g->edges[i][j] = 0; 
			//}
				g->ver[i].dis = INT_MAX;	
				g->ver[i].finish = 0; 
				g->ver[i].state = UNVISITED; 
				g->ver[i].pre = -1; 
		} 

 return g;
}


int graph_add_edge(graph g, int u, int v, int is_bi){
	/*adds the the edege to the gaph from u -> v, in is_bi is 1 
  		the it makes it bi directonal, adding u <- v  as well */
	
	g->edges[u][v] = 1;
	if(is_bi) g->edges[v][u] = 1;

	return EXIT_SUCCESS;
}



int graph_bfs(graph g, int source){
	queue q;
	q = queue_new();
	
	g->ver[source].state = VISITED_SELF;
	g->ver[source].dis = 0;
	
	enqueue(q, source);
	while(queue_size(q) > 0){
		int dequeued;
		int i;
		queue_print(q);
		dequeued = dequeue(q);
		printf("dequeued: %d\n",dequeued);
		for(i = 0; i < g->size; i++){
			if((g->edges[dequeued][i] == 1) && (IS_UNVISITED(g->ver[i]))){
				
				g->ver[i].state = VISITED_SELF;
				g->ver[i].dis = g->ver[dequeued].dis + 1;
				g->ver[i].pre = dequeued;
				enqueue(q, i);
			}
		
}
		g->ver[dequeued].state = VISITED_DESCENDANTS; 
	}
	return EXIT_SUCCESS;
}

int visit(int v, graph g){

	int i;
	g->ver[v].state =  VISITED_SELF;
	step++;
	g->ver[v].dis = step;

	for(i = 0; i < g->size; i++){

		if(g->edges[v][i] == 1){

			if(IS_UNVISITED(g->ver[i])){
				g->ver[i].pre = v;
				visit(i, g);
			}
		}
	}
	step++;
	g->ver[v].state = VISITED_DESCENDANTS;
	g->ver[v].finish =  step;
			
	return EXIT_SUCCESS;
}

int graph_dfs(graph g){
	int i;
	
	for(i = 0; i < g->size; i++){
		g->ver[i].state = UNVISITED; 
		g->ver[i].pre = -1; 
		g->ver[i].finish = 0;
		g->ver[i].dis= -1;
	}	

	step = 0;
for (i = 0; i < g->size; i++){
		if(IS_UNVISITED(g->ver[i])) visit(i, g);
			
		
	}
	return EXIT_SUCCESS;
	
}

int get_minunvisited(graph g){
	//get the vertex with the smallest distance
	int i, min, curpre;
	for(i = 0; i < g->size; i++) if(IS_UNVISITED(g->ver[i])){
		min = i;
		break;
	}
	for(i; i<g->size; i++) if(IS_UNVISITED(g->ver[i])){
		
		if(g->ver[min].dis > g->ver[i].dis){
			min = i;
		}
	}
	return min;
}



int* graph_dijkstraspath(graph g, int s, int t){
	/*find the sortest path using dijkstra's algortihm 
		if no path was found it returns a NULL pointer, else, 
		i returns pointer to NULL
	t = target node, cur = current
	s = start node,  neibr =  neighbor vertex  */
	int* path;
	int i, neibr, cur, unvisited, path_c = 1;
	unvisited = g->size;
	path = emalloc(sizeof(*path) * path_c);
	path[0] = s;
	for(i = 0; i < g->size; i++) if(i != s){	
			//se every node to unvisited
			g->ver[i].state = UNVISITED;
			g->ver[i].dis = 100000000;//find beter way to define infitinity
			g->ver[i].pre = -1; 
	}
	g->ver[s].dis = 0;
	
	while( unvisited != 0 ){
		cur = get_minunvisited(g);
		//printf("cur: %d---\n", cur);
		g->ver[cur].state = VISITED_SELF;
		unvisited--;
		for(neibr = 0; neibr < g->size; neibr++) if(g->edges[cur][neibr]){
				//printf("    cur's neibr: %d\n", neibr); 
				//printf("    cur.dis: %d + %d \n", g->ver[cur].dis, g->edges[cur][neibr]); 
			if(g->ver[cur].dis + g->edges[cur][neibr] < g->ver[neibr].dis){
				g->ver[neibr].dis = g->ver[cur].dis + g->edges[cur][neibr];
				g->ver[neibr].pre = cur;
			}
		} 
	}

	//for(i = 0; i < g->size; i++){
		//printf("node: %d, state: %d, dis: %d, pre: %d\n", 
			//		i,  g->ver[i].state, g->ver[i].dis, g->ver[i].pre);
	//}
	

	cur = g->ver[t].pre;
	printf("t.pre: %d\n", cur); 
	while(1){
		if(cur == -1) {
			printf("it returned NULL\n");
			return NULL;
		}else if(cur == s){
			path = erealloc(path, sizeof(*path) * ++path_c);
			path[path_c - 1] = t;
			path[path_c] = NULL;
			printf("it returned path \n");
			return path;
		}else{
			cur = g->ver[cur].pre;
			path = erealloc(path, sizeof(*path) * path_c);
			path[path_c] = cur;
			printf("add cur: %d", cur);
		}
	}	


}	



void graph_free(graph g){
	//free all the nodes in the graph;
	int i;

	free(g->ver);
	for(i = 0; i < g->size; i++) free(g->edges[i]);
	free(g->edges);
	free(g);
}


void graph_print(graph g){
	int i, j;

	printf("____graph:___\n");
	for(i = 0; i < g->size; i++){
		printf("%d | ", i); 
		
		for(j = 0; j < g->size; j++){
			if(g->edges[i][j] == 1) printf("%d, ", j);
		}
		printf("\n");

	}

	//graph_print_bfs(g);

}

void graph_print_bfs(graph g){
	int i;

	printf("____DFS:___\n");
	printf("Vertex | Distance | Predecesor | finish\n");
	for(i = 0; i < g->size; i++){
		
		printf("%2d  %7d %12d %12d \n", i, g->ver[i].dis, g->ver[i].pre, g->ver[i].finish); 

	}

}


