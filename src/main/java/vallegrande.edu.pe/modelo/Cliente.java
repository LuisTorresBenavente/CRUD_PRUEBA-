package vallegrande.edu.pe.modelo;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String contacto; // Email, teléfono o dirección
    private String historialCompras;

    // Constructor vacío
    public Cliente() {}

    // Constructor completo
    public Cliente(int idCliente, String nombre, String contacto, String historialCompras) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.contacto = contacto;
        this.historialCompras = historialCompras;
    }

    // Constructor para CREATE (sin ID)
    public Cliente(String nombre, String contacto, String historialCompras) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.historialCompras = historialCompras;
    }

    // --- Getters y Setters ---
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getHistorialCompras() { return historialCompras; }
    public void setHistorialCompras(String historialCompras) { this.historialCompras = historialCompras; }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", contacto='" + contacto + '\'' +
                ", historialCompras='" + historialCompras + '\'' +
                '}';
    }
}