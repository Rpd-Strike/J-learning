package Database.Backend;

import Database.DbBackend;
import Database.DbStore;

public class InMemoryBackend extends DbBackend {

    @Override
    public void Initialize(DbStore data) {
        System.out.println("In memory backend initialized!");
    }

    @Override
    public void Save(DbStore data) {
        // nothing should happen
    }
}
