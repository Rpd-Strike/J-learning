package Models;

import java.io.PrintStream;

import Database.DbStore;
import Workers.IO;

public abstract class Model implements Comparable<Model> {
    public abstract String getKey();

    public abstract String ModelName();

    protected abstract void Show(PrintStream out);
    
    public abstract void Update() throws Exception;

    public abstract void New() throws Exception;

    /**
     * Checks if model is consistent with regard to other database data.
     * If something is bad throws Exception
     * @param ds
     */
    public abstract void dbValidation(DbStore ds) throws Exception;
    
    public int compareTo(Model oth)
    {
        return getKey().compareTo(oth.getKey());
    }    

    public void Show()
    {
        Show(System.out);
    }

    protected String UpdatedString(String keyName, String oldValue)
    {
        System.out.print(keyName + " [" + oldValue + "]: ");
        String newValue = IO.getInstance().getLine();
        
        if (newValue.length() == 0)
            return oldValue;
        return newValue;
    }

    protected String CreatedString(String keyName)
    {
        System.out.print(keyName + ": ");
        String newValue = IO.getInstance().getLine();

        return newValue;
    }
}
