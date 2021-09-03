package mainPackage;

import Interpreter.ExecuteVM;
import ast.Node;
import ast.SVMVisitorImpl;
import ast.SimpLanPlusVisitorImpl;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
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
import java.io.FileWriter;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws Exception {

        String fileName = "tests/test_1.simplanplus";
        //String fileName = "prova.simplanplus";

        CharStream input = CharStreams.fromFileName(fileName);
        SimpLanPlusLexer lexer = new SimpLanPlusLexer(input);
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
            System.out.println("Scope checking ok!");
            System.out.println("------------------");
            ArrayList<SemanticError> typeErr = new ArrayList<SemanticError>();
            Node type = ast.typeCheck(typeErr); // type-checking bottom-up
            if (typeErr.size() > 0) {
                System.out.println("You had: " + typeErr.size() + " type errors:");
                for (SemanticError e : typeErr)
                    System.out.println("\t" + e);

            } else {
                System.out.println("Type checking ok!");
                System.out.println("------------------");

                Environment sigma = new Environment();

                ArrayList<SemanticError> effectErr = new ArrayList<SemanticError>();
                effectErr = ast.checkEffects(sigma);
                if (effectErr.size() > 0) {
                    System.out.println("You had: " + effectErr.size() + " effect errors:");
                    for (SemanticError e : effectErr)
                        System.out.println("\t" + e);
                } else {

                    System.out.println("Effect checking ok!");
                    System.out.println("------------------");

                    // AST visualization
                    System.out.println("Visualizing AST...\n");
                    System.out.println(ast.toPrint(""));
                    System.out.println("------------------");

                    // CODE GENERATION prova.SimpLan.asm
                    String code = ast.codeGeneration(new CGenEnv());
                    BufferedWriter out = new BufferedWriter(new FileWriter(fileName + ".asm"));
                    out.write(code);
                    out.close();
                    System.out.println("Code generated! Assembling and running generated code.");

                    CharStream inputASM = CharStreams.fromFileName(fileName + ".asm");
                    SVMLexer lexerASM = new SVMLexer(inputASM);
                    CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);
                    SVMParser parserASM = new SVMParser(tokensASM);

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
