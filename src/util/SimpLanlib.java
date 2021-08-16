package util;

import ast.Node;

public class SimpLanlib {

    private static int labCount = 0;

    private static int funLabCount = 0;

    private static String funCode = "";

    //valuta se il tipo "a" <= al tipo "b", dove "a" e "b" sono tipi di base: int o bool
    public static boolean isSubtype(Node a, Node b) {
        return a.getClass().equals(b.getClass()); //||
        // ( (a instanceof BoolTypeNode) && (b instanceof IntTypeNode) ); //
    }

    public static String freshLabel() {
        return "label" + (labCount++);
    }

    public static String freshFunLabel() {
        return "function" + (funLabCount++);
    }

    public static void putCode(String c) {
        funCode += "\n" + c; //aggiunge una linea vuota di separazione prima di funzione
    }

    public static String getCode() {
        return funCode;
    }

    public static Status maxStatus(Status s1, Status s2) {
        if (s1.ordinal() <= s2.ordinal())
            return s2;
        else
            return s1;
    }

    public static Status seqStatus(Status s1, Status s2) {
        Status tmp = maxStatus(s1, s2);
        if (tmp.ordinal() <= Status.READWRITE.ordinal())
            return (tmp);
        else if ((s1.ordinal() <= Status.READWRITE.ordinal() && s2 == Status.DELETED)
                //       || (s1 == Status.DELETED && s2 == Status.DECLARED)
        )
            return Status.DELETED;
        else
            return Status.ERROR;
    }

    public static Status parStatus(Status s1, Status s2) {
        return maxStatus(seqStatus(s1, s2), seqStatus(s2, s1));
    }

}