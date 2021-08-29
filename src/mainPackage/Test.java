package mainPackage;

import Interpreter.ExecuteVM;
import ast.Node;
import ast.SVMVisitorImpl;
import ast.SimpLanPlusVisitorImpl;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.SVMLexer;
import parser.SVMParser;
import parser.SimpLanPlusLexer;
import parser.SimpLanPlusParser;
import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.ThrowingErrorListener;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws Exception {

        String fileName = "tests/test_25.simplanplus";

        // String fileName = "./examples/example33.simplan";

        FileInputStream is = new FileInputStream(fileName);
        ANTLRInputStream input = new ANTLRInputStream(is);
        SimpLanPlusLexer lexer = new SimpLanPlusLexer(input);
        //ThrowingErrorListener errorListener = new ThrowingErrorListener();
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        SimpLanPlusParser parser = new SimpLanPlusParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

        SimpLanPlusVisitorImpl visitor = new SimpLanPlusVisitorImpl();
        Node ast = visitor.visit(parser.block()); // generazione AST

        Environment env = new Environment();

        ArrayList<SemanticError> err = ast.checkSemantics(env);
        if (err.size() > 0) {
            System.out.println("You had: " + err.size() + " errors:");
            for (SemanticError e : err)
                System.out.println("\t" + e);
        } else {
            System.out.println("Visualizing AST...");
            System.out.println(ast.toPrint(""));

            ArrayList<SemanticError> typeErr = new ArrayList<SemanticError>();
            Node type = ast.typeCheck(typeErr); // type-checking bottom-up
            if (typeErr.size() > 0) {
                System.out.println("You had: " + typeErr.size() + " type errors:");
                for (SemanticError e : typeErr)
                    System.out.println("\t" + e);

            } else {
                System.out.println(type.toPrint("Type checking ok! Type of the program is: "));

                Environment sigma = new Environment();

                ArrayList<SemanticError> effectErr = new ArrayList<SemanticError>();
                effectErr = ast.checkEffects(sigma);
                if (effectErr.size() > 0) {
                    System.out.println("You had: " + effectErr.size() + " effect errors:");
                    for (SemanticError e : effectErr)
                        System.out.println("\t" + e);
                } else {

                    System.out.println(type.toPrint("Effect checking ok! Type of the program is: "));

                    // CODE GENERATION prova.SimpLan.asm
                    String code = ast.codeGeneration(new CGenEnv());
                    BufferedWriter out = new BufferedWriter(new FileWriter(fileName + ".asm"));
                    out.write(code);
                    out.close();
                    System.out.println("Code generated! Assembling and running generated code.");

                    FileInputStream isASM = new FileInputStream(fileName + ".asm");
                    ANTLRInputStream inputASM = new ANTLRInputStream(isASM);
                    SVMLexer lexerASM = new SVMLexer(inputASM);
                    CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);
                    SVMParser parserASM = new SVMParser(tokensASM);

                    // parserASM.assembly();

                    SVMVisitorImpl visitorSVM = new SVMVisitorImpl();
                    visitorSVM.visit(parserASM.assembly());

                    System.out.println("You had: " + lexerASM.lexicalErrors + " lexical errors and "
                            + parserASM.getNumberOfSyntaxErrors() + " syntax errors.");
                    if (lexerASM.lexicalErrors > 0 || parserASM.getNumberOfSyntaxErrors() > 0)
                        System.exit(1);

                    System.out.println("Starting Virtual Machine...");
                    System.out.println("--------------------------------------------------------------------------");
                    ExecuteVM vm = new ExecuteVM(visitorSVM.code);
                    vm.cpu();
                }
            }
        }
    }

}
