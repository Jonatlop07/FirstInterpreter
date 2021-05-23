import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class PsiCoderInterpreter {
    public static void main( String[] args ) {
        PsiCoderLexer lexer = new PsiCoderLexer( CharStreams.fromFileName( "input.txt" ) );
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        PsiCoderParser parser = new PsiCoderParser( tokens );
        ParseTree tree = parser.program();
        
        PsiCoderVisitorImpl<Object> loader = new PsiCoderVisitorImpl< ~ > ( );
    }
}
