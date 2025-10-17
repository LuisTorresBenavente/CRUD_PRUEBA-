package vallegrande.edu.pe.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Configuración de la conexión con el puerto 3307 y usuario root
    private static final String URL = "jdbc:mysql://localhost:3307/nombre_de_tu_db?serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CLAVE = "tu_contraseña_root"; // ¡Asegúrate de cambiar esto!

    /**
     * Establece la conexión con la base de datos.
     * @return Objeto Connection si es exitosa.
     */
    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("Conexión exitosa a MySQL en 3307.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error de conexión. Verifica la BD, el puerto 3307 y la contraseña de root.");
            e.printStackTrace();
        }
        return conexion;
    }

    /**
     * Cierra la conexión.
     */
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                // System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}