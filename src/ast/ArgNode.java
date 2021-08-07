package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class ArgNode implements Node {

    private String id;
    private Node type;

    public ArgNode (String i, Node t) {
        this.id = i;
        this.type = t;
    }

    public String getId(){
        return id;
    }

    public Node getType(){
        return type;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }

    public String toPrint(String s) {
        return s+"Arg:" + id +"\n"
                +type.toPrint(s+"  ") ;
    }

    //non utilizzato
    public Node typeCheck () {
        return null;
    }

    //non utilizzato
    public String codeGeneration() {
        return "";
    }

}