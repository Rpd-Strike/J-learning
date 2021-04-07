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
            default:
                throw new Exception(type.toString() + " backend not implemented!");
        }
            
        return backend;
    }
}
