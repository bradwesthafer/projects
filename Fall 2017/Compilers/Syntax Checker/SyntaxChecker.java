// Brad Westhafer
// CMPSC 470
// Project 2

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SyntaxChecker {
    public static final int NoSuchLexeme = -1;
    public static final int RETURN     = 11;
    public static final int MAIN       = 12;
    public static final int BEGIN      = 13;
    public static final int END        = 14;
    public static final int PRINT      = 15;
    public static final int LPAREN     = 16;
    public static final int RPAREN     = 17;
    public static final int ASSIGN     = 18;
    public static final int PLUS       = 19;
    public static final int MINUS      = 20;
    public static final int MULT       = 21;
    public static final int DIV        = 22;
    public static final int MOD        = 23;
    public static final int SEMI       = 24;
    public static final int NUM        = 25;
    public static final int ID         = 26;
    public static final int REAL       = 27;
    public static final int IF         = 28;
    public static final int WHILE      = 29;
    public static final int ELSE       = 30;
    public static final int INT        = 31;
    public static final int FLOAT      = 32;
    public static final int TRUE       = 33;
    public static final int FALSE      = 34;
    public static final int EQ         = 35;
    public static final int NEQ        = 36;
    public static final int LT         = 37;
    public static final int GT         = 38;
    public static final int LTE        = 39;
    public static final int GTE        = 40;
    int headLoc;
    java.io.Reader r;
	ArrayList<Integer> tokens = new ArrayList<>();
	public static void main(String[] args) throws IOException {
	    if(args.length <= 0) {
	        System.err.println("Provide a file name as an argument.");
	        System.exit(-1);
        }
        java.io.Reader r = new java.io.FileReader(args[0]);
	    SyntaxChecker checker = new SyntaxChecker(r);
	    boolean valid = checker.checkSyntax();
	    if(valid) {
	        System.out.println("Success");
        }
        else {
	        System.out.println("Error: Syntax error");
        }
	}
	public SyntaxChecker(java.io.Reader r) {
	    this.r = r;
    }
    public boolean checkBounds(int headLoc) {
	    if(tokens.size() <= headLoc) {
	        return false;
        }
        else if(headLoc < 0) {
	        return false;
        }
        else {
	        return true;
        }
    }
    public Boolean checkSyntax() throws IOException {
	    readTokens();
	    headLoc = 0;

        // program -> main_decl
        // main_decl -> "main" "(" ")" compound_stmt
        // Check for main ()
        // Return false if not found as first 3 tokens
        if(!checkBounds(headLoc)) {
            return false;
        }
        if(tokens.get(headLoc) != MAIN) {
            return false;
        }
        headLoc++;
        if(!checkBounds(headLoc)) {
            return false;
        }
        if(tokens.get(headLoc) != LPAREN) {
            return false;
        }
        headLoc++;
        if(!checkBounds(headLoc)) {
            return false;
        }
        if(tokens.get(headLoc) != RPAREN) {
            return false;
        }
        headLoc++;
        // main_decl -> compound_stmt
        return compound_stmt() && !checkBounds(headLoc);
    }
    public boolean compound_stmt() {
        int initialHeadLoc = headLoc;
        // compound_stmt -> "{" local_decls stmt_list "}"
        // look for {
        // return false if not found
        if(!checkBounds(headLoc)) {
            return false;
        }
        if(tokens.get(headLoc) != BEGIN) {
            return false;
        }
        headLoc++;
        // compound_stmt -> local_decls stmt_list "}"
        local_decls();
        // compound_stmt -> stmt_list "}"
        // stmt_list -> stmt stmt_list | epsilon
        boolean stmt_list_not_epsilon = true;
        while(stmt_list_not_epsilon) {
            stmt_list_not_epsilon = stmt();
        }
        // compound_stmt -> "}"
        if(!checkBounds(headLoc) || tokens.get(headLoc) != END) {
            headLoc = initialHeadLoc;
            return false;
        }
        else {
            headLoc++;
            return true;
        }
    }
    public void local_decls() {
        // local_decls -> Local_decl local_decls | epsilon
        boolean local_decls_not_epsilon = true;
        while(local_decls_not_epsilon) {
            // local_decl -> type_spec ID ";"
            // type_spec -> "int" | "float"
            int initialHeadLoc = headLoc;
            if(!checkBounds(headLoc)) {
                headLoc = initialHeadLoc;
                return;
            }
            if(tokens.get(headLoc) == INT || tokens.get(headLoc) == FLOAT) {
                headLoc++;
                // local_decl -> ID ";"
                if(!checkBounds(headLoc)) {
                    headLoc = initialHeadLoc;
                    return;
                }
                if(tokens.get(headLoc) == ID) {
                    headLoc++;
                    // local_decl -> ";"
                    if(!checkBounds(headLoc)) {
                        headLoc = initialHeadLoc;
                        return;
                    }
                    if(tokens.get(headLoc) != SEMI) {
                        // local_decls -> epsilon
                        // reset read head
                        headLoc = initialHeadLoc;
                        local_decls_not_epsilon = false;
                        return;
                    }
                    headLoc++;
                    // else local_decls -> local_decls
                    // repeat loop
                }
                else {
                    // local_decls -> epsilon
                    // reset read head
                    headLoc = initialHeadLoc;
                    local_decls_not_epsilon = false;
                }
            }
            else {
                // local_decls -> epsilon
                // reset read head
                headLoc = initialHeadLoc;
                local_decls_not_epsilon = false;
            }
        }
    }
    public boolean stmt() {
        // stmt -> expr_stmt | print_stmt | compound_stmt | if_stmt | while_stmt | return_stmt
        int initialHeadLoc = headLoc;
        // expr_stmt -> id "=" expr ";" | ";"
        if(!checkBounds(headLoc)) {
            return false;
        }
        else if(tokens.get(headLoc) == ID) {
            //expr_stmt -> "=" expr ";"
            headLoc++;
            if(!checkBounds(headLoc)) {
                headLoc = initialHeadLoc;
                return false;
            }
            if(tokens.get(headLoc) == ASSIGN) {
                //expr_stmt -> expr ";"
                headLoc++;
                if(expr()) {
                    // expr_stmt -> ";"
                    if(!checkBounds(headLoc)) {
                        headLoc = initialHeadLoc;
                        return false;
                    }
                    if(tokens.get(headLoc) != SEMI) {
                        headLoc = initialHeadLoc;
                        return false;
                    }
                    else {
                        headLoc++;
                        return true;
                    }
                }
                else {
                    headLoc = initialHeadLoc;
                    return false;
                }
            }
        }
        else if(tokens.get(headLoc) == SEMI) {
            headLoc++;
            return true;
        }
        //print_stmt -> "print" expr ";"
        else if(tokens.get(headLoc) == PRINT) {
            //print_stmt -> expr ";"
            headLoc++;
            if(expr()) {
                //print_stmt -> ";"
                if(!checkBounds(headLoc)) {
                    headLoc = initialHeadLoc;
                    return false;
                }
                else if(tokens.get(headLoc) == SEMI) {
                    headLoc++;
                    return true;
                }
                else {
                    headLoc = initialHeadLoc;
                    return false;
                }
            }
            else {
                headLoc = initialHeadLoc;
                return false;
            }
        }
        else if(compound_stmt()) {
            return true;
        }
        //if_stmt -> "if" "(" bexpr ")" stmt "else" stmt
        else if(tokens.get(headLoc) == IF) {
            headLoc++;
            //if_stmt -> "(" bexpr ")" stmt "else" stmt
            if(!checkBounds(headLoc)) {
                headLoc = initialHeadLoc;
                return false;
            }
            else if(tokens.get(headLoc) == LPAREN) {
                headLoc++;
                //if_stmt -> bexpr ")" stmt "else" stmt
                if(bexpr()) {
                    //if_stmt -> ")" stmt "else" stmt
                    if(!checkBounds(headLoc)) {
                        headLoc = initialHeadLoc;
                        return false;
                    }
                    else if(tokens.get(headLoc) == RPAREN) {
                        headLoc++;
                        //if_stmt -> stmt "else" stmt
                        if(stmt()) {
                            //if_stmt -> "else" stmt
                            if(!checkBounds(headLoc)) {
                                headLoc = initialHeadLoc;
                                return false;
                            }
                            else if(tokens.get(headLoc) == ELSE) {
                                headLoc++;
                                if(stmt()) {
                                    return true;
                                }
                                else {
                                    headLoc = initialHeadLoc;
                                    return false;
                                }
                            }
                            else {
                                headLoc = initialHeadLoc;
                                return false;
                            }
                        }
                        else {
                            headLoc = initialHeadLoc;
                            return false;
                        }
                    }
                    else {
                        headLoc = initialHeadLoc;
                        return false;
                    }
                }
                else {
                    headLoc = initialHeadLoc;
                    return false;
                }
            }
            else {
                headLoc = initialHeadLoc;
                return false;
            }
        }
        //while_stmt -> "while" "(" bexpr ")" stmt
        else if(tokens.get(headLoc) == WHILE) {
            headLoc++;
            //while_stmt -> "(" bexpr ")" stmt
            if(!checkBounds(headLoc)) {
                headLoc = initialHeadLoc;
                return false;
            }
            else if(tokens.get(headLoc) == LPAREN) {
                headLoc++;
                //while_stmt -> bexpr ")" stmt
                if(bexpr()) {
                    //while_stmt -> ")" stmt
                    if(!checkBounds(headLoc)) {
                        headLoc = initialHeadLoc;
                        return false;
                    }
                    else if(tokens.get(headLoc) == RPAREN) {
                        headLoc++;
                        //while_stmt -> stmt
                        if(stmt()) {
                            return true;
                        }
                        else {
                            headLoc = initialHeadLoc;
                            return false;
                        }
                    }
                }
                else {
                    headLoc = initialHeadLoc;
                    return false;
                }
            }
            else {
                headLoc = initialHeadLoc;
                return false;
            }
        }
        //return_stmt -> "return" ";" | "return" expr ";"
        else if(tokens.get(headLoc) == RETURN) {
            headLoc++;
            //return_stmt -> ";" | expr ";"
            if(!checkBounds(headLoc)) {
                headLoc = initialHeadLoc;
                return false;
            }
            else if(tokens.get(headLoc) == SEMI) {
                headLoc++;
                return true;
            }
            else if(expr()) {
                //return_stmt -> ";"
                if(!checkBounds(headLoc)) {
                    headLoc = initialHeadLoc;
                    return false;
                }
                else if(tokens.get(headLoc) == SEMI) {
                    headLoc++;
                    return true;
                }
                else {
                    headLoc = initialHeadLoc;
                    return false;
                }
            }
            else {
                headLoc = initialHeadLoc;
                return false;
            }
        }
        headLoc = initialHeadLoc;
        return false;
    }
    public boolean bexpr() {
	    //bexpr -> "true" | "false" | expr "==" expr | expr "!=" expr | expr "<" expr | expr ">" expr | expr "<=" expr | expr ">=" expr
        int initialHeadLoc = headLoc;
        if(!checkBounds(headLoc)) {
            headLoc = initialHeadLoc;
            return false;
        }
        else if(tokens.get(headLoc) == TRUE || tokens.get(headLoc) == FALSE) {
            headLoc++;
            return true;
        }
        else if(expr()) {
            //bexpr -> "==" expr | "!=" expr | "<" expr | ">" expr | "<=" expr| ">=" expr
            if(!checkBounds(headLoc)) {
                headLoc = initialHeadLoc;
                return false;
            }
            else if(tokens.get(headLoc) == EQ || tokens.get(headLoc) == NEQ || tokens.get(headLoc) == LT || tokens.get(headLoc) == GT || tokens.get(headLoc) == LTE || tokens.get(headLoc) == GTE) {
                headLoc++;
                //bexpr -> expr
                if(expr()) {
                    return true;
                }
                else {
                    headLoc = initialHeadLoc;
                    return false;
                }
            }
            else {
                headLoc = initialHeadLoc;
                return false;
            }
        }
        else {
            headLoc = initialHeadLoc;
            return false;
        }
    }
    public boolean expr() {
        //expr -> term expr'
        if(term()) {
            // expr -> expr'
            return expr_prime();
        }
        else {
            return false;
        }
    }
    public boolean expr_prime() {
	    //expr' -> "+" term expr' | "-" term expr' | epsilon
        int initialHeadLoc = headLoc;
        if(!checkBounds(headLoc)) {
            //expr' -> epsilon
            headLoc = initialHeadLoc;
            return true;
        }
        else if(tokens.get(headLoc) == PLUS || tokens.get(headLoc) == MINUS) {
            headLoc++;
            //expr' -> term expr'
            if(term()) {
                //expr' -> expr'
                return expr_prime();
            }
            else {
                //expr' -> epsilon
                headLoc = initialHeadLoc;
                return true;
            }
        }
        else {
            //expr' -> epsilon
            headLoc = initialHeadLoc;
            return true;
        }
    }
    public boolean term() {
	    // term -> factor term'
        if(factor()) {
            //term -> term'
            return term_prime();
        }
        else {
            return false;
        }
    }
    public boolean term_prime() {
	    // term' -> "*" factor term' | "/" factor term' | "%" factor term' | epsilon
        int initialHeadLoc = headLoc;
        if(!checkBounds(headLoc)) {
            // term' -> epsilon
            headLoc = initialHeadLoc;
            return true;
        }
        else if(tokens.get(headLoc) == MULT || tokens.get(headLoc) == DIV || tokens.get(headLoc) == MOD) {
            // term' -> factor term'
            headLoc++;
            if(factor()) {
                // term' -> term'
                return term_prime();
            }
            else {
                // term' -> epsilon
                headLoc = initialHeadLoc;
                return true;
            }
        }
        else {
            // term' -> epsilon
            headLoc = initialHeadLoc;
            return true;
        }
    }
    public boolean factor() {
        int initialHeadLoc = headLoc;
        // factor -> "(" expr ")" | ID | NUM| REAL
        if(!checkBounds(headLoc)) {
            headLoc = initialHeadLoc;
            return false;
        }
        else if(tokens.get(headLoc) == LPAREN) {
            //factor -> expr ")"
            headLoc++;
            if(expr()) {
                //factor -> ")"
                if(!checkBounds(headLoc)) {
                    headLoc = initialHeadLoc;
                    return false;
                }
                else if(tokens.get(headLoc) == RPAREN) {
                    headLoc++;
                    return true;
                }
                else {
                    headLoc = initialHeadLoc;
                    return false;
                }
            }
        }
        else if(tokens.get(headLoc) == ID || tokens.get(headLoc) == NUM || tokens.get(headLoc) == REAL) {
            headLoc++;
            return true;
        }
        headLoc = initialHeadLoc;
        return false;
    }
    public void readTokens() throws IOException {
        Lex lexer = new Lex(r, this);
        int nextLexeme = lexer.yylex();
        while(nextLexeme != 0) {
            tokens.add(nextLexeme);
//            System.err.println(lexer.yytext());
            nextLexeme = lexer.yylex();
        }
	}
}
