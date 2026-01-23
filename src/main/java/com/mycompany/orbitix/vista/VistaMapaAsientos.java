package com.mycompany.orbitix.vista;

import com.mycompany.orbitix.modelo.Vuelo;
import com.mycompany.orbitix.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VistaMapaAsientos extends JDialog {

    private List<String> asientosSeleccionados = new ArrayList<>();
    private Vuelo vuelo;
    private Usuario usuarioLogueado;

    public VistaMapaAsientos(JFrame padre, Vuelo vuelo, Usuario usuario) {
        super(padre, "Seleccione sus asientos - Orbitix", true);
        this.vuelo = vuelo;
        this.usuarioLogueado = usuario;
        initComponents();
        setSize(850, 750); 
        setLocationRelativeTo(padre);
    }

    private void initComponents() {
        int capacidad = vuelo.getAvion().getCapacidad();
        int numColumnas = 6;
        int filas = (int) Math.ceil((double) capacidad / numColumnas);

        JPanel panelAsientos = new JPanel(new GridLayout(filas, numColumnas, 8, 8));
        panelAsientos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelAsientos.setBackground(new Color(30, 30, 30));

        char[] letras = {'A', 'B', 'C', 'D', 'E', 'F'};
        int generados = 0;

        JButton btnConfirmar = new JButton("CONFIRMAR SELECCIÓN (0)");
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirmar.setBackground(new Color(153, 0, 255));
        btnConfirmar.setForeground(Color.WHITE);

        for (int f = 1; f <= filas; f++) {
            Color colorClase;
            String etiquetaClase;
            if (f <= 2) {
                colorClase = new Color(255, 215, 0); // Dorado
                etiquetaClase = " [1ra]";
            } else if (f <= 5) {
                colorClase = new Color(0, 191, 255); // Celeste
                etiquetaClase = " [Eje]";
            } else {
                colorClase = Color.GREEN;
                etiquetaClase = "";
            }

            for (char letra : letras) {
                if (generados < capacidad) {
                    String nombreAsiento = letra + String.valueOf(f);
                    JButton btnAsiento = new JButton(nombreAsiento + etiquetaClase);
                    btnAsiento.setFont(new Font("Arial", Font.PLAIN, 10));

                    if (!vuelo.esAsientoDisponible(nombreAsiento)) {
                        btnAsiento.setBackground(Color.RED);
                        btnAsiento.setEnabled(false);
                    } else {
                        btnAsiento.setBackground(colorClase);
                        btnAsiento.addActionListener(e -> {
                            if (asientosSeleccionados.contains(nombreAsiento)) {
                                asientosSeleccionados.remove(nombreAsiento);
                                btnAsiento.setBackground(colorClase);
                            } else {
                                asientosSeleccionados.add(nombreAsiento);
                                btnAsiento.setBackground(Color.YELLOW);
                            }
                            btnConfirmar.setText("CONFIRMAR SELECCIÓN (" + asientosSeleccionados.size() + ")");
                        });
                    }
                    panelAsientos.add(btnAsiento);
                    generados++;
                }
            }
        }

        btnConfirmar.addActionListener(e -> {
    if (asientosSeleccionados.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe elegir al menos un asiento.");
    } else {
        JFrame framePadre = (JFrame) SwingUtilities.getWindowAncestor(this);
        VistaRegistroPasajero vistaRegistro = new VistaRegistroPasajero(
                framePadre, 
                vuelo, 
                asientosSeleccionados, 
                true,
                usuarioLogueado 
        );

        new com.mycompany.orbitix.controlador.PasajeControlador(
                vistaRegistro, 
                vuelo, 
                asientosSeleccionados, 
                usuarioLogueado
        );

        this.dispose(); 
        vistaRegistro.setVisible(true); 
    }
});

        JPanel panelLeyenda = new JPanel();
        panelLeyenda.add(new JLabel("Dorado: 1ra Clase | Celeste: Ejecutiva | Verde: Económica | Rojo: Ocupado"));

        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(panelAsientos), BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelLeyenda, BorderLayout.NORTH);
        panelSur.add(btnConfirmar, BorderLayout.SOUTH);

        this.add(panelSur, BorderLayout.SOUTH);
        // -------------------------------------------------
    }

    public List<String> getAsientosSeleccionados() {
        return this.asientosSeleccionados;
    }
}