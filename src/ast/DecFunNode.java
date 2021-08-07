package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

import java.util.ArrayList;
import java.util.HashMap;

public class DecFunNode implements Node {

    private String id;
    private Node type;
    private ArrayList<Node> parlist = new ArrayList<Node>();
    private Node body;

    public DecFunNode(String id, Node type, Node body) {
        this.id = id;
        this.type = type;
        this.body = body;
    }

    public void addArg(Node p){
        parlist.add(p);
    }


    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }


    public String toPrint(String s) {
        String parlstr="";
        for (Node par:parlist)
            parlstr+=par.toPrint(s+"  ");
        String declstr="";
        if (parlist!=null)
            for (Node dec:parlist)
                declstr+=dec.toPrint(s+"  ");
        return s+"Fun:" + id +"\n"
                +type.toPrint(s+"  ")
                +parlstr
                +declstr
                +body.toPrint(s+"  ") ;
    }

    //valore di ritorno non utilizzato
    public Node typeCheck() {
        return null;
    }

    public String codeGeneration() {
        return null;
    }
}