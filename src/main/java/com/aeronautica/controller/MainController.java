package com.aeronautica.controller;

import com.aeronautica.model.Aeronave;
import com.aeronautica.model.Usuario;
import com.aeronautica.service.AeronaveService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {

    @FXML
    private TableView<Aeronave> tableAeronaves;
    @FXML
    private TableColumn<Aeronave, String> colMatricula;
    @FXML
    private TableColumn<Aeronave, String> colModelo;
    @FXML
    private TableColumn<Aeronave, String> colEstado;

    private final AeronaveService service = new AeronaveService();
    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        System.out.println("Usuario establecido: " + usuario.getUsername() + ", Rol: " + usuario.getRol());
        // No es necesario aplicar permisos aquí ya que la tabla es solo de lectura por ahora
    }

    @FXML
    public void initialize() {
        colMatricula.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMatricula()));
        colModelo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getModelo()));
        colEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEstado()));

        refrescarTabla();
    }

    @FXML
    private void refrescarTabla() {
        ObservableList<Aeronave> lista = FXCollections.observableArrayList(service.listar());
        tableAeronaves.setItems(lista);
    }

    @FXML
    private void guardarAeronave() {
        System.out.println("Función guardar aeronave - Por implementar");
    }

    @FXML
    private void eliminarAeronave() {
        Aeronave sel = tableAeronaves.getSelectionModel().getSelectedItem();
        if (sel != null) {
            service.eliminar(sel);
            refrescarTabla();
        }
    }
}
