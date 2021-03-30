package JLearn;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Welcome to JLearn admin tools!" );

        AppService service = AppService.getInstance();

        service.runApp(args);
        // Compilation error, YAY!!
        // DbStore store = new DbStore();
    }
}
