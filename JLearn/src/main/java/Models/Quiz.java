package Models;

import java.io.PrintStream;
import java.util.ArrayList;

import Database.DbStore;
import Exceptions.DeleteException;
import Exceptions.InputException;
import JLearn.Config;

public class Quiz extends Model<Quiz> {
    private String name;
    private String cursKey;
    private ArrayList<String> questions;
    
    public Quiz () { }

    public Quiz(String name, String cursKey, ArrayList<String> qs)
    {
        this.name = name;
        this.cursKey = cursKey;
        this.questions = qs;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public String ModelName() {
        return Config.StoreNames.quiz;
    }

    @Override
    protected void Show(PrintStream out) {
        out.println("Quiz:  " + name);
        out.println("Curs:  " + cursKey);
        out.println("Questions [" + questions.size() + "]:");
        for (String s : questions) {
            out.println("  - " + s);
        }
    }

    @Override
    protected void Update() throws InputException {
        System.out.println("Quiz: " + name);
        cursKey = UpdatedString("Curs", cursKey);
        questions = UpdatedList("Questions:", questions);
    }

    @Override
    protected void New() throws InputException {
        name = CreatedString("Quiz");
        cursKey = CreatedString("Curs");
        questions = CreatedList("Questions");
    }

    @Override
    public void selfValidation() throws Exception {
        // Nothing special
    }

    @Override
    public void dbValidation(DbStore ds) throws Exception {
        if (!DbStore.hasKey(ds.getCourses(), cursKey)) 
            throw new InputException("Did not find <" + Config.StoreNames.curs + 
                "> model with key <" + cursKey + ">");
        for (String q : questions) {
            if (!DbStore.hasKey(ds.getQuizProblems(), q))
                throw new InputException("Did not find <" + Config.StoreNames.quizPrbl + 
                    "> model with key <" + q + ">");
        }
    }

    @Override
    public void deleteValidation(DbStore ds) throws DeleteException {
        // Nothing depends on this model
    }

    @Override
    public Quiz copyModel() {
        return new Quiz(name, cursKey, new ArrayList<>(questions));
    }

    public ArrayList<String> getQuestions()
    {
        return questions;
    }

    public String getCursKey()
    {
        return cursKey;
    }

    @Override
    public Quiz loadFromTokens(String[] tokens) {
        return new Quiz(
            tokens[0],
            tokens[1],
            getArrayTokens(tokens, 2)
        );
    }

    @Override
    public String[] toTokens() {
        String[] tokens = new String[2 + 1 + questions.size()];
        tokens[0] = name;
        tokens[1] = cursKey;
        writeStringsToArray(tokens, 2, questions);
        return tokens;
    }
}
