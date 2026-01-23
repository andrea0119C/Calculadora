/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orbitix.controlador;

import com.mycompany.orbitix.datos.RepositorioArchivos;
import com.mycompany.orbitix.datos.RepositorioDatos;
import com.mycompany.orbitix.modelo.Usuario;
import com.mycompany.orbitix.vista.VistaInicio;
import com.mycompany.orbitix.vista.VistaLogin;
import com.mycompany.orbitix.vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author karla
 */
public class LoginControlador {

    private VistaLogin vista;
    private UsuarioControlador usuarioControlador;
    private int intentos = 0;

    public LoginControlador(VistaLogin vista) {
        this.vista = vista;
        this.usuarioControlador = new UsuarioControlador();

        
        this.vista.addIngresarListener(e -> login());
        this.vista.addVolverListener(e -> volver());
    }

    private void login() {
        String correo = vista.getCorreo();
        String password = vista.getPassword();

        Usuario user = usuarioControlador.login(correo, password);

        if (user != null) {
            intentos = 0;
            vista.mostrarMensaje("Bienvenido: " + user.getNombre());

            VistaPrincipal principal = new VistaPrincipal(user);
            new com.mycompany.orbitix.controlador.VistaPrincipalControlador(principal, user);
            principal.setVisible(true);
            vista.cerrar();

        } else {
            intentos++;

            if (intentos >= 3) {
                vista.mostrarMensaje(
                    "Has superado el límite de 3 intentos.\nEl sistema se cerrará."
                );
                System.exit(0);
            } else {
                vista.mostrarMensaje(
                    "Correo o contraseña incorrectos.\nIntentos restantes: " + (3 - intentos)
                );
            }
        }
    }

    private void volver() {
  
    VistaInicio inicio = new VistaInicio();
    
    new com.mycompany.orbitix.controlador.InicioControlador(inicio);
  
    inicio.setVisible(true);
    vista.cerrar();
}
}