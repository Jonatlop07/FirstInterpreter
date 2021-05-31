import java.util.*;

public class Scope {
    
    private final Scope parentScope;
    private final Map<String, PsiCoderType> variables;
    private final boolean isAFunction;
    
    public static Map<String, StructDeclaration> structsDeclared = new HashMap<>();
    
    public Scope( Scope parentScope, boolean isAFunction ) {
        this.parentScope = parentScope;
        this.isAFunction = isAFunction;
        variables = new HashMap<>();
    }
    
    public Scope getParentScope() {
        return parentScope;
    }
    
    private void update( String identifier, PsiCoderType value ) {
        if ( variables.containsKey( identifier ) )
            variables.put( identifier, value );
        else if ( parentScope != null ) {
            parentScope.update( identifier, value );
        }
    }
    
    public void add( String identifier, PsiCoderType value ) {
        if ( searchId( identifier, !isAFunction ) != null )
            update( identifier, value );
        else variables.put( identifier, value );
    }
    
    public PsiCoderType searchId( String identifier ) {
        return searchId( identifier, true );
    }
    
    public PsiCoderType searchId( String identifier, boolean searchInParentScope ) {
        PsiCoderType variableValue = variables.get( identifier );
        if ( variableValue != null )
            return variableValue;
        if ( parentScope != null && searchInParentScope )
            return parentScope.searchId( identifier, !parentScope.isAFunction );
        return null;
    }
    
    public void assignToStructMember( List<String> literals, PsiCoderType value ) {
        String identifier = literals.get( 0 );
        PsiCoderType result = searchId( identifier );
        if ( result != null ) {
            if ( result.isStruct() ) {
                literals.remove( 0 );
                update( identifier, result.toStruct().assignMemberValue( literals, value ) );
            } else
                SemanticError.throwError( "La variable '" + identifier + "' no es de tipo estructura" );
        } else
            SemanticError.throwError( "La variable '" + identifier + "' no se encuentra declarada" );
    }
    
    public PsiCoderType getStructMemberValue( List<String> literals ) {
        String identifier = literals.get( 0 );
        PsiCoderType variable = searchId( identifier );
        if ( variable != null ) {
            if ( variable.isStruct() ) {
                literals.remove( 0 );
                return variable.toStruct().getMemberValue( literals );
            } else
                SemanticError.throwError( "La variable '" + identifier + "' no es de tipo estructura" );
        } else
            SemanticError.throwError( "La variable '" + identifier + "' no se encuentra declarada" );
        return null;
    }
}
