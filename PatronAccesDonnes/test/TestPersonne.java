import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPersonne {
    private Connection connection;
    @BeforeEach
    public void createTAble() throws SQLException {
        //insertion
        Personne.createTable();
        new Personne("Spielberg", "Steven").save();
        new Personne("Scott", "Ridley").save();
        new Personne("Kubrick", "Stanley").save();
        new Personne("Spielberg", "George").save();

    }
    @AfterEach
    public void DelTable() throws SQLException {
        Personne.deleteTable();
    }
    @Test
    public void test_findAll() throws SQLException {
     int taille = Personne.findAll().size();
     assertEquals(taille, 4);
    }
    @Test
    public void test_findByName_OK() throws SQLException {
        assertEquals(Personne.findByName("Fincher").getFirst().getPrenom(),"David" );
    }
    @Test
    public void test_findByName_absent() throws SQLException {
        assertEquals(Personne.findByName("Ponroy").size(),0 );
    }
    @Test
    public void test_findByID_OK() throws SQLException {
        assertEquals(Personne.findById(1).getPrenom(), "Steven");
    }
    @Test
    public void test_findById_absent() throws SQLException {
        assertEquals(Personne.findById(0),null);
    }
}
