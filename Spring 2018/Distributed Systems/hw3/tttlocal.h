/* Brad Westhafer
   COMP 512
   Homework #3
   Problem 3 b */

#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
/* Added stdbool.h so I could use the bool type 
#include <stdbool.h>
/* Added time.h to seed the random number generator */
#include <time.h>

/* Changed port to 6989 to avoid collisions with other people's code */
#define PORT   6988
static  char   buf[BUFSIZ]; 
