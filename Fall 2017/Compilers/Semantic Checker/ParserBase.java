// Brad Westhafer
// CMPSC 470
// Project 3

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class ParserBase
{
    public  int   linenum;
    public int startLinenum = 0;
    Env values = new Env(null);
    Env types = new Env(null);
    Env functiontypes = new Env(null);
    Env functionparams = new Env(null);


    Object CallProgram(Object main_decl)
    {
        return "CallProgram";
    }
    Object CallDeclListRec(Object decl_list, Object decl)
    {
        ((ArrayList<Object>)decl_list).add(decl);
        return decl_list;
    }
    Object CallDeclListEps() { return new ArrayList<Object>(); }
    Object CallDecl(Object fun_decl) {
        //System.err.println(fun_decl);
        return "CallDecl"; }
    Object CallTypeSpecInt(Object INT) { return "CallTypeSpecInt"; }
    Object CallTypeSpecFloat(Object FLOAT) { return "CallTypeSpecFloat"; }
    Object CallTypeSpecBool(Object BOOL) { return "CallTypeSpecBool"; }
    Object CallMain(Object compound_stmt)
    {
        return "CallMain";
    }
    Object CallFunDecl(Object type_spec, String ID, Object params) {
        try {
            if (((String) type_spec).equals("CallTypeSpecInt")) {
                types.Put(ID, "int()", true);
                functionparams.Put(ID, params, true);
            } else if (((String) type_spec).equals("CallTypeSpecFloat")) {
                types.Put(ID, "float()", true);
                functionparams.Put(ID, params, true);
            } else if (((String) type_spec).equals("CallTypeSpecBool")) {
                types.Put(ID, "bool()", true);
                functionparams.Put(ID, params, true);
            }
        }catch (Exception e) {
            System.out.println("Error at line " + linenum + ": the function " + ID + " is already defined.");
            System.exit(0);
        }
        return "CallFunDecl "+type_spec+" "+params;
    }
    Object CallParamsLst(Object param_list) { return param_list; }
    Object CallParamsEps() { return ""; }
    Object CallParamListRec(Object param_list, Object param)
    {
        //System.err.println(param_list);
        //System.err.println(param);
        param_list = (String)param_list + " " + (String)param;
        //((ArrayList<String>)param_list).add((String)param);
        return param_list;
    }
    Object CallParamListTerm(Object param) { return param; }
    Object CallParam(Object type_spec) {
        if(type_spec.toString().equals("CallTypeSpecInt")) {
            return "int";
        }
        if(type_spec.toString().equals("CallTypeSpecFloat")) {
            return "float";
        }
        else {
           return "bool";
        }
    }
    Object CallStmtListRec(Object stmt_list, Object stmt)
    {
        ((ArrayList<Object>)stmt_list).add(stmt);
        return stmt_list;
    }
    Object CallStmtListEps()
    {
        return new ArrayList<String>();
    }
    Object CallStmtExpr(Object expr_stmt) { return "CallStmtExpr"; }
    Object CallStmtPrint(Object print_stmt)
    {
        return "CallStmtPrint";
    }
    Object CallStmtCompound(Object compound_stmt)
    {
        return "CallStmtCompound";
    }
    Object CallStmtIf(Object if_stmt) { return "CallStmtIf"; }
    Object CallStmtWhile(Object while_stmt) { return "CallStmtWhile"; }
    Object CallStmtReturn(Object return_stmt) { return "CallStmtReturn"; }
    Object CallPrintExpr(Object expr)
    {
        //System.out.println("print "+expr+" at line "+linenum);
        return "print "+expr;
    }
    Object CallExprStmtExpr(Object ID, Object expr) {
        //System.err.println(ID);
        //System.err.println(expr);
        if(types.Get(ID.toString()) == null) {
            System.out.println("Error at line " + linenum + ": undefined " + ID.toString() + " is used.");
            System.exit(0);
        }
        if(types.Get(ID.toString()).equals("int")) {
            try {
                int i = Integer.parseInt(expr.toString());
               // System.err.print(i);
                values.Put(ID.toString(), i);
            }catch (Exception e) {
                try {
                    double d = Double.parseDouble(expr.toString());
                    System.out.println("Error at line " + linenum + ": try to assign float value to int variable " + ID.toString() + ".");
                    System.exit(0);
                } catch (Exception ee) {
                    System.out.println("Error at line " + linenum + ": try to assign bool value to int variable " + ID.toString() + ".");
                    System.exit(0);
                }
            }
        }
        else if(types.Get(ID.toString()).equals("float")) {
            try {
                double d = Double.parseDouble(expr.toString());
                //System.err.println(d);
                values.Put(ID.toString(), d);
            }catch(Exception e) {
                //System.err.println(expr.toString());
                System.out.println("Error at line " + linenum + ": try to assign bool value to float variable " + ID.toString() + ".");
                System.exit(0);
            }
        }
        else if(types.Get(ID.toString()).equals("bool")) {
            if(expr.toString().equals("true") || expr.toString().equals("false")) {
                values.Put(ID.toString(), expr.toString());
            }
            else {
            try {
                int i = Integer.parseInt(expr.toString());
                System.out.println("Error at line " + linenum + ": try to assign int value to bool variable " + ID.toString() + ".");
                System.exit(0);
            }catch(Exception ee) {
                System.out.println("Error at line " + linenum + ": try to assign float value to bool variable " + ID.toString() + ".");
                System.exit(0);
                }
            }
        }
        else if(types.Get(ID.toString()).equals("int()") || types.Get(ID.toString()).equals("float()") || types.Get(ID.toString()).equals("bool")) {
            System.out.println("Error Needs to be added");
            System.exit(0);
        }
        return "CallExprStmtExpr"+expr;
    }
    Object CallExprStmtSemi() { return "CallExprStmtSemi"; }
    Object CallWhileStmt(Object expr, Object stmt) {
        if(!(expr.toString().equals("true") || expr.toString().equals("false"))) {
            System.out.println("Error at line " + startLinenum + ": while statement accepts only boolean variables for condition.");
            System.exit(0);
        }
        return "CallWhileStmt"+expr+" "+stmt;
    }
    Object CallCompoundStmtBegin()
    {
        types = new Env(types);
        values = new Env(values);
        return "CallCompoundStmtBegin";
    }
    Object CallCompoundStmtRest(Object begin, Object local_decls, Object stmt_list)
    {
        types = types.prev;
        values = values.prev;
        return "CallCompoundStmtRest";
    }
    Object CallLocalDeclsRec(Object local_decls, Object local_decl)
    {
        ((ArrayList<String>)local_decls).add((String)local_decl);
        return local_decls;
    }
    Object CallLocalDeclsEps()
    {
        return new ArrayList<Object>();//"CallLocalDeclsEps";
    }
    Object CallLocalDecl(Object type_spec, String ID)
    {
//        System.err.println(type_spec);
//        System.err.println(ID);
        try {
            if (((String) type_spec).equals("CallTypeSpecInt")) {
                types.Put(ID, "int", true);
                values.Put(ID, new Integer(0), true);
            } else if (((String) type_spec).equals("CallTypeSpecFloat")) {
                types.Put(ID, "float", true);
                values.Put(ID, new Double(0.0), true);
            } else if (((String) type_spec).equals("CallTypeSpecBool")) {
                types.Put(ID, "bool", true);
                values.Put(ID, new Boolean(false), true);
            }
        }catch (Exception e) {
            System.out.println("Error at line " + linenum + ": the variable " + ID + " is already defined in this block.");
            System.exit(0);
        }
        return "CallLocalDecl" + type_spec + " " + ID;
    }
    Object CallIfStmt(Object expr, Object stmt1, Object stmt2) {
        if(!(expr.toString().equals("true") || expr.toString().equals("false"))) {
            System.out.println("Error at line " + startLinenum + ": if statement accepts only boolean variables for condition.");
            System.exit(0);
        }
        return "CallIfStmt " + expr + " " + stmt1 + " " + stmt2;
    }
    Object CallReturnStmt() { return "CallReturnStmt"; }
    Object CallArgListList(Object arg_list, Object expr) { return ""+arg_list.toString()+" "+expr.toString(); }
    Object CallArgListExpr(Object expr) { return ""+expr.toString(); }
    Object CallArgsList(Object arg_list) { return ""+arg_list; }
    Object CallArgsEps() { return ""; }
    Object CallExprOr(Object expr1, Object expr2) {
        if(!expr1.toString().equals("false") && !expr1.toString().equals("true")) {
            if(!expr2.toString().equals("false") && !expr2.toString().equals("true")) {
                try {
                    Integer.parseInt(expr1.toString());
                    Integer.parseInt(expr2.toString());
                    System.out.println("Error at line " + linenum + ": (int or int) is not allowed.");
                    System.exit(0);
                } catch(Exception e) {
                    try{
                        Integer.parseInt(expr1.toString());
                        Double.parseDouble(expr2.toString());
                        System.out.println("Error at line " + linenum + ": (int or float) is not allowed.");
                        System.exit(0);
                    }catch(Exception ee) {
                        try {
                            Double.parseDouble(expr1.toString());
                            Integer.parseInt(expr2.toString());
                            System.out.println("Error at line " + linenum + ": (float or int) is not allowed.");
                            System.exit(0);
                        }catch (Exception eee) {
                            Double.parseDouble(expr1.toString());
                            Double.parseDouble(expr2.toString());
                            System.out.println("Error at line " + linenum + ": (float or float) is not allowed.");
                            System.exit(0);
                        }
                    }
                }
            }
            else {
                try {
                    Integer.parseInt(expr1.toString());
                    System.out.println("Error at line " + linenum + ": (int or bool) is not allowed.");
                    System.exit(0);
                }catch (Exception e) {
                    System.out.println("Error at line " + linenum + ": (float or bool) is not allowed.");
                    System.exit(0);
                }
            }
        }
        else if(!expr2.toString().equals("false") && !expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr2.toString());
                System.out.println("Error at line " + linenum + ": (bool or int) is not allowed.");
                System.exit(0);
            }catch (Exception e) {
                System.out.println("Error at line " + linenum + ": (bool or float) is not allowed.");
                System.exit(0);
            }
        }
        return (Boolean.parseBoolean(expr1.toString()) || Boolean.parseBoolean(expr2.toString()));
    }
    Object CallExprAnd(Object expr1, Object expr2) {
        if(!expr1.toString().equals("false") && !expr1.toString().equals("true")) {
            if(!expr2.toString().equals("false") && !expr2.toString().equals("true")) {
                try {
                    Integer.parseInt(expr1.toString());
                    Integer.parseInt(expr2.toString());
                    System.out.println("Error at line " + linenum + ": (int and int) is not allowed.");
                    System.exit(0);
                } catch(Exception e) {
                    try{
                        Integer.parseInt(expr1.toString());
                        Double.parseDouble(expr2.toString());
                        System.out.println("Error at line " + linenum + ": (int and float) is not allowed.");
                        System.exit(0);
                    }catch(Exception ee) {
                        try {
                            Double.parseDouble(expr1.toString());
                            Integer.parseInt(expr2.toString());
                            System.out.println("Error at line " + linenum + ": (float and int) is not allowed.");
                            System.exit(0);
                        }catch (Exception eee) {
                            Double.parseDouble(expr1.toString());
                            Double.parseDouble(expr2.toString());
                            System.out.println("Error at line " + linenum + ": (float and float) is not allowed.");
                            System.exit(0);
                        }
                    }
                }
            }
            else {
                try {
                    Integer.parseInt(expr1.toString());
                    System.out.println("Error at line " + linenum + ": (int and bool) is not allowed.");
                    System.exit(0);
                }catch (Exception e) {
                    System.out.println("Error at line " + linenum + ": (float and bool) is not allowed.");
                    System.exit(0);
                }
            }
        }
        else if(!expr2.toString().equals("false") && !expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr2.toString());
                System.out.println("Error at line " + linenum + ": (bool and int) is not allowed.");
                System.exit(0);
            }catch (Exception e) {
                System.out.println("Error at line " + linenum + ": (bool and float) is not allowed.");
                System.exit(0);
            }
        }
        return (Boolean.parseBoolean(expr1.toString()) && Boolean.parseBoolean(expr2.toString()));
    }
    Object CallExprNot(Object expr) {
        if(!expr.toString().equals("false") && !expr.toString().equals("true")) {
            try {
                Integer.parseInt(expr.toString());
                System.out.println("Error at line " + linenum + ": (not int) is not allowed.");
                System.exit(0);
            }catch (Exception e) {
                System.out.println("Error at line " + linenum + ": (not float) is not allowed.");
                System.exit(0);
            }
        }
        return (!Boolean.parseBoolean(expr.toString()));
    }
    Object CallExprNe(Object expr1, Object expr2) {
        if(expr1.toString().equals("false") || expr1.toString().equals("true")) {
            if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
                return Boolean.parseBoolean(expr1.toString()) != Boolean.parseBoolean(expr2.toString());
            }
            else {
                try {
                    Integer.parseInt(expr2.toString());
                    System.out.println("Error at line " + linenum + ": (bool != int) is not allowed.");
                    System.exit(0);
                }catch (Exception e) {
                    System.out.println("Error at line " + linenum + ": (bool != float) is not allowed.");
                    System.exit(0);
                }
            }
        }
        else if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr1.toString());
                System.out.println("Error at line " + linenum + ": (int != bool) is not allowed.");
                System.exit(0);
            }catch (Exception e) {
                System.out.println("Error at line " + linenum + ": (float != bool) is not allowed.");
                System.exit(0);
            }
        }
        try {
            int i = Integer.parseInt(expr1.toString());
            int j = Integer.parseInt(expr2.toString());
            return i != j;
        }catch (Exception e) {
            try {
                int i = Integer.parseInt(expr1.toString());
                double j = Double.parseDouble(expr2.toString());
                return i != j;
            }catch (Exception ee) {
                double i = Double.parseDouble(expr1.toString());
                try {
                    int j = Integer.parseInt(expr2.toString());
                    return i != j;
                }catch (Exception eee) {
                    double j = Double.parseDouble(expr2.toString());
                    return i != j;
                }
            }
        }
    }
    Object CallExprEq(Object expr1, Object expr2) {
        if(expr1.toString().equals("false") || expr1.toString().equals("true")) {
            if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
                return Boolean.parseBoolean(expr1.toString()) == Boolean.parseBoolean(expr2.toString());
            }
            else {
                try {
                    Integer.parseInt(expr2.toString());
                    System.out.println("Error at line " + linenum + ": (bool == int) is not allowed.");
                    System.exit(0);
                }catch (Exception e) {
                    System.out.println("Error at line " + linenum + ": (bool == float) is not allowed.");
                    System.exit(0);
                }
            }
        }
        else if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr1.toString());
                System.out.println("Error at line " + linenum + ": (int == bool) is not allowed.");
                System.exit(0);
            }catch (Exception e) {
                System.out.println("Error at line " + linenum + ": (float == bool) is not allowed.");
                System.exit(0);
            }
        }
        try {
            int i = Integer.parseInt(expr1.toString());
            int j = Integer.parseInt(expr2.toString());
            return i == j;
        }catch (Exception e) {
            try {
                int i = Integer.parseInt(expr1.toString());
                double j = Double.parseDouble(expr2.toString());
                return i == j;
            }catch (Exception ee) {
                double i = Double.parseDouble(expr1.toString());
                try {
                    int j = Integer.parseInt(expr2.toString());
                    return i == j;
                }catch (Exception eee) {
                    double j = Double.parseDouble(expr2.toString());
                    return i == j;
                }
            }
        }
    }
    Object CallExprLt(Object expr1, Object expr2) {
        if(expr1.toString().equals("false") || expr1.toString().equals("true")) {
            if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
                System.out.println("Error at line " + linenum + ": (bool < bool) is not allowed.");
                System.exit(0);
            }
            else {
                try {
                    Integer.parseInt(expr2.toString());
                    System.out.println("Error at line " + linenum + ": (bool < int) is not allowed.");
                    System.exit(0);
                }catch (Exception e) {
                    System.out.println("Error at line " + linenum + ": (bool < float) is not allowed.");
                    System.exit(0);
                }
            }
        }
        else if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr1.toString());
                System.out.println("Error at line " + linenum + ": (int < bool) is not allowed.");
                System.exit(0);
            }catch (Exception e) {
                System.out.println("Error at line " + linenum + ": (float < bool) is not allowed.");
                System.exit(0);
            }
        }
        try {
            int i = Integer.parseInt(expr1.toString());
            int j = Integer.parseInt(expr2.toString());
            return i < j;
        }catch (Exception e) {
            try {
                int i = Integer.parseInt(expr1.toString());
                double j = Double.parseDouble(expr2.toString());
                return i < j;
            }catch (Exception ee) {
                double i = Double.parseDouble(expr1.toString());
                try {
                    int j = Integer.parseInt(expr2.toString());
                    return i < j;
                }catch (Exception eee) {
                    double j = Double.parseDouble(expr2.toString());
                    return i < j;
                }
            }
        }
    }
    Object CallExprGt(Object expr1, Object expr2) {
        if(expr1.toString().equals("false") || expr1.toString().equals("true")) {
            if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
                System.out.println("Error at line " + linenum + ": (bool > bool) is not allowed.");
                System.exit(0);
            }
            else {
                try {
                    Integer.parseInt(expr2.toString());
                    System.out.println("Error at line " + linenum + ": (bool > int) is not allowed.");
                    System.exit(0);
                }catch (Exception e) {
                    System.out.println("Error at line " + linenum + ": (bool > float) is not allowed.");
                    System.exit(0);
                }
            }
        }
        else if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr1.toString());
                System.out.println("Error at line " + linenum + ": (int > bool) is not allowed.");
                System.exit(0);
            }catch (Exception e) {
                System.out.println("Error at line " + linenum + ": (float > bool) is not allowed.");
                System.exit(0);
            }
        }
        try {
            int i = Integer.parseInt(expr1.toString());
            int j = Integer.parseInt(expr2.toString());
            return i > j;
        }catch (Exception e) {
            try {
                int i = Integer.parseInt(expr1.toString());
                double j = Double.parseDouble(expr2.toString());
                return i > j;
            }catch (Exception ee) {
                double i = Double.parseDouble(expr1.toString());
                try {
                    int j = Integer.parseInt(expr2.toString());
                    return i > j;
                }catch (Exception eee) {
                    double j = Double.parseDouble(expr2.toString());
                    return i > j;
                }
            }
        }
    }
    Object CallExprLe(Object expr1, Object expr2) {
        if(expr1.toString().equals("false") || expr1.toString().equals("true")) {
            if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
                System.out.println("Error at line " + linenum + ": (bool <= bool) is not allowed.");
                System.exit(0);
            }
            else {
                try {
                    Integer.parseInt(expr2.toString());
                    System.out.println("Error at line " + linenum + ": (bool <= int) is not allowed.");
                    System.exit(0);
                }catch (Exception e) {
                    System.out.println("Error at line " + linenum + ": (bool <= float) is not allowed.");
                    System.exit(0);
                }
            }
        }
        else if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr1.toString());
                System.out.println("Error at line " + linenum + ": (int <= bool) is not allowed.");
                System.exit(0);
            }catch (Exception e) {
                System.out.println("Error at line " + linenum + ": (float <= bool) is not allowed.");
                System.exit(0);
            }
        }
        try {
            int i = Integer.parseInt(expr1.toString());
            int j = Integer.parseInt(expr2.toString());
            return i <= j;
        }catch (Exception e) {
            try {
                int i = Integer.parseInt(expr1.toString());
                double j = Double.parseDouble(expr2.toString());
                return i <= j;
            }catch (Exception ee) {
                double i = Double.parseDouble(expr1.toString());
                try {
                    int j = Integer.parseInt(expr2.toString());
                    return i <= j;
                }catch (Exception eee) {
                    double j = Double.parseDouble(expr2.toString());
                    return i <= j;
                }
            }
        }
    }
    Object CallExprGe(Object expr1, Object expr2) {
        if(expr1.toString().equals("false") || expr1.toString().equals("true")) {
            if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
                System.out.println("Error at line " + linenum + ": (bool >= bool) is not allowed.");
                System.exit(0);
            }
            else {
                try {
                    Integer.parseInt(expr2.toString());
                    System.out.println("Error at line " + linenum + ": (bool >= int) is not allowed.");
                    System.exit(0);
                }catch (Exception e) {
                    System.out.println("Error at line " + linenum + ": (bool >= float) is not allowed.");
                    System.exit(0);
                }
            }
        }
        else if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr1.toString());
                System.out.println("Error at line " + linenum + ": (int >= bool) is not allowed.");
                System.exit(0);
            }catch (Exception e) {
                System.out.println("Error at line " + linenum + ": (float >= bool) is not allowed.");
                System.exit(0);
            }
        }
        try {
            int i = Integer.parseInt(expr1.toString());
            int j = Integer.parseInt(expr2.toString());
            return i >= j;
        }catch (Exception e) {
            try {
                int i = Integer.parseInt(expr1.toString());
                double j = Double.parseDouble(expr2.toString());
                return i >= j;
            }catch (Exception ee) {
                double i = Double.parseDouble(expr1.toString());
                try {
                    int j = Integer.parseInt(expr2.toString());
                    return i >= j;
                }catch (Exception eee) {
                    double j = Double.parseDouble(expr2.toString());
                    return i >= j;
                }
            }
        }
    }
    Object CallTypeInt()
    {
        return "CallTypeInt";
    }
    //Allow int-float match
    //Throw error on int-bool, bool-bool or float-bool
    Object CallExprAdd(Object expr1, Object expr2)
    {
        if(expr1.toString().equals("true") || expr1.toString().equals("false")) {
            if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
                System.out.println("Error at line " + linenum + ": (bool + bool) is not allowed.");
                System.exit(0);
            }
            try {
                Integer.parseInt(expr2.toString());
                System.out.println("Error at line " + linenum + ": (bool + int) is not allowed.");
                System.exit(0);
            }catch (Exception E) {
                System.out.println("Error at line " + linenum + ": (bool + float) is not allowed.");
                System.exit(0);
            }
        }
        else if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr1.toString());
                System.out.println("Error at line " + linenum + ": (int + bool) is not allowed.");
                System.exit(0);
            } catch(Exception E) {
                System.out.println("Error at line " + linenum + ": (float + bool) is not allowed.");
                System.exit(0);
            }
        }
        try {
            int i = Integer.parseInt(expr1.toString());
            int j = Integer.parseInt(expr2.toString());
            return i + j;
        }catch (Exception e) {
            try {
                int i = Integer.parseInt(expr1.toString());
                double j = Double.parseDouble(expr2.toString());
                return i + j;
            }catch (Exception ee) {
                double i = Double.parseDouble(expr1.toString());
                try {
                    int j = Integer.parseInt(expr2.toString());
                    return i + j;
                }catch (Exception eee) {
                    double j = Double.parseDouble(expr2.toString());
                    return i + j;
                }
            }
        }
        //return ((int)expr1 + (int)expr2);
    }
    Object CallExprMinus(Object expr1, Object expr2)
    {
        if(expr1.toString().equals("true") || expr1.toString().equals("false")) {
            if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
                System.out.println("Error at line " + linenum + ": (bool - bool) is not allowed.");
                System.exit(0);
            }
            try {
                Integer.parseInt(expr2.toString());
                System.out.println("Error at line " + linenum + ": (bool - int) is not allowed.");
                System.exit(0);
            }catch (Exception E) {
                System.out.println("Error at line " + linenum + ": (bool - float) is not allowed.");
                System.exit(0);
            }
        }
        else if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr1.toString());
                System.out.println("Error at line " + linenum + ": (int - bool) is not allowed.");
                System.exit(0);
            } catch(Exception E) {
                System.out.println("Error at line " + linenum + ": (float - bool) is not allowed.");
                System.exit(0);
            }
        }
        try {
            int i = Integer.parseInt(expr1.toString());
            int j = Integer.parseInt(expr2.toString());
            return i - j;
        }catch (Exception e) {
            try {
                int i = Integer.parseInt(expr1.toString());
                double j = Double.parseDouble(expr2.toString());
                return i - j;
            }catch (Exception ee) {
                double i = Double.parseDouble(expr1.toString());
                try {
                    int j = Integer.parseInt(expr2.toString());
                    return i - j;
                }catch (Exception eee) {
                    double j = Double.parseDouble(expr2.toString());
                    return i - j;
                }
            }
        }
    }
    Object CallExprMul(Object expr1, Object expr2)
    {
        if(expr1.toString().equals("true") || expr1.toString().equals("false")) {
            if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
                System.out.println("Error at line " + linenum + ": (bool * bool) is not allowed.");
                System.exit(0);
            }
            try {
                Integer.parseInt(expr2.toString());
                System.out.println("Error at line " + linenum + ": (bool * int) is not allowed.");
                System.exit(0);
            }catch (Exception E) {
                System.out.println("Error at line " + linenum + ": (bool * float) is not allowed.");
                System.exit(0);
            }
        }
        else if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr1.toString());
                System.out.println("Error at line " + linenum + ": (int * bool) is not allowed.");
                System.exit(0);
            } catch(Exception E) {
                System.out.println("Error at line " + linenum + ": (float * bool) is not allowed.");
                System.exit(0);
            }
        }
        try {
            int i = Integer.parseInt(expr1.toString());
            int j = Integer.parseInt(expr2.toString());
            return i * j;
        }catch (Exception e) {
            try {
                int i = Integer.parseInt(expr1.toString());
                double j = Double.parseDouble(expr2.toString());
                return i * j;
            }catch (Exception ee) {
                double i = Double.parseDouble(expr1.toString());
                try {
                    int j = Integer.parseInt(expr2.toString());
                    return i * j;
                }catch (Exception eee) {
                    double j = Double.parseDouble(expr2.toString());
                    return i * j;
                }
            }
        }
    }
    Object CallExprDiv(Object expr1, Object expr2)
    {
        if(expr1.toString().equals("true") || expr1.toString().equals("false")) {
            if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
                System.out.println("Error at line " + linenum + ": (bool / bool) is not allowed.");
                System.exit(0);
            }
            try {
                Integer.parseInt(expr2.toString());
                System.out.println("Error at line " + linenum + ": (bool / int) is not allowed.");
                System.exit(0);
            }catch (Exception E) {
                System.out.println("Error at line " + linenum + ": (bool / float) is not allowed.");
                System.exit(0);
            }
        }
        else if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr1.toString());
                System.out.println("Error at line " + linenum + ": (int / bool) is not allowed.");
                System.exit(0);
            } catch(Exception E) {
                System.out.println("Error at line " + linenum + ": (float / bool) is not allowed.");
                System.exit(0);
            }
        }
        try {
            int i = Integer.parseInt(expr1.toString());
            int j = Integer.parseInt(expr2.toString());
            return i / j;
        }catch (Exception e) {
            try {
                int i = Integer.parseInt(expr1.toString());
                double j = Double.parseDouble(expr2.toString());
                return i / j;
            }catch (Exception ee) {
                double i = Double.parseDouble(expr1.toString());
                try {
                    int j = Integer.parseInt(expr2.toString());
                    return i / j;
                }catch (Exception eee) {
                    double j = Double.parseDouble(expr2.toString());
                    return i / j;
                }
            }
        }
    }
    Object CallExprMod(Object expr1, Object expr2)
    {
        if(expr1.toString().equals("true") || expr1.toString().equals("false")) {
            if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
                System.out.println("Error at line " + linenum + ": (bool % bool) is not allowed.");
                System.exit(0);
            }
            try {
                Integer.parseInt(expr2.toString());
                System.out.println("Error at line " + linenum + ": (bool % int) is not allowed.");
                System.exit(0);
            }catch (Exception E) {
                System.out.println("Error at line " + linenum + ": (bool % float) is not allowed.");
                System.exit(0);
            }
        }
        else if(expr2.toString().equals("false") || expr2.toString().equals("true")) {
            try {
                Integer.parseInt(expr1.toString());
                System.out.println("Error at line " + linenum + ": (int % bool) is not allowed.");
                System.exit(0);
            } catch(Exception E) {
                System.out.println("Error at line " + linenum + ": (float % bool) is not allowed.");
                System.exit(0);
            }
        }
        try {
            int i = Integer.parseInt(expr1.toString());
            int j = Integer.parseInt(expr2.toString());
            return i % j;
        }catch (Exception e) {
            try {
                int i = Integer.parseInt(expr1.toString());
                double j = Double.parseDouble(expr2.toString());
                return i % j;
            }catch (Exception ee) {
                double i = Double.parseDouble(expr1.toString());
                try {
                    int j = Integer.parseInt(expr2.toString());
                    return i % j;
                }catch (Exception eee) {
                    double j = Double.parseDouble(expr2.toString());
                    return i % j;
                }
            }
        }
    }
    Object CallExprParen(Object expr)
    {
        return expr;
    }
    Object CallFactorId(String ID) {
        if(types.Get(ID) == null) {
            System.out.println("Error at line " + linenum + ": undefined " + ID + " is used.");
            System.exit(0);
        }
        if(types.Get(ID).equals("int()") || types.Get(ID).equals("float()") || types.Get(ID).equals("bool()")) {
            System.out.println("Error at line " + linenum + ": function " + ID + " cannot be used as a variable.");
            System.exit(0);
        }
        return values.Get(ID);
    };
    Object CallFactorIdArgs(String ID, Object args) {
        //System.err.println(args);
        //System.err.println(ID);
        if(functionparams.Get(ID) == null && types.Get(ID) != null) {
            System.out.println("Error at line " + linenum + ": " + types.Get(ID) + " variable " + ID + " cannot be used as a function.");
            System.exit(0);
        }
        if(types.Get(ID) == null) {
            System.out.println("Error at line " + linenum + ": undefined " + ID + " is used.");
            System.exit(0);
        }
        String params = functionparams.Get(ID).toString();
        String argString = args.toString();
        int nextArg = argString.indexOf(" ");
        int nextParam = params.indexOf(" ");
        while (nextArg != -1 && nextParam != -1) {
            String curArg = argString.substring(0, nextArg);
            argString = argString.substring(nextArg + 1);
            String curParam = params.substring(0, nextParam);
            params = params.substring(nextParam + 1);
            nextArg = argString.indexOf(" ");
            nextParam = params.indexOf(" ");
            if (curParam.equals("int")) {
                try {
                    Integer.parseInt(curArg);
                } catch (Exception e) {
                    System.out.println("Error at line " + linenum + ": incorrect type of parameter(s) is passed to function " + ID.toString() + "(...).");
                    System.exit(0);
                }
            } else if (curParam.equals("float")) {
                try {
                    Double.parseDouble(curArg);
                } catch (Exception e) {
                    System.out.println("Error at line " + linenum + ": incorrect type of parameter(s) is passed to function " + ID.toString() + "(...).");
                    System.exit(0);
                }
            } else {
                try {
                    Boolean.parseBoolean(curArg);
                } catch (Exception e) {
                    System.out.println("Error at line " + linenum + ": incorrect type of parameter(s) is passed to function " + ID.toString() + "(...).");
                    System.exit(0);
                }
            }
        }
        if(nextArg != -1 || nextParam != -1 || (args.equals("") && !params.equals("")) || (!args.equals("") && params.equals(""))) {
            System.out.println("Error at line " + linenum + ": incorrect number of parameters are passed to function " + ID + "(...).");
            System.exit(0);
        }
        if (params.equals("")) {

        } else if (params.equals("int")) {
            try {
                Integer.parseInt(argString);
            } catch (Exception e) {
                System.out.println("Error at line " + linenum + ": incorrect type of parameter(s) is passed to function " + ID.toString() + "(...).");
                System.exit(0);
            }
        } else if (params.equals("float")) {
            try {
                Double.parseDouble(argString);
            } catch (Exception e) {
                System.out.println("Error at line " + linenum + ": incorrect type of parameter(s) is passed to function " + ID.toString() + "(...).");
                System.exit(0);
            }
        } else {
            try {
                Boolean.parseBoolean(argString);
            } catch (Exception e) {
                System.out.println("Error at line " + linenum + ": incorrect type of parameter(s) is passed to function " + ID.toString() + "(...).");
                System.exit(0);
            }
        }
        if (types.Get(ID).equals("int()")) {
            return 0;
        } else if (types.Get(ID).equals("float()")) {
            return 0.0;
        } else {
            //System.err.println(types.Get(ID));
            return true;
        }
    }
    double CallFactorReal(String REAL) { return Double.parseDouble(REAL); }
    boolean CallFactorTrue() { return true; }
    boolean CallFactorFalse() { return false; }
    int CallFactorNum(String NUM)
    {
        return Integer.parseInt(NUM);
    }
}
