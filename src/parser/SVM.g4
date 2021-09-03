grammar SVM;

@lexer::members {
public int lexicalErrors=0;
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
  
assembly: (instruction)* ;

instruction:
    (  PUSH r=REGISTER
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
      | JR
	  | JAL l=LABEL
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

PUSH  	    : 'push' ; 	// pushes label/number/register on the stack
POP	        : 'pop' ; 	// pops from stack
ADD	        : 'add' ;  	// puts the result of _r1_+_r2_ in _r3_
ADDI        : 'addi';   // add integer to register
SUB	        : 'sub' ;	// puts the result of _r1_-_r2_ in _r3_
MULT	    : 'mult' ;  // puts the result of _r1_*_r2_ in _r3_
DIV	        : 'div' ;	// puts the result of _r1_/_r2_ in _r3_
STOREW	    : 'sw' ; 	// store in the memory cell pointed by _dest_ the value in _val_ (_val_ is a register)
STOREI      : 'si' ;    // store in the memory cell pointed by _dest_ the value in _val_ (_val_ is a number)
LOADW	    : 'lw' ;	// load a value from the memory cell pointed _source_ inside register _val_
LOADI       : 'li' ;    // load the integer _source_ inside register _val_
BRANCH	    : 'b' ;	    // jump to _label_
BRANCHEQ    : 'beq' ;	// jump to _label_ if _e1_ == _e2_
BRANCHLESSEQ:'bleq' ;	// jump to _label_ if _e1_ <= _e2_
LESS        :'less' ;	// if (_e1_ < _e2_) puts 1 in _res_, else puts 0 in _res_
LESSEQ      :'leq' ;	// if (_e1_ <= _e2_) puts 1 in _res_, else puts 0 in _res_
EQ          :'eq' ;	    // if (_e1_ == _e2_) puts 1 in _res_, else puts 0 in _res_
NEQ         :'neq' ;	// if (_e1_ != _e2_) puts 1 in _res_, else puts 0 in _res_
AND         :'and' ;	// if (_e1_ + _e2_ == 2) puts 1 in _res_, else puts 0 in _res_
OR          :'or' ;	    // if (_e1_ + _e2_ != 0) puts 1 in _res_, else puts 0 in _res_
JR          : 'jr' ;    // store $ra inside $ip
JAL         : 'jal' ;   // store next istruction address in $ra, jump to _label_
LOADRA	    : 'lra' ;	// store $ra into $a0
STORERA     : 'sra' ;	// store $a0 into $ra
LOADRV	    : 'lrv' ;	// store $rv into $a0
STORERV     : 'srv' ;	// store $a0 into rv
LOADFP	    : 'lfp' ;	// store $fp into $a0
STOREFP	    : 'sfp' ;	// store $a0 into $fp
COPYFP      : 'cfp' ;   // copy $sp into $fp
LOADAL      : 'lal' ;   // store $al into $a0
STOREAL     : 'sal' ;   // store $a0 into $al
COPYAL      : 'cal' ;   // copy $fp into $al
LOADHP	    : 'lhp' ;	// store $hp into $a0
STOREHP	    : 'shp' ;	// store $a0 into $hp
PRINT	    : 'print' ; // print value of $a0
HALT	    : 'halt' ;	// stop execution

COL	 : ':' ;
NUMBER	 : '0' | ('-')?(('1'..'9')('0'..'9')*) ;
LABEL	 : ('_')?('_')('a'..'z' | 'A'..'Z' | '0'..'9')+('_') ;
REGISTER : A0 | T0 | SP | RA | FP | AL | HP | RV;
A0 : '$a0';   // register used to perform each operation
T0 : '$t0';   // temporary register
SP : '$sp';   // stack pointer
RA : '$ra';   // return address
FP : '$fp';   // frame pointer
AL : '$al';   // access link
HP : '$hp';   // heap pointer
RV : '$rv';   // return value

WHITESP  : ( '\t' | ' ' | '\r' | '\n' )+   -> channel(HIDDEN);

ERR   	 : . { System.err.println("Invalid char: "+ getText()); lexicalErrors++;  } -> channel(HIDDEN); 

