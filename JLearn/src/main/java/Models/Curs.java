package Models;

import java.io.PrintStream;

import Database.DbStore;

public class Curs extends Model {
    private String name;
    private int credits;
    
    public Curs() { }

    public Curs(String name, int credits)
    {
        this.name = name;
        this.credits = credits;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public String ModelName() {
        return "Curs";
    }

    @Override
    protected void Show(PrintStream out) {
        out.println("Course name: " + name);
        out.println("Credits:     " + credits);
    }

    private void selfValidation() throws Exception
    {
        if (credits < 1)
            throw new Exception("The course has to be worth at least 1 credit!");
    }

    @Override
    public void Update(DbStore ds) throws Exception {
        name = UpdatedString("Course name", name);
        credits = Integer.parseInt(
               UpdatedString("Credits    ", Integer.toString(credits)));
        selfValidation();
    }

    @Override
    public void New(DbStore ds) throws Exception {
        name    =                  CreatedString("Course name");
        credits = Integer.parseInt(CreatedString("Credits"));
        selfValidation();    
    }

    @Override
    public void dbValidation(DbStore ds) throws Exception {
        // Nothing for now
    }

    @Override
    public Model copyModel() {
        return new Curs(name, credits);
    }
}
