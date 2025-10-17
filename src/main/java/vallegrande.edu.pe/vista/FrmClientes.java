package vallegrande.edu.pe.vista;

import vallegrande.edu.pe.modelo.Cliente;
import vallegrande.edu.pe.dao.ClienteDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmClientes extends JFrame {

    // Componentes de la interfaz
    private JTable tblClientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtId, txtNombre, txtContacto, txtHistorial;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JScrollPane scrollPane;

    // DAO (Acceso a datos)
    private ClienteDAO clienteDAO = new ClienteDAO();

    public FrmClientes() {
        // Configuración inicial del formulario
        setTitle("Gestión de Clientes - CRUD MAESTRO_CLIENTES");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar formulario

        setLayout(new BorderLayout(10, 10)); // Usar BorderLayout para la estructura principal

        inicializarComponentes();
        cargarClientes(); // Carga inicial de datos
    }

    private void inicializarComponentes() {
        // --- Panel de Campos (Norte) ---
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10)); // 5 filas, 2 columnas
        panelCampos.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        txtId = new JTextField();
        txtId.setEnabled(false); // ID no editable, solo lectura
        txtNombre = new JTextField();
        txtContacto = new JTextField();
        txtHistorial = new JTextField();

        panelCampos.add(new JLabel("ID:"));
        panelCampos.add(txtId);
        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(txtNombre);
        panelCampos.add(new JLabel("Contacto (Email/Teléfono):"));
        panelCampos.add(txtContacto);
        panelCampos.add(new JLabel("Historial Compras:"));
        panelCampos.add(txtHistorial);

        // --- Panel de Botones (Oeste) ---
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));

        btnGuardar = new JButton("Guardar (Insertar)");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar Campos");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Colocar los paneles de control en el NORTE del frame
        JPanel panelControl = new JPanel(new BorderLayout(10, 10));
        panelControl.add(panelCampos, BorderLayout.CENTER);
        panelControl.add(panelBotones, BorderLayout.EAST);
        add(panelControl, BorderLayout.NORTH);


        // --- Tabla (Centro) ---
        String[] columnas = {"ID", "Nombre", "Contacto", "Historial Compras"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblClientes = new JTable(modeloTabla);
        scrollPane = new JScrollPane(tblClientes);
        add(scrollPane, BorderLayout.CENTER);

        // --- Configuración de Eventos ---
        configurarEventos();
    }

    private void configurarEventos() {
        // Evento: Click en la tabla para cargar datos en los campos
        tblClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tblClientes.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtContacto.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtHistorial.setText(modeloTabla.getValueAt(fila, 3).toString());
                }
            }
        });

        // Evento: Guardar
        btnGuardar.addActionListener(e -> {
            Cliente nuevoCliente = new Cliente(
                    txtNombre.getText(),
                    txtContacto.getText(),
                    txtHistorial.getText()
            );
            if (clienteDAO.insertar(nuevoCliente)) {
                JOptionPane.showMessageDialog(this, "Cliente guardado exitosamente.");
                cargarClientes();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento: Actualizar
        btnActualizar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar.");
                return;
            }
            Cliente clienteActualizado = new Cliente(
                    Integer.parseInt(txtId.getText()),
                    txtNombre.getText(),
                    txtContacto.getText(),
                    txtHistorial.getText()
            );
            if (clienteDAO.actualizar(clienteActualizado)) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.");
                cargarClientes();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento: Eliminar
        btnEliminar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.");
                return;
            }
            int id = Integer.parseInt(txtId.getText());
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este cliente?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                if (clienteDAO.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente.");
                    cargarClientes();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Evento: Limpiar
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void cargarClientes() {
        // Limpiar filas existentes
        modeloTabla.setRowCount(0);

        // Obtener clientes del DAO
        List<Cliente> clientes = clienteDAO.listarTodos();

        // Llenar el modelo de la tabla
        for (Cliente c : clientes) {
            modeloTabla.addRow(new Object[]{
                    c.getIdCliente(),
                    c.getNombre(),
                    c.getContacto(),
                    c.getHistorialCompras()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtContacto.setText("");
        txtHistorial.setText("");
        tblClientes.clearSelection();
    }
}