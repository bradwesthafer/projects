// Brad Westhafer
// CMPSC 470
// Project 1

import static java.lang.Math.*;
import java.util.HashMap;
%%

%class Lex
%byaccj
%int

%{
    Object obj;
    public Lex(java.io.Reader r, Object obj) {
        this(r);
        this.obj = obj;
    }
    HashMap<String, Integer> ids = new HashMap<>();
%}

digit      = [0-9]
number     = {digit}+
real       = {number}(.{number})?(E[+-]?{number})?
letter_    = [A-Za-z_]
identifier = {letter_}({digit}|{letter_})*
comment    = "//" [^\n]* {newline}?
newline    = \n
whitespace = [ \t\r]+

%%
{comment}       { String lexeme = yytext(); System.out.print(lexeme); /*return Parser.COMMENT;   */}
"main"          { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.MAIN;      }
"if"            { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.IF;        }
"else"          { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.ELSE;      }
"while"         { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.WHILE;     }
"bool"          { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.BOOL;      }
"int"           { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.INT;       }
"float"         { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.FLOAT;     }
"print"         { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.PRINT;     }
"true"          { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.TRUE;      }
"false"         { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.FALSE;     }
"{"             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.BEGIN;     }
"}"             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.END;       }
"="             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.ASSIGN;    }
"=="            { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.EQ;        }
"!="            { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.NEQ;       }
"<"             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.LT;        }
">"             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.GT;        }
"<="            { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.LTE;       }
">="            { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.GTE;       }
"+"             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.PLUS;      }
"-"             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.MINUS;     }
"*"             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.MULT;      }
"/"             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.DIV;       }
"("             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.LPAREN;    }
")"             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.RPAREN;    }
"["             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.LBRACK;    }
"]"             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.RBRACK;    }
","             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.COMMA;     }
";"             { String lexeme = yytext(); System.out.print("<" + lexeme + ">"); return Parser.SEMI;      }
{number}        { String lexeme = yytext(); System.out.print("<num," + lexeme + ">"); return Parser.NUM;       }
{real}          { String lexeme = yytext(); System.out.print("<real," + lexeme + ">"); return Parser.REAL;      }
{identifier}    { 
                  String lexeme = yytext(); 
                  if(!ids.containsKey(lexeme)) {
                       System.out.print("<< add " + lexeme + " into symbol table at " + ids.size() + " >>");
                       ids.put(lexeme, ids.size());
                  }
                  System.out.print("<id," + ids.get(lexeme) + ">");
                  //System.out.print("<" + lexeme + ">");
                  return Parser.ID;
                }

{newline}       { String lexeme = yytext(); System.out.print(lexeme); /*return Parser.NEWLINE;   */}
{whitespace}    { String lexeme = yytext(); System.out.print(lexeme); /*return Parser.WHITESPACE;*/}
/* error handling */
[^]             {
                    System.err.println("Error: unsupported lexeme '"+yytext()+"'");
                    return -1;
                }
