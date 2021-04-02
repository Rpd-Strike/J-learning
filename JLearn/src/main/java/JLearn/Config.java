package JLearn;
/**
 * Class that holds configuration variables for the app
 */
public class Config {
    public enum DbType 
    {
        IN_MEMORY,
        // CSV_FILE,
        // JDBC,
    }

    public enum EnvType
    {
        PROD,
        TEST,
    }

    public static final DbType  dbType = DbType.IN_MEMORY;
    
    /**
     * at the start of the app, before loading database, 
     * populate with extra mock data
     */
    public static final boolean useMockData = true;

    private Config() { }
}
