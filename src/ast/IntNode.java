package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class IntNode implements Node {

    private Integer val;
    private Integer nestingLevel;

    public IntNode(Integer val, Integer nestingLevel) {
        this.val = val;
        this.nestingLevel = nestingLevel;
    }

    public String toPrint(String s) {
        return s + "Int:" + Integer.toString(val) + "\n";
    }

    public Node typeCheck() {
        return new IntTypeNode(this.nestingLevel);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return new ArrayList<SemanticError>();
    }

    public String codeGeneration() {
        return "push " + val + "\n";
    }

}  