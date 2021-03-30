package JLearn;

import Database.DbContext;
import Workers.Interactor;

public final class AppService {
    static private AppService instance = null;

    DbContext db;
    Interactor interactor;

    public static AppService getInstance()
    {
        if (instance == null)
            instance = new AppService();
        return instance;
    }

    /**
     * Constructor of AppService, run init code here
     */
    private AppService() { 
        try {
            db = new DbContext(Config.dbType);
        }
        catch (Exception e) {
            System.out.println("Exceptie la initializarea aplicatiei!");
            System.out.println(e.toString());
            System.exit(0);
        }
    }

    public void runApp(String[] args)
    {
        interactor = Interactor.getInstance();

        System.out.println("app is running!");
    }
}
