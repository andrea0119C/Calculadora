/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.orbitix.vista;

import com.mycompany.orbitix.modelo.MetodoPago;
import com.mycompany.orbitix.modelo.PagoTarjeta;
import com.mycompany.orbitix.modelo.Pasaje;
import com.mycompany.orbitix.modelo.Usuario;
import com.mycompany.orbitix.modelo.Vuelo;
import java.util.List;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author karla
 */

public class VistaCompra extends javax.swing.JFrame {
    
    private Vuelo vuelo;
    private List<Pasaje> pasajes; 
    private Usuario usuarioLogueado;
    

    public VistaCompra(JFrame padre, Vuelo vuelo, List<Pasaje> pasajes, Usuario usuario) {
        this.vuelo = vuelo;
        this.pasajes = pasajes; // Asignación de la lista de objetos
        this.usuarioLogueado = usuario;

        initComponents();

        // Configuración estética manual
        Fondo fondo = new Fondo("/recursos/fondo_vPrincipal_orbitix.png");
        fondo.setLayout(new java.awt.BorderLayout());
        setContentPane(fondo);
        panelCompraFinal.setOpaque(false); 
        fondo.add(panelCompraFinal, java.awt.BorderLayout.CENTER);
        panelTarjeta.setBackground(new java.awt.Color(102, 0, 153, 200)); 

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        llenarTablaResumen();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelCompraFinal = new javax.swing.JPanel();
        labeltargeta = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        labelCp = new javax.swing.JLabel();
        panelTarjeta = new javax.swing.JPanel();
        rbVisa = new javax.swing.JRadioButton();
        rbMasterCard = new javax.swing.JRadioButton();
        labelNomTitular = new javax.swing.JLabel();
        labelNumTarjeta1 = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        labelCVV = new javax.swing.JLabel();
        txtNumTarjeta = new javax.swing.JTextField();
        txtNumTarjeta1 = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        txtCVV = new javax.swing.JTextField();
        btnPagar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelCompraFinal.setBackground(new java.awt.Color(153, 0, 153));
        panelCompraFinal.setPreferredSize(new java.awt.Dimension(1360, 677));

        labeltargeta.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        labeltargeta.setForeground(new java.awt.Color(255, 255, 255));
        labeltargeta.setText("Ingresa tarjeta para el pago");
        labeltargeta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        labelCp.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        labelCp.setForeground(new java.awt.Color(255, 255, 255));
        labelCp.setText("COMPRA DE PASAJES");
        labelCp.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        panelTarjeta.setBackground(new java.awt.Color(102, 0, 153));

        buttonGroup1.add(rbVisa);
        rbVisa.setForeground(new java.awt.Color(255, 255, 255));
        rbVisa.setText("VISA");
        rbVisa.setContentAreaFilled(false);

        buttonGroup1.add(rbMasterCard);
        rbMasterCard.setForeground(new java.awt.Color(255, 255, 255));
        rbMasterCard.setText("MASTERCARD");
        rbMasterCard.setContentAreaFilled(false);

        labelNomTitular.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelNomTitular.setForeground(new java.awt.Color(255, 255, 255));
        labelNomTitular.setText("Nombre Titular:");
        labelNomTitular.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        labelNumTarjeta1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelNumTarjeta1.setForeground(new java.awt.Color(255, 255, 255));
        labelNumTarjeta1.setText("Número de tarjeta:");
        labelNumTarjeta1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        labelFecha.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelFecha.setForeground(new java.awt.Color(255, 255, 255));
        labelFecha.setText("Fecha (MM/AA):");
        labelFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        labelCVV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelCVV.setForeground(new java.awt.Color(255, 255, 255));
        labelCVV.setText("CVV:");
        labelCVV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        txtNumTarjeta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumTarjeta1ActionPerformed(evt);
            }
        });

        btnPagar.setBackground(new java.awt.Color(51, 204, 255));
        btnPagar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPagar.setForeground(new java.awt.Color(255, 255, 255));
        btnPagar.setText("PAGAR");
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTarjetaLayout = new javax.swing.GroupLayout(panelTarjeta);
        panelTarjeta.setLayout(panelTarjetaLayout);
        panelTarjetaLayout.setHorizontalGroup(
            panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTarjetaLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(rbVisa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbMasterCard)
                .addGap(104, 104, 104))
            .addGroup(panelTarjetaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTarjetaLayout.createSequentialGroup()
                        .addComponent(labelNumTarjeta1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNumTarjeta1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTarjetaLayout.createSequentialGroup()
                        .addComponent(labelNomTitular)
                        .addGap(81, 81, 81)
                        .addComponent(txtNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTarjetaLayout.createSequentialGroup()
                        .addGroup(panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTarjetaLayout.createSequentialGroup()
                                .addComponent(labelFecha)
                                .addGap(77, 77, 77))
                            .addGroup(panelTarjetaLayout.createSequentialGroup()
                                .addComponent(labelCVV)
                                .addGap(144, 144, 144)))
                        .addGroup(panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCVV, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPagar)
                .addContainerGap())
        );
        panelTarjetaLayout.setVerticalGroup(
            panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTarjetaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbVisa)
                    .addComponent(rbMasterCard))
                .addGap(12, 12, 12)
                .addGroup(panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelNumTarjeta1)
                    .addComponent(txtNumTarjeta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNomTitular)
                    .addComponent(txtNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFecha)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCVV)
                    .addComponent(txtCVV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPagar))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelCompraFinalLayout = new javax.swing.GroupLayout(panelCompraFinal);
        panelCompraFinal.setLayout(panelCompraFinalLayout);
        panelCompraFinalLayout.setHorizontalGroup(
            panelCompraFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompraFinalLayout.createSequentialGroup()
                .addGap(393, 393, 393)
                .addGroup(panelCompraFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(panelCompraFinalLayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(labeltargeta)))
                .addContainerGap(515, Short.MAX_VALUE))
            .addGroup(panelCompraFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelCompraFinalLayout.createSequentialGroup()
                    .addGap(441, 441, 441)
                    .addComponent(labelCp)
                    .addContainerGap(544, Short.MAX_VALUE)))
        );
        panelCompraFinalLayout.setVerticalGroup(
            panelCompraFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompraFinalLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labeltargeta)
                .addGap(18, 18, 18)
                .addComponent(panelTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(panelCompraFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelCompraFinalLayout.createSequentialGroup()
                    .addGap(51, 51, 51)
                    .addComponent(labelCp)
                    .addContainerGap(572, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(panelCompraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(panelCompraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumTarjeta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumTarjeta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumTarjeta1ActionPerformed

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        // 1. Validaciones iniciales
    if (usuarioLogueado == null) {
        JOptionPane.showMessageDialog(this, "Error: Sesión no válida.");
        return;
    }

    // Usamos el campo txtNumTarjeta1 que es el que definiste en tu diseño para el número
    if (txtNumTarjeta1.getText().trim().isEmpty() || txtCVV.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete los datos de su tarjeta.");
        return;
    }

    try {
        // 2. Crear la compra vinculada al usuario logueado
        String codigoCompra = "C-" + (int) (Math.random() * 10000);
        com.mycompany.orbitix.modelo.Compra nuevaCompra = new com.mycompany.orbitix.modelo.Compra(codigoCompra, usuarioLogueado);

        // Agregamos los pasajes que recibimos en el constructor
        if (pasajes != null) {
            for (com.mycompany.orbitix.modelo.Pasaje p : pasajes) {
                nuevaCompra.agregarPasaje(p);
            }
        }

        // 3. Configuración del Pago (Patrón Strategy)
        // CORRECCIÓN: Declaramos idPago antes de usarla
        String idPago = "PAG-" + (System.currentTimeMillis() % 10000);
        
        com.mycompany.orbitix.modelo.MetodoPago metodoSeleccionado = new com.mycompany.orbitix.modelo.PagoTarjeta(); 

        com.mycompany.orbitix.modelo.Pago objetoPago = new com.mycompany.orbitix.modelo.Pago(
                idPago,
                nuevaCompra.getTotal(),
                new java.util.Date(),
                metodoSeleccionado 
        );

        // 4. Procesar el pago
        boolean pagoExitoso = objetoPago.procesarPago();

        if (pagoExitoso) {
            nuevaCompra.setPago(objetoPago);
            
            // 5. Persistencia y Documentación
            com.mycompany.orbitix.datos.RepositorioArchivos repo = new com.mycompany.orbitix.datos.RepositorioArchivos();
            repo.guardarCompra(nuevaCompra);
            
            // Generar Factura
            String numFactura = "FAC-" + (System.currentTimeMillis() % 100000);
            String facturaTxt = com.mycompany.orbitix.util.GenerarFactura.generarFactura(
                    numFactura, usuarioLogueado, vuelo, pasajes
            );

            // Guardar factura en archivo físico
            String nombreArchivo = "factura_" + numFactura + ".txt";
            repo.guardarFacturaTxt(nombreArchivo, facturaTxt);

            // 6. Interfaz de usuario: Mostrar factura
            javax.swing.JTextArea area = new javax.swing.JTextArea(facturaTxt);
            area.setEditable(false);
            area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            javax.swing.JScrollPane sp = new javax.swing.JScrollPane(area);
            sp.setPreferredSize(new java.awt.Dimension(650, 450));
            
            JOptionPane.showMessageDialog(this, sp, "Factura Generada", JOptionPane.INFORMATION_MESSAGE);
            
            // 7. Mensaje de éxito final y redirección
            JOptionPane.showMessageDialog(this, "¡Compra Exitosa!\nCódigo de Compra: " + codigoCompra + "\nTotal: $" + String.format("%.2f", nuevaCompra.getTotal()));

            // Volver a la vista principal
            new VistaPrincipal(usuarioLogueado).setVisible(true);
            this.dispose();
            
        } else {
            JOptionPane.showMessageDialog(this, "El pago ha sido rechazado por la entidad bancaria.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error crítico al procesar la compra: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnPagarActionPerformed

    /**
     * @param args the command line arguments
     */
public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(VistaCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Solo un invokeLater con el mensaje de aviso
        java.awt.EventQueue.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Orbitix: Esta ventana requiere datos de vuelo para iniciar.\nPor favor, acceda desde la búsqueda de vuelos.");
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPagar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel labelCVV;
    private javax.swing.JLabel labelCp;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelNomTitular;
    private javax.swing.JLabel labelNumTarjeta1;
    private javax.swing.JLabel labeltargeta;
    private javax.swing.JPanel panelCompraFinal;
    private javax.swing.JPanel panelTarjeta;
    private javax.swing.JRadioButton rbMasterCard;
    private javax.swing.JRadioButton rbVisa;
    private javax.swing.JTextField txtCVV;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNumTarjeta;
    private javax.swing.JTextField txtNumTarjeta1;
    // End of variables declaration//GEN-END:variables

    private void llenarTablaResumen() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);

        for (Pasaje p : pasajes) {
            // Mostramos la suma de base + recargo en la tabla
            double precioFinal = p.getPrecio() + p.getRecargo(); 

            modelo.addRow(new Object[]{
                p.getPasajero().getNombre(),
                p.getAsiento(),
                p.getClase(),
                "$" + String.format(Locale.US, "%.2f", precioFinal)
            });
        }
    }

}
