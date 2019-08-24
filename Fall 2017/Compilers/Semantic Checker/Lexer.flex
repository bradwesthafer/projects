// Brad Westhafer
// CMPSC 470
// Project 3

%%

%class Lexer
%byaccj
%int

%{

  public Parser   yyparser;
  public int      linenum = 1;

  public Lexer(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

digit      = [0-9]
number     = {digit}+
real       = {number}(\.{number})?(E[+-]?{number})?
letter_    = [A-Za-z_]
identifier = {letter_}({digit}|{letter_})*
comment    = "//" [^\n]* {newline}?
newline    = \n
whitespace = [ \t\r]+
%%

"main"          {   yyparser.linenum = linenum; return Parser.MAIN    ; }
"("             {   yyparser.linenum = linenum; return Parser.LPAREN  ; }
")"             {   yyparser.linenum = linenum; return Parser.RPAREN  ; }
"{"             {   yyparser.linenum = linenum; return Parser.BEGIN   ; }
"}"             {   yyparser.linenum = linenum; return Parser.END     ; }
";"             {   yyparser.linenum = linenum; return Parser.SEMI    ; }
","             {   yyparser.linenum = linenum; return Parser.COMMA   ; }
"int"           {   yyparser.linenum = linenum; return Parser.INT     ; }
"float"         {   yyparser.linenum = linenum; return Parser.FLOAT   ; }
"bool"          {   yyparser.linenum = linenum; return Parser.BOOL    ; }
"print"         {   yyparser.linenum = linenum; return Parser.PRINT   ; }
"if"            {   yyparser.startLinenum = linenum; yyparser.linenum = linenum; return Parser.IF      ; }
"else"          {   yyparser.linenum = linenum; return Parser.ELSE    ; }
"while"         {   yyparser.startLinenum = linenum; yyparser.linenum = linenum; return Parser.WHILE   ; }
"return"        {   yyparser.linenum = linenum; return Parser.RETURN  ; }
"+"             {   yyparser.linenum = linenum; return Parser.PLUS    ; }
"-"             {   yyparser.linenum = linenum; return Parser.MINUS   ; }
"*"             {   yyparser.linenum = linenum; return Parser.MUL     ; }
"/"             {   yyparser.linenum = linenum; return Parser.DIV     ; }
"%"             {   yyparser.linenum = linenum; return Parser.MOD     ; }
"or"            {   yyparser.linenum = linenum; return Parser.OR      ; }
"and"           {   yyparser.linenum = linenum; return Parser.AND     ; }
"not"           {   yyparser.linenum = linenum; return Parser.NOT     ; }
"=="            {   yyparser.linenum = linenum; return Parser.EQ      ; }
"="             {   yyparser.linenum = linenum; return Parser.ASSIGN  ; }
"!="            {   yyparser.linenum = linenum; return Parser.NE      ; }
"<"             {   yyparser.linenum = linenum; return Parser.LT      ; }
">"             {   yyparser.linenum = linenum; return Parser.GT      ; }
"<="            {   yyparser.linenum = linenum; return Parser.LE      ; }
">="            {   yyparser.linenum = linenum; return Parser.GE      ; }
"true"          {   yyparser.linenum = linenum; return Parser.TRUE    ; }
"false"         {   yyparser.linenum = linenum; return Parser.FALSE   ; }
{number}        {   yyparser.linenum = linenum; yyparser.yylval = new ParserVal(yytext()); return Parser.NUM ; }
{real}          {   yyparser.linenum = linenum; yyparser.yylval = new ParserVal(yytext()); return Parser.REAL; }
{identifier}    {   yyparser.linenum = linenum; yyparser.yylval = new ParserVal(yytext()); return Parser.ID  ; }
{comment}       {   /* skip */}
{newline}       {   linenum++; /* skip */}
{whitespace}    {   /* skip */}

\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    {} /*{ System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }*/
