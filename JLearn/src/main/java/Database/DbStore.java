package Database;

import java.util.SortedSet;
import java.util.TreeSet;

import Models.Profesor;

public class DbStore
{
    public SortedSet<Profesor> profesors;

    // Package-private access constructor
    DbStore() { 
        profesors = new TreeSet<Profesor>();
    }

    public void insertData(DbStore otherData) throws Exception
    {
        if (this == otherData) {
            throw new Exception("Trying to combine one instance of <DbStore> to exactly the same <DbStore>");
        }
    }
}
