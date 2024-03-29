/* Brad Westhafer
   COMP 512
   Homework #3
   Problem 3 a */

/* Internet domain, connection-oriented SERVER   */

#include "local.h"

/*Added a function to reverse the string since strrev is not part of GCC's library */
void strrev(char* str) {
  char retstr[BUFSIZ];
  int start = 0;
  while(str[start] != '\n') {
    start++;
  }
  start--;
  int i = 0;
  for(int j = start; j >= 0; j--) {
    retstr[i] = str[j];
    i++;
  }
  for(int j = i - 1; j >= 0; j--) {
    str[j] = retstr[j];
  }
}

int main ( void )  {
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
    while ( (len=read(new_sock, buf, BUFSIZ)) > 0) {
      for (i=0; i<len; ++i)                  /* Change the case      */
        buf[i] = toupper(buf[i]);
      /*added code to call strrev to reverse the string*/
      strrev(buf);
      write(new_sock, buf, len);             /* write it back        */
      if ( buf[0] == '.' ) break;            /* are we done yet?     */
    }
    close(new_sock);                         /* In CHILD process     */
    exit( 0);
  } else close(new_sock);                    /* In PARENT process    */
  } while( 1 );                              /* FOREVER              */
}
