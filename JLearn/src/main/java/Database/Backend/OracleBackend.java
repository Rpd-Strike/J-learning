package Database.Backend;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import Database.DbStore;
import Database.DbBackend;
import Models.Model;
import Workers.Audit;

public class OracleBackend extends DbBackend
{
    private static final int AlreadyExistingNameError = 955;

    @Override
    public void Initialize(DbStore data) throws Exception {
        // Create database tables
        for (var storage : data.getAllData().values()) {
            try (Statement statement = DbConnection.getInstance().conn.createStatement()) {
                String query = "CREATE TABLE " + storage.ModelName() + 
                    "( name VARCHAR(200), csv VARCHAR(200), " +
                    "  CONSTRAINT " + storage.ModelName() + "_pk PRIMARY KEY (name) )";
                
                statement.executeUpdate(query);
            }
            catch (SQLException e) {
                if (e.getErrorCode() == AlreadyExistingNameError) {
                    // Acest cod semnifica faptul ca tabela este deja in baza de date
                    // Audit.getInstance().logOp("Tabela " + storage.ModelName() + 
                    //     " exista deja");
                }
                else {
                    Audit.getInstance().logOp("Exceptie SQL din BD: " + e);
                    throw e;
                }
            }
            catch (Exception e) {
                Audit.getInstance().logOp("Exceptie citind din BD: " + e);
                throw e;
            }
        }

        // retrieve data
        for (var storage : data.getAllData().values()) {
            try (Statement statement = DbConnection.getInstance().conn.createStatement()) {
                String query = "SELECT csv FROM " + storage.ModelName();
                ResultSet results = statement.executeQuery(query);

                // iterate through all the lines of the table.
                while (results.next()) {
                    // A doua coloana tine content-ul
                    String content = results.getString(1);
                    // Parseaza csv
                    Reader reader = new StringReader(content);
                    CSVReader csvreader = new CSVReader(reader);
                    String[] value = csvreader.readNext();
                    csvreader.close();
                    reader.close();
                    // adauga in structura de date
                    storage.insertFromTokens(value);
                }
            }
            catch (Exception e) {
                Audit.getInstance().logOp("Exceptie citind din BD: " + e.toString());
            }
        }

        System.out.println("Oracle backend initialized!");
    }

    @Override
    public void Save(DbStore data) {
        // nothing should happen, should be taken care of new, update and delete methods
    }

    @Override
    public <T extends Model<T>> 
    void New(Model<T> obj) 
    throws IOException, SQLException 
    {
        String newStatement = String.format(
            "INSERT INTO %s VALUES ('%s', '%s')", 
            obj.ModelName(), obj.getKey(), toCSV(obj)
        );

        try (Statement stmt = DbConnection.getInstance()
                .conn.createStatement()) {

            stmt.executeUpdate(newStatement);

        }
        catch (SQLException e) {
            Audit.getInstance().logOp("Exceptie @ New in DB: " + e.toString());
            throw e;
        }
    }

    @Override
    public <T extends Model<T>> 
    void Update(Model<T> old, Model<T> newer) 
    throws IOException, SQLException 
    {
        String updateStatement = String.format(
            "UPDATE %s SET name='%s', csv='%s' " +
            "WHERE name='%s'", 
            newer.ModelName(), newer.getKey(), toCSV(newer),
            old.getKey()
        );
        
        try (Statement stmt = DbConnection.getInstance()
                .conn.createStatement()) {
        
            stmt.executeUpdate(updateStatement);
        }
        catch (SQLException e) {
            Audit.getInstance().logOp("Exceptie @ Update in DB: " + e.toString());
            throw e;
        }
    }

    @Override
    public <T extends Model<T>> 
    void Delete(Model<T> obj) 
    throws IOException, SQLException 
    {
        String deleteStatement = String.format(
            "DELETE FROM %s " +
            "WHERE name='%s'", 
            obj.ModelName(), obj.getKey()
        );

        try (Statement stmt = DbConnection.getInstance()
            .conn.createStatement()) {
        
            stmt.executeUpdate(deleteStatement);
        }
        catch (SQLException e) {
            Audit.getInstance().logOp("Exceptie @ Delete in DB: " + e.toString());
            throw e;
        }
    }
    
    private <T extends Model<T>> 
    String toCSV(Model<T> obj) 
    throws IOException
    {
        // List<String> numbers = Arrays.asList("How", "To", "Do", "In", "Java");
 
        Writer writer = new StringWriter();
        CSVWriter csvwriter = new CSVWriter(writer);
        csvwriter.writeNext(obj.toTokens());
        csvwriter.close();
        return writer.toString();
    }

    @Override
    public void HardSave(DbStore data) 
    throws IOException, SQLException 
    {
        // retrieve data
        for (var storage : data.getAllData().values()) {
            for (var obj : storage.getContainer()) {
                New(obj);
                SelfUpdate(obj);
            }
        }
    }

    private <T extends Model<T>> 
    void SelfUpdate(Model<T> obj) 
    throws IOException, SQLException
    {
        Update(obj, obj);
    }
}

class DbConnection {
    static private DbConnection instance = null;

    static private final String username = "c##felix";
    static private final String password = "parolasecreta";
    static private final String url = "jdbc:oracle:thin:@localhost:1521:ORCLCDB";
    
    public Connection conn;

    private DbConnection() throws SQLException, IOException
    {
        try {
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e) {
            Audit.getInstance().logOp("Error creating connection to DB: " + e);
            throw e;
        }
        Audit.getInstance().logOp("Created connection to database server");
    }

    static public DbConnection getInstance() 
    throws SQLException, IOException
    {
        if (instance == null)
            instance = new DbConnection();
        return instance;
    }
}