package util;

import java.util.ArrayList;
import java.util.HashMap;

import ast.STentry;

/**
 * Symbol table implementation as a list of hash maps.
 */

public class Environment {

	public ArrayList<HashMap<String,STentry>>  symTable = new ArrayList<>();
	public int nestingLevel = -1;
	public int offset = 0;

	public Environment(int nestingLevel, int offset){
		this.nestingLevel = nestingLevel;
		this.offset = offset;
	}
	public Environment() {

	}
}
