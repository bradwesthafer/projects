// Brad Westhafer
// CMPSC 470
// Project 2

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
{comment}       { String lexeme = yytext(); /*return SyntaxChecker.COMMENT;   */}//
"main"          { String lexeme = yytext(); return SyntaxChecker.MAIN;      }//
"if"            { String lexeme = yytext(); return SyntaxChecker.IF;        }//
"else"          { String lexeme = yytext(); return SyntaxChecker.ELSE;      }//
"while"         { String lexeme = yytext(); return SyntaxChecker.WHILE;     }//
//"bool"          { String lexeme = yytext(); return SyntaxChecker.BOOL;      }
"int"           { String lexeme = yytext(); return SyntaxChecker.INT;       }//
"float"         { String lexeme = yytext(); return SyntaxChecker.FLOAT;     }//
"print"         { String lexeme = yytext(); return SyntaxChecker.PRINT;     }//
"true"          { String lexeme = yytext(); return SyntaxChecker.TRUE;      }//
"false"         { String lexeme = yytext(); return SyntaxChecker.FALSE;     }//
"return"        { String lexeme = yytext(); return SyntaxChecker.RETURN;    }//
"{"             { String lexeme = yytext(); return SyntaxChecker.BEGIN;     }//
"}"             { String lexeme = yytext(); return SyntaxChecker.END;       }//
"="             { String lexeme = yytext(); return SyntaxChecker.ASSIGN;    }//
"=="            { String lexeme = yytext(); return SyntaxChecker.EQ;        }//
"!="            { String lexeme = yytext(); return SyntaxChecker.NEQ;       }//
"<"             { String lexeme = yytext(); return SyntaxChecker.LT;        }//
">"             { String lexeme = yytext(); return SyntaxChecker.GT;        }//
"<="            { String lexeme = yytext(); return SyntaxChecker.LTE;       }//
">="            { String lexeme = yytext(); return SyntaxChecker.GTE;       }//
"+"             { String lexeme = yytext(); return SyntaxChecker.PLUS;      }//
"-"             { String lexeme = yytext(); return SyntaxChecker.MINUS;     }//
"*"             { String lexeme = yytext(); return SyntaxChecker.MULT;      }//
"/"             { String lexeme = yytext(); return SyntaxChecker.DIV;       }//
"%"             { String lexeme = yytext(); return SyntaxChecker.MOD;       }//
"("             { String lexeme = yytext(); return SyntaxChecker.LPAREN;    }//
")"             { String lexeme = yytext(); return SyntaxChecker.RPAREN;    }//
//"["             { String lexeme = yytext(); return SyntaxChecker.LBRACK;    }
//"]"             { String lexeme = yytext(); return SyntaxChecker.RBRACK;    }
//","             { String lexeme = yytext(); return SyntaxChecker.COMMA;     }
";"             { String lexeme = yytext(); return SyntaxChecker.SEMI;      }//
{number}        { String lexeme = yytext(); return SyntaxChecker.NUM;       }//
{real}          { String lexeme = yytext(); return SyntaxChecker.REAL;      }//
{identifier}    { String lexeme = yytext(); return SyntaxChecker.ID;        }//
{newline}       { String lexeme = yytext(); /*return SyntaxChecker.NEWLINE;   */}//
{whitespace}    { String lexeme = yytext(); /*return SyntaxChecker.WHITESPACE;*/}//
/* error handling */
[^]             { return SyntaxChecker.NoSuchLexeme; }
