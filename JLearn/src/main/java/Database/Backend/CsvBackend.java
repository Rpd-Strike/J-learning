package Database.Backend;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import Database.DbBackend;
import Database.DbStore;

public class CsvBackend extends DbBackend {

    @Override
    public void Initialize(DbStore data) {
        System.out.println("Initializing CSV backend");

        for (var collection : data.getAllData().values()) {
            try (Reader reader = Files.newBufferedReader(
                    Paths.get(FolderPath() + collection.ModelName() + ".csv"))) {
                CSVReader csvreader = new CSVReader(reader);

                for (String[] tokenized_model : csvreader.readAll()) {
                    collection.insertFromTokens(tokenized_model);
                }

                csvreader.close();
            }
            catch (Exception e) {
                System.out.println("Error reading and creating objects from CSV: " + e.getMessage());
                System.out.println("Probably file not existent, but now it just got created");
            }
        }

        System.out.println("Loaded data using CSV backend!");
    }

    @Override
    public void Save(DbStore data) {
        for (var collection : data.getAllData().values()) {
            try (Writer writer = Files.newBufferedWriter(
                    Paths.get(FolderPath() + collection.ModelName() + ".csv"))) {

                CSVWriter csvwriter = new CSVWriter(writer);
                csvwriter.writeAll(collection.toTokens());
                csvwriter.close();
            }
            catch (Exception e) {
                System.out.println("Error saving to csv: " + e.getMessage());  
            }
        }
    }

    private String FolderPath() {
        String path = System.getProperty("user.home") + "/.JLearn/";
        File theDir = new File(path);
        if (!theDir.exists())
            theDir.mkdirs();
        return path;
    }
}
