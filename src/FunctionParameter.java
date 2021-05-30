public class FunctionParameter {
    
    private final String identifier;
    private final PsiCoderType value;
    
    public FunctionParameter( String identifier, PsiCoderType value ) {
        this.identifier = identifier;
        this.value = value;
    }
    
    public String getIdentifier() {
        return identifier;
    }
    
    public PsiCoderType getValue() {
        return value;
    }
}
