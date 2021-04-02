package Workers;

// import java.io.InputStream;
import java.util.Scanner;

public class IO {
    private static IO instance = null;
    
    private Scanner scanner;

    private IO () {
        scanner = new Scanner(System.in);
    }

    // private IO (InputStream is)
    // {
    //     scanner = new Scanner(is);
    // }

    public static IO getInstance()
    {
        if (instance == null)
            instance = new IO();
        return instance;
    }

    // public static IO getInstance(InputStream is)
    // {
    //     if (instance == null)
    //         instance = new IO(is);
    //     return instance;
    // }

    public int getInt()
    {
        return scanner.nextInt();
    }

    public String getLine()
    {
        return scanner.nextLine();
    }

    public String[] getWords()
    {
        return scanner.nextLine().split(" ");
    }

    public void clearScreen()
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
}
