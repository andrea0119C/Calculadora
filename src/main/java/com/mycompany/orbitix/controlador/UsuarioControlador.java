/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orbitix.controlador;
import com.mycompany.orbitix.datos.RepositorioArchivos;
import com.mycompany.orbitix.modelo.Cliente;
import com.mycompany.orbitix.modelo.Usuario;
/**
 *
 * @author paula
 */

public class UsuarioControlador {
 private RepositorioArchivos repo = new RepositorioArchivos();
   private static Usuario usuarioActual;
   
    public boolean existeCorreo(String email) {
 
        return repo.autenticarUsuario(email, "") != null || 
               repo.autenticarUsuario(email, "validar_existencia") != null;
    }

    public boolean registrar(String cedula, String nombre, String email, String pass) {

    if (cedula.isEmpty() || nombre.isEmpty() ||
        email.isEmpty() || pass.isEmpty() || pass.length() < 4) {
        return false;
    }

    Cliente nuevo = new Cliente(
        cedula.trim(),
        nombre.trim(),
        email.trim(),
        pass.trim()
    );

    repo.guardarCliente(nuevo);
    return true;
    }

    public Usuario login(String email, String pass) {
    if (email.isEmpty() || pass.isEmpty()) return null;

    Usuario u = repo.autenticarUsuario(email.trim(), pass.trim());

    if (u != null) {
        usuarioActual = u; 
    }

    return u;
    }
    public static Usuario getUsuarioActual() {
    return usuarioActual;
    }

    public static void cerrarSesion() {
    usuarioActual = null;
    }
}

