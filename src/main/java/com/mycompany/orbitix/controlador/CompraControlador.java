/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orbitix.controlador;

import com.mycompany.orbitix.datos.RepositorioArchivos;
import com.mycompany.orbitix.modelo.*;
import com.mycompany.orbitix.vista.VistaCompra;
import com.mycompany.orbitix.vista.VistaFacturacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.JOptionPane;

public class CompraControlador implements ActionListener {

    private VistaCompra vista;
    private Usuario usuario;
    private Vuelo vuelo;
    private List<Pasaje> pasajes;
    private double totalPagar;

    public CompraControlador(VistaCompra vista, Usuario usuario, Vuelo vuelo, List<Pasaje> pasajes) {
        this.vista = vista;
        this.usuario = usuario;
        this.vuelo = vuelo;
        this.pasajes = pasajes;

        this.totalPagar = calcularTotal();
   
        this.vista.cargarResumen(pasajes);

        this.vista.addBtnPagarListener(this);
    }

    private double calcularTotal() {
        double suma = 0;
        for (Pasaje p : pasajes) {
            suma += (p.getPrecio() + p.getRecargo());
        }
        return suma;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        realizarPago();
    }

    private void realizarPago() {
        try {

            String idPago = UUID.randomUUID().toString();
            MetodoPago metodoTarjeta = new PagoTarjeta(); 
            
            Pago pago = new Pago(idPago, totalPagar, new Date(), metodoTarjeta);
            
            String codigoCompra = "COM-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            
            Compra nuevaCompra = new Compra(codigoCompra, usuario);

            nuevaCompra.setPago(pago);
            for (Pasaje p : pasajes) {
                nuevaCompra.agregarPasaje(p); 
            }
            RepositorioArchivos repo = new RepositorioArchivos();
            repo.guardarCompra(nuevaCompra);
            JOptionPane.showMessageDialog(vista, "¡Pago realizado con éxito!\nTotal: $" + String.format("%.2f", totalPagar));
            VistaFacturacion vistaFac = new VistaFacturacion();
            new FacturacionControlador(vistaFac, usuario, vuelo, pasajes); 
            
            vistaFac.setVisible(true);
            vista.dispose(); 
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al procesar el pago: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void cancelar() {
        vista.dispose();
    }
}