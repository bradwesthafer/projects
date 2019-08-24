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

/* Internet domain, connection-oriented SERVER   */

/* Changed include statement to include tttlocal.h instead of local.h */
#include "tttlocal.h"
/* Added isValid to determine if the client's move is valid. Returns 1 + client's move. */
int isValid(char* board, char* buf) {
  /* Check to make sure the client followed the input format */
  if(buf[0] < 49 || buf[0] > 51 || buf[1] != 32 || buf[2] < 49 || buf[2] > 51) {
    return 0;
  }
  /* Determine what move the client picked */
  int move = 0;/*((buf[0] - 49) * 3) + (buf[2] - 49);*/
  if(buf[0] == 50) {
    move += 3;
  }
  else if(buf[0] == 51) {
    move += 6;
  }
  if(buf[2] == 50) {
    move += 1;
  }
  else if(buf[2] == 51) {
    move += 2;
  }
  /* Make sure the client's move is a valid move (the spot is unused) */
  if(board[move] != '_') {
    return 0;
  }
  /* At this point, the move is valid, so make return true by returning the move + 1 */
  return 1 + move;
}
/* Added isDone to determine if the game is over. */
int isDone(char* str) {
  /* Check each row and column */
  for(int i = 0; i < 3; i++) {
    /* Check Row i+1 */
    if(str[i * 3] == str[(i * 3) + 1] && str[i * 3] == str[(i * 3) + 2]) {
      if(str[i * 3] == 'C' || str[i * 3] == 'S') {
        return 1;
      }
    }
    /* Check Column i + 1 */
    if(str[i] == str[i + 3] && str[i] == str[i + 6]) {
      if(str[i] == 'C' || str[i] == 'S') {
        return 1;
      }
    }
  }
  /* Check the diagonals */
  if(str[0] == str[4] && str[0] == str[8]) {
    if(str[0] == 'C' || str[0] == 'S') {
      return 1;
    }
  }
  if(str[2] == str[4] && str[2] == str[6]) {
    if(str[2] == 'C' || str[2] == 'S') {
      return 1;
    }
  }
  /* Traverse the char[]. Return false if you find a single _ in the string. */
  for(int i = 0; i < 9; i++) {
    if(str[i] == '_') {
      return 0;
    }
  }
  /* If the entire board is filled up, return true. */
  return 1;
}
/* Added determineMove to determine the server's move */
int determineMove(char* board) {
  /* For the programmer's convenience, determine all past moves by the client, all 
  past moves by the server and all valid moves. Also count the number of valid moves. */
  int valid[9];
  int clientMoves[9];
  int serverMoves[9];
  int numValid = 0;
  for(int i = 0; i < 9; i++) {
    if(board[i] == '_') {
      valid[i] = 1;
      clientMoves[i] = 0;
      serverMoves[i] = 0;
      numValid++;
    }
    else if(board[i] == 'C') {
      valid[i] = 0;
      clientMoves[i] = 1;
      serverMoves[i] = 0;
    }
    else {
      valid[i] = 0;
      clientMoves[i] = 0;
      serverMoves[i] = 1;
    }
  }
  /* If only 1 move is valid, make the only valid move */
  if(numValid == 1) {
    for(int i = 0; i < 9; i++) {
      if(valid[i]) {
        return i;
      }
    }
  }
  /* If more than 1 move is valid, determine the best move */
  /* If row 2, column 2 is empty, move there based on a priori knowledge */
  if(valid[4]) {
    return 4;
  }
  /* For each valid move (except row 2, column 2), determine if you can win
  immediately if you make that move. */
  if(valid[0]) {
    if((serverMoves[1] && serverMoves[2]) ||
       (serverMoves[3] && serverMoves[6]) ||
       (serverMoves[4] && serverMoves[8])) {
      return 0;
    }
  }
  if(valid[1]) {
    if((serverMoves[0] && serverMoves[2]) ||
       (serverMoves[4] && serverMoves[7])) {
      return 1;
    }
  }
  if(valid[2]) {
    if((serverMoves[0] && serverMoves[1]) ||
       (serverMoves[5] && serverMoves[8]) ||
       (serverMoves[4] && serverMoves[6])) {
      return 2;
    }
  }
  if(valid[3]) {
    if((serverMoves[4] && serverMoves[5]) ||
       (serverMoves[0] && serverMoves[6])) {
      return 3;
    }
  }
  if(valid[5]) {
    if((serverMoves[3] && serverMoves[4]) ||
       (serverMoves[2] && serverMoves[8])) {
      return 5;
    }
  }
  if(valid[6]) {
    if((serverMoves[7] && serverMoves[8]) ||
       (serverMoves[0] && serverMoves[3]) ||
       (serverMoves[2] && serverMoves[4])) {
      return 6;
    }
  }
  if(valid[7]) {
    if((serverMoves[6] && serverMoves[8]) ||
       (serverMoves[1] && serverMoves[4])) {
      return 7;
    }
  }
  if(valid[8]) {
    if((serverMoves[6] && serverMoves[7]) ||
       (serverMoves[2] && serverMoves[5]) ||
       (serverMoves[0] && serverMoves[4])) {
      return 8;
    }
  }
  /* For each valid move (except row 2, column 2), determine if the client
  can win on their next turn if you don't make that move. */
  if(valid[0]) {
    if((clientMoves[1] && clientMoves[2]) ||
       (clientMoves[3] && clientMoves[6]) ||
       (clientMoves[4] && clientMoves[8])) {
      return 0;
    }
  }
  if(valid[1]) {
    if((clientMoves[0] && clientMoves[2]) ||
       (clientMoves[4] && clientMoves[7])) {
      return 1;
    }
  }
  if(valid[2]) {
    if((clientMoves[0] && clientMoves[1]) ||
       (clientMoves[5] && clientMoves[8]) ||
       (clientMoves[4] && clientMoves[6])) {
      return 2;
    }
  }
  if(valid[3]) {
    if((clientMoves[4] && clientMoves[5]) ||
       (clientMoves[0] && clientMoves[6])) {
      return 3;
    }
  }
  if(valid[5]) {
    if((clientMoves[3] && clientMoves[4]) ||
       (clientMoves[2] && clientMoves[8])) {
      return 5;
    }
  }
  if(valid[6]) {
    if((clientMoves[7] && clientMoves[8]) ||
       (clientMoves[0] && clientMoves[3]) ||
       (clientMoves[2] && clientMoves[4])) {
      return 6;
    }
  }
  if(valid[7]) {
    if((clientMoves[6] && clientMoves[8]) ||
       (clientMoves[1] && clientMoves[4])) {
      return 7;
    }
  }
  if(valid[8]) {
    if((clientMoves[6] && clientMoves[7]) ||
       (clientMoves[2] && clientMoves[5]) ||
       (clientMoves[0] && clientMoves[4])) {
      return 8;
    }
  }
  /* At this point, all remaining moves do not lead to an immediate win by
  the server or permit a win by the client on the next turn. So just generate
  a random move until it generates a valid move */
  while(1) {
    int move = rand() % 9;
    if(valid[move]) {
      return move;
    }
  }
}
int main ( void )  {
  /* Added code to call srand and pass it the time so that it seeds
  the random number generator once per run */
  srand(time(0));
  int            orig_sock,  /* Original socket descriptor in server */
                 new_sock,   /* New socket descriptor from connect   */
                 clnt_len;   /* Length of client address             */
  struct sockaddr_in
                 clnt_adr,   /* Internet address of client & server  */
                 serv_adr;
  int            len, i;     /* Misc counters, etc.                  */

  if ((orig_sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
    perror("generate error");
    exit(1);
  }
  memset ( &serv_adr, 0, sizeof(serv_adr) );  /* Clear structure     */
  serv_adr.sin_family       = AF_INET;        /* Set address type    */
  serv_adr.sin_addr.s_addr  = htonl(INADDR_ANY); /* Any interface    */
  serv_adr.sin_port         = htons(PORT);    /* Use our fake port   */
                                              /* Now BIND            */
  if (bind( orig_sock, (struct sockaddr *) &serv_adr,  
            sizeof(serv_adr)) <0) {
    perror("bind error");
  close(orig_sock);
  exit(2);
  }
  if (listen(orig_sock, 5) <0) {              /* LISTEN              */
    perror("listen error");
    exit(3);
  }
  do {
  clnt_len = sizeof(clnt_adr);
    if ((new_sock = accept ( orig_sock, (struct sockaddr *) &clnt_adr,
                             &clnt_len)) <0)  {      /* ACCEPT       */
    perror("accept error");
    close(orig_sock);
    exit(4);
  }
  if ( fork( ) == 0)  {                      /* In CHILD process     */
    /* Added a do-while loop to allow the client to choose to play
    another game with everything reset. Also created a variable to
    hold the client's answer. */
    char answer[BUFSIZ];
    do {
      /* Added a boolean to mark whether the game is done and a char[] to
      represent the board. */
      int done = 0;
      char board[] = "_________";
      /* replaced code setting len equal to length of input with code
      setting it equal to 10, which is the size of the board representation */
      len = 10;
      while ( (read(new_sock, buf, BUFSIZ)) > 0) {
        /* Removed code to change the case and replaced it with code to
        check if the move is valid (returning "not-valid" if it isn't),
        determine if the game is over (if it is, return the current board
        state), make the server's move check if the game ended after the server's
        move, return the state of the board and exit the loop if the game is over. */
        int move = isValid(board, buf);
        if(move) {
          board[move - 1] = 'C';
          done = isDone(board);
          if(!done) {
            int serverMove = determineMove(board);
            board[serverMove] = 'S';
            done = isDone(board);
          }
          /* Write either the board or "not-valid" back */
          write(new_sock, board, len);             /* write something back */
          /* changed condition to end the game if its done */
          if ( done ) break;
        }
        else {
          write(new_sock, "not-valid", len);
        }
      }
      read(new_sock, answer, BUFSIZ);
      if(toupper(answer[0]) == 'N') {
        write(new_sock, "Come back soon!\n", 16);
      }
      else {
        write(new_sock, "Rematch Accepted\n", 18);
      }
    }while(toupper(answer[0]) != 'N');
    close(new_sock);                         /* In CHILD process     */
    exit( 0);
  } else close(new_sock);                    /* In PARENT process    */
  } while( 1 );                              /* FOREVER              */
}
