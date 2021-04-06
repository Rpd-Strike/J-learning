package Database;

import java.util.HashMap;
import java.util.TreeSet;

import JLearn.Config;
import Models.*;

public class DbStore
{
    private final HashMap<String, ModelStorage<? extends Model<?>>> storage;

    private final TreeSet<Profesor> profesors = new TreeSet<>();
    private final TreeSet<Curs> cursuri = new TreeSet<>();
    private final TreeSet<Student> students = new TreeSet<>();
    private final TreeSet<Enrollment> enrollments = new TreeSet<>();
    private final TreeSet<Grupa> groups = new TreeSet<>();
    private final TreeSet<Serie> series = new TreeSet<>();
    private final TreeSet<Quiz> quizes = new TreeSet<>();
    private final TreeSet<QuizProblem> quizProblems = new TreeSet<>();

    // Package-private access constructor
    DbStore() throws NoSuchMethodException, SecurityException {
        storage = new HashMap<>();
        
        storage.put(Config.StoreNames.profesor, 
            new ModelStorage<Profesor>(Profesor.class, profesors));
        storage.put(Config.StoreNames.curs, 
            new ModelStorage<Curs>(Curs.class, cursuri));
        storage.put(Config.StoreNames.student, 
            new ModelStorage<Student>(Student.class, students));
        storage.put(Config.StoreNames.enrollment, 
            new ModelStorage<Enrollment>(Enrollment.class, enrollments));
        storage.put(Config.StoreNames.grupa, 
            new ModelStorage<Grupa>(Grupa.class, groups));
        storage.put(Config.StoreNames.serie, 
            new ModelStorage<Serie>(Serie.class, series));
        storage.put(Config.StoreNames.quiz, 
            new ModelStorage<Quiz>(Quiz.class, quizes));
        storage.put(Config.StoreNames.quizPrbl, 
            new ModelStorage<QuizProblem>(QuizProblem.class, quizProblems));
        
    }

    // public void insertData(DbStore otherData) throws Exception
    // { Work in progress
    //     if (this == otherData) {
    //         throw new Exception("Trying to combine one instance of <DbStore> " +
    //             "to exactly the same <DbStore>");
    //     }
    // }

    public HashMap<String, ModelStorage<? extends Model<?>>> getAllData()
    {
        return storage;
    }

    public static Boolean hasKey(TreeSet<? extends Model<?>> models, String key) 
    {
        for (Model<?> obj : models) {
            if (obj.getKey().equals(key))
                return true;
        }
        return false;
    }

    public TreeSet<Profesor> getProfesors()
    {
        return profesors;
    }

    public TreeSet<Curs> getCourses()
    {
        return cursuri;
    }

    public TreeSet<Student> getStudents()
    {
        return students;
    }

    public TreeSet<Enrollment> getEnrollments()
    {
        return enrollments;
    }

    public TreeSet<Grupa> getGroups()
    {
        return groups;
    }

    public TreeSet<Serie> getSeries()
    {
        return series;
    }

    public TreeSet<Quiz> getQuizes()
    {
        return quizes;
    }

    public TreeSet<QuizProblem> getQuizProblems()
    {
        return quizProblems;
    }
}
