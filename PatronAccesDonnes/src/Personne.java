import java.sql.*;
import java.util.ArrayList;

public class Personne {
    private String nom;
    private String prenom;
    private int id;

    Personne(String Nom, String Prenom){

        this.id=-1;
        this.nom=Nom;
        this.prenom = Prenom;
    }
    Personne(String Nom, String Prenom, int id){
        this.id=id;
        this.nom=Nom;
        this.prenom = Prenom;
    }
    public static ArrayList<Personne> findAll() throws SQLException {
        Connection connect = DBConnection.getConnection();

        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        ArrayList<Personne> pers = new ArrayList<>();
        // s'il y a un resultat
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            Personne ptmp = new Personne(nom,prenom,id);
            pers.add(ptmp);
        }
        return pers;
    }

    @Override
    public String toString() {
        String res = "";
        res +=  this.nom + " " + this.prenom + " id : "+this.id ;
        return res;
    }
    public static Personne findById(int id) throws SQLException {
        Connection connect = DBConnection.getConnection();
        Personne pers = null;
        String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            pers = new Personne(nom,prenom,id);
        }
        return pers;
    }
    public static ArrayList<Personne> findByName(String name) throws SQLException {
        Connection connect = DBConnection.getConnection();
        ArrayList pers = new ArrayList<>();

        String SQLPrep = "SELECT * FROM Personne WHERE nom=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setString(1, name);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        while (rs.next()) {
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            pers.add( new Personne(name,prenom,id));
        }
        return pers;
    }
    public static void createTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String drop = "DROP TABLE Personne";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }
    public void save() throws SQLException {
        if(this.id==-1){
            saveNew();
        }else {
            update();
        }
    }
    private void saveNew() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
        PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.executeUpdate();

        // recuperation de la derniere ligne ajoutee (auto increment)
        // recupere le nouvel id
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
        }
    }
    private void update() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.setInt(3, this.id);
        prep.execute();
    }
    public void delete() throws SQLException {
        Connection connect = DBConnection.getConnection();

        PreparedStatement prep = connect.prepareStatement("DELETE FROM Personne WHERE id=?");
        prep.setInt(1, 1);
        prep.execute();
        this.id=-1;

    }
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setId(int id) {
        this.id = id;
    }
}
