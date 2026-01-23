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
import com.mycompany.orbitix.vista.VistaFacturacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


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

    }

    @Override
    public void actionPerformed(ActionEvent e) {
  
        if (e.getSource() == vista.getBtnImprimirFactura()) {
            imprimirFactura();
        }
    }

    private void imprimirFactura() {
        try {

            String numFactura = "FAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            String contenidoFactura = GenerarFactura.generarFactura(numFactura, usuario, vuelo, pasajes);
            String nombreArchivo = "Factura_" + numFactura + ".txt";
            
            try (FileWriter escritor = new FileWriter(nombreArchivo)) {
                escritor.write(contenidoFactura);
            }

            vista.mostrarMensaje("¡Factura generada con éxito!\nArchivo: " + nombreArchivo);
            
        } catch (IOException ex) {
            vista.mostrarMensaje("Error al guardar la factura: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            vista.mostrarMensaje("Ocurrió un error inesperado: " + ex.getMessage());
        }
    }
}

