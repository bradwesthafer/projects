%{
import java.io.*;
%}

%left PLUS
%left MUL

%token <sval> NUM ID REAL FLOAT BOOL INT
%token MAIN PRINT BEGIN END
%token LPAREN RPAREN PLUS MINUS MUL DIV MOD SEMI INT
%token GT LT GE LE NE EQ
%token LPAREN RPAREN IF ELSE WHILE RETURN COMMA
%token TRUE FALSE ASSIGN

%type <obj> program type_spec main_decl
%type <obj> local_decls local_decl
%type <obj> stmt_list stmt print_stmt compound_stmt
%type <obj> expr
%type <obj> decl_list decl fun_decl params param_list param
%type <obj> expr_stmt while_stmt if_stmt return_stmt
%type<obj> arg_list args

%right ASSIGN
%right NOT
%left OR
%left AND
%left EQ
%left NE
%left GT
%left LT
%left LE
%left GE
%left MINUS
%left DIV
%left MOD

%%

/*
program       -> decl_list main_decl
decl_list     -> decl_list decl | eps
decl          -> fun_decl
type_spec     -> INT | FLOAT | BOOL
main_decl     -> MAIN LPAREN RPAREN compound_stmt
fun_decl      -> type_spec ID LPAREN params RPAREN SEMI
params        -> param_list | eps
param_list    -> param_list COMMA param | param
param         -> type_spec
stmt_list     -> stmt_list stmt | eps
stmt          -> expr_stmt | print_stmt | compound_stmt | if_stmt | while_stmt | return_stmt
print_stmt    -> PRINT expr SEMI
expr_stmt     -> ID ASSIGN expr SEMI | SEMI
while_stmt    -> WHILE LPAREN expr RPAREN stmt
compound_stmt -> BEGIN local_decls stmt_list END
local_decls   -> local_decls local_decl | eps
local_decl    -> type_spec ID SEMI
if_stmt       -> IF LPAREN expr RPAREN stmt ELSE stmt
return_stmt   -> RETURN SEMI
arg_list      -> arg_list COMMA expr | expr
args          -> arg_list | eps
expr          -> expr OR expr | expr AND expr | NOT expr
               | expr EQ expr | expr NE expr | expr LE expr | expr GT expr | expr GE expr | expr LT expr
               | expr PLUS expr | expr MINUS expr | expr MUL expr | expr DIV expr | expr MOD expr
               | LPAREN expr RPAREN | ID | ID LPAREN args RPAREN
               | NUM | REAL | TRUE | FALSE
*/

program       : decl_list main_decl              { $$ = CallProgram($1);                  }
              ;                                  /*{ $$ = CallProgram($1);                  }*/
decl_list     : decl_list decl                   { $$ = CallDeclListRec($1, $2);          }
              |                                  { $$ = CallDeclListEps();                }
              ;
decl          : fun_decl                         { $$ = CallDecl($1);                     }
              ;
type_spec     : INT                              { $$ = CallTypeSpecInt($1);              }
              | FLOAT                            { $$ = CallTypeSpecFloat($1);            }
              | BOOL                             { $$ = CallTypeSpecBool($1);             }
              ;
main_decl     : MAIN LPAREN RPAREN compound_stmt { $$ = CallMain($4);                     }
              ;
fun_decl      : type_spec ID LPAREN params RPAREN SEMI { $$ = CallFunDecl($1, $2, $4);        }
              ;
params        : param_list                       { $$ = CallParamsLst($1);                }
              |                                  { $$ = CallParamsEps();                  }
              ;
param_list    : param_list COMMA param           { $$ = CallParamListRec($1, $3);         }
              | param                            { $$ = CallParamListTerm($1);            }
              ;
param         : type_spec                        { $$ = CallParam($1);                    }
              ;
stmt_list     : stmt_list stmt                   { $$ = CallStmtListRec($1, $2);          }
              |                                  { $$ = CallStmtListEps();                }
              ;
stmt          : expr_stmt                        { $$ = CallStmtExpr($1);                 }
              | print_stmt                       { $$ = CallStmtPrint($1);                }
              | compound_stmt                    { $$ = CallStmtCompound($1);             }
              | if_stmt                          { $$ = CallStmtIf($1);                   }
              | while_stmt                       { $$ = CallStmtWhile($1);                }
              | return_stmt                      { $$ = CallStmtReturn($1);               }
              ;
print_stmt    : PRINT expr SEMI                  { $$ = CallPrintExpr($2);                }
              ;
expr_stmt     : ID ASSIGN expr SEMI              { $$ = CallExprStmtExpr($1, $3);             }
              | SEMI                             { $$ = CallExprStmtSemi();               }
              ;
while_stmt    : WHILE LPAREN expr RPAREN stmt    { $$ = CallWhileStmt($3, $5);            }
              ;
compound_stmt : BEGIN                            { $$ = CallCompoundStmtBegin();          } /* (1) */
                      local_decls stmt_list END  { $$ = CallCompoundStmtRest($2, $3, $4); } /* $2 represents result from (1), $3 represents local_decls, and $4 represents stmt_list */
              ;
local_decls   : local_decls local_decl           { $$ = CallLocalDeclsRec($1, $2);        }
              |                                  { $$ = CallLocalDeclsEps();              }
              ;
local_decl    : type_spec ID SEMI                { $$ = CallLocalDecl($1, $2);            }
              ;
/*type_spec     : INT                              { $$ = CallTypeInt();                    }
              ;*/
if_stmt       : IF LPAREN expr RPAREN stmt ELSE stmt { $$ = CallIfStmt($3, $5, $7);        }
              ;
return_stmt   : RETURN SEMI                      { $$ = CallReturnStmt();                 }
              ;
arg_list      : arg_list COMMA expr              { $$ = CallArgListList($1, $3);          }
              | expr                             { $$ = CallArgListExpr($1);              }
              ;
args          : arg_list                         { $$ = CallArgsList($1);                 }
              |                                  { $$ = CallArgsEps();                    }
              ;
expr          : expr OR expr                     { $$ = CallExprOr($1, $3);               }
              | expr AND expr                    { $$ = CallExprAnd($1, $3);              }
              | NOT expr                         { $$ = CallExprNot($2);                  }
              | expr EQ expr                     { $$ = CallExprEq($1, $3);               }
              | expr NE expr                     { $$ = CallExprNe($1, $3);               }
              | expr LE expr                     { $$ = CallExprLe($1, $3);               }
              | expr GT expr                     { $$ = CallExprGt($1, $3);               }
              | expr GE expr                     { $$ = CallExprGe($1, $3);               }
              | expr LT expr                     { $$ = CallExprLt($1, $3);               }
              | expr PLUS expr                   { $$ = CallExprAdd($1, $3);              }
              | expr MINUS expr                  { $$ = CallExprMinus($1, $3);              }
              | expr MUL expr                    { $$ = CallExprMul($1, $3);              }
              | expr DIV expr                    { $$ = CallExprDiv($1, $3);              }
              | expr MOD expr                    { $$ = CallExprMod($1, $3);              }
              | LPAREN expr RPAREN               { $$ = CallExprParen($2);                }
              | ID                               { $$ = CallFactorId($1);                 }
              | ID LPAREN args RPAREN            { $$ = CallFactorIdArgs($1, $3);             }
              | NUM                              { $$ = CallFactorNum($1);                }
              | REAL                             { $$ = CallFactorReal($1);               }
              | TRUE                             { $$ = CallFactorTrue();               }
              | FALSE                            { $$ = CallFactorFalse();              }
              ;

%%
private Lexer lexer;

private int yylex () {
    int yyl_return = -1;
    try {
        yylval = new ParserVal(0);
        yyl_return = lexer.yylex();
    }
    catch (IOException e) {
        System.err.println("IO error :"+e);
    }
    return yyl_return;
}

public void yyerror (String error) {
    System.out.println("Error: " + error);
}

public Parser(Reader r) {
    lexer = new Lexer(r, this);
}
