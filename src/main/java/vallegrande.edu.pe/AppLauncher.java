package vallegrande.edu.pe;

import vallegrande.edu.pe.vista.FrmClientes;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Garantizar que Swing corra en el hilo de despacho de eventos (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Instanciar y hacer visible el formulario de clientes
                FrmClientes formulario = new FrmClientes();
                formulario.setVisible(true);
            }
        });
    }
}