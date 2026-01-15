package com.aeronautica.controller;

import com.aeronautica.model.Aeronave;
import com.aeronautica.model.Usuario;
import com.aeronautica.service.AeronaveService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class MainController {

    @FXML
    private TableView<Aeronave> tableAeronaves;
    @FXML
    private TableColumn<Aeronave, String> colMatricula;
    @FXML
    private TableColumn<Aeronave, String> colModelo;
    @FXML
    private TableColumn<Aeronave, String> colEstado;

    @FXML
    private TextField txtMatricula, txtModelo, txtEstado;

    private final AeronaveService service = new AeronaveService();
    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        aplicarPermisos();
    }

    @FXML
    public void initialize() {
        colMatricula.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMatricula()));
        colModelo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getModelo()));
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEstado()));

        refrescarTabla();

        tableAeronaves.setOnMouseClicked(this::cargarSeleccion);
    }

    private void aplicarPermisos() {
        // Ejemplo: pilotos no pueden editar aeronaves
        boolean editable = usuario.getRol().name().equals("ADMIN") || usuario.getRol().name().equals("MECANICO");
        txtMatricula.setDisable(!editable);
        txtModelo.setDisable(!editable);
        txtEstado.setDisable(!editable);
    }

    private void cargarSeleccion(MouseEvent event) {
        Aeronave sel = tableAeronaves.getSelectionModel().getSelectedItem();
        if (sel != null) {
            txtMatricula.setText(sel.getMatricula());
            txtModelo.setText(sel.getModelo());
            txtEstado.setText(sel.getEstado());
        }
    }

    @FXML
    private void refrescarTabla() {
        ObservableList<Aeronave> lista = FXCollections.observableArrayList(service.listar());
        tableAeronaves.setItems(lista);
    }

    @FXML
    private void guardarAeronave() {
        Aeronave a = new Aeronave(txtMatricula.getText(), txtModelo.getText(), txtEstado.getText());
        service.guardar(a);
        refrescarTabla();
        limpiarCampos();
    }

    @FXML
    private void eliminarAeronave() {
        Aeronave sel = tableAeronaves.getSelectionModel().getSelectedItem();
        if (sel != null) {
            service.eliminar(sel);
            refrescarTabla();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtMatricula.clear();
        txtModelo.clear();
        txtEstado.clear();
    }
}
