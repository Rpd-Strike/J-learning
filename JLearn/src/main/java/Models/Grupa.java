package Models;

import java.io.PrintStream;
import java.util.ArrayList;

import Database.DbStore;
import Exceptions.DeleteException;
import Exceptions.InputException;
import JLearn.Config;

public class Grupa extends Model {
    private String name;
    private ArrayList<String> students;

    public Grupa() { }

    public Grupa(String name, ArrayList<String> students)
    {
        this.name = name;
        this.students = students;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public String ModelName() {
        return Config.StoreNames.grupa;
    }

    @Override
    protected void Show(PrintStream out) {
        out.println("Grupa: " + name);
        out.println("Students  [" + students.size() + "]:");
        for (String s : students) {
            System.out.println("  - " + s);
        }
    }

    @Override
    protected void Update() throws InputException {
        System.out.println("Grupa: " + name);
        students = UpdatedList("Students:", students);
    }

    @Override
    protected void New() throws InputException {
        name = CreatedString("Grupa");
        students = CreatedList("Students");       
    }

    @Override
    protected void selfValidation() throws Exception {
        // Nothing special
    }

    @Override
    public void dbValidation(DbStore ds) throws Exception {
        for (String s : students) {
            if (!DbStore.hasKey(ds.students, s))
                throw new InputException("Did not find a <" + Config.StoreNames.student + 
                    "> model with key <" + s + ">");
        }
    }

    @Override
    public void deleteValidation(DbStore ds) throws DeleteException {
        for (Serie ser : ds.series) {
            if (ser.getGroups().contains(name))
                throw new DeleteException("Deleting/Modifying <" + Config.StoreNames.grupa + ">" + 
                    " invalidates <" + Config.StoreNames.serie + ">: '" + ser.getKey() + "'");
        }
    }

    @Override
    public Model copyModel() {
        return new Grupa(name, new ArrayList<>(students));
    }

    public ArrayList<String> getStudents() {
        return students;
    }
}
