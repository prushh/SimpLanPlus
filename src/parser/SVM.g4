grammar SVM;

@header {
import java.util.HashMap;
}

@lexer::members {
public int lexicalErrors=0;
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
  
assembly: (instruction)* ;

instruction:
    ( PUSH n=NUMBER 
	  | PUSH l=LABEL
	  | PUSH r=REGISTER
	  | POP
	  | ADD	r1=REGISTER r2=REGISTER res=REGISTER
	  | SUB	r1=REGISTER r2=REGISTER res=REGISTER
	  | MULT r1=REGISTER r2=REGISTER res=REGISTER
	  | DIV	r1=REGISTER r2=REGISTER res=REGISTER
      | STOREW val=REGISTER dest=REGISTER offset=NUMBER
	  | LOADW val=REGISTER source=REGISTER offset=NUMBER
	  | l=LABEL COL
	  | BRANCH l=LABEL
	  | BRANCHEQ l=LABEL e1=REGISTER e2=REGISTER
	  | BRANCHLESSEQ l=LABEL e1=REGISTER e2=REGISTER
	  | JAL l=LABEL
      | JR ra=REGISTER
	  | LOADRA dest=REGISTER ra=REGISTER
	  | STORERA source=REGISTER ra=REGISTER
	  | LOADRV dest=REGISTER rv=REGISTER
	  | STORERV source=REGISTER rv=REGISTER
	  | LOADFP dest=REGISTER fp=REGISTER
	  | STOREFP source=REGISTER fp=REGISTER
	  | COPYFP stack=REGISTER frame=REGISTER
	  | LOADHP dest=REGISTER hp=REGISTER
	  | STOREHP source=REGISTER hp=REGISTER
	  | PRINT val=REGISTER
	  | HALT
	  ) ;

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

PUSH  	 : 'push' ; 	// pushes constant in the stack
POP	 : 'pop' ; 	// pops from stack
ADD	 : 'add' ;  	// add two values from the stack
SUB	 : 'sub' ;	// add two values from the stack
MULT	 : 'mult' ;  	// add two values from the stack
DIV	 : 'div' ;	// add two values from the stack
STOREW	 : 'sw' ; 	// store in the memory cell pointed by top the value next
LOADW	 : 'lw' ;	// load a value from the memory cell pointed by top
BRANCH	 : 'b' ;	// jump to label
BRANCHEQ : 'beq' ;	// jump to label if top == next
BRANCHLESSEQ:'bleq' ;	// jump to label if top <= next
JR       : 'jr' ;   // --todo--
JAL       : 'jal' ;     // --todo--
LOADRA	 : 'lra' ;	// load from ra
STORERA  : 'sra' ;	// store top into ra
LOADRV	 : 'lrv' ;	// load from rv
STORERV  : 'srv' ;	// store top into rv
LOADFP	 : 'lfp' ;	// load frame pointer in the stack
STOREFP	 : 'sfp' ;	// store top into frame pointer
COPYFP   : 'cfp' ;      // copy stack pointer into frame pointer
LOADHP	 : 'lhp' ;	// load heap pointer in the stack
STOREHP	 : 'shp' ;	// store top into heap pointer
PRINT	 : 'print' ;	// print top of stack
HALT	 : 'halt' ;	// stop execution

COL	 : ':' ;
LABEL	 : ('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;
NUMBER	 : '0' | ('-')?(('1'..'9')('0'..'9')*) ;
REGISTER : '$a0' | '$t0' | '$sp' | '$ra' | '$fp' | '$al';

WHITESP  : ( '\t' | ' ' | '\r' | '\n' )+   -> channel(HIDDEN);

ERR   	 : . { System.err.println("Invalid char: "+ getText()); lexicalErrors++;  } -> channel(HIDDEN); 

