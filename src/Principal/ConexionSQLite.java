package Principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLite {
    public static final String URL = "jdbc:sqlite:ProyectoArrendamiento.db";

    public static Connection conectar() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(URL);
            System.out.println("Conexión exitosa.");
            return con;
        } catch (Exception e) {
            System.err.println("Error al conectar: " + e.getMessage());
            return null; // Importante controlar este caso
        }
    }
}
