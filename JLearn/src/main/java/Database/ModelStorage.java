package Database;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import Exceptions.DeleteException;
import Exceptions.InputException;
import Models.Model;
import Workers.Audit;

public class ModelStorage <T extends Model<T>>
{
    private final TreeSet<T> container;
    private final Constructor<T> ctor;

    ModelStorage(Class<T> clazz) 
    throws NoSuchMethodException, SecurityException
    {
        container = new TreeSet<T>();
        ctor = clazz.getDeclaredConstructor();
    }

    ModelStorage(Class<T> clazz, TreeSet<T> container) 
    throws NoSuchMethodException, SecurityException
    {
        this.container = container;
        ctor = clazz.getDeclaredConstructor();
    }

    public void Delete(String key, String modelName, DbStore db) throws Exception
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

            Audit.getInstance().logOp("CRUD Delete on: " + modelName + ", with key: " + key);
        }
        catch (Exception e)
        {
            container.add(obj);
            throw new Exception("  Delete failed: " + e.getMessage());
        }
    }

    public void Update(String key, String modelName, DbStore db) throws Exception
    {
        T obj = strictSearch(key);
        if (obj == null) {
            throw new Exception("<" + ModelName() + "> by key <" +
                key + "> doesn't exist!");
        }
        T copie = obj.copyModel();
        try {
            obj.Update(db);
        }
        catch (Exception e) {
            container.remove(obj);
            container.add(copie);
            throw e;
        } finally {
            Audit.getInstance().logOp("CRUD Update on: " + modelName + ", with key: " + key);
        }
    }

    public void insertFromTokens(String[] tokens) throws Exception
    {
        T obj = ctor.newInstance().loadFromTokens(tokens);
        container.add(obj);
    }

    public List<String[]> toTokens()
    {
        List<String[]> csv_items = new ArrayList<>();
        for (T model : container) {
            csv_items.add(model.toTokens());
        }
        return csv_items;
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