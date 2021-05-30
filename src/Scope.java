import java.util.*;

public class Scope {
    
    private Scope parentScope;
    private Map<String, Optional<PsiCoderType>> variables;
    private Map<String, Struct> structs;
    private boolean isAFunction;
    
    public static Map<String, StructDeclaration> structsDeclared = new HashMap<>();
    
    public Scope( Scope parentScope, boolean isAFunction ) {
        this.parentScope = parentScope;
        this.isAFunction = isAFunction;
        variables = new HashMap<>();
        structs = new HashMap<>();
    }
    
    public Scope getParentScope() {
        return parentScope;
    }
    
    public void addArgument( String identifier, Optional<PsiCoderType> value ) {
        variables.put( identifier, value );
    }
    
    public void add( String identifier, Optional<PsiCoderType> value ) {
        if ( searchId( identifier, !isAFunction ) != null )
            update( identifier, value );
        else variables.put( identifier, value );
    }
    
    public void add( String identifier, String structId ) {
        structs.put( identifier,
            new Struct(
                structsDeclared.get( structId ).getAtomic(),
                structsDeclared.get( structId ).getCompound()
            )
        );
    }
    
    /* public Optional<PsiCoderType> searchId( String identifier ) {
        return searchId( identifier, true );
    }*/
    
    /*public Optional<PsiCoderType> searchId( String identifier, boolean searchInParentScope ) {
        Optional variableValue = variables.get( identifier );
        if ( variableValue != null )
            return variableValue;
        if ( parentScope != null && searchInParentScope )
            return parentScope.searchId( identifier, !parentScope.isAFunction );
        return null;
    }*/
    
    public void assignStructMember( List<String> literals, PsiCoderType value ) {
        String identifier = literals.get( 0 );
        Struct struct = structs.get( identifier );
        if ( struct == null ) {
            if ( parentScope == null )
                throw new RuntimeException( "La variable " + identifier + " no se encuentra declarada" );
            parentScope.assignStructMember( literals, value );
        } else {
            literals.remove( 0 );
            structs.put( identifier, struct.assignMemberValue( literals, value ) );
        }
    }
    
    public Optional<PsiCoderType> getStructMemberValue( List<String> literals ) {
        Struct struct = ( Struct ) searchId( literals.get( 0 ) ).get();
        literals.remove( 0 );
        return struct.getMemberValue( literals );
    }
    
    public Optional searchId( String identifier ) {
        return searchId( identifier, true );
    }
    
    public Optional searchId( String identifier, boolean searchInParentScope ) {
        Optional<PsiCoderType> variableValue = variables.get( identifier );
        if ( variableValue != null )
            return variableValue;
        Struct struct = structs.get( identifier );
        if ( struct != null )
            return Optional.of( struct );
        if ( parentScope != null && searchInParentScope )
            return parentScope.searchId( identifier, !parentScope.isAFunction );
        return null;
    }
    
    private void update( String identifier, Optional<PsiCoderType> value ) {
        if ( variables.containsKey( identifier ) )
            variables.put( identifier, value );
        else if ( parentScope != null ) {
            parentScope.update( identifier, value );
        }
    }
    
    private class Struct {
        private Map<String, Optional<PsiCoderType>> primitiveTypeMembers;
        private Map<String, Struct> structTypeMembers;
        
        public Struct(
            Map<String, Optional<PsiCoderType>> primitiveTypeMembers,
            Map<String, String> structTypeMembers
        ) {
            this.primitiveTypeMembers = primitiveTypeMembers;
            this.structTypeMembers = new HashMap<>();
            Iterator<String> keysIterator = structTypeMembers.keySet().stream().iterator();
            while ( keysIterator.hasNext() ) {
                String structMemberIdentifier = keysIterator.next();
                this.structTypeMembers.put(
                    structMemberIdentifier,
                    new Struct(
                        structsDeclared.get( keysIterator ).getAtomic(),
                        structsDeclared.get( keysIterator ).getCompound()
                    )
                );
            }
        }
        
        public Optional<PsiCoderType> getMemberValue( List<String> literals ) {
            if ( literals.isEmpty() )
                throw new RuntimeException( "Error semantico: No se puede emplear una variable de tipo estructura en una expresion." );
            if ( this.primitiveTypeMembers.containsKey( literals.get( 0 ) ) ) {
                return this.primitiveTypeMembers.get( literals.get( 0 ) );
            } else if ( this.structTypeMembers.containsKey( literals.get( 0 ) ) ) {
                String structMemberLiteral = literals.remove( 0 );
                return this.structTypeMembers.get( structMemberLiteral ).getMemberValue( literals );
            } else throw new RuntimeException( "El literal " + literals.get( 0 ) + " no existe." );
        }
        
        public Struct assignMemberValue( List<String> literals, PsiCoderType value ) {
            if ( literals.isEmpty() )
                throw new RuntimeException( "Error semantico: No esta permitido asignar una variable de tipo estructura a otra" );
            
            String identifier = literals.get( 0 );
            PsiCoderType primitiveValue = this.primitiveTypeMembers.get( identifier ).get();
            Struct structMember = this.structTypeMembers.get( identifier );
            
            if ( primitiveValue != null ) {
                if ( primitiveValue.isCompatible( value ) ) {
                    if ( primitiveValue.isInteger() && value.isReal() )
                        this.primitiveTypeMembers.put( identifier, Optional.of( new PsiCoderType( value.toInteger() ) ) );
                    else
                        this.primitiveTypeMembers.put( identifier, Optional.of( value ) );
                } else {
                    throw new RuntimeException( "Error semantico: El valor asignado al miembro " + identifier + " no es del tipo esperado" );
                }
            } else if ( structMember != null ) {
                literals.remove( 0 );
                this.structTypeMembers.put( identifier, structMember.assignMemberValue( literals, value ) );
            } else throw new RuntimeException( "Error semantico: El literal " + literals.get( 0 ) + " no existe." );
            return this;
        }
    }
    
}
