public class PsiCoderType implements Comparable<PsiCoderType> {
    private Object value;
    
    public PsiCoderType( Object value ) {
        this.value = value;
    }
    
    public Object getValue() {
        return value;
    }
    
    public Integer toInteger() {
        return ( Integer ) value;
    }
    
    public boolean isInteger() {
        return value instanceof Integer;
    }
    
    public Double toReal() {
        return ( Double ) value;
    }
    
    public boolean isReal() {
        return value instanceof Double;
    }
    
    public Boolean toBoolean() {
        return ( Boolean ) value;
    }
    
    public boolean isBoolean() {
        return value instanceof Boolean;
    }
    
    public Character toCharacter() {
        return ( Character ) value;
    }
    
    public boolean isCharacter() {
        return value instanceof Character;
    }
    
    public String toStringType() {
        return ( String ) value;
    }
    
    public boolean isString() {
        return value instanceof String;
    }
    
    @Override
    public int compareTo( PsiCoderType other ) {
        if ( ( this.isInteger() || this.isReal() ) && ( other.isInteger() || other.isReal() ) ) {
            if ( this.equals( other ) ) return 0;
            else return this.toReal().compareTo( other.toReal() );
        } else if ( this.isString() && other.isString() )
            return this.toStringType().compareTo( other.toStringType() );
        else if ( this.isCharacter() && other.isCharacter() )
            return this.toCharacter().compareTo( other.toCharacter() );
        else if ( this.isBoolean() && other.isBoolean() )
            return this.toBoolean().compareTo( other.toBoolean() );
        else throw new RuntimeException( "No se puede comparar `" + this + "` con `" + other + "`" );
    }
    
    @Override
    public boolean equals( Object other ) {
        if ( this == other ) return true;
        
        PsiCoderType _other = ( PsiCoderType ) other;
        
        if ( ( this.isInteger() || this.isReal() ) && ( _other.isInteger() || _other.isReal() ) )
            return Math.abs( this.toReal() - _other.toReal() ) < 0.0000001;
        else return this.getValue().equals( _other.getValue() );
    }
}
