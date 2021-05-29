import java.util.HashMap;
import java.util.Map;

public class Struct {
    private Map<String, PsiCoderType> atomic = new HashMap<>();
    private Map<String, String> compound = new HashMap<>();
    
    public Map<String, PsiCoderType> getAtomic() {
        return atomic;
    }
    
    public Map<String, String> getCompound() {
        return compound;
    }
}

