#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "mylib.h"
#include "queue.h"

typedef struct q_item *q_item;

struct q_item {
	int item;
	q_item next;
};

struct queue {

	q_item first;
	q_item last;
	int len; /*length*/
};


queue queue_new(void){
	/*make a new structure of a queue*/
	queue q;
	q = emalloc(sizeof *q);
	q->first = NULL;
	q->last = NULL;
	q->len = 0;	
	return q;
}

int prioenqueue(queue q, int item){
	//enqueue into a priority queue
	q_item previous, current, toqueue;
	toqueue = emalloc(sizeof *toqueue);
	toqueue->item = item;
	toqueue->next = NULL;
	previous = NULL;
	current = q->first;

	while(1){
		if(current == NULL){
				//if it is first item.
			if(previous == NULL){
			  q->first = toqueue;
				q->last = toqueue;
				q->len++;
				return EXIT_SUCCESS;
			}else{
				current = toqueue;
				previous->next = toqueue;
				q->len++;
				return EXIT_SUCCESS;
			}
		}else if (current->item < toqueue->item){
				if(previous == NULL){
					q->first = toqueue;
					toqueue->next =  current;
					q->len++;
					return EXIT_SUCCESS;
				}else{
					previous->next = toqueue;
					toqueue->next = current;
					q->len++;
					return EXIT_SUCCESS;
				}
		}else{
			previous = current;
			current = current->next;
		}
	}	
}
void enqueue(queue q, int item){
	q_item current;
	current = emalloc(sizeof *current);
	current->item = item;
	current->next = NULL;
	if(q->first == NULL){
		q->first = current;
		q->last = current;

	}else{
		q->last->next = current;
		q->last = current;
	}
	q->len++;
}

int dequeue(queue q){
	/**/
	int popped;
	popped = 0;
	if(q->first == NULL){
		fprintf(stderr, "there are not items on the queue to dequeue\n");
		return EXIT_FAILURE;
	}else{
		q_item tmp;
		tmp = q->first;
		popped = q->first->item;
		q->first = q->first->next;
		free(tmp);
		q->len--;
	}
return popped;
}
int rdequeue(queue q){
	/*it dequeue in the reverse of the queue taking the queued item of the end of the queuet*/
	int popped;
	q_item current, temp;
	current = q->first;
	popped = (int)q->last->item;
	temp = q->last;
	
	if(q->last == NULL){
		fprintf(stderr, "there are not items on the queue to reverse dequeue\n");
		return EXIT_FAILURE;
	}
	
	while(current->next != q->last){
		/*find the previous last item in the queue*/
		current = current->next;
	}
	free(temp);
	current->next = NULL;
	q->last = current;	
	q->len--;
	return popped;
}

void queue_print(queue q){
	q_item current;
	current = q->first;
	while(current != NULL){
		printf(" %d ->", current->item);
		current = current->next;
	}	
	printf(" NULL\n");
}
int queue_size(queue q){
	return q->len;
}

int free_qitems(q_item item){
  if(item == NULL) return 1;
	if(item->next != NULL) free_qitems(item->next);
	free(item);
	return 1;
}

int queue_free(queue q){
 
	free_qitems(q->first);
	free(q);
	return EXIT_SUCCESS;

}


