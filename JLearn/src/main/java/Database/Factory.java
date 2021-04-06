package Database;

import java.util.ArrayList;
import java.util.Arrays;

import Models.*;

public class Factory {
    private static Student[] student_mock = {
        new Student("Matei", "23", "800"),
        new Student("Ion", "22", "0"),
        new Student("Andreea", "21", "400")
    };

    private static Curs[] curs_mock = {
        new Curs("algebra 1", 2),
        new Curs("algebra 2", 4),
        new Curs("info", 3),
        new Curs("web", 1),
        new Curs("geometrie", 5),
        new Curs("algoritmi", 2),
    };

    private static Profesor[] profesor_mock = {
        new Profesor("Stupariu", "stu@unibuc", "+721", 
            new ArrayList<String>(Arrays.asList("geometrie", "algoritmi", "algebra 1"))),
        new Profesor("Dumitran", "dumitran@unibuc", "93214",
            new ArrayList<String>(Arrays.asList("algoritmi", "info")))
    };

    private static Enrollment[] enr_mock = {
        new Enrollment("E1", "info", "Matei", "Progress", "-"),
        new Enrollment("E2", "algoritmi", "Andreea", "Finished", "10"),
        new Enrollment("EE1", "web", "Andreea", "Aborted", "-")
    };

    private static Grupa[] grupa_mock = {
        new Grupa("234", new ArrayList<String>(Arrays.asList("Matei", "Andreea"))),
        new Grupa("233", new ArrayList<String>(Arrays.asList("Ion"))),
        new Grupa("121", new ArrayList<String>())
    };

    private static Serie[] serie_mock = {
        new Serie("Anul 1", new ArrayList<String>(Arrays.asList("121"))),
        new Serie("Anul 2", new ArrayList<String>(Arrays.asList("234", "233"))),
        new Serie("Anul 3", new ArrayList<String>()),
    };

    private static QuizProblem[] quizQuestions_mock = {
        new QuizProblem("Cine a inventat masina turing", 
            new ArrayList<String> (Arrays.asList(
            "Alan Turing",
            "Katy Perry",
            "Proful de la curs",
            "Extraterestrii")), 1),
        new QuizProblem("Ce algoritm cunosti", 
            new ArrayList<String> (Arrays.asList(
            "Niciunul",
            "Toate")), 1)
    };

    private static Quiz[] quiz_mock = {
        new Quiz("Quiz Algoritmi", "info", 
            new ArrayList<String> (Arrays.asList(
            "Cine a inventat masina turing",
            "Ce algoritm cunosti")))
    };

    public static void addMockData(DbStore ds) 
    throws Exception
    {
        try {
            for (Student obj : student_mock) 
                ds.getStudents().add(obj);
            for (Curs obj : curs_mock) 
                ds.getCourses().add(obj);
            for (Profesor obj : profesor_mock) 
                ds.getProfesors().add(obj);
            for (Enrollment obj : enr_mock) 
                ds.getEnrollments().add(obj);
            for (Grupa obj : grupa_mock) 
                ds.getGroups().add(obj);
            for (Serie obj : serie_mock) 
                ds.getSeries().add(obj);
            for (QuizProblem obj : quizQuestions_mock) 
                ds.getQuizProblems().add(obj);
            for (Quiz obj : quiz_mock) 
                ds.getQuizes().add(obj);
            for (ModelStorage<?> ms : ds.getAllData().values()) {
                for (Model<?> obj : ms.getContainer()) {
                    obj.selfValidation();
                    obj.dbValidation(ds);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error trying to add mock data! " + e.toString());
            throw e;
        }
    }
}
