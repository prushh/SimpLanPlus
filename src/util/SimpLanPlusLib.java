package util;

import ast.Node;
import ast.STentry;
import ast.node.other.ArgNode;
import ast.node.type.ArrowTypeNode;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.VoidTypeNode;
import org.antlr.v4.runtime.Token;
import parser.SVMParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class container for auxiliary functions used throughout all the code in the
 * packages.
 */

public class SimpLanPlusLib {

	/*
	 * This counter is required to make unique label, because it's possible to have
	 * redefinitions of the same function in inner blocks or functions.
	 */
	private static int funLabelCount = 0;

	public static int getUniqueLabel() {
		return funLabelCount++;
	}

	/**
	 * Verify if "a" <= "b", where "a" and "b" are base types: int or bool
	 * 
	 * @param a -> base type
	 * @param b -> base type
	 * @return boolean
	 */
	public static boolean isSubtype(Node a, Node b) {
		return a.getClass().equals(b.getClass());
	}

	// Operations for effects analysis: max, seq and par.
	/**
	 * Return max between two instances of Status enumeration.
	 * 
	 * @param s1 -> Status
	 * @param s2 -> Status
	 * @return max(s1, s2)
	 */
	public static Status maxStatus(Status s1, Status s2) {
		if (s1.ordinal() <= s2.ordinal())
			return s2;
		else
			return s1;
	}
	
	/**
	 * Perform sequence between two status. Please note condition of the else-if clause:
	 *  
	 * (s1 == Status.DELETED && s2 == Status.READWRITE)
	 * 
	 * s2 is READWRITE because we assume that function formal parameter are READWRITE.
	 * 
	 * @param s1 -> initial Status
	 * @param s2 -> final Status
	 * @return s1 seq s2
	 */
	public static Status seqStatus(Status s1, Status s2) {
		Status tmp = maxStatus(s1, s2);
		if (tmp.ordinal() <= Status.READWRITE.ordinal())
			return (tmp);
		else if ((s1.ordinal() <= Status.READWRITE.ordinal() && s2 == Status.DELETED)
				|| (s1 == Status.DELETED && s2 == Status.READWRITE))
			return Status.DELETED;
		else
			return Status.ERROR;
	}
	
	/**
	 * Perform par operation between two status. This is used only for aliasing.
	 * 
	 * @param s1 -> Status
	 * @param s2 -> Status
	 * @return s1 par s2
	 */
	public static Status parStatus(Status s1, Status s2) {
		return maxStatus(seqStatus(s1, s2), seqStatus(s2, s1));
	}

	/**
	 * Return a copy of the environment. New environment has different memory locations from
	 * the input environment.
	 * 
	 * @param env to copy
	 * @return new environment 
	 */
	public static Environment cloneEnvironment(Environment env) {
		Environment newEnv = new Environment(env.nestingLevel, env.offset);
		for (HashMap<String, STentry> map : env.symTable) {
			newEnv.symTable.add(new HashMap<>());
			for (Map.Entry<String, STentry> entry : map.entrySet()) {
				HashMap<String, STentry> newMap = newEnv.symTable.get(newEnv.symTable.size() - 1);
				int newNestingLevel = entry.getValue().getNestinglevel();
				Node newType = entry.getValue().getType();
				int newPointLevel = newType.getPointLevel();
				Status newStatus = newType.getStatus();
				int newOffset = entry.getValue().getOffset();
				Node newEnvType;
				if (newType instanceof ArgNode) {
					System.out.println("ERRORE FATALE");
					System.out.println("GAGAGAGAGAGAGA");
					System.exit(0);
				}
				if (newType instanceof BoolTypeNode) {
					newEnvType = new BoolTypeNode(newPointLevel, newStatus);
				} else if (newType instanceof IntTypeNode) {
					newEnvType = new IntTypeNode(newPointLevel, newStatus);
				} else {
					ArrayList<ArgNode> newArgList = new ArrayList<>();
					ArrowTypeNode tmpArrowType = ((ArrowTypeNode) newType);
					for (ArgNode arg : tmpArrowType.getArgList()) {
						ArgNode tmpArgNode = arg;
						String argId = tmpArgNode.getId();
						Node newArgType = tmpArgNode.getType();
						int newArgPoint = newArgType.getPointLevel();
						Status newArgStatus = newArgType.getStatus();
						Node newEnvArgType;
						if (newArgType instanceof BoolTypeNode) {
							newEnvArgType = new BoolTypeNode(newArgPoint, newArgStatus);
						} else {
							newEnvArgType = new IntTypeNode(newArgPoint, newArgStatus);
						}
						newArgList.add(new ArgNode(argId, newEnvArgType));
					}
					Node newArrowRetType = ((ArrowTypeNode) newType).getRet();
					Node newEnvArrowRetType;

					if (newArrowRetType instanceof BoolTypeNode) {
						newEnvArrowRetType = new BoolTypeNode(0, Status.DECLARED);
					} else if (newArrowRetType instanceof IntTypeNode) {
						newEnvArrowRetType = new IntTypeNode(0, Status.DECLARED);
					} else {
						newEnvArrowRetType = new VoidTypeNode(Status.DECLARED);
					}
					newEnvType = new ArrowTypeNode(newArgList, newEnvArrowRetType);
				}
				newMap.put(entry.getKey(), new STentry(newNestingLevel, newEnvType, newOffset));
			}
		}

		return newEnv;
	}
	
	/**
	 * Auxiliary function for interpreter code.
	 * 
	 * @param register
	 * @return
	 */
	public static int getRegister(Token register) {
		switch (register.getText()) {
		case "$a0":
			return SVMParser.A0;
		case "$t0":
			return SVMParser.T0;
		case "$sp":
			return SVMParser.SP;
		case "$ra":
			return SVMParser.RA;
		case "$fp":
			return SVMParser.FP;
		case "$al":
			return SVMParser.AL;
		case "$hp":
			return SVMParser.HP;
		case "$rv":
			return SVMParser.RV;
		default:
			return -1;
		}
	}

}