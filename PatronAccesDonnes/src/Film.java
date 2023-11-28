import java.sql.*;

public class Film {
    private String titre;
    private int id;
    private int id_real;

    Film(String  titre, Personne realisateur){
        this.id = -1;
        this.id_real = realisateur.getId();
        this.titre = titre;
    }
    private Film (String titre, int id, int id_real){
        this.titre= titre;
        this.id_real = id_real;
        this.id = id;
    }
    public static Film findById(int id) throws SQLException {
        Connection connect = DBConnection.getConnection();
        Film film = null;
        String SQLPrep = "SELECT * FROM Film WHERE id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String titre = rs.getString("titre");
            int id_r = rs.getInt("id_real");
            film = new Film(titre,id_r,id);
        }
        return film;
    }
    public Personne getRealisateur() throws SQLException {
        Connection connect = DBConnection.getConnection();
        Personne personne = null;

        String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, id_real);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            personne = new Personne(nom,prenom,id_real);
        }
        return personne;
    }
    public static void createTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String createString = "CREATE TABLE Film ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "Titre varchar(40) NOT NULL, " + "Is_rea INTEGER NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String drop = "DROP TABLE Film";
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
        String SQLPrep = "INSERT INTO Film (titre, id_rea) VALUES (?,?);";
        PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
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
        String SQLprep = "update Personne set titre=?, idrea=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.setInt(3, this.id);
        prep.execute();
    }
    public void delete() throws SQLException {
        Connection connect = DBConnection.getConnection();

        PreparedStatement prep = connect.prepareStatement("DELETE FROM Film WHERE id=?");
        prep.setInt(1, 1);
        prep.execute();
        this.id=-1;

    }

    public int getId() {
        return id;
    }

    public int getId_real() {
        return id_real;
    }

    public String getTitre() {
        return titre;
    }

}
