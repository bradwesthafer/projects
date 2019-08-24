/* Brad Westhafer
   COMP 512
   Homework #3
   Problem 3 a */

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

/* Changed port to 6989 to avoid collisions with other people's code */
#define PORT   6989
static  char   buf[BUFSIZ]; 
