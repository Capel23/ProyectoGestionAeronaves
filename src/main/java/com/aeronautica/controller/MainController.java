package com.aeronautica.controller;

import com.aeronautica.model.Aeronave;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML
    private TableView<Aeronave> tableAeronaves;

    @FXML
    private TableColumn<Aeronave, String> colMatricula;

    @FXML
    private TableColumn<Aeronave, String> colModelo;

    @FXML
    private TableColumn<Aeronave, String> colEstado;

    private ObservableList<Aeronave> aeronavesList;

    @FXML
    public void initialize() {
        // Configurar columnas
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Cargar datos iniciales
        aeronavesList = FXCollections.observableArrayList(
                new Aeronave("EC-1234", "Boeing 737", "Disponible"),
                new Aeronave("EC-5678", "Airbus A320", "En mantenimiento"),
                new Aeronave("EC-9012", "Cessna 172", "Disponible")
        );

        tableAeronaves.setItems(aeronavesList);
    }

    @FXML
    private void refrescarTabla() {
        // Por ahora solo recarga los mismos datos
        tableAeronaves.setItems(aeronavesList);
    }
}
