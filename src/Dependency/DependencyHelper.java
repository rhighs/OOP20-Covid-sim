package Dependency;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rob
 */
public class DependencyHelper {
    
    public static final Map<String, Object> dependencies = new HashMap<>();
    
    public static void setDependency(final String key, final Object object){
        dependencies.put(key, object);
    }
    
    public static Object getDependency(final String key){
        return dependencies.get(key);
    }
    
}