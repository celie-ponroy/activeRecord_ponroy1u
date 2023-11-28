import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDBC {
    @BeforeEach
    public void before() throws SQLException {
        DBConnection.setNomDB("testpersonne");
    }
    @Test
    public void test_multiple_connection() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();
        assertEquals(c1, c2);

    }
    @Test
    public void test_changement_bdok() throws SQLException {
        DBConnection.getConnection();
        assertEquals(DBConnection.getDbName(), "testpersonne");
        DBConnection.setNomDB("testpersonne2");
        assertEquals(DBConnection.getDbName(), "testpersonne2");
    }
    @Test
    public void test_changement_bd_null() throws SQLException {
        DBConnection.getConnection();
        assertEquals(DBConnection.getDbName(), "testpersonne");
        DBConnection.setNomDB(null);
        DBConnection.getConnection();
        assertEquals(DBConnection.getDbName(), "testpersonne");
    }


}
