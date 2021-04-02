package Models;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import Database.DbStore;
import Exceptions.InputException;

public class Profesor extends Model
{
    private String fullName;
    private String email;
    private String phone;
    private ArrayList<String> cursuri;

    public Profesor() { }

    public Profesor(String fullName, String email, String phone, ArrayList<String> cursuri)
    {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.cursuri = cursuri;
    }

    @Override
    public String getKey() {
        return fullName;
    }

    @Override
    public String ModelName() {
        return "Profesor";
    }

    @Override
    public void Show(PrintStream out)
    {
        out.println("Full Name: " + fullName);
        out.println("Email: " + email);
        out.println("Phone: " + phone);
        out.println("Cursuri [" + cursuri.size() + "]:");
        for (String c : cursuri) {
            System.out.println("  - " + c);
        }
    }
    
    private void selfValidation() throws Exception
    {
        if (!Pattern.matches("^\\+?\\d+$", phone)) {
            throw new Exception("Phone number does not match regex: ^\\+?\\d+$");
        } 
    }

    @Override
    public void Update(DbStore ds) throws Exception
    {
        fullName = UpdatedString("Full Name:", fullName);
        email    = UpdatedString("Email:    ", email);
        phone    = UpdatedString("Phone:    ", phone);
        cursuri  = UpdatedList("Cursuri", cursuri);
        selfValidation();
        dbValidation(ds);
    }

    @Override
    public void New(DbStore ds) throws Exception
    {
        fullName = CreatedString("Full name");
        email    = CreatedString("Email");
        phone    = CreatedString("Phone");
        cursuri  = CreatedList("Cursuri");
        selfValidation();
        dbValidation(ds);
    }

    @Override
    public void dbValidation(DbStore ds) throws Exception {
        for (String curs : cursuri) {
            if (!DbStore.hasKey(ds.cursuri, curs))
                throw new InputException("Did not find a <Curs> model with key <" + curs + ">");
        }
    }

    @Override
    public Model copyModel()
    {
        return new Profesor(fullName, email, phone, new ArrayList<>(cursuri));
    }
}
