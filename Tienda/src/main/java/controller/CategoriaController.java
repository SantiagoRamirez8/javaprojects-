package controller;

import com.dao.CategoriaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Categoria;
import views.CategoriaView;

public class CategoriaController {

    private final CategoriaView view;
    private final CategoriaDAO dao;

    public CategoriaController(CategoriaView view, CategoriaDAO dao) {
        this.view = view;
        this.dao = dao;

        // Asociar eventos a los botones
        this.view.btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCategoria();
            }
        });

        this.view.btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCategorias();
            }
        });

        this.view.btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCategoria();
            }
        });

        this.view.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCategoria();
            }
        });

        this.view.btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarVentana();
            }
        });

        // Configurar tabla
        configurarTabla();
    }

    private void crearCategoria() {
        String nombre = view.txtNombre.getText().trim();
        String id = view.txtIdCategoria.getText().trim();

        if (!nombre.isEmpty()) {
            Categoria categoria = new Categoria(id, nombre);
            dao.crearCategoria(categoria);
            mostrarCategorias();
            JOptionPane.showMessageDialog(view, "Categoria creada correctamente");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(view, "Ingrese un nombre de categoría válido");
        }
    }

    private void mostrarCategorias() {
        List<Categoria> categorias = dao.leerCategorias();
        DefaultTableModel model = (DefaultTableModel) view.tablaCategoria.getModel();
        model.setRowCount(0);
        for (Categoria categoria : categorias) {
            model.addRow(new Object[]{categoria.getId(), categoria.getNombre()});
        }
    }

    private void actualizarCategoria() {
        int filaSeleccionada = view.tablaCategoria.getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                int id = (int) view.tablaCategoria.getValueAt(filaSeleccionada, 0);
                String nombre = view.txtNombre.getText().trim();
                Categoria categoria = new Categoria(nombre, nombre);
                dao.actualizarCategoria(categoria);
                mostrarCategorias();
                JOptionPane.showMessageDialog(view, "Categoria actualizada correctamente");
                limpiarCampos();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Error al actualizar la categoría");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Seleccione una categoría de la tabla para actualizar");
        }
    }

    private void eliminarCategoria() {
        int filaSeleccionada = view.tablaCategoria.getSelectedRow();
        if (filaSeleccionada != -1) {
            int id = (int) view.tablaCategoria.getValueAt(filaSeleccionada, 0);
            dao.eliminarCategoria(id);
            mostrarCategorias();
            JOptionPane.showMessageDialog(view, "Categoria eliminada correctamente");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(view, "Seleccione una categoría de la tabla para eliminar");
        }
    }

    private void limpiarCampos() {
        view.txtIdCategoria.setText("");
        view.txtNombre.setText("");
    }

    private void cerrarVentana() {
        view.getTopLevelAncestor().setVisible(false);
    }

    private void configurarTabla() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        view.tablaCategoria.setModel(model);
    }
}
