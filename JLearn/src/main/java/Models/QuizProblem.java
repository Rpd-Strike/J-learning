package Models;

import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.commons.lang3.math.NumberUtils;

import Database.DbStore;
import Exceptions.DeleteException;
import Exceptions.InputException;
import JLearn.Config;

public class QuizProblem extends Model<QuizProblem> {
    private String text;
    private int correct;
    private ArrayList<String> answers;

    public QuizProblem() { }

    public QuizProblem(String text, int correct, ArrayList<String> answers)
    {
        this.text = text;
        this.answers = answers;
        this.correct = correct;
    }

    @Override
    public String getKey() {
        return text;
    }

    @Override
    public String ModelName() {
        return Config.StoreNames.quizPrbl;
    }

    @Override
    public void Show(PrintStream out)
    {
        out.println("Text: " + text);
        out.println("Answers  [" + answers.size() + "]:");
        for (int i = 0; i < answers.size(); ++i) {
            System.out.println(" " + (i + 1) + ": " + answers.get(i));
        }
        out.println("Correct Answer: " + correct);
    }
    
    @Override
    public void Update() throws InputException
    {
        System.out.println("Text: " + text);
        answers  = UpdatedList("Answers", answers);
        if (answers.size() < 1)
            throw new InputException("Should have at least one answer");
        String stringNumber = UpdatedString("Correct Answer (1-" + answers.size() + ")", 
                                            Integer.toString(correct));
        if (!NumberUtils.isParsable(stringNumber)) 
            throw new InputException("Expected a number between 1 and " + answers.size());
        correct = NumberUtils.toInt(stringNumber);
    }
    
    @Override
    public void New() throws InputException
    {
        text = CreatedString("Text");
        answers = CreatedList("Answers");
        if (answers.size() < 1)
            throw new InputException("Should have at least one answer");
        String stringNumber = UpdatedString("Correct Answer (1-" + answers.size() + ")", "None");
        if (!NumberUtils.isParsable(stringNumber)) 
            throw new InputException("Expected a number between 1 and " + answers.size());
        correct = NumberUtils.toInt(stringNumber);
    }
    
    public void selfValidation() throws Exception
    {
        if (answers.size() < 0) 
            throw new Exception("Question doesn't have any answers!");
        if (correct < 1 || correct > answers.size())
            throw new Exception("Correct answer index " + correct + " is outside of bounds:" + 
                "[1-" + answers.size() + "]");
    }
    
    @Override
    public void dbValidation(DbStore ds) throws Exception {
        // It doesnt have foreign keys
    }

    @Override
    public void deleteValidation(DbStore ds) throws DeleteException {
        for (Quiz qz :  ds.getQuizes()) {
            if (qz.getQuestions().contains(text))
                throw new DeleteException("Deleting/Modifying <" + Config.StoreNames.quizPrbl + ">" + 
                    " invalidates <" + Config.StoreNames.quiz + ">: '" + qz.getKey() + "'");
        }
    }

    @Override
    public QuizProblem copyModel()
    {
        return new QuizProblem(text, correct, new ArrayList<>(answers));
    }

    @Override
    public QuizProblem loadFromTokens(String[] tokens) {
        return new QuizProblem(
            tokens[0],
            Integer.parseInt(tokens[1]),
            getArrayTokens(tokens, 2)
        );
    }

    @Override
    public String[] toTokens() {
        String[] tokens = new String[2 + 1 + answers.size()];
        tokens[0] = text;
        tokens[1] = Integer.toString(correct);
        writeStringsToArray(tokens, 2, answers);
        return tokens;
    }
}
