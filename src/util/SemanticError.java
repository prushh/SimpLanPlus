package util;

/**
 * String error appended to returned lists of checkSemantics, typeCheck or checkEffects.
 */

public class SemanticError {
	
	public final String msg;
	
	public SemanticError(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		
		return msg;
	}
}
