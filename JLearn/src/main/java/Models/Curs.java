package Models;

import java.io.PrintStream;

import Database.DbStore;
import Exceptions.DeleteException;
import Exceptions.InputException;
import JLearn.Config;

public class Curs extends Model<Curs> {
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
        return Config.StoreNames.curs;
    }

    @Override
    protected void Show(PrintStream out) {
        out.println("Course name: " + name);
        out.println("Credits:     " + credits);
    }

    @Override
    public void selfValidation() throws Exception {
        if (credits < 1)
            throw new Exception("The course has to be worth at least 1 credit!");
    }

    @Override
    public void Update() throws InputException {
        System.out.println("Course name: " + name);
        credits = Integer.parseInt(
               UpdatedString("Credits    ", Integer.toString(credits)));
    }

    @Override
    public void New() throws InputException {
        name    =                  CreatedString("Course name");
        credits = Integer.parseInt(CreatedString("Credits"));    
    }

    @Override
    public void dbValidation(DbStore ds) throws Exception {
        // Nothing for now
    }

    @Override
    public void deleteValidation(DbStore ds) throws DeleteException {
        for (Profesor prof : ds.getProfesors()) {
            if (prof.getCursuri().contains(name))
                throw new DeleteException("Deleting/Modifying <" + Config.StoreNames.curs + ">" + 
                    " invalidates <" + Config.StoreNames.profesor + ">: '" + prof.getKey() + "'");
        }
        for (Quiz quiz : ds.getQuizes()) {
            if (quiz.getCursKey().equals(name))
                throw new DeleteException("Deleting/Modifying <" + Config.StoreNames.curs + ">" + 
                    " invalidates <" + Config.StoreNames.quiz + ">: '" + quiz.getKey() + "'");
        }
        for (Enrollment enr : ds.getEnrollments()) {
            if (enr.getCurs().equals(name))
                throw new DeleteException("Deleting/Modifying <" + Config.StoreNames.curs + ">" + 
                    " invalidates <" + Config.StoreNames.enrollment + ">: '" + enr.getKey() + "'");
        }
    }
    
    @Override
    public Curs copyModel() {
        return new Curs(name, credits);
    }
}
