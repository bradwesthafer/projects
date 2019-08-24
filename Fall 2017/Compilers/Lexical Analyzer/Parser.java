// Brad Westhafer
// CMPSC 470
// Project 1

public class Parser
{
    public static final int WHITESPACE = 10;
    public static final int NEWLINE    = 11;
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
    public static final int SEMI       = 23;
    public static final int NUM        = 24;
    public static final int ID         = 25;
    public static final int REAL       = 26;
    public static final int IF         = 27;
    public static final int WHILE      = 28;
    public static final int ELSE       = 29;
    public static final int INT        = 30;
    public static final int BOOL       = 31;
    public static final int FLOAT      = 32;
    public static final int TRUE       = 33;
    public static final int FALSE      = 34;
    public static final int EQ         = 35;
    public static final int NEQ        = 36;
    public static final int LT         = 37;
    public static final int GT         = 38;
    public static final int LTE        = 39;
    public static final int GTE        = 40;
    public static final int LBRACK     = 41;
    public static final int RBRACK     = 42;
    public static final int COMMA      = 43;
    public static final int COMMENT    = 44;

    Compiler _compiler;
    public java.util.HashMap<String, Integer> SymbolTable()
    {
        return _compiler._symtbl;
    }

    public Parser(java.io.Reader r, Compiler compiler) throws java.io.IOException
    {
        this._compiler = compiler;
        this._lex      = new Lex(r, this);
        this._lookhead = null;
    }

    Lex     _lex;
    Integer _lookhead;
    public ParserVal yylval = new ParserVal();
    public int LookAhead() throws java.io.IOException
    {
        if (_lookhead == null)
            Advance();
        return _lookhead;
    }
    public void Advance() throws java.io.IOException
    {
        if(_lookhead != null && _lookhead > 23 && _lookhead < 27) {
            yylval.sval = _lex.yytext();
        }
        else {
            yylval.sval = null;
        }
        _lookhead = _lex.yylex();
    }
    public ParserVal Match(int token) throws java.io.IOException
    {
        if (LookAhead() == token)
        {
            ParserVal attr = yylval;
            Advance();
            return attr;
        }
        Advance();
        return null;
    }

    public int yyparse() throws java.io.IOException
    {
        Program();
        return 0;
    }

    /// program     : mainfunc
    ///             ;
    public void Program() throws java.io.IOException
    {
        switch (LookAhead())
        {
            case MAIN:
                MainFunc();
                break;
        }
    }
    /// mainfunc    : MAIN LPAREN RPAREN BEGIN stmt_seq END
    ///             ;
    public void MainFunc() throws java.io.IOException
    {
        switch (LookAhead())
        {
            case MAIN:
                Match(MAIN);
                Match(LPAREN);
                Match(RPAREN);
                Match(BEGIN);
                StmtSeq();
                Match(END);
                break;
        }
    }
    /// stmt_seq    : stmt stmt_seq
    ///             | stmt
    ///             ;
    public void StmtSeq() throws java.io.IOException
    {
        while(true)
        {
            switch (LookAhead())
            {
                case END:
                    return;
                default:
                    Stmt();
                    break;
            }
        }
    }
    /// stmt        : ID ASSIGN ID  PLUS ID  SEMI
    ///             | ID ASSIGN ID  PLUS NUM SEMI
    ///             | ID ASSIGN NUM PLUS ID  SEMI
    ///             | ID ASSIGN NUM PLUS NUM SEMI
    ///             | ID ASSIGN ID  SEMI
    ///             | ID ASSIGN NUM SEMI
    ///             | WRITE ID SEMI
    ///             ;
    public void Stmt() throws java.io.IOException
    {
        switch (LookAhead())
        {
            case ID:
                {
                    ParserVal attr = Match(ID);
                    String name = attr.sval;
                    Match(ASSIGN);
                    int factor = Factor();
                    if (LookAhead() == PLUS)
                    {
                        Match(PLUS);
                        int nextfactor = Factor();
                        factor += nextfactor;
                    }

                    SymbolTable().put(name, factor);
                    Match(SEMI);
                }
                break;
            case PRINT:
                {
                    Match(PRINT);
                    ParserVal attr = Match(ID);
                    String name = attr.sval;
                    int  factor = SymbolTable().get(name);
                    System.out.print("<<print output " + factor + " >>");
                    Match(SEMI);
                }
                break;
            default:
                while(true)
                    if(Match(SEMI) != null)
                        break;
                break;
        }
    }
    public int Factor() throws java.io.IOException
    {
        ParserVal attr;
        int val;
        switch (LookAhead())
        {
            case ID:
                attr = Match(ID);
                val = SymbolTable().get(attr.sval);
                return val;
            case NUM:
                attr = Match(NUM);
                val = Integer.parseInt(attr.sval);
                return val;
            default:
                return 0;
        }
    }
}

