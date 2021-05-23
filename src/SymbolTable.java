import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private SymbolTable parentScopeTable;
    private Map<String, Object> table;
    
    public SymbolTable( SymbolTable parentScopeTable ) {
        table = new HashMap<>();
        this.parentScopeTable = parentScopeTable;
    }
    
    public void addSymbol( String id, Object value, String type ) {
        table.put( id, value );
    }
    
    public boolean hasSymbol( String id ) {
        if (!table.containsKey( id )) {
            if (parentScopeTable == null) return false;
            return parentScopeTable.hasSymbol( id );
        }
        return true;
    }
    
    public Object getValue (String id) {
        return table.get( id );
    }
}
