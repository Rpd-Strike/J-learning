package Database;

import Database.Backend.InMemoryBackend;
import JLearn.Config.*;

public abstract class DbBackend {
    public abstract void Initialize(DbStore data);

    public abstract void Save(DbStore data);

    protected DbBackend() { }

    public static DbBackend newBackend(DbType type) throws Exception
    {
        DbBackend backend = null;
        switch (type) {
            case IN_MEMORY: 
                backend = new InMemoryBackend();
                break;

            case CSV_FILE: 
                throw new Exception("CSV File backend not implemented");
        
            case JDBC:
                throw new Exception("JDBC backend not implemented");
        }
        if (backend == null)
            throw new Exception("Backend is null!");
            
        return backend;
    }
}
