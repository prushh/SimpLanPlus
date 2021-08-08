package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class BoolNode implements Node {

    private boolean val;
    private Integer nestingLevel;

    public BoolNode(boolean val, Integer nestingLevel) {
        this.val = val;
        this.nestingLevel = nestingLevel;
    }

    public String toPrint(String s) {
        if (val) return s + "Bool:true\n";
        else return s + "Bool:false\n";
    }

    public Node typeCheck() {
        return new BoolTypeNode(this.nestingLevel);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return new ArrayList<SemanticError>();
    }

    public String codeGeneration() {
        return "push " + (val ? 1 : 0) + "\n";
    }

}  