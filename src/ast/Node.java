package ast;

import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

public interface Node {

    String toPrint(String indent);

    //fa il type checking e ritorna:
    //  per una espressione, il suo tipo (oggetto BoolTypeNode o IntTypeNode)
    //  per una dichiarazione, "null"

    Node typeCheck();

    String codeGeneration();

    ArrayList<SemanticError> checkSemantics(Environment env);

    int getPointLevel();

    Status getStatus();

    void setStatus(Status status);

}  