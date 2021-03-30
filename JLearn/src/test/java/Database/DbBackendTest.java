package Database;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import JLearn.Config.DbType;

public class DbBackendTest {
    /**
     * Rigorous Test :-)
     * @throws Exception
     */
    @Test
    public void shouldCreateAllDbBackends() throws Exception
    {
        for (DbType type : DbType.values()) {
            DbBackend someBackend = DbBackend.newBackend(type);
            someBackend.Initialize(new DbStore());
            assertTrue("Cannot create Backend of type: " + type.toString(), someBackend != null);
        }
        assertTrue( true );
    }
}
