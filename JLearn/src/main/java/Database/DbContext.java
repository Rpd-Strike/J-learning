package Database;

import JLearn.Config;
import JLearn.Config.DbType;

public class DbContext {

    private DbStore data;
    private DbBackend backend;

    public DbContext(DbType type) throws Exception
    {
        // Create empty storage
        data = new DbStore();
        
        // Instantiate the type of backend needed by the app
        backend = DbBackend.newBackend(type);
        // Depending on backend, load from file / JDBC / do nothing
        backend.Initialize(data);

        // Populate with mock data first
        if (Config.useMockData)
            Factory.addMockData(data);
    }

    public DbStore getData()
    {
        return data;
    }

    public void Save()
    {
        backend.Save(data);
    }
}
