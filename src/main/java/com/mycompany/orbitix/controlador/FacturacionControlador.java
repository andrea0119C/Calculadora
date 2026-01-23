/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author USUARIO
 */
package com.mycompany.orbitix.controlador;

import com.mycompany.orbitix.modelo.Pasaje;
import com.mycompany.orbitix.modelo.Usuario;
import com.mycompany.orbitix.modelo.Vuelo;
import com.mycompany.orbitix.util.GenerarFactura;
import com.mycompany.orbitix.util.Ticket;
import com.mycompany.orbitix.vista.VistaFacturacion;
import com.mycompany.orbitix.vista.VistaPrincipal;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FacturacionControlador implements ActionListener {

    private VistaFacturacion vista;
    private Usuario usuario;
    private Vuelo vuelo;
    private List<Pasaje> pasajes;

    public FacturacionControlador(VistaFacturacion vista, Usuario usuario, Vuelo vuelo, List<Pasaje> pasajes) {
        this.vista = vista;
        this.usuario = usuario;
        this.vuelo = vuelo;
        this.pasajes = pasajes;

        this.vista.addBtnImprimirFacturaListener(this);
        this.vista.addBtnImprimirTicketListener(this);
        this.vista.addBtnContinuarCompraListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

      
        if (e.getSource() == vista.getBtnImprimirFactura()) {
            imprimirFactura();
        }

       
        if (e.getSource() == vista.getBtnImprimirTicket()) {
            imprimirTickets();
        }

       
        if (e.getSource() == vista.getBtnContinuarCompra()) {
             VistaPrincipal principal = new VistaPrincipal(this.usuario);
             new VistaPrincipalControlador(principal, this.usuario);
             principal.setVisible(true);
             vista.dispose();
        }
    }

    private void imprimirFactura() {
        try {
            String numFactura = "FAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            String contenido = GenerarFactura.generarFactura(numFactura, usuario, vuelo, pasajes);
            String nombreArchivo = "Factura_" + numFactura + ".txt";
            guardarArchivo(nombreArchivo, contenido);
            mostrarPrevisualizacion("Factura Generada", contenido);
            
        } catch (Exception ex) {
            vista.mostrarMensaje("Error al generar factura: " + ex.getMessage());
        }
    }

    private void imprimirTickets() {
        if (pasajes == null || pasajes.isEmpty()) {
            vista.mostrarMensaje("No hay pasajes para generar tickets.");
            return;
        }

        StringBuilder contenidoTickets = new StringBuilder();
        for (Pasaje p : pasajes) {
            contenidoTickets.append(Ticket.generarBoardingPass(p));
            contenidoTickets.append("\n\n"); 
        }

        try {
         
            String codVuelo = (vuelo != null) ? vuelo.getCodigo() : "GENERICO";
            String nombreArchivo = "Tickets_" + codVuelo + "_" + System.currentTimeMillis() + ".txt";
            
            guardarArchivo(nombreArchivo, contenidoTickets.toString());
            
         
            mostrarPrevisualizacion("Tickets de Abordaje", contenidoTickets.toString());
            
        } catch (Exception ex) {
            vista.mostrarMensaje("Error al generar tickets: " + ex.getMessage());
        }
    }
    private void mostrarPrevisualizacion(String titulo, String texto) {

        JTextArea areaTexto = new JTextArea(texto);
        areaTexto.setEditable(false); 
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setPreferredSize(new Dimension(500, 600)); 
        JOptionPane.showMessageDialog(vista, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private void guardarArchivo(String nombre, String contenido) throws IOException {
        try (FileWriter escritor = new FileWriter(nombre)) {
            escritor.write(contenido);
        }
    }
}