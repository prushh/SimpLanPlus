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
      | JR
	  | LOADRA
	  | STORERA
	  | LOADRV
	  | STORERV
	  | LOADFP
	  | STOREFP
	  | COPYFP
	  | STOREAL
	  | LOADAL
	  | COPYAL
	  | LOADHP
	  | STOREHP
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
LOADRA	 : 'lra' ;	// load $ra into $a0
STORERA  : 'sra' ;	// store $a0 into $ra
LOADRV	 : 'lrv' ;	// load $rv into $a0
STORERV  : 'srv' ;	// store $a0 into rv
LOADFP	 : 'lfp' ;	// load $fp into $a0
STOREFP	 : 'sfp' ;	// store $a0 into $fp
COPYFP   : 'cfp' ;  // copy $sp into $fp
LOADAL   : 'lal' ;  // load $a0 into $al
STOREAL  : 'sal' ;  // store $al into $a0
COPYAL   : 'cal' ;  // copy $fp intp $al
LOADHP	 : 'lhp' ;	// load $hp into $a0
STOREHP	 : 'shp' ;	// store $a0 into $hp
PRINT	 : 'print' ; // print value of $a0
HALT	 : 'halt' ;	// stop execution

COL	 : ':' ;
LABEL	 : ('__')?('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;
NUMBER	 : '0' | ('-')?(('1'..'9')('0'..'9')*) ;
REGISTER : A0 | T0 | SP | RA | FP | AL | HP;
A0 : '$a0';
T0 : '$t0';
SP : '$sp';
RA : '$ra';
FP : '$fp';
AL : '$al';
HP : '$hp';

WHITESP  : ( '\t' | ' ' | '\r' | '\n' )+   -> channel(HIDDEN);

ERR   	 : . { System.err.println("Invalid char: "+ getText()); lexicalErrors++;  } -> channel(HIDDEN); 

