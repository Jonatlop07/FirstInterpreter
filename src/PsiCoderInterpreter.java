import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class PsiCoderInterpreter {
    public static void main( String[] args ) throws IOException {
        System.setIn(new FileInputStream(new File("input/in_1.txt")));
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        PsiCoderLexer lexer = new PsiCoderLexer( input );
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        PsiCoderParser parser = new PsiCoderParser( tokens );
        ParseTree tree = parser.program();
        
        PsiCoderVisitorImpl loader = new PsiCoderVisitorImpl ();
        loader.visit(tree);
    }
}
