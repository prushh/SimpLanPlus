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
	  | ADDI r1=REGISTER val=NUMBER
	  | SUB	r1=REGISTER r2=REGISTER res=REGISTER
	  | MULT r1=REGISTER r2=REGISTER res=REGISTER
	  | DIV	r1=REGISTER r2=REGISTER res=REGISTER
      | STOREW val=REGISTER dest=REGISTER
      | STOREI val=NUMBER dest=REGISTER
	  | LOADW val=REGISTER source=REGISTER
	  | LOADI val=REGISTER source=NUMBER
	  | l=LABEL COL
	  | BRANCH l=LABEL
	  | BRANCHEQ l=LABEL e1=REGISTER e2=REGISTER
	  | BRANCHLESSEQ l=LABEL e1=REGISTER e2=REGISTER
	  | LESS e1=REGISTER e2=REGISTER res=REGISTER
	  | LESSEQ e1=REGISTER e2=REGISTER res=REGISTER
	  | EQ e1=REGISTER e2=REGISTER res=REGISTER
	  | NEQ e1=REGISTER e2=REGISTER res=REGISTER
	  | AND e1=REGISTER e2=REGISTER res=REGISTER
	  | OR e1=REGISTER e2=REGISTER res=REGISTER
	  | JAL l=LABEL
      | JR r=REGISTER
	  | LOADRA
	  | STORERA
	  | LOADRV
	  | STORERV
	  | LOADFP
	  | STOREFP
	  | COPYFP
	  | LOADHP source=REGISTER hp=REGISTER
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
ADDI : 'addi';      // --todo--
SUB	 : 'sub' ;	// add two values from the stack
MULT	 : 'mult' ;  	// add two values from the stack
DIV	 : 'div' ;	// add two values from the stack
STOREW	 : 'sw' ; 	// store in the memory cell pointed by top the value next
STOREI    : 'si' ;   // --todo--
LOADW	 : 'lw' ;	// load a value from the memory cell pointed by top
LOADI    : 'li' ;   // --todo--
BRANCH	 : 'b' ;	// jump to label
BRANCHEQ : 'beq' ;	// jump to label if top == next
BRANCHLESSEQ:'bleq' ;	// jump to label if top <= next
LESS:'less' ;	// --todo--
LESSEQ:'leq' ;	// --todo--
EQ:'eq' ;	//  --todo--
NEQ:'neq' ;	//  --todo--
AND:'and' ;	// --todo--
OR:'or' ;	// --todo--
JR       : 'jr' ;   // --todo--
JAL       : 'jal' ;     // --todo--
LOADRA	 : 'lra' ;	// load from ra
STORERA  : 'sra' ;	// store $a0
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
LABEL	 : ('__')?('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;
NUMBER	 : '0' | ('-')?(('1'..'9')('0'..'9')*) ;
REGISTER : '$a0' | '$t0' | '$sp' | '$ra' | '$fp' | '$al';

WHITESP  : ( '\t' | ' ' | '\r' | '\n' )+   -> channel(HIDDEN);

ERR   	 : . { System.err.println("Invalid char: "+ getText()); lexicalErrors++;  } -> channel(HIDDEN); 

