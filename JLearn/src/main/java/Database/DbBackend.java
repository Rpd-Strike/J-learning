package Database;

import java.io.IOException;
import java.sql.SQLException;

import Database.Backend.CsvBackend;
import Database.Backend.InMemoryBackend;
import Database.Backend.OracleBackend;
import JLearn.Config.*;
import Models.Model;

public abstract class DbBackend {
    /**
     * At the start of the app, load data from CSV or DB, or nothing if InMemory
     * @param data
     * @throws IOException
     * @throws Exception
     */
    public abstract void Initialize(DbStore data) throws IOException, Exception;

    /**
     * Perform a save of the current data.
     * 
     * Called after each CRUD operation and at exit 
     * @param data
     */
    public abstract void Save(DbStore data);

    public abstract <T extends Model<T>>
    void New(Model<T> obj) 
    throws IOException, SQLException;

    public abstract <T extends Model<T>>
    void Update(Model<T> old, Model<T> newer) 
    throws IOException, SQLException;

    public abstract <T extends Model<T>>
    void Delete(Model<T> obj) 
    throws IOException, SQLException;

    protected DbBackend() { }

    public static DbBackend newBackend(DbType type) throws Exception
    {
        DbBackend backend = null;
        switch (type) {
            case IN_MEMORY: 
                backend = new InMemoryBackend();
                break;
            case CSV_FILE: 
                backend = new CsvBackend();
                break;
            case JDBC:
                backend = new OracleBackend();
                break;
            default:
                throw new Exception(type.toString() + " backend not implemented!");
        }
            
        return backend;
    }

    /**
     * Forcibly rewrite all data to the chose database type
     * @param data
     * @throws SQLException
     * @throws IOException
     */
    public abstract void HardSave(DbStore data) throws IOException, SQLException;
}
