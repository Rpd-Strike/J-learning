package Workers;

public class Interactor {
    private static Interactor instance = null;

    private Interactor() { }

    public static Interactor getInstance()
    {
        if (instance == null)
            instance = new Interactor();
        return instance;
    }
}
