package Database.Backend;

import Database.DbBackend;
import Database.DbStore;
import Models.Model;

public class InMemoryBackend extends DbBackend {

    @Override
    public void Initialize(DbStore data) {
        System.out.println("In memory backend initialized!");
    }

    @Override
    public void Save(DbStore data) {
        // nothing should happen
    }

    @Override
    public <T extends Model<T>> void New(Model<T> obj) {
        // Nothing
    }

    @Override
    public <T extends Model<T>> void Update(Model<T> old, Model<T> newer) {
        // Nothing
    }

    @Override
    public <T extends Model<T>> void Delete(Model<T> obj) {
        // Nothing
    }

    @Override
    public void HardSave(DbStore data) {
        Save(data);
    }
}
