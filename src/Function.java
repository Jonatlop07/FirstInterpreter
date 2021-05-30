import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Function {
    private Scope parentScope;
    private PsiCoderType returnType;
    private List<FunctionParameter> parameters;
    private ParseTree instructionsCtx, returnExpCtx;
    
    public Function( Scope parentScope, PsiCoderType returnType, List<FunctionParameter> parameters,
                     ParseTree instructionsCtx, ParseTree returnExpCtx ) {
        this.parentScope = parentScope;
        this.returnType = returnType;
        this.parameters = parameters;
        this.instructionsCtx = instructionsCtx;
        this.returnExpCtx = returnExpCtx;
    }
    
    public PsiCoderType makeCall( String identifier, List<PsiCoderType> arguments,
                                  Map<String, Function> functions) {
        Scope scope = new Scope( parentScope, true );
        
        for ( int i = 0; i < arguments.size(); ++i ) {
            if ( !arguments.get( i ).isCompatible( parameters.get( i ).getValue() ) ) {
                System.err.printf( "Error semantico: En la funcion " + identifier + ", tipo "
                    + "incorrecto en el argumento " + parameters.get( i ).getIdentifier() );
                System.exit( -1 );
            }
            scope.add( parameters.get( i ).getIdentifier(), Optional.of( arguments.get( i ) ) );
        }
        
        PsiCoderVisitorImpl functionVisitor = new PsiCoderVisitorImpl( scope, functions );
        functionVisitor.visit( instructionsCtx );
        PsiCoderType returnValue = functionVisitor
            .visitReturnExpression( ( PsiCoderParser.ReturnExpressionContext ) returnExpCtx );
        if ( !returnType.isCompatible( returnValue ) ) {
            System.err.printf( "Error semantico: En la funcion " + identifier + ", valor de retorno "
                + " del tipo incorrecto" );
            System.exit( -1 );
        }
        return returnValue;
    }
}
