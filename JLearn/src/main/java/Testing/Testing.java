package Testing;

import JLearn.Config.EnvType;

/**
 * Work in progress, just ignore what is here
 */

public class Testing {
    private static Testing instance = null;

    private EnvType env;

    public static Testing getInstance()
    {
        if (instance != null)
            return instance;
        
        Boolean isJunit = isJUnitTest();
        EnvType e = EnvType.PROD;
        if (isJunit)
            e = EnvType.TEST;
        if (instance ==  null)
            instance = new Testing(e);

        return instance;
    }

    private Testing(EnvType et)
    {
        env = et;
    }

    public Boolean isTesting()
    {
        return env == EnvType.TEST;
    }

    private static boolean isJUnitTest() {  
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }           
        }
        return false;
    }
}
