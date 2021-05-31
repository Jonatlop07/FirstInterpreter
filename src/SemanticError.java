public class SemanticError {
    private SemanticError() { }
    
    public static void throwError(String message, int row, int column ) {
        System.err.printf( "Error semantico-(%d : %d): %s", row, column, message );
        System.exit( -1 );
    }
    
    public static void throwError( String message ) {
        System.err.println( "Error semantico: " + message );
        System.exit( -1 );
    }
}
