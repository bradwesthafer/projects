/* Brad Westhafer
   COMP 512
   Homework #3
   Problem 1 */

/* This is the program pipe-hw3.c */
  
/* This program uses a pipe to send a string argument from 
   the parent as a message to the child.                */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
/* Added an include for ctype.h so that I could use tolower and toupper functions */
#include <ctype.h>

int main(int argc, char *argv[ ]) {

  int pipefd[2];
  /* added a return pipe */
  int piperet[2];

  static char message[1024];

  if (argc != 2) {
    fprintf(stderr, "Usage: %s message\n", *argv);
    exit(1);
  }

  if (pipe(pipefd) == -1) {     /* create the pipe */
    perror("Pipe");   
    exit(2);
  }
  /* Added code to create the return pipe. Created it
     here so that both the parent and child would share
     the same pipe. */
  if (pipe(piperet) == -1) {
    perror("Pipe");   
    exit(2);
  }

  switch (fork( )) {
  case -1:                      /* pipe error */
    perror("Fork");   
    exit(3);
  case 0:   			/* I am the child */
    close(pipefd[1]);
    /* added code to close the return pipe */
    close(piperet[0]);
    if (read(pipefd[0], message, 1024) != -1) {
      printf("Message received by child: [%s]\n", message);
      fflush(stdout);
      /* Added code to change the case of the message.
         This code iterates through the string, changing the
         case of every alphabetical character using toupper or
         tolower as appropriate until it reads a null string. */
      int i = 0;
      while (message[i] != '\0') {
        if (message[i] > 64 && message[i] < 91) {
          message[i] = tolower(message[i]);
        }
        else if (message[i] > 96 && message[i] < 123) {
          message[i] = toupper(message[i]);
        }
        i++;
      }
      /* Added code to send the new message back to the parent. */
      if(write(piperet[1], message, strlen(message)) != -1) {
        printf("Message sent by child   :  [%s]\n", message);
        fflush(stdout);
      }
      /* Added code to send an error message if the child can't
         write to the pipe. */
      else {
        perror("Write");
        exit(6);
      }
    } 
    else {
      perror("Read");   
      exit(4);
    }
    break;
  default: 			/* I am the parent */
    close(pipefd[0]);
    /* added code to close the return pipe */
    close(piperet[1]);
    if (write(pipefd[1], argv[1], strlen(argv[1])) != -1)  {
      printf("Message sent by parent   :  [%s]\n", argv[1]);
      fflush(stdout);
      /* Added code to read from the return pipe */
      if (read(piperet[0], message, 1024) != 1) {
        printf("Message received by parent: [%s]\n", message);
        fflush(stdout);
      }
    } 
    else {
      perror("Write");   
      exit(5);
    }
  }
  exit(0);
}
