package com.aeronautica.controller;

import com.aeronautica.model.Aeronave;
import com.aeronautica.model.Rol;
import com.aeronautica.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    @FXML
    private TableView<Aeronave> tableAeronaves;

    @FXML
    private TableColumn<Aeronave, String> colMatricula;

    @FXML
    private TableColumn<Aeronave, String> colModelo;

    @FXML
    private TableColumn<Aeronave, String> colEstado;

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        aplicarPermisos();
    }

    @FXML
    public void initialize() {
        colMatricula.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getMatricula())
        );
        colModelo.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getModelo())
        );
        colEstado.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getEstado())
        );
    }

    private void aplicarPermisos() {
        if (usuario.getRol() == Rol.PILOTO) {
            tableAeronaves.setEditable(false);
        }
    }

    @FXML
    private void refrescarTabla() {
        ObservableList<Aeronave> lista = FXCollections.observableArrayList(
                new Aeronave("EC-ABC", "Airbus A320", "Operativa"),
                new Aeronave("EC-XYZ", "Boeing 737", "Mantenimiento")
        );
        tableAeronaves.setItems(lista);
    }
}
