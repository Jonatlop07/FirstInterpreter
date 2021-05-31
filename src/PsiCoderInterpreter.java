import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class PsiCoderInterpreter {
    public static void main( String[] args ) throws IOException {
        int i = 1;
        for (i = 16; i < 17; ++i) {
            System.setIn( new FileInputStream( new File( "input/in_"+i+".txt" ) ) );
            ANTLRInputStream input = new ANTLRInputStream( System.in );
            PsiCoderLexer lexer = new PsiCoderLexer( input );
            CommonTokenStream tokens = new CommonTokenStream( lexer );
            PsiCoderParser parser = new PsiCoderParser( tokens );
            ParseTree tree = parser.program();
    
            PsiCoderVisitorImpl loader = new PsiCoderVisitorImpl(
                new Scope( null, false ),
                new HashMap<String, Function>()
            );
            loader.visit( tree );
        }
    }
}
