/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.orbitix.controlador;

import com.mycompany.orbitix.datos.RepositorioArchivos;
import com.mycompany.orbitix.modelo.Usuario;
import com.mycompany.orbitix.modelo.Vuelo;
import com.mycompany.orbitix.vista.VistaMapaAsientos; 
import com.mycompany.orbitix.vista.VistaPrincipal;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VistaPrincipalControlador {

    private VistaPrincipal vista;
    private Usuario usuario;
    private RepositorioArchivos repo;
    private List<Vuelo> vuelos;

    public VistaPrincipalControlador(VistaPrincipal vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.repo = new RepositorioArchivos();
        this.vuelos = repo.cargarVuelos();
        llenarCombos();
        eventos();
    }
    private void llenarCombos() {
        vista.getCbselorigen().removeAllItems();
        vista.getCbseldestino().removeAllItems();

        Set<String> origenes = new HashSet<>();
        Set<String> destinos = new HashSet<>();
        for (Vuelo v : vuelos) {
            if (v.getRuta() != null) {
                origenes.add(v.getRuta().getOrigen());
                destinos.add(v.getRuta().getDestino());
            }
        }

        for (String o : origenes) vista.getCbselorigen().addItem(o);
        for (String d : destinos) vista.getCbseldestino().addItem(d);
    }

    private void eventos() {
        vista.getBtnBuscarVuelos().addActionListener(e -> buscarVuelos());
        vista.getBtnSelecComprar().addActionListener(e -> seleccionarVuelo());
        vista.getBtnSalir().addActionListener(e -> salir());
    }

    private void buscarVuelos() {
        if (vista.getCbselorigen().getSelectedItem() == null || 
            vista.getCbseldestino().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(vista, "No hay rutas disponibles para buscar.");
            return;
        }

        String origen = vista.getCbselorigen().getSelectedItem().toString();
        String destino = vista.getCbseldestino().getSelectedItem().toString();

        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaVuelos().getModel();
        modelo.setRowCount(0); 

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        boolean encontrado = false;

        for (Vuelo v : vuelos) {
            if (v.getRuta().getOrigen().equalsIgnoreCase(origen) && 
                v.getRuta().getDestino().equalsIgnoreCase(destino)) {

                modelo.addRow(new Object[]{
                        v.getCodigo(),
                        sdf.format(v.getFecha()),
                        v.getRuta().getOrigen() + " -> " + v.getRuta().getDestino(),
                        "$" + String.format("%.2f", v.getPrecio()),
                        v.getRuta().getDuracionFormateada(),
                        v.getAsientosDisponibles()
                });
                encontrado = true;
            }
        }
        
        if (!encontrado) {
            JOptionPane.showMessageDialog(vista, "No se encontraron vuelos para esa ruta.");
        }
    }

    private void seleccionarVuelo() {
        int fila = vista.getTablaVuelos().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un vuelo primero.");
            return;
        }

        String codigo = vista.getTablaVuelos().getValueAt(fila, 0).toString();

        Vuelo seleccionado = vuelos.stream()
                .filter(v -> v.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);

        if (seleccionado != null) {
             JOptionPane.showMessageDialog(vista, "Vuelo seleccionado: " + seleccionado.getCodigo() + "\nIr a Mapa de Asientos...");
             
             VistaMapaAsientos mapa = new VistaMapaAsientos(vista, seleccionado, usuario); 
             mapa.setVisible(true);
             vista.dispose();
        }
    }

    private void salir() {
        int confirm = JOptionPane.showConfirmDialog(vista, "¿Está seguro que desea salir?", "Orbitix", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}