package Database;

import java.lang.reflect.Constructor;
import java.util.TreeSet;

import Models.Model;

public class ModelStorage <T extends Model>
{
    private final TreeSet<T> container;
    private final Constructor<T> ctor;

    ModelStorage(Class<T> clazz) throws NoSuchMethodException, SecurityException
    {
        container = new TreeSet<T>();
        ctor = clazz.getDeclaredConstructor();
    }

    public TreeSet<T> getContainer() {
        return container;
    }

    public Constructor<T> getCtor() {
        return ctor;
    }

    public T strictSearch(String key)
    {
        for (T obj : container) {
            if (obj.getKey().equals(key))
                return obj;
        }
        return null;
    }

    public String ModelName() throws Exception
    {
        try {
            return ctor.newInstance().ModelName();
        }
        catch (Exception e) {
            throw new Exception("Failed getting Model Name");
        }
    }
}