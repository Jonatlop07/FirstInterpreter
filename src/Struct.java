import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Struct {
    
    private final String type;
    private Map<String, PsiCoderType> members;
    
    public Struct( String type, Map<String, PsiCoderType> primitiveTypeMembers,
                   Map<String, String> structTypeMembers ) {
        this.type = type;
        this.members = primitiveTypeMembers;
        if ( !structTypeMembers.isEmpty() ) {
            Iterator<String> keysIterator = structTypeMembers.keySet().stream().iterator();
            while ( keysIterator.hasNext() ) {
                String memberIdentifier = keysIterator.next();
                String structName = structTypeMembers.get( memberIdentifier );
                this.members.put(
                    memberIdentifier,
                    new PsiCoderType(
                        new Struct(
                            structName,
                            Scope.structsDeclared.get( structName ).getAtomic(),
                            Scope.structsDeclared.get( structName ).getCompound()
                        )
                    )
                );
            }
        }
    }
    
    public PsiCoderType getMemberValue( List<String> literals ) {
        if ( literals.isEmpty() )
            return new PsiCoderType( this );
        if ( this.members.containsKey( literals.get( 0 ) ) ) {
            if ( this.members.get( literals.get( 0 ) ).isStruct() ) {
                String structMemberLiteral = literals.remove( 0 );
                return this.members.get( structMemberLiteral ).toStruct().getMemberValue( literals );
            }
            return this.members.get( literals.get( 0 ) );
        } else
            SemanticError.throwError( "El literal '" + literals.get( 0 ) + "' no existe." );
        return null;
    }
    
    public PsiCoderType assignMemberValue( List<String> literals, PsiCoderType value ) {
        String identifier = literals.get( 0 );
        PsiCoderType member = this.members.get( identifier );
        if ( member != null ) {
            if ( member.isStruct() ) {
                literals.remove( 0 );
                if ( !literals.isEmpty() )
                    this.members.put( identifier, member.toStruct().assignMemberValue( literals, value ) );
            } else {
                if ( member.isCompatible( value ) ) {
                    if ( member.isInteger() && value.isReal() )
                        this.members.put( identifier, new PsiCoderType( value.toInteger() ) );
                    else
                        this.members.put( identifier, value );
                } else
                    SemanticError.throwError( "El valor asignado al miembro '"
                        + identifier + "' no es del tipo esperado" );
            }
        } else
            SemanticError.throwError( "El literal '" + literals.get( 0 ) + "' no existe." );
        return new PsiCoderType( this );
    }
    
    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        String stringRepresentation = "{ ";
        Iterator<String> membersIterator = members.keySet().stream().iterator();
        while ( membersIterator.hasNext() ) {
            String memberIdentifier = membersIterator.next();
            stringRepresentation += memberIdentifier + ": ";
            PsiCoderType member = members.get( memberIdentifier );
            if ( member.isStruct() ) {
                stringRepresentation += member.toStruct().toString();
            } else {
                stringRepresentation += String.valueOf( member.getValue() );
            }
            stringRepresentation += ", ";
        }
        if ( stringRepresentation.length() > 2 ) {
            stringRepresentation = stringRepresentation.substring( 0, stringRepresentation.length() - 2 );
        }
        return stringRepresentation + " }";
    }
}