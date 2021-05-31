import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.Map;

public class Function {
    private final Scope parentScope;
    private final PsiCoderType returnType;
    private final List<FunctionParameter> parameters;
    private final ParseTree instructionsCtx, returnExpCtx;
    
    public Function( Scope parentScope, PsiCoderType returnType, List<FunctionParameter> parameters,
                     ParseTree instructionsCtx, ParseTree returnExpCtx ) {
        this.parentScope = parentScope;
        this.returnType = returnType;
        this.parameters = parameters;
        this.instructionsCtx = instructionsCtx;
        this.returnExpCtx = returnExpCtx;
    }
    
    public PsiCoderType makeCall( String identifier, List<PsiCoderType> arguments, Map<String, Function> functions ) {
        Scope scope = new Scope( parentScope, true );
        for ( int i = 0; i < arguments.size(); ++i ) {
            String parameterIdentifier = parameters.get( i ).getIdentifier();
            PsiCoderType parameterValue = parameters.get( i ).getValue();
            if ( parameterValue.isCompatible( arguments.get( i ) ) ) {
                if (
                    parameterValue.isStruct()
                        && !parameterValue.toStruct().getType().equals( arguments.get( i ).toStruct().getType() )
                )
                    SemanticError.throwError( "En la funcion '" + identifier + "', tipo "
                        + "de estructura incorrecto en el parametro '" + parameterIdentifier + "'" );
                scope.add( parameterIdentifier, arguments.get( i ) );
            } else
                SemanticError.throwError( "En la funcion '" + identifier + "', tipo "
                    + "incorrecto en el parametro '" + parameterIdentifier + "'" );
        }
        PsiCoderVisitorImpl functionVisitor = new PsiCoderVisitorImpl( scope, functions );
        functionVisitor.visit( instructionsCtx );
        PsiCoderType returnValue = functionVisitor.visitReturnExpression(
            ( PsiCoderParser.ReturnExpressionContext ) returnExpCtx
        );
        
        if ( !returnType.isCompatible( returnValue ) )
            SemanticError.throwError( "En la funcion '" + identifier + "', el valor retornado es de un tipo incorrecto" );
        return returnValue;
    }
}
