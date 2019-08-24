/* Brad Westhafer
   COMP 512
   Homework #3
   Problem 3 b */

/* Internal Game Board Representation: char[] of 9 chars + null string
C = client
S = server
_ = unused
0-2 = row 1/column 1, row 1/column 2, row 1/column 3
3-5 = row 2/column 1, row 2/column 2, row 2/column 3
6-8 = row 3/column 1, row 2/column 2, row 3/column 3  */

/* Internet domain, connection-oriented CLIENT   */

/* Changed include statement to include tttlocal.h instead of local.h */
#include "tttlocal.h"
/* Added isDone to determine if the game is over. If it is, print a message
indicating the result of the game. */
int isDone(char* str) {
  int done = 0;
  int win = 0;
  int lose = 0;
  /* Check each row and column */
  for(int i = 0; i < 3; i++) {
    /* Check Row i+1 */
    if(str[i * 3] == str[(i * 3) + 1] && str[i * 3] == str[(i * 3) + 2]) {
      if(str[i * 3] == 'C') {
        done = 1;
        win = 1;
      }
      else if(str[i * 3] == 'S') {
        done = 1;
        lose = 1;
      }
    }
    /* Check Column i + 1 */
    if(str[i] == str[i + 3] && str[i] == str[i + 6]) {
      if(str[i] == 'C') {
        done = 1;
        win = 1;
      }
      if(str[i] == 'S') {
        done = 1;
        lose = 1;
      }
    }
  }
  /* Check the diagonals */
  if(str[0] == str[4] && str[0] == str[8]) {
    if(str[0] == 'C') {
      done = 1;
      win = 1;
    }
    if(str[0] == 'S') {
      done = 1;
      lose = 1;
    }
  }
  if(str[2] == str[4] && str[2] == str[6]) {
    if(str[2] == 'C') {
      done = 1;
      win = 1;
    }
    if(str[2] == 'S') {
      done = 1;
      lose = 1;
    }
  }
  /* If done, print a message and return true */
  if(done) {
    if(win) {
      printf("You win!\n");
    }
    else if(lose) {
      printf("You lose.\n");
    }
    return done;
  }
  /* Traverse the char[]. Return false if you find a single _ in the string. */
  for(int i = 0; i < 9; i++) {
    if(str[i] == '_') {
      return 0;
    }
  }
  /* If the entire board is filled up, print a message and return true. */
  printf("This game is a draw.\n");
  return 1;
}
int main ( int argc, char *argv[]) {
  int             orig_sock, /* Original socket descriptor in client */
                  len;       /* Length of server address             */
  struct sockaddr_in
                   serv_adr; /* Internet address of server           */
  struct hostent    *host;   /* The host (server)                    */

  if (argc != 2 ) {          /* Expect name of host on command line  */
    fprintf(stderr, "usage: %s server\n", argv[0]);
    exit(1);
  }
  host = gethostbyname(argv[1]);              /* Get host info       */
  if (host == (struct hostent *) NULL ) {
    perror("gethostbyname ");
    exit(2);
  }
  memset(&serv_adr, 0, sizeof( serv_adr));    /* Clear the structure */
  serv_adr.sin_family = AF_INET;              /* Set address type    */
  memcpy(&serv_adr.sin_addr, host->h_addr, host->h_length);  /* adr  */
  serv_adr.sin_port  = htons( PORT );         /* Use our fake port   */

  if ((orig_sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) { /* SOCKET */
    perror("generate error");
    exit(3);
  }

/* CONNECT                                                           */
  if (connect( orig_sock, (struct sockaddr *)&serv_adr,
              sizeof(serv_adr)) < 0) {
    perror("connect error");
    exit(4);
  }
  /* Added a welcome to Tic Tac Toe message */
  printf("Welcome to Tic Tac Toe!\nYou have the first move.\n");
  /* Added a char[] called answer that stores the user's response to a "Do you want
  to play again?" prompt. */
  char answer[BUFSIZ];
  /* Added outer do-while loop to allow the user to play again. */
  do {
    /* Added a boolean variable to mark whether the game is done */
    int done = 0;
    do {
      /* Changed the prompt to clearly state what the user should enter */
      write(fileno(stdout), 
      "Enter a row (1, 2 or 3) and column (1, 2 or 3), separated by a space > ", 72);
      if ((len=read(fileno(stdin), buf, BUFSIZ) ) > 0) { /* Get input   */
        write(orig_sock, buf, len);                      /* Write to sck*/
        /* Server returns a char[] in the format described above for
        internal board representation. */
        /* Changed length to 10 to reflect the size of the board
        representation or in-valid message*/
        if ((len=read(orig_sock, buf, 10)) >0) {        /* If returned */
          /* Replaced code to display return value with code printing out
          the state of the board */
          if(buf[0] == 'n') {
            write(fileno(stdout), "Invalid Move.\n", 15);
          }
          else {
            char output[13];
            output[12] = '\0';
            output[3] = '\n';
            output[7] = '\n';
            output[11] = '\n';
            for(int i = 0; i < 3; i++) {
              output [(i * 3) + i] = buf[(i * 3)];
              output [(i * 3) + i + 1] = buf[(i * 3) + 1];
              output [(i * 3) + i + 2] = buf[(i * 3) + 2];
            }
            write(fileno(stdout), output, 13);
            /* Added code to determine if the game is done */
            done = isDone(buf);
          }
        }
      }
      /* Changed while loop condition to continue while the game is not done */
    } while(done == 0);
    /* Added code to give the user a chance to play again */
    write(fileno(stdout), "Do you want to play again? (Y/N) > ", 36);
    len = read(fileno(stdin), answer, BUFSIZ);
    while(toupper(answer[0]) != 'Y' && toupper(answer[0]) != 'N') {
      write(fileno(stdout), "Invalid input. Enter Y for yes or N for no. > ", 47);
      len = read(fileno(stdin), answer, BUFSIZ);
    }
    /* Send the user's answer to the server */
    write(orig_sock, answer, len);
    /* Read the server's response then display it */
    len = read(orig_sock, buf, 18);
    write(fileno(stdout), buf, len);
  } while(toupper(answer[0]) != 'N');
  close(orig_sock);
  exit(0);
} 
