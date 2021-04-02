package Database;

import java.util.HashMap;
import java.util.TreeSet;

import Models.Curs;
import Models.Model;
import Models.Profesor;

public class DbStore
{
    public final HashMap<String, ModelStorage<? extends Model>> storage;

    public final TreeSet<Profesor> profesors;
    public final TreeSet<Curs> cursuri;

    // Package-private access constructor
    DbStore() throws NoSuchMethodException, SecurityException {
        storage = new HashMap<>();

        var storeProfesors = new ModelStorage<Profesor>(Profesor.class);
        var storeCursuri = new ModelStorage<Curs>(Curs.class);
        
        profesors = storeProfesors.getContainer();
        cursuri = storeCursuri.getContainer();

        storage.put("profesor", storeProfesors);
        storage.put("curs", storeCursuri);
    }

    public void insertData(DbStore otherData) throws Exception
    {
        if (this == otherData) {
            throw new Exception("Trying to combine one instance of <DbStore> to exactly the same <DbStore>");
        }
    }

    public HashMap<String, ModelStorage<? extends Model>> getAllData()
    {
        return storage;
    }

    public static Boolean hasKey(TreeSet<? extends Model> models, String key) 
    {
        for (Model obj : models) {
            if (obj.getKey().equals(key))
                return true;
        }
        return false;
    }
}
