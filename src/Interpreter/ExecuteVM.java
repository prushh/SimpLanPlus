package Interpreter;

import parser.SVMParser;

public class ExecuteVM {

    public static final int CODESIZE = 10000;
    public static final int MEMSIZE = 10000;

    private int[] code;
    private int[] memory = new int[MEMSIZE];

    private int ip = 0;
    private int sp = MEMSIZE;
    private int hp = -1;
    private int fp = MEMSIZE - 1;
    private int a0;
    private int t0;
    private int ra;
    private int rv;
    private int al;

    public ExecuteVM(int[] code) {
        this.code = code;
    }

    public void cpu() {
        while (true) {
            if (hp + 1 >= sp) {
                System.out.println("\nError: Out of memory");
                return;
            } else {
                int bytecode = code[ip++]; // fetch
                int v1, v2, v3;
                int address;
                switch (bytecode) {
                    case SVMParser.PUSH:
                        push(getRegister(code[ip++]));
                        break;
                    case SVMParser.POP:
                        pop();
                        break;
                    case SVMParser.ADD:
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        setRegister(code[ip++], v1 + v2);
                        break;
                    case SVMParser.ADDI:
                        setRegister(code[ip], getRegister(code[ip++]) + code[ip++]);
                        break;
                    case SVMParser.SUB:
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        setRegister(code[ip++], v1 - v2);
                        break;
                    case SVMParser.MULT:
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        setRegister(code[ip++], v1 * v2);
                        break;
                    case SVMParser.DIV:
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        setRegister(code[ip++], v1 / v2);
                        break;
                    case SVMParser.STOREW:
                        v1 = getRegister(code[ip++]);
                        memory[getRegister(code[ip++])] = v1;
                        break;
                    case SVMParser.STOREI:
                        v1 = code[ip++];
                        memory[getRegister(code[ip++])] = v1;
                        break;
                    case SVMParser.LOADW:
                        // check if object address where we take the method label
                        // is null value (-10000)
                        if (sp < 10000) {
                            if (memory[sp] == -10000) {
                                System.out.println("\nError: Null pointer exception");
                                return;
                            }
                        }
                        setRegister(code[ip++], memory[getRegister(code[ip++])]);
                        break;
                    case SVMParser.LOADI: //
                        // check if object address where we take the method label
                        // is null value (-10000)
                        if (sp < 10000) {
                            if (memory[sp] == -10000) {
                                System.out.println("\nError: Null pointer exception");
                                return;
                            }
                        }
                        setRegister(code[ip++], code[ip++]);
                        break;
                    case SVMParser.LABEL:
                        break;
                    case SVMParser.BRANCH:
                        address = code[ip];
                        ip = address;
                        break;
                    case SVMParser.BRANCHEQ: //
                        address = code[ip++];
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        if (v2 == v1) ip = address;
                        break;
                    case SVMParser.BRANCHLESSEQ:
                        address = code[ip++];
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        if (v2 > v1) ip = address;
                        break;
                    case SVMParser.LESS:
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        if (v1 < v2)
                            v3 = 1;
                        else
                            v3 = 0;
                        setRegister(code[ip++], v3);
                        break;
                    case SVMParser.LESSEQ:
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        if (v1 <= v2)
                            v3 = 1;
                        else
                            v3 = 0;
                        setRegister(code[ip++], v3);
                        break;
                    case SVMParser.EQ:
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        if (v1 == v2)
                            v3 = 1;
                        else
                            v3 = 0;
                        setRegister(code[ip++], v3);
                        break;
                    case SVMParser.NEQ:
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        if (v1 != v2)
                            v3 = 1;
                        else
                            v3 = 0;
                        setRegister(code[ip++], v3);
                        break;
                    case SVMParser.AND:
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        if (v1 + v2 == 2)
                            v3 = 1;
                        else
                            v3 = 0;
                        setRegister(code[ip++], v3);
                        break;
                    case SVMParser.OR:
                        v1 = getRegister(code[ip++]);
                        v2 = getRegister(code[ip++]);
                        if (v1 + v2 != 0)
                            v3 = 1;
                        else
                            v3 = 0;
                        setRegister(code[ip++], v3);
                        break;
                    case SVMParser.JR: //
                        ip = ra;
                        break;
                    case SVMParser.JAL: //
                        address = code[ip++];
                        ra = ip;
                        ip = address;
                        break;
                    case SVMParser.LOADRA: //
                        a0 = ra;
                        break;
                    case SVMParser.STORERA: //
                        ra = a0;
                        break;
                    case SVMParser.LOADRV: //
                        a0 = rv;
                        break;
                    case SVMParser.STORERV: //
                        rv = a0;
                        break;
                    case SVMParser.LOADFP: //
                        a0 = fp;
                        break;
                    case SVMParser.STOREFP: //
                        fp = a0;
                        break;
                    case SVMParser.COPYFP: //
                        fp = sp;
                        break;
                    case SVMParser.LOADAL: //
                        a0 = al;
                        break;
                    case SVMParser.STOREAL: //
                        al = a0;
                        break;
                    case SVMParser.COPYAL: //
                        al = fp;
                        break;
                    case SVMParser.LOADHP: //
                        a0 = hp;
                        break;
                    case SVMParser.STOREHP: //
                        hp = a0;
                        break;
                    case SVMParser.PRINT:
                        System.out.println(getRegister(code[ip++]));
                        break;
                    case SVMParser.HALT:
                        //to print the result
                        System.out.println("--------------------------------------------------------------------------");
                        System.out.println("\nExit: no Errors -- Halt\n");
                        System.exit(0);
                        return;
                    default:
                        System.out.println("--------------------------------------------------------------------------");
                        System.out.println("\nExit: no Errors \n");
                        System.exit(0);
                }
            }
        }
    }

    private void setRegister(int token, int val) {
        switch (token) {
            case SVMParser.A0:
                this.a0 = val;
                break;
            case SVMParser.T0:
                this.t0 = val;
                break;
            case SVMParser.RA:
                this.ra = val;
                break;
            case SVMParser.SP:
                this.sp = val;
                break;
            case SVMParser.FP:
                this.fp = val;
                break;
            case SVMParser.AL:
                this.al = val;
                break;
            case SVMParser.HP:
                this.hp = val;
                break;
            case SVMParser.RV:
                this.rv = val;
                break;
            default:
                break;
        }
    }

    private int getRegister(int token) {
        switch (token) {
            case SVMParser.A0:
                return this.a0;
            case SVMParser.T0:
                return this.t0;
            case SVMParser.RA:
                return this.ra;
            case SVMParser.SP:
                return this.sp;
            case SVMParser.FP:
                return this.fp;
            case SVMParser.AL:
                return this.al;
            case SVMParser.HP:
                return this.hp;
            case SVMParser.RV:
                return this.rv;
            default:
                return -1;
        }
    }

    private int pop() {
        return memory[sp++];
    }

    private void push(int v) {
        memory[--sp] = v;
    }

}