package Models;

import java.io.InputStream;
import java.io.PrintStream;
// import java.util.Scanner;

import Workers.IO;

public abstract class Model implements Comparable<Model> {
    public abstract String getKey();

    protected abstract void Show(PrintStream out);
    
    protected abstract void Update(PrintStream out, InputStream in);

    public abstract void New();
    
    /**
     * Using Stdin/out, update the object
     */ 
    public void Update()
    {
        Update(System.out, System.in);
    }    
    
    public int compareTo(Model oth)
    {
        return getKey().compareTo(oth.getKey());
    }    

    public void Show()
    {
        Show(System.out);
    }

    public String UpdatedString(String keyName, String oldValue)
    {
        System.out.print(keyName + " [" + oldValue + "]: ");
        String newValue = IO.getInstance().getLine();
        
        if (newValue.length() == 0)
            return oldValue;
        return newValue;
    }

    // public abstract Boolean Valid(DbStore ds);
}
