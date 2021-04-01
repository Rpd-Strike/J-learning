package Database;

import Models.Profesor;

public class Factory {
    public static DbStore simpleMockData() 
    throws NoSuchMethodException, SecurityException
    {
        DbStore data = new DbStore();
        data.profesors.add(new Profesor("Jimmy Prof", "jimmy.prof@univ.academy", "0712345678"));
        return data;
    }
}
