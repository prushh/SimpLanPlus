package ast.node.declaration;

import ast.Node;
import ast.STentry;
import ast.node.type.NullTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Variables declaration node. It consists of an identifier and a type
 * (sometimes also an expression as initialization).
 *
 * decVar : type ID ('=' exp)? ';' ;
 */

public class DecVarNode implements Node {

	private String ID;
	private Node type;
	private Node exp;

	public DecVarNode(String ID, Node type, Node exp) {
		this.ID = ID;
		this.type = type;
		this.exp = exp;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> res = new ArrayList<>();

		// Get scope at the top of symbol table
		HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
		// Create a new entry in the symbol table for the variable
		STentry entry = new STentry(env.nestingLevel, type, env.offset--);

		/*
		 * First check exp, because we want to evaluate it in the environment before
		 * adding the declaration to it
		 */

		if (exp != null) {
			res.addAll(exp.checkSemantics(env));
		}

		if (hm.put(ID, entry) != null) {
			res.add(new SemanticError("DecVar " + ID + " already declared"));
		}

		return res;
	}

	@Override
	public Node typeCheck(ArrayList<SemanticError> typeErr) {
		Node l = this.type;
		if (exp != null) {
			Node r = this.exp.typeCheck(typeErr);
			if (!(SimpLanPlusLib.isSubtype(l, r))) {
				typeErr.add(new SemanticError("incompatible value for variable " + this.ID));
			} else {
				// Need one last check because we also have pointer types
				if (l.getPointLevel() != r.getPointLevel()) {
					typeErr.add(new SemanticError("cannot assign variable or pointers of different type"));
				}
			}
		}
		return new NullTypeNode(Status.DECLARED);
	}

	@Override
	public ArrayList<SemanticError> checkEffects(Environment env) {
		ArrayList<SemanticError> res = new ArrayList<>();

		HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
		STentry entry;

		if (exp != null) {
			/**
			 * If variable is also initialized during declaration, then its status must be
			 * READWRITE (default id DECLARED, so no need to set it in the else branch)
			 */
			res.addAll(exp.checkEffects(env));
			Status newDecAsg = Status.READWRITE;
			Node tmpLhs = type;
			tmpLhs.setStatus(newDecAsg);
			entry = new STentry(env.nestingLevel, tmpLhs, env.offset--);
		} else {
			entry = new STentry(env.nestingLevel, type, env.offset--);
		}

		hm.put(ID, entry);

		return res;
	}

	@Override
	public String toPrint(String indent) {
		StringBuilder builder = new StringBuilder();
		String str = indent + "DecVar: " + ID + "\n" + type.toPrint(indent + "\t");
		builder.append(str);
		if (exp != null) {
			builder.append(exp.toPrint(indent + "\t"));
		}
		return builder.toString();
	}

	@Override
	public String codeGeneration(CGenEnv env) {
		StringBuilder builder = new StringBuilder();
		if (exp != null) {
			builder.append(exp.codeGeneration(env));
			builder.append("push $a0\n");
		} else {
			builder.append("addi $sp -1\n");
		}

		return builder.toString();
	}

	@Override
	public int getPointLevel() {
		return 0;
	}

	@Override
	public Status getStatus() {
		return Status.DECLARED;
	}

	@Override
	public void setStatus(Status status) {

	}

}