package Models;

import java.io.PrintStream;
import java.util.ArrayList;

import Database.DbStore;
import Exceptions.DeleteException;
import Exceptions.InputException;
import Workers.IO;

public abstract class Model implements Comparable<Model> {
    public abstract String getKey();

    public abstract String ModelName();

    protected abstract void Show(PrintStream out);
    
    public abstract void Update(DbStore ds) throws Exception;

    public abstract void New(DbStore ds) throws Exception;

    public abstract Model copyModel();

    /**
     * Checks if other references to this model are valid
     * (i.e. does all expected Models that are referenced still exist after a delete?)
     * Throws exception if now
     * @throws DeleteException
     */
    public abstract void deleteValidation(DbStore ds) throws DeleteException;

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

    protected ArrayList<String> CreatedList(String keyName) 
    throws InputException
    {
        System.out.println("Type keys / names of <" + keyName + ">  (Submit empty string to finish)");
        ArrayList<String> arr = new ArrayList<>();
        var io = IO.getInstance();
        int cnt = 0;
        while (true) {
            ++cnt;
            System.out.print("# " + cnt + ": ");

            String name = io.getLine();
            
            if (name.length() < 1)
                break;
            if (arr.contains(name))
                throw new InputException("Duplicate key '" + name + "' in given list!");
            
            arr.add(name);  
        }
        return arr;
    }

    protected ArrayList<String> UpdatedList(String keyName, ArrayList<String> old) 
    throws InputException
    {
        ArrayList<String> newList = new ArrayList<>();
        System.out.println("Update list of <" + keyName + ">");
        var io = IO.getInstance();
        int cnt = 0;
        for (String oldKey : old) {
            ++cnt;
            System.out.print("# " + cnt + ": ('null' to delete entry) [" + oldKey + "]: ");
            
            String name = io.getLine();
            if (name.length() < 1) {
                newList.add(oldKey);
                continue;
            }
            if (name.equals("null"))
                continue;
                
            if (newList.contains(name))
                throw new InputException("Duplicate key '" + name + "' in given list!");
            
            newList.add(name);
        }
        while (true) {
            ++cnt;
            System.out.print("# " + cnt + ": (Empty name to finish) []: ");

            String name = io.getLine();
            if (name.length() < 1)
                break;
            if (newList.contains(name))
                throw new InputException("Duplicate key '" + name + "' in given list!");
            
            newList.add(name);
        }
        return newList;
    }
}
