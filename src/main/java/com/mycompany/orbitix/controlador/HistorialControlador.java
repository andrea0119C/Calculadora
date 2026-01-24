/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orbitix.controlador;
import com.mycompany.orbitix.datos.RepositorioHistorialVuelo;
import com.mycompany.orbitix.modelo.HistorialVuelo;
import com.mycompany.orbitix.modelo.Usuario;
import com.mycompany.orbitix.modelo.Vuelo;
import java.text.SimpleDateFormat;
import java.util.List;
/**
 *
 * @author paopa
 */

public class HistorialControlador {

    public static void registrarHistorial(
            Usuario usuarioLogueado,
            Vuelo vuelo,
            List<String> asientos,
            double totalFinal
    ) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (String asiento : asientos) {

            String fechaStr = sdf.format(vuelo.getFecha());
            String fechaHora = fechaStr + " " + vuelo.getHora();

            HistorialVuelo hv = new HistorialVuelo(
                usuarioLogueado.getCedula(),
                vuelo.getCodigo(),
                vuelo.getRuta().getOrigen(),
                vuelo.getRuta().getDestino(),
                fechaHora,
                asiento,
                totalFinal
            );

          
            RepositorioHistorialVuelo.guardar(hv);
        }
    }

    public static List<HistorialVuelo> obtenerHistorial(Usuario usuarioLogueado) {
        return RepositorioHistorialVuelo
                .listarPorUsuario(usuarioLogueado.getCedula());
    }
}
