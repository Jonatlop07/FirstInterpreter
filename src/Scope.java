import java.util.HashMap;
import java.util.Map;

public class Scope {
    
    private Scope parentScope;
    private Map<String, PsiCoderType> variables;
    private boolean isAFunction;
    
    public Scope( Scope parentScope, boolean isAFunction ) {
        this.parentScope = parentScope;
        this.isAFunction = isAFunction;
        variables = new HashMap<>();
    }
    
    public Scope getParentScope() {
        return parentScope;
    }
    
    public void addArgument( String identifier, PsiCoderType value ) {
        variables.put( identifier, value );
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
        PsiCoderType value = variables.get( identifier );
        
        if ( value != null )
            return value;
        else if ( parentScope != null && searchInParentScope )
            return parentScope.searchId( identifier, !parentScope.isAFunction );
        return null;
    }
    
    private void update( String identifier, PsiCoderType value ) {
        if ( variables.containsKey( identifier ) )
            variables.put( identifier, value );
        else if ( parentScope != null ) {
            parentScope.update( identifier, value );
        }
    }
    
}
