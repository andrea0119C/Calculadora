/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orbitix.modelo;

import java.util.Date;

public class Pago {
    private String id;
    private double monto;
    private Date fecha;
    private MetodoPago metodo;

    public Pago(String id, double monto, Date fecha, MetodoPago metodo) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.metodo = metodo;
    }

    public boolean procesarPago() {
        return metodo.procesar(monto);
    }


    public String getId(){
        return id; 
    }
    public void setId(String id){
        this.id = id;
    }
    public double getMonto(){
        return monto;
    }
    public void setMonto(double monto){
        this.monto = monto;
    }
    public Date getFecha(){
        return fecha;
    }
    public void setFecha(Date fecha){
        this.fecha = fecha;
    }
    public MetodoPago getMetodo(){
        return metodo;
    }
    public void setMetodo(MetodoPago metodo){
        this.metodo = metodo;
    }
}
