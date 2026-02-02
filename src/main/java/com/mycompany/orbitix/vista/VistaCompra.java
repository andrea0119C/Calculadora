/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.orbitix.vista;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import com.mycompany.orbitix.modelo.Pasaje;
import com.mycompany.orbitix.modelo.Usuario;
import com.mycompany.orbitix.modelo.Vuelo;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.mycompany.orbitix.controlador.HistorialControlador;

public class VistaCompra extends JFrame {

    private JTextField txtCodigoCompra;
    private JTextField txtTotal;
    private JButton btnCancelarCompra;
    private Vuelo vueloActual;
    private List<Pasaje> pasajesActuales;
    private Usuario usuarioLogueado;
    
    private void habilitarCamposTarjeta(boolean enabled) {
    txtNumTarjeta1.setEnabled(enabled);
    txtNumTarjeta.setEnabled(enabled);
    txtFecha.setEnabled(enabled);
    txtCVV.setEnabled(enabled);

    if (!enabled) {
        txtNumTarjeta1.setText("");
        txtNumTarjeta.setText("");
        txtFecha.setText("");
        txtCVV.setText("");
    }
}

    public VistaCompra(JFrame parent, Vuelo vuelo, List<Pasaje> pasajes, Usuario usuario)  {
    initComponents();
   
    ((AbstractDocument) txtNumTarjeta1.getDocument()).setDocumentFilter(new SoloDigitosFilter(19)); // tarjeta
    ((AbstractDocument) txtCVV.getDocument()).setDocumentFilter(new SoloDigitosFilter(4));          // cvv
    ((AbstractDocument) txtNumTarjeta.getDocument()).setDocumentFilter(new SoloLetrasYEspaciosFilter(40)); // titular
    ((AbstractDocument) txtFecha.getDocument()).setDocumentFilter(new FechaMMYYFilter());           // MM/AA

    habilitarCamposTarjeta(false);
    btnPagar.setEnabled(false);
    this.vueloActual = vuelo;
    this.pasajesActuales = pasajes;
    this.usuarioLogueado = usuario;
    
    cargarResumen(pasajesActuales);
    
    
            rbVisa.addActionListener(e -> {
        habilitarCamposTarjeta(true);
        btnPagar.setEnabled(true); 
        txtNumTarjeta1.requestFocusInWindow();
    });

    rbMasterCard.addActionListener(e -> {
        habilitarCamposTarjeta(true);
        btnPagar.setEnabled(true); 
        txtNumTarjeta1.requestFocusInWindow();
    });

    
        Fondo fondo = new Fondo("/recursos/fondo_vPrincipal_orbitix.png");
        fondo.setLayout(new java.awt.BorderLayout());
        setContentPane(fondo);
        panelCompraFinal.setOpaque(false); 
        fondo.add(panelCompraFinal, java.awt.BorderLayout.CENTER);
         

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
public void cargarResumen(java.util.List<Pasaje> pasajes) {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("Pasajero");
    modelo.addColumn("Asiento");
    modelo.addColumn("Precio");

    double totalCalculado = 0;

    if (pasajes != null) {
        for (Pasaje p : pasajes) {
            double precioFinal = p.getPrecio() + p.getRecargo();
            totalCalculado += precioFinal;
            
            modelo.addRow(new Object[]{
                p.getPasajero().getNombre(),
                p.getAsiento(),
                String.format("%.2f", precioFinal)
            });
        }
    }
    jTable1.setModel(modelo);
    System.out.println("Total calculado: " + totalCalculado);
}

    public void setTotalTexto(String total) {
    System.out.println("Total a pagar: " + total); 
    }
    public String getCodigoCompra() {
        return txtCodigoCompra.getText();
    }

    public double getTotal() {
        return Double.parseDouble(txtTotal.getText());
    }

    public JButton getBtnPagar() {
        return btnPagar;
    }

    public void addBtnPagarListener(ActionListener l) {
        btnPagar.addActionListener(l);
    }

    public String getCodigo() {
        return txtCodigoCompra.getText();
    }

    public void limpiarCampos() {
        txtCodigoCompra.setText("");
        txtTotal.setText("");
    }

public boolean validarAntesDePagar() {

    if (!rbVisa.isSelected() && !rbMasterCard.isSelected()) {
        JOptionPane.showMessageDialog(this,
                "Debes seleccionar un tipo de tarjeta (VISA o MASTERCARD).",
                "Tarjeta no seleccionada",
                JOptionPane.WARNING_MESSAGE);
        return false;
    }

    String numeroRaw = txtNumTarjeta1.getText();
    String titular = txtNumTarjeta.getText(); 
    String fecha = txtFecha.getText();
    String cvv = txtCVV.getText();

    if (numeroRaw.trim().isEmpty() || titular.trim().isEmpty() || fecha.trim().isEmpty() || cvv.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Completa todos los datos de la tarjeta antes de pagar.",
                "Datos incompletos",
                JOptionPane.WARNING_MESSAGE);
        return false;
    }

    String numero = limpiarNumeroTarjeta(numeroRaw);

    if (!numero.matches("^\\d{13,19}$")) {
        JOptionPane.showMessageDialog(this,
                "El número de tarjeta debe tener entre 13 y 19 dígitos.\nPuedes escribirlo con espacios o guiones.",
                "Número de tarjeta inválido",
                JOptionPane.WARNING_MESSAGE);
        txtNumTarjeta1.requestFocusInWindow();
        return false;
    }

    if (!esNumeroTarjetaValidoLuhn(numero)) {
        JOptionPane.showMessageDialog(this,
                "El número de tarjeta no es válido (falló la verificación).",
                "Número de tarjeta inválido",
                JOptionPane.WARNING_MESSAGE);
        txtNumTarjeta1.requestFocusInWindow();
        return false;
    }

    if (!validarNombreTitular(titular)) {
        JOptionPane.showMessageDialog(this,
                "El nombre del titular debe contener solo letras y al menos 2 palabras.\nEjemplo: \"Juan Perez\"",
                "Nombre inválido",
                JOptionPane.WARNING_MESSAGE);
        txtNumTarjeta.requestFocusInWindow();
        return false;
    }

    if (!validarFechaMMYYNoVencida(fecha)) {
        JOptionPane.showMessageDialog(this,
                "La fecha debe estar en formato MM/AA y no puede estar vencida.\nEjemplo: 08/27",
                "Fecha inválida",
                JOptionPane.WARNING_MESSAGE);
        txtFecha.requestFocusInWindow();
        return false;
    }

    if (!validarCVV(cvv)) {
        JOptionPane.showMessageDialog(this,
                "El CVV debe tener 3 o 4 dígitos.",
                "CVV inválido",
                JOptionPane.WARNING_MESSAGE);
        txtCVV.requestFocusInWindow();
        return false;
    }

    if (usuarioLogueado == null || vueloActual == null || pasajesActuales == null || pasajesActuales.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "No hay datos de compra para registrar.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    return true;
}


public void procesarPagoYRegistrarHistorial() {
    java.util.List<String> asientosSeleccionados = new java.util.ArrayList<>();
    double totalFinal = 0;

    for (Pasaje p : pasajesActuales) {
        asientosSeleccionados.add(p.getAsiento());
        totalFinal += (p.getPrecio() + p.getRecargo());
    }

    HistorialControlador.registrarHistorial(
            usuarioLogueado,
            vueloActual,
            asientosSeleccionados,
            totalFinal
    );

    JOptionPane.showMessageDialog(this, "Pago realizado y vuelo registrado en tu historial.");
}

private String limpiarNumeroTarjeta(String input) {
    if (input == null) return "";
    return input.replaceAll("[\\s-]", ""); 
}

private boolean esNumeroTarjetaValidoLuhn(String numero) {
    
    int suma = 0;
    boolean alternar = false;

    for (int i = numero.length() - 1; i >= 0; i--) {
        int n = numero.charAt(i) - '0';
        if (alternar) {
            n *= 2;
            if (n > 9) n -= 9;
        }
        suma += n;
        alternar = !alternar;
    }
    return suma % 10 == 0;
}

private boolean validarNombreTitular(String nombre) {
    if (nombre == null) return false;
    nombre = nombre.trim();

    
    if (nombre.split("\\s+").length < 2) return false;

    
    return nombre.matches("^[\\p{L}]+(?:\\s+[\\p{L}]+)+$");
}

private boolean validarFechaMMYYNoVencida(String fecha) {
    if (fecha == null) return false;
    fecha = fecha.trim();

    
    if (!fecha.matches("^(0[1-9]|1[0-2])\\/\\d{2}$")) return false;

    try {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yy", Locale.US);
        YearMonth ym = YearMonth.parse(fecha, fmt);

        
        YearMonth actual = YearMonth.now();
        return !ym.isBefore(actual);

    } catch (DateTimeParseException ex) {
        return false;
    }
}

private boolean validarCVV(String cvv) {
    if (cvv == null) return false;
    cvv = cvv.trim();

    
    return cvv.matches("^\\d{3,4}$");
}


private static class SoloDigitosFilter extends DocumentFilter {
    private final int maxLen;

    public SoloDigitosFilter(int maxLen) {
        this.maxLen = maxLen;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;
        reemplazar(fb, offset, 0, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) return;
        reemplazar(fb, offset, length, text, attrs);
    }

    private void reemplazar(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {

        
        String filtrado = text.replaceAll("\\D", "");

        int currentLen = fb.getDocument().getLength();
        int newLen = currentLen - length + filtrado.length();

        if (newLen <= maxLen) {
            super.replace(fb, offset, length, filtrado, attrs);
        } else {
        
            int allowed = maxLen - (currentLen - length);
            if (allowed > 0) {
                super.replace(fb, offset, length, filtrado.substring(0, allowed), attrs);
            }
        }
    }
}


private static class SoloLetrasYEspaciosFilter extends DocumentFilter {
    private final int maxLen;

    public SoloLetrasYEspaciosFilter(int maxLen) {
        this.maxLen = maxLen;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;
        replace(fb, offset, 0, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {

        if (text == null) return;

        
        String filtrado = text.replaceAll("[^\\p{L}\\s]", "");

        int currentLen = fb.getDocument().getLength();
        int newLen = currentLen - length + filtrado.length();

        if (newLen <= maxLen) {
            super.replace(fb, offset, length, filtrado, attrs);
        } else {
            int allowed = maxLen - (currentLen - length);
            if (allowed > 0) {
                super.replace(fb, offset, length, filtrado.substring(0, allowed), attrs);
            }
        }
    }
}

private static class FechaMMYYFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;
        replace(fb, offset, 0, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {

        if (text == null) return;

        String actual = fb.getDocument().getText(0, fb.getDocument().getLength());
        StringBuilder sb = new StringBuilder(actual);
        sb.replace(offset, offset + length, text);

        String digits = sb.toString().replaceAll("\\D", "");

        if (digits.length() > 4) digits = digits.substring(0, 4);

       
        String formateado;
        if (digits.length() <= 2) {
            formateado = digits;
        } else {
            formateado = digits.substring(0, 2) + "/" + digits.substring(2);
        }

        if (formateado.length() > 5) formateado = formateado.substring(0, 5);

        fb.replace(0, fb.getDocument().getLength(), formateado, attrs);
    }
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
                .addGap(22, 22, 22))
        );
        panelTarjetaLayout.setVerticalGroup(
            panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTarjetaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbVisa)
                    .addComponent(rbMasterCard))
                .addGroup(panelTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTarjetaLayout.createSequentialGroup()
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
                            .addComponent(txtCVV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(42, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTarjetaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPagar)
                        .addGap(16, 16, 16))))
        );

        javax.swing.GroupLayout panelCompraFinalLayout = new javax.swing.GroupLayout(panelCompraFinal);
        panelCompraFinal.setLayout(panelCompraFinalLayout);
        panelCompraFinalLayout.setHorizontalGroup(
            panelCompraFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompraFinalLayout.createSequentialGroup()
                .addGap(393, 393, 393)
                .addGroup(panelCompraFinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                    .addGroup(panelCompraFinalLayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(labeltargeta)))
                .addContainerGap(470, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
}
