/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orbitix.controlador;

import com.mycompany.orbitix.datos.RepositorioArchivos;
import com.mycompany.orbitix.modelo.*;
import com.mycompany.orbitix.vista.*;
import javax.swing.*;
import java.util.List;

public class PasajeControlador {

    private VistaRegistroPasajero vistaRegistro;
    private Vuelo vuelo;
    private List<String> asientos;
    private Usuario usuario;
    private List<Pasaje> pasajesRegistrados;
    private int indiceActual = 0;

    public PasajeControlador(VistaRegistroPasajero vista, Vuelo vuelo, List<String> asientos, Usuario usuario) {
        this.vistaRegistro = vista;
        this.vuelo = vuelo;
        this.asientos = asientos;
        this.usuario = usuario;
        this.pasajesRegistrados = new java.util.ArrayList<>();

        vistaRegistro.getBtnSiguiente().addActionListener(e -> siguiente());
        vistaRegistro.getBtnAnterior().addActionListener(e -> anterior());

        vistaRegistro.setInfoVuelo(vuelo);
        actualizarVista();
    }

    private void siguiente() {
    try {
        Pasajero pasajero = vistaRegistro.capturarPasajero();
        Equipaje equipaje = vistaRegistro.capturarEquipaje();
        String asiento = asientos.get(indiceActual);

        RepositorioArchivos repo = new RepositorioArchivos();
        repo.guardarPasajero(pasajero);

        ClaseAsiento clase = calcularClase(asiento);

        String codigoTkt = "TKT-" + System.currentTimeMillis() + "-" + (indiceActual + 1);
        Pasaje pasaje = new Pasaje(codigoTkt, vuelo.getPrecio(), asiento, clase, pasajero, vuelo, equipaje);

        // Guardar pasaje en la lista
        if (indiceActual < pasajesRegistrados.size()) {
            pasajesRegistrados.set(indiceActual, pasaje);
        } else {
            pasajesRegistrados.add(pasaje);
        }

        if (indiceActual < asientos.size() - 1) {
            indiceActual++;
            actualizarVista();
        } else {
            finalizarRegistro();
        }

    } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(vistaRegistro, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void anterior() {
        if (indiceActual > 0) {
            indiceActual--;
            actualizarVista();
        }
    }

    private void actualizarVista() {
        vistaRegistro.actualizarInterfaz(pasajesRegistrados, indiceActual, asientos);
    }

    private ClaseAsiento calcularClase(String asiento) {
        try {
            int fila = Integer.parseInt(asiento.substring(1));
            if (fila <= 2) return ClaseAsiento.PRIMERA_CLASE;
            else if (fila <= 5) return ClaseAsiento.EJECUTIVA;
            else return ClaseAsiento.ECONOMICA;
        } catch (Exception e) {
            return ClaseAsiento.ECONOMICA;
        }
    }

    private void finalizarRegistro() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(vistaRegistro);
        JOptionPane.showMessageDialog(parentFrame, "Registro de pasajeros finalizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        vistaRegistro.dispose();
    }
}
