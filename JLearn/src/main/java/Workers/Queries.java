package Workers;

import java.io.IOException;

import Database.DbStore;

public class Queries {
    private static Queries instance = null;

    private Queries() { }

    public static Queries getInstance()
    {
        if (instance == null)
            instance = new Queries();
        return instance;
    }

    void showHelp()
    {   
        System.out.println("Available Queries:\n" + 
                           "  - listOverview\n" + 
                           "  - listAll");
    }

    public void runQuery(String args[], DbStore ds) throws IOException
    {
        if (args.length < 1) {
            System.out.println("Expected name of query!\n  Usage:");
            showHelp();
            return ;
        }
        switch (args[0]) {
            case "listOverview":
                listOverview(ds);
                break;
            case "listAll":
                listAll(ds);
                break;
            default:
                System.out.println("Did not recognize query: '" + args[0] + "'");
                showHelp();
        }
    }

    /**
     * Shows an overview of the data stored in "database"
     * @param ds
     * @throws IOException
     */
    public void listOverview(DbStore ds) throws IOException
    {
        int total_items = 0;
        for (String model : ds.getAllData().keySet()) {
            int count = ds.getAllData().get(model).getContainer().size();
            System.out.println("Model: " + model);
            System.out.println(" Found " + count + " items");
            total_items += count;
        }
        System.out.println("In total the database contains " + total_items + " items");

        Audit.getInstance().logOp("List Overview");
    }

    /**
     * Lists all models saved in "database"
     * @param ds
     * @throws IOException
     */
    public void listAll(DbStore ds) throws IOException
    {
        System.out.println("List of all data for each model");
        for (String model : ds.getAllData().keySet()) {
            System.out.println("");
            int count = ds.getAllData().get(model).getContainer().size();
            System.out.println("Model: " + model + "  (" + count + " items)");
            for (var obj : ds.getAllData().get(model).getContainer()) {
                obj.Show();
            }
        }

        Audit.getInstance().logOp("Listed all items in database");
    }
}
