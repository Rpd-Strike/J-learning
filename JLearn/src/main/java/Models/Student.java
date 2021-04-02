package Models;

import java.io.PrintStream;

import Database.DbStore;
import Exceptions.DeleteException;
import Exceptions.InputException;
import JLearn.Config;

public class Student extends Model<Student> {
    private String fullName;
    private String age;
    private String bursa;
    
    public Student() { }

    public Student(String fullName, String age, String bursa)
    {
        this.fullName = fullName;
        this.age = age;
        this.bursa = bursa;
    }

    @Override
    public String ModelName() {
        return Config.StoreNames.student;
    }

    @Override
    public Student copyModel() {
        return new Student(fullName, age, bursa);
    }

    @Override
    public String getKey() {
        return fullName;
    }

    @Override
    protected void Show(PrintStream out) {
        out.println("Full name: " + fullName);
        out.println("age:       " + age);
        out.println("Bursa:     " + bursa);
    }

    @Override
    public void New() throws InputException {
        fullName = CreatedString("Full name");
        age      = CreatedString("Age");
        bursa    = CreatedString("Bursa");
    }

    @Override
    public void Update() throws InputException {
        fullName = UpdatedString("Full name:", fullName);
        age      = UpdatedString("Age:      ", age);
        bursa    = UpdatedString("Bursa:    ", bursa);
    }

    @Override
    protected void selfValidation() throws Exception {
        expectRegex("age", age, "^\\d+$");
        expectRegex("bursa", bursa, "^\\d+$");
    }

    @Override
    public void dbValidation(DbStore ds) throws Exception {
        // Student is kinda independent model
    }

    @Override
    public void deleteValidation(DbStore ds) throws DeleteException {
        for (Enrollment enr : ds.enrollments) {
            if (enr.getKey().equals(fullName))
                throw new DeleteException("Deleting/Modifying <" + Config.StoreNames.student + ">" + 
                    " invalidates <" + Config.StoreNames.enrollment + ">: '" + enr.getKey() + "'");
        }

        for (Grupa grupa : ds.groups)
            for (String std : grupa.getStudents())
                if (std.equals(fullName))
                    throw new DeleteException("Deleting/Modifying <" + Config.StoreNames.student + ">" + 
                        " invalidates <" + Config.StoreNames.grupa + ">: '" + grupa.getKey() + "'");
    }
}
