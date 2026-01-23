/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orbitix.modelo;

/**
 *
 * @author USUARIO
 */
public class PagoTarjeta implements MetodoPago{
    @Override
    public boolean procesar(double monto) {
        System.out.println("Procesando pago con TARJETA por $" + monto);
        return monto > 0;
    }
}
