package Database;

import java.lang.reflect.Constructor;
import java.util.TreeSet;

import Exceptions.DeleteException;
import Exceptions.InputException;
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

    public void Delete(String key, DbStore db) throws Exception
    {
        T obj = strictSearch(key);
        if (obj == null) {
            throw new DeleteException("<" + ModelName() + "> by key <" +
                key + "> doesn't exist!");
        }
        try {
            if (!container.remove(obj)) 
                throw new InputException("Did not find <" + ModelName() + 
                    "> by key <" + key + ">");
            obj.deleteValidation(db);
        }
        catch (Exception e)
        {
            container.add(obj);
            throw new Exception("  Delete failed: " + e.getMessage());
        }
    }

    public void Update(String key, DbStore db) throws Exception
    {
        T obj = strictSearch(key);
        if (obj == null) {
            throw new Exception("<" + ModelName() + "> by key <" +
                key + "> doesn't exist!");
        }
        @SuppressWarnings("unchecked")
        T copie = (T) obj.copyModel();
        try {
            obj.Update(db);
        }
        catch (Exception e) {
            container.remove(obj);
            container.add(copie);
            throw e;
        }
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