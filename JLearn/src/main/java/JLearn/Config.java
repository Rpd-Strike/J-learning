package JLearn;
/**
 * Class that holds configuration variables for the app
 */

public class Config {
    public enum DbType 
    {
        IN_MEMORY,
        CSV_FILE,
        // JDBC,
    }

    public enum EnvType
    {
        PROD,
        TEST,
    }

    public static class StoreNames
    {
        public static final String profesor = "profesor";
        public static final String curs = "curs";
        public static final String quiz = "quiz";
        public static final String quizPrbl = "quizPrbl";
        public static final String enrollment = "enrollment";
        public static final String student = "student";
        public static final String grupa = "grupa";
        public static final String serie = "serie";
    }

    public static final DbType  dbType = DbType.IN_MEMORY;
    
    /**
     * at the start of the app, before loading database, 
     * populate with extra mock data
     */
    public static final boolean useMockData = true;

    private Config() { }
}

