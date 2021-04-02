package Database;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class DataConsistency {
    @Rule
    public final TextFromStandardInputStream systemInMock
        = TextFromStandardInputStream.emptyStandardInputStream();

    @Rule
    public final SystemOutRule systemOutRule 
        = new SystemOutRule().enableLog();

    @Test
    public void shouldInsertAllData() throws Exception
    {
        systemInMock.provideLines(ConsistencyData.insertCommands());
        
        JLearn.App.main(new String[] {});

        assertTrue("Got exception while inserting", checkLogForException(systemOutRule.getLog()));

        systemInMock.provideLines(new String[] {"exit"});
    }

    protected Boolean checkLogForException(String output)
    {
        return output.contains("Exception") || output.contains("exception");
    }
}

class ConsistencyData
{
    public static String[] insertCommands()
    {
        return new String[] {
            "curs New", 
            "analiza 1",
            "3",
            
            "curs New",
            "tehnici web",
            "4",

            "curs New",
            "structuri de date",
            "3"
        };
    }

    protected static String getSingleString()
    {
        String[] cmds = insertCommands();
        String ret = "";
        for (String s : cmds) {
            ret += "\n" + s;
        }
        return ret;
    }

    public static InputStream getInputStream() throws UnsupportedEncodingException
    {
        return new ByteArrayInputStream(getSingleString().getBytes("UTF-8"));
    }
}