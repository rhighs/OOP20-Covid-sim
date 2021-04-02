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
        if(dependencies.keySet().contains(key)){
            dependencies.put(key, object);
        }
    }
    
    //only returns the object if they type matches
    public static <T> Object getDependency(final String key, Class<T> c){
        if(dependencies.get(key) != null && c.isAssignableFrom(dependencies.get(key).getClass())){
            return dependencies.get(key);
        }
        
        return null;
    }
    
}
