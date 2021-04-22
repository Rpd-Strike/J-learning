package Workers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.opencsv.CSVWriter;

public class Audit {
    private static Audit instance = null;

    private Audit() throws IOException
    { }

    public static Audit getInstance() throws IOException
    {
        if (instance == null)
            instance = new Audit();
        return instance;
    }

    public void logOp(String action_name) throws IOException
    {
        FileWriter writer = new FileWriter(
            FolderPath() + "audit.csv", true
        );
        CSVWriter csvWriter = new CSVWriter(writer);

        csvWriter.writeNext(new String[] {
            action_name,
            formattedDate()
        });
        csvWriter.close();
    }

    public String formattedDate()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }

    private String FolderPath()
    {
        String path = System.getProperty("user.home") + "/.JLearn/";
        File theDir = new File(path);
        if (!theDir.exists())
            theDir.mkdirs();
        return path;
    }
}
