package Models;

import java.io.PrintStream;

import Database.DbStore;
import Exceptions.DeleteException;
import Exceptions.InputException;
import JLearn.Config;

public class Enrollment extends Model<Enrollment> {
    private String key;
    private String cursKey;
    private String studentKey;
    private String status;
    private String grade;

    public Enrollment() { }

    public Enrollment(
        String key,
        String cursKey,
        String studentKey,
        String status,
        String grade
    ) {
        this.key = key;
        this.cursKey = cursKey;
        this.studentKey = studentKey;
        this.status = status;
        this.grade = grade;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String ModelName() {
        return Config.StoreNames.enrollment;
    }

    @Override
    public void dbValidation(DbStore ds) throws Exception {
        if (!DbStore.hasKey(ds.getCourses(), cursKey))
            throw new Exception("<Curs> key <" + cursKey + "> does not exists!");
        if (!DbStore.hasKey(ds.getStudents(), studentKey))
            throw new Exception("<Student> key <" + studentKey + "> does not exists!");
    }

    @Override
    public void deleteValidation(DbStore ds) throws DeleteException {
        // Nothing references an Enrollment
    }

    public void selfValidation() throws Exception
    {
        expectRegex("Status", status, "^Aborted|Progress|Finished$");
        expectRegex("Grade", grade, "^-|([1-9])|10$");
    }

    @Override
    protected void Show(PrintStream out) {
        out.println("Key:     " + key);
        out.println("Student: " + studentKey);
        out.println("Curs:    " + cursKey);
        out.println("Status:  " + status);
        out.println("Grade:   " + grade);
    }

    @Override
    public void New() throws InputException {
        key = CreatedString("Key");
        studentKey = CreatedString("Student");
        cursKey = CreatedString("Curs");
        status = CreatedString("status");
        grade = CreatedString("grade");
    }

    @Override
    public void Update() throws InputException {
        System.out.println("Key:     " + key);
        studentKey = UpdatedString("Student:", studentKey);
        cursKey    = UpdatedString("Curs:   ", cursKey);
        status     = UpdatedString("Status: ", status);
        grade      = UpdatedString("Grade:  ", grade);
        
    }

    @Override
    public Enrollment copyModel() {
        return new Enrollment(
            key,
            cursKey,
            studentKey,
            status,
            grade
        );
    }

    public String getStudent()
    {
        return studentKey;
    }

    public String getCurs()
    {
        return cursKey;
    }

    @Override
    public Enrollment loadFromTokens(String[] tokens) {
        return new Enrollment(
            tokens[0],
            tokens[1],
            tokens[2],
            tokens[3],
            tokens[4]
        );
    }

    @Override
    public String[] toTokens() {
        return new String [] {
            key,
            cursKey,
            studentKey,
            status,
            grade
        };
    }
}
