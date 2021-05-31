public class PsiCoderType implements Comparable<PsiCoderType> {
    
    private Object value;
    
    public PsiCoderType( Object value ) {
        this.value = value;
    }
    
    public Object getValue() {
        return value;
    }
    
    public Integer toInteger() {
        return ( ( Number ) value ).intValue();
    }
    
    public boolean isInteger() {
        return value instanceof Integer;
    }
    
    public Double toReal() {
        return ( ( Number ) value ).doubleValue();
    }
    
    public boolean isReal() {
        return value instanceof Double;
    }
    
    public boolean isNumber() {
        return value instanceof Number;
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
    
    public Struct toStruct() { return ( Struct ) value; }
    
    public boolean isStruct() { return value instanceof Struct; }
    
    @Override
    public int compareTo( PsiCoderType other ) {
        if ( this.isNumber() && other.isNumber() ) {
            if ( this.equals( other ) ) return 0;
            return this.toReal().compareTo( other.toReal() );
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
        if ( this.isNumber() && _other.isNumber() ) {
            return Math.abs( this.toReal() - _other.toReal() ) < 0.0000001;
        }
        return this.getValue().equals( _other.getValue() );
    }
    
    public boolean isCompatible( PsiCoderType other ) {
        return ( ( this.isNumber() && other.isNumber() )
            || ( this.isBoolean() && other.isBoolean() )
            || ( this.isCharacter() && other.isCharacter() )
            || ( this.isString() && other.isString() )
            || ( this.isStruct() && other.isStruct() )
        );
    }
}
