package Models;

import java.io.InputStream;
import java.io.PrintStream;

public class Profesor extends Model
{
    private String fullName;
    private String email;
    private String phone;

    public Profesor(String fullName, String email, String phone)
    {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String getKey() {
        return fullName;
    }

    @Override
    public void Show(PrintStream out)
    {
        out.println("something");
    }
    
    @Override
    public void Update(PrintStream out, InputStream in)
    {
        fullName = UpdatedString("Full Name: ", fullName);
        email    = UpdatedString("Email:     ", email);
        phone    = UpdatedString("Phone:     ", phone);
        // TODO: Add checking phone format
    }

    @Override
    public void New()
    {

    }
}
