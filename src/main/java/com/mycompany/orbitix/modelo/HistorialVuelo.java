/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orbitix.modelo;

/**
 *
 * @author paopa
 */

public class HistorialVuelo {

    private String cedulaUsuario;
    private String codigoVuelo;
    private String origen;
    private String destino;
    private String fecha;
    private String asiento;
    private double totalPagado;

    public HistorialVuelo(String cedulaUsuario, String codigoVuelo,
                          String origen, String destino, String fecha,
                          String asiento, double totalPagado) {
        this.cedulaUsuario = cedulaUsuario;
        this.codigoVuelo = codigoVuelo;
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.asiento = asiento;
        this.totalPagado = totalPagado;
    }

    public String getCedulaUsuario() {
        return cedulaUsuario;
    }

    public String toLine() {
        return cedulaUsuario + "|" + codigoVuelo + "|" + origen + "|" +
               destino + "|" + fecha + "|" + asiento + "|" + totalPagado;
    }

    public static HistorialVuelo fromLine(String line) {
        String[] p = line.split("\\|");
        return new HistorialVuelo(
                p[0], p[1], p[2], p[3], p[4], p[5],
                Double.parseDouble(p[6])
        );
    }
}
