package vallegrande.edu.pe.dao;

import vallegrande.edu.pe.conexion.ConexionBD;
import vallegrande.edu.pe.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Comandos SQL para la tabla maestro_clientes
    private static final String SQL_INSERT =
            "INSERT INTO maestro_clientes (nombre, contacto, historial_compras) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_ALL =
            "SELECT id_cliente, nombre, contacto, historial_compras FROM maestro_clientes";
    private static final String SQL_UPDATE =
            "UPDATE maestro_clientes SET nombre = ?, contacto = ?, historial_compras = ? WHERE id_cliente = ?";
    private static final String SQL_DELETE =
            "DELETE FROM maestro_clientes WHERE id_cliente = ?";


    /**
     * 1. CREATE: Inserta un nuevo cliente.
     */
    public boolean insertar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean exito = false;
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getContacto());
            stmt.setString(3, cliente.getHistorialCompras());

            exito = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            ConexionBD.cerrarConexion(conn);
        }
        return exito;
    }

    /**
     * 2. READ ALL: Lista todos los clientes.
     */
    public List<Cliente> listarTodos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("contacto"),
                        rs.getString("historial_compras")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) { e.printStackTrace(); }
            ConexionBD.cerrarConexion(conn);
        }
        return clientes;
    }

    /**
     * 3. UPDATE: Actualiza un cliente existente por ID.
     */
    public boolean actualizar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean exito = false;
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getContacto());
            stmt.setString(3, cliente.getHistorialCompras());
            stmt.setInt(4, cliente.getIdCliente());

            exito = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            ConexionBD.cerrarConexion(conn);
        }
        return exito;
    }

    /**
     * 4. DELETE: Elimina un cliente por ID.
     */
    public boolean eliminar(int idCliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean exito = false;
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idCliente);

            exito = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            ConexionBD.cerrarConexion(conn);
        }
        return exito;
    }
}