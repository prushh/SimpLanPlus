package util;

import java.util.ArrayList;
import java.util.HashMap;

import ast.STentry;

/**
 * Symbol table implementation as a list of hash maps.
 */

public class Environment {
	
	//THESE VARIABLES SHOULDN'T BE PUBLIC
	//THIS CAN BE DONE MUCH BETTER
	public ArrayList<HashMap<String,STentry>>  symTable = new ArrayList<HashMap<String,STentry>>();
	public int nestingLevel = -1;
	public int offset = 0;

	public Environment(int nestingLevel, int offset){
		this.nestingLevel = nestingLevel;
		this.offset = offset;
	}
	public Environment(){

	}
	//livello ambiente con dichiarazioni piu' esterno e' 0 (prima posizione ArrayList) invece che 1 (slides)
	//il "fronte" della lista di tabelle e' symTable.get(nestingLevel)
	
}
