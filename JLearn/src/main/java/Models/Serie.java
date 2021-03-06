package Models;

import java.io.PrintStream;
import java.util.ArrayList;

import Database.DbStore;
import Exceptions.DeleteException;
import Exceptions.InputException;
import JLearn.Config;

public class Serie extends Model<Serie> {
    private String name;
    private ArrayList<String> groups;

    public Serie() { }

    public Serie(String name, ArrayList<String> groups)
    {
        this.name = name;
        this.groups = groups;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public String ModelName() {
        return Config.StoreNames.serie;
    }

    @Override
    protected void Show(PrintStream out) {
        out.println("Serie: " + name);
        out.println("Grupe:  [" + groups.size() + "]:");
        for (String s : groups) {
            System.out.println("  - " + s);
        }
    }

    @Override
    protected void Update() throws InputException {
        System.out.println("Serie: " + name);
        groups = UpdatedList("Grupe:", groups);
    }

    @Override
    protected void New() throws InputException {
        name = CreatedString("Serie");
        groups = CreatedList("Grupe");      
    }

    @Override
    public void selfValidation() throws Exception {
        // Nothing special
    }

    @Override
    public void dbValidation(DbStore ds) throws Exception {
        for (String g : groups) {
            if (!DbStore.hasKey(ds.getGroups(), g))
                throw new InputException("Did not find <" + Config.StoreNames.grupa + 
                    "> model with key <" + g + ">");
        }
    }

    @Override
    public void deleteValidation(DbStore ds) throws DeleteException {
        // No one depends on Serie
    }

    @Override
    public Serie copyModel() {
        return new Serie(name, new ArrayList<>(groups));
    }

    public ArrayList<String> getGroups()
    {
        return groups;
    }

    @Override
    public Serie loadFromTokens(String[] tokens) {
        return new Serie(
            tokens[0],
            getArrayTokens(tokens, 1)
        );
    }

    @Override
    public String[] toTokens() {
        String[] tokens = new String[1 + 1 + groups.size()];
        tokens[0] = name;
        writeStringsToArray(tokens, 1, groups);
        return tokens;
    }
}
