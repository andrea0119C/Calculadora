/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orbitix.datos;
import com.mycompany.orbitix.modelo.HistorialVuelo;
import java.io.*;
import java.util.*;

/**
 *
 * @author paopa
 */

public class RepositorioHistorialVuelo {

    private static final String ARCHIVO = "historial_vuelos.txt";

    public static void guardar(HistorialVuelo hv) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(hv.toLine());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error al guardar historial: " + e.getMessage());
        }
    }

    public static List<HistorialVuelo> listarPorUsuario(String cedula) {
        List<HistorialVuelo> lista = new ArrayList<>();

        File file = new File(ARCHIVO);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                HistorialVuelo hv = HistorialVuelo.fromLine(linea);
                if (hv.getCedulaUsuario().equals(cedula)) {
                    lista.add(hv);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer historial: " + e.getMessage());
        }
        return lista;
    }
}
