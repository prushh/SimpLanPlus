package ast;

import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;



/**
 * This is the interface implemented by each node in the abstract syntax tree (AST).
 * 
 */

public interface Node {
	
	/**
	 * Visit AST in order to verify whether scopes are correctly formed, with reference to
	 * scope rule, and progressively build the symbol table; it also fills some data structures
	 * during the visit of AST that will be used in later visits.
	 * 
	 * @param env -> list of hash maps, see util/Environment.java
	 * @return a lists with all scope errors
	 */
	
	ArrayList<SemanticError> checkSemantics(Environment env);
	
	/**
	 * Perform the second visit of AST looking for mismatching types in the code. 
	 * 
	 * @param typeErr -> lists of type errors that will be filled during the second AST visit
	 * @return the type of the whole program
	 */
	
	Node typeCheck(ArrayList<SemanticError> typeErr);
	
	/**
	 * This function makes the third visit of the AST performing effects analysis,
	 * dealing mainly with pointers, identifiers not initialized and aliasing. 
	 * 
	 * @param env -> lists of hash maps, same as in checkSemantics
	 * @return lists with errors founded
	 */
	
	ArrayList<SemanticError> checkEffects(Environment env);
	
	/**
	 * Prints AST as semantic analysis is finished.
	 * 
	 */
	
	String toPrint(String indent);	
	
	/**
	 * Once semantic analysis is done, this function visits AST for the last time so that bytecode
	 * for the interpreter is generated. In this part of the compiler, information like offsets and
	 * symbol table entries left in the AST by previous visits are used with a minimal version of
	 * Environment data structure in order to implement semantic correctly.
	 * 
	 * @param env -> list of labels, see util/CGenEnv.java
	 * @return the translation of the SimpLanPlus code into bytecode, seen as a sequence of strings
	 * 		linked together
	 */
	
	String codeGeneration(CGenEnv env);

	/**
	 * Retrieve number of dereferencing operations to get data store by the variable. Normal
	 * variables has point level 0, while pointer types has point level > 0. This is intended
	 * to be used only on IntTypeNode and BoolTypeNode.
	 * 
	 * @return point level of the node
	 */

    int getPointLevel();
    
    /**
     * Retrieve status associated to the node in order to perform effects analysis. This is intended
	 * to be used only on IntTypeNode and BoolTypeNode. See util/Status.java
     * 
     * @return status of the node
     */

    Status getStatus();
    
    /**
     * Set status associated to the node as result of effects analysis. This is intended
	 * to be used only on IntTypeNode and BoolTypeNode.
     * 
     * @param new status of the node
     */

    void setStatus(Status status);

}
