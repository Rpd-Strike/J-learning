package Models;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import Database.DbStore;

public class Profesor extends Model
{
    private String fullName;
    private String email;
    private String phone;
    private ArrayList<String> cursuri;

    public Profesor() { }

    public Profesor(String fullName, String email, String phone)
    {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.cursuri = new ArrayList<>();
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
    }
    
    private void selfValidation() throws Exception
    {
        if (!Pattern.matches("^\\+?\\d+$", phone)) {
            throw new Exception("Phone number does not match regex: ^\\+?\\d+$");
        } 
    }

    @Override
    public void Update() throws Exception
    {
        fullName = UpdatedString("Full Name:", fullName);
        email    = UpdatedString("Email:    ", email);
        phone    = UpdatedString("Phone:    ", phone);
        selfValidation();
    }

    @Override
    public void New() throws Exception
    {
        fullName = CreatedString("Full name: ");
        email    = CreatedString("Email: ");
        phone    = CreatedString("Phone: ");
        selfValidation();
    }

    @Override
    public void dbValidation(DbStore ds) throws Exception {
        // Nothing here for now
    }
}
