package Models;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import Database.DbStore;
import Exceptions.InputException;
import JLearn.Config;

public class Profesor extends Model<Profesor>
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
        return Config.StoreNames.profesor;
    }

    @Override
    public void Show(PrintStream out)
    {
        out.println("Full Name: " + fullName);
        out.println("Email:     " + email);
        out.println("Phone:     " + phone);
        out.println("Cursuri  [" + cursuri.size() + "]:");
        for (String c : cursuri) {
            System.out.println("  - " + c);
        }
    }
    
    @Override
    public void Update() throws InputException
    {
        System.out.println("Full Name: " + fullName);
        email    = UpdatedString("Email:    ", email);
        phone    = UpdatedString("Phone:    ", phone);
        cursuri  = UpdatedList("Cursuri", cursuri);
    }
    
    @Override
    public void New() throws InputException
    {
        fullName = CreatedString("Full name");
        email    = CreatedString("Email");
        phone    = CreatedString("Phone");
        cursuri  = CreatedList("Cursuri");
    }
    
    public void selfValidation() throws Exception
    {
        if (!Pattern.matches("^\\+?\\d+$", phone)) {
            throw new Exception("Phone number does not match regex: ^\\+?\\d+$");
        } 
    }
    
    @Override
    public void dbValidation(DbStore ds) throws Exception {
        for (String curs : cursuri) {
            if (!DbStore.hasKey(ds.getCourses(), curs))
                throw new InputException("Did not find a <Curs> model with key <" + curs + ">");
        }
    }

    @Override
    public void deleteValidation(DbStore ds) {
        // Nimic nu depinde de profesor   (  :))  )
    }

    @Override
    public Profesor copyModel()
    {
        return new Profesor(fullName, email, phone, new ArrayList<>(cursuri));
    }

    public ArrayList<String> getCursuri()
    {
        return cursuri;
    }

    @Override
    public Profesor loadFromTokens(String[] tokens) {
        return new Profesor(
            tokens[0],
            tokens[1],
            tokens[2],
            getArrayTokens(tokens, 3)
        );
    }

    @Override
    public String[] toTokens() {
        String[] tokens = new String[3 + 1 + cursuri.size()];
        tokens[0] = fullName;
        tokens[1] = email;
        tokens[2] = phone;
        writeStringsToArray(tokens, 3, cursuri);
        return tokens;
    }
}
