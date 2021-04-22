package Workers;

import java.io.IOException;
import java.util.Arrays;

import Database.DbStore;
import Database.ModelStorage;
import Exceptions.InputException;
import JLearn.AppService;
import Models.Model;

public class CRUD {
    private static CRUD instance = null;

    private DbStore db;

    private CRUD()
    {
        db = AppService.getInstance().getDbStore();
    }

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
            " - List                Show all objects\n" + 
            " - New                 Create a new object\n" + 
            " - Update <Key/Name>   Update model with the given key\n" + 
            " - Delete <Key/Name>   Delete model with the given key\n" + 
            " - Show   <Key/Name>   Show model with the given key\n" + 
            " - Search <query>  Tries to find objects that have similar keys to the query\n"
        );
    }

    public <T extends Model<T>> 
    void runQuery(
        String args[],
        ModelStorage<T> container) 
    throws Exception
    {
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
            case "Show":
                opShow(args, container);
                break;
            case "Search":
                opSearch(args, container);
                break;
            case "New":
            opNew(container);
                break;
            case "Update":
                opUpdate(args, container);
                break;
            case "Delete":
                opDelete(args, container);
                break;
            default:
                System.out.println("Command on Model not recognized ... ");
                showHelp();
        }
        // TODO: profesor <Model>   -- shows nothing
    }

    private <T extends Model<T>> 
    void opSearch(String[] args, ModelStorage<T> container) throws Exception {
        if (args.length < 1)
            throw new InputException("Expected search query");
        String key = String.join(" ", args);
        for (T obj : container.getContainer()) {
            if (obj.getKey().contains(key)) {
                obj.Show();
                System.out.println("");
            }
        }

        Audit.getInstance().logOp("CRUD Search on: " + container.ModelName());
    }

    private <T extends Model<T>> 
    void opShow(String[] args, ModelStorage<T> container) throws Exception {
        if (args.length < 1)
            throw new InputException("Expected a <Key/Name> argument");
        String key = String.join(" ", args);
        T obj = container.strictSearch(key);
        if (obj == null)
            System.out.println("Couldn't find an object matching the key");
        else
            obj.Show();

        Audit.getInstance().logOp("CRUD Show on: " + container.ModelName() + ", with key: " + key);
    }

    private <T extends Model<T>> 
    void opDelete(String[] args, ModelStorage<T> container) throws Exception {
        if (args.length < 1)
            throw new InputException("Expected a <Key/Name> argument");
        String key = String.join(" ", args);
        container.Delete(key, container.ModelName(), db);
    }

    private <T extends Model<T>> 
    void opUpdate(String[] args, ModelStorage<T> container) 
    throws Exception {
        if (args.length < 1)
            throw new InputException("Expected name of model");
        String key = String.join(" ", args);
        container.Update(key, container.ModelName(), db);
    }

    private <T extends Model<T>> 
    void opNew(ModelStorage<T> container) 
    throws Exception 
    {
        var obj = container.getCtor().newInstance();
        obj.New(db);
        if (db.getAllData().get(obj.ModelName()).getContainer().contains(obj)) {
            throw new Exception("<" + obj.ModelName() + 
                "> already contains key '" + obj.getKey() + "'");
        }
        container.getContainer().add(obj);
        
        Audit.getInstance().logOp("CRUD New on: " + container.ModelName() + ", with key: " + obj.getKey());
    }

    private 
    void opList(ModelStorage<? extends Model<?>> container)
    throws Exception 
    {
        System.out.println("");
        for (var obj : container.getContainer()) {
            obj.Show();
            System.out.println("");
        }

        Audit.getInstance().logOp("List models: " + container.ModelName());
    }
}
