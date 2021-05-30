import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StructDeclaration {
    private Map<String, Optional<PsiCoderType>> atomic = new HashMap<>();
    private Map<String, String> compound = new HashMap<>();
    
    public Map<String, Optional<PsiCoderType>> getAtomic() {
        return atomic;
    }
    
    public Map<String, String> getCompound() {
        return compound;
    }
}

