package Workers;

import java.util.Arrays;

import Database.DbStore;
import Database.ModelStorage;
import Exceptions.InputException;
import Models.Model;

public class CRUD {
    private static CRUD instance = null;

    private DbStore db;

    public static CRUD getInstance()
    {
        if (instance == null)
            instance = new CRUD();
        return instance;
    }

    protected void showHelp()
    {
        System.out.print(
            "Operations permitted on models:\n" + 
            " - List Show all objects\n" + 
            " - New  Create a new object\n" + 
            " - Update <Key/Name>  Update model with the given key\n" + 
            " - Delete <Key/Name>  Delete model with the given key\n" + 
            " - Show <Key/Name>    Show model with the given key\n" + 
            " - Search <Key/Name>  Tries to find objects that have similar keys to the argument\n"
        );
    }

    public <T extends Model> 
    void runQuery(
        String args[], 
        DbStore db,
        ModelStorage<T> container) 
    throws Exception
    {
        this.db = db;

        if (args.length < 1) {
            showHelp();
            return ;
        }
        String cmd = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);
        switch (cmd) {
            case "List":
                opList(container);
                break;
            case "New":
                opNew(container, db);
                break;
            case "Update":
                opUpdate(args, container, db);
                break;
            case "Delete":
                opDelete(args, container);
                break;
            case "Show":
                opShow(args, container);
                break;
            case "Search":
                opSearch(args, container);
                break;
            default:
                System.out.println("Command on Model not recognized ... ");
                showHelp();
        }

    }

    private <T extends Model> 
    void opSearch(String[] args, ModelStorage<T> container) throws InputException {
        if (args.length < 1)
            throw new InputException("Expected search query");
        String key = String.join(" ", args);
        for (T obj : container.getContainer()) {
            if (obj.getKey().contains(key)) {
                obj.Show();
                System.out.println("");
            }
        }
    }

    private <T extends Model> 
    void opShow(String[] args, ModelStorage<T> container) throws InputException {
        if (args.length < 1)
            throw new InputException("Expected a <Key/Name> argument");
        T obj = container.strictSearch(String.join(" ", args));
        if (obj == null)
            System.out.println("Couldn't find an object matching the key");
        else
            obj.Show();
    }

    private <T extends Model> 
    void opDelete(String[] args, ModelStorage<T> container) {
    }

    private <T extends Model> 
    void opUpdate(String[] args, ModelStorage<T> container, DbStore ds) 
    throws Exception {
        if (args.length < 1)
            throw new InputException("Expected name of model");
        String key = String.join(" ", args);
        T obj = container.strictSearch(key);
        if (obj == null) {
            System.out.println("Did not find <" + container.ModelName() + "> by key <" + key + ">");
            return ;
        }
        
        obj.Update(ds);
    }

    private <T extends Model> 
    void opNew(ModelStorage<T> container, DbStore ds) 
    throws Exception 
    {
        var obj = container.getCtor().newInstance();
        obj.New(ds);
        obj.dbValidation(db);
        container.getContainer().add(obj);
    }

    private void opList(ModelStorage<? extends Model> container) {
        System.out.println("");
        for (var obj : container.getContainer()) {
            obj.Show();
            System.out.println("");
        }
    }
}
