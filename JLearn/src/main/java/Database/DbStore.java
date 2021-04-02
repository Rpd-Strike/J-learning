package Database;

import java.util.HashMap;
import java.util.TreeSet;

import JLearn.Config;
import Models.Curs;
import Models.*;

public class DbStore
{
    private final HashMap<String, ModelStorage<? extends Model>> storage;

    public final TreeSet<Profesor> profesors;
    public final TreeSet<Curs> cursuri;
    public final TreeSet<Student> students;
    public final TreeSet<Enrollment> enrollments;

    // Package-private access constructor
    DbStore() throws NoSuchMethodException, SecurityException {
        storage = new HashMap<>();

        var storeProfesors = new ModelStorage<Profesor>(Profesor.class);
        var storeCursuri = new ModelStorage<Curs>(Curs.class);
        var storeStudents = new ModelStorage<Student>(Student.class);
        var storeEnrollments = new ModelStorage<Enrollment>(Enrollment.class);
        
        profesors = storeProfesors.getContainer();
        cursuri = storeCursuri.getContainer();
        students = storeStudents.getContainer();
        enrollments = storeEnrollments.getContainer();

        storage.put(Config.StoreNames.profesor, storeProfesors);
        storage.put(Config.StoreNames.curs, storeCursuri);
        storage.put(Config.StoreNames.student, storeStudents);
        storage.put(Config.StoreNames.enrollment, storeEnrollments);
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
