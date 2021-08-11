package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class NullTypeNode implements Node {

    public NullTypeNode() {
    }

    public String toPrint(String s) {
        return s + "NullType\n";
    }

    //non utilizzato
    public Node typeCheck() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return new ArrayList<SemanticError>();
    }

    //non utilizzato
    public String codeGeneration() {
        return "";
    }

    @Override
    public Integer getPointLevel() {
        return 0;
    }

    @Override
    public int getPointLevel() {
        return 0;
    }
}  