package Workers;

import java.util.Arrays;

import Database.DbStore;

public class Interactor {
    private static Interactor instance = null;

    private Interactor() { }

    public static Interactor getInstance()
    {
        if (instance == null)
            instance = new Interactor();
        return instance;
    }

    private void showHelp()
    {
        System.out.println(
            "Usage: - exit  Quits the application\n" + 
            "       - help  Displays this help message\n" + 
            "       - models  Shows all the available <MODEL> commands\n" + 
            "       - queries  Shows all <QUERY> commands\n" +
            "       - <MODEL> <COMMAND>  Execute Command for specific model\n" +
            "       - <QUERY> <ARGS...>  Execute Query with given arguments"
        );
    }

    public Boolean runQuery(String[] args, DbStore dbStore) 
    throws Exception
    {
        if (args.length < 1)
            return false;
        switch (args[0]) {
            case "exit":
                return true;
            case "help":
                showHelp();
                return false;
            case "clear":
                IO.getInstance().clearScreen();
                return false;
            case "models":
                for (String model : dbStore.getAllData().keySet())
                    System.out.println(" " + model);
                return false;
            case "queries":
                System.out.println("Nothing for now :)");
                return false;
        }
        for (String model : dbStore.getAllData().keySet()) {
            if (model.equals(args[0])) {
                String newArgs[] = Arrays.copyOfRange(args, 1, args.length);
                var container = dbStore.getAllData().get(model);
                CRUD.getInstance().runQuery(newArgs, container);
                return false;
            }
        }
        if (args[0].length() > 0)
            showHelp();
        return false;
    }

    public Boolean Interact(DbStore dbStore) throws Exception
    {
        System.out.print(" >> ");
        
        return runQuery(IO.getInstance().getWords(), dbStore);
    }
}
