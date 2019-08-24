// Brad Westhafer
// CMPSC 470
// Project 3

public class SemanticChecker {
	public static void main(String[] args) throws Exception
    {
        //java.io.Reader r = new java.io.StringReader
        //("main()\n"                   // line 1
        //+"{\n"                        // line 2
        //+"    int x;\n"               // line 3
        //+"    print (2+3)*4+5;\n"     // line 4 => print 25
        //+"    print (1+2)*3;\n"       // line 5 => print  9
        //+"    print 1+2*3+4;\n"       // line 6 => print 11
        //+"    print 1+2*(3*4+5);\n"   // line 7 => print 35
        //+"}"                          // line 8
        //);
        /*java.io.Reader r = new java.io.StringReader
                ("main()\n"                    // line 1
                        +"{\n"                         // line 2
                        +"int y;\n"
                        //+"int y;\n"
                        //+"y = 5;\n"
                        //+"    bool x;\n"               // line 3 : add x into symbol table env
                        //+"    int  y;\n"               // line 4 : add y into symbol table env
                        //+"float z;\n"
                        //+"y = y + (true + 1/2+y%3)/0.5\n"
                        //+"z = 3E5 / 5E3;\n"
                        //+"y = true % 5;\n"
                        //+"y = 5 + 3.3;\n"
                        //+"print z;\n"
                        //+"y = 2;\n"
                        //+"print y;\n"
                        //+"{\nint x;\n}\n"
                        //+"return;\n"
                        //+"if(true)\nprint x;\nelse\nprint y;\n"
                        //+"while(true)\nprint y;\n"
                        //+";\n"
                        //+"    y = y + 2 * ( 3 + 4 );\n"// line 5 : calculate y
                        //+"print y;\n"
                        //+"    y = 2.5;\n"                // line 6 : assign int y into bool x <= error
                        //+"z = 5;"
                        +"}"                           // line 7
                );*/
        if(args.length <= 0)
            return;
        String foopath = args[0];
        java.io.Reader r = new java.io.FileReader(foopath);

        Parser yyparser = new Parser(r);
        try {
            if (yyparser.yyparse() == 0)
                System.out.println("Success");
        }
        catch(Exception e)
        {
            System.err.println(e.toString());
        }
	}
}
