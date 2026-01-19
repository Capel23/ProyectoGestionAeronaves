package com.aeronautica.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aeronautica.model.Aeronave;
import com.aeronautica.model.Usuario;
import com.aeronautica.service.AeronaveService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private TableView<Aeronave> tableAeronaves;
    @FXML
    private TableColumn<Aeronave, Long> colId;
    @FXML
    private TableColumn<Aeronave, String> colMatricula;
    @FXML
    private TableColumn<Aeronave, String> colModelo;
    @FXML
    private TableColumn<Aeronave, Double> colHorasVuelo;
    @FXML
    private TableColumn<Aeronave, String> colEstado;
    @FXML
    private TableColumn<Aeronave, Integer> colRevisiones;

    @FXML
    private Label lblUsuario;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblOperativas;
    @FXML
    private Label lblMantenimiento;
    @FXML
    private Label lblFueraServicio;

    private final AeronaveService service = new AeronaveService();
    private ObservableList<Aeronave> listaCompleta;

    public void setUsuario(Usuario usuario) {
        lblUsuario.setText("👤 " + usuario.getUsername() + " (" + usuario.getRol() + ")");
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colHorasVuelo.setCellValueFactory(new PropertyValueFactory<>("horasVuelo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colRevisiones.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleIntegerProperty(
                data.getValue().getRevisiones() != null ? data.getValue().getRevisiones().size() : 0
            ).asObject()
        );

        colEstado.setCellFactory(column -> new TableCell<Aeronave, String>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estado);
                    switch (estado.toUpperCase()) {
                        case "OPERATIVA" -> setStyle("-fx-background-color: #d4edda; -fx-text-fill: #155724;");
                        case "EN MANTENIMIENTO" -> setStyle("-fx-background-color: #fff3cd; -fx-text-fill: #856404;");
                        case "FUERA DE SERVICIO" -> setStyle("-fx-background-color: #f8d7da; -fx-text-fill: #721c24;");
                        default -> setStyle("");
                    }
                }
            }
        });

        refrescarTabla();
    }

    @FXML
    public void refrescarTabla() {
        listaCompleta = FXCollections.observableArrayList(service.listar());
        tableAeronaves.setItems(listaCompleta);
        actualizarEstadisticas();
    }

    @FXML
    public void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login - Sistema de Gestión de Aeronaves");
        } catch (IOException e) {
            mostrarError("Error al cerrar sesión", e.getMessage());
        }
    }

    @FXML
    public void nuevaAeronave() {
        Dialog<Aeronave> dialog = new Dialog<>();
        dialog.setTitle("Nueva Aeronave");
        dialog.setHeaderText("Registrar nueva aeronave");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtMatricula = new TextField();
        txtMatricula.setPromptText("EC-XXX");
        TextField txtModelo = new TextField();
        txtModelo.setPromptText("Boeing 737-800");
        TextField txtHoras = new TextField();
        txtHoras.setPromptText("0.0");
        ComboBox<String> cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("OPERATIVA", "EN MANTENIMIENTO", "FUERA DE SERVICIO");
        cmbEstado.setValue("OPERATIVA");

        grid.add(new Label("Matrícula:"), 0, 0);
        grid.add(txtMatricula, 1, 0);
        grid.add(new Label("Modelo:"), 0, 1);
        grid.add(txtModelo, 1, 1);
        grid.add(new Label("Horas de Vuelo:"), 0, 2);
        grid.add(txtHoras, 1, 2);
        grid.add(new Label("Estado:"), 0, 3);
        grid.add(cmbEstado, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    Aeronave aeronave = new Aeronave();
                    aeronave.setMatricula(txtMatricula.getText());
                    aeronave.setModelo(txtModelo.getText());
                    aeronave.setHorasVuelo(Double.parseDouble(txtHoras.getText()));
                    aeronave.setEstado(cmbEstado.getValue());
                    return aeronave;
                } catch (NumberFormatException e) {
                    mostrarError("Error", "Las horas de vuelo deben ser un número válido");
                    return null;
                }
            }
            return null;
        });

        Optional<Aeronave> resultado = dialog.showAndWait();
        resultado.ifPresent(aeronave -> {
            service.guardar(aeronave);
            refrescarTabla();
            mostrarInfo("Éxito", "Aeronave registrada correctamente");
        });
    }

    @FXML
    public void editarAeronave() {
        Aeronave seleccionada = tableAeronaves.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAdvertencia("Selección requerida", "Por favor, selecciona una aeronave para editar");
            return;
        }

        Dialog<Aeronave> dialog = new Dialog<>();
        dialog.setTitle("Editar Aeronave");
        dialog.setHeaderText("Modificar datos de la aeronave");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtMatricula = new TextField(seleccionada.getMatricula());
        TextField txtModelo = new TextField(seleccionada.getModelo());
        TextField txtHoras = new TextField(String.valueOf(seleccionada.getHorasVuelo()));
        ComboBox<String> cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("OPERATIVA", "EN MANTENIMIENTO", "FUERA DE SERVICIO");
        cmbEstado.setValue(seleccionada.getEstado());

        grid.add(new Label("Matrícula:"), 0, 0);
        grid.add(txtMatricula, 1, 0);
        grid.add(new Label("Modelo:"), 0, 1);
        grid.add(txtModelo, 1, 1);
        grid.add(new Label("Horas de Vuelo:"), 0, 2);
        grid.add(txtHoras, 1, 2);
        grid.add(new Label("Estado:"), 0, 3);
        grid.add(cmbEstado, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    seleccionada.setMatricula(txtMatricula.getText());
                    seleccionada.setModelo(txtModelo.getText());
                    seleccionada.setHorasVuelo(Double.parseDouble(txtHoras.getText()));
                    seleccionada.setEstado(cmbEstado.getValue());
                    return seleccionada;
                } catch (NumberFormatException e) {
                    mostrarError("Error", "Las horas de vuelo deben ser un número válido");
                    return null;
                }
            }
            return null;
        });

        Optional<Aeronave> resultado = dialog.showAndWait();
        resultado.ifPresent(aeronave -> {
            service.actualizar(aeronave);
            refrescarTabla();
            mostrarInfo("Éxito", "Aeronave actualizada correctamente");
        });
    }

    @FXML
    public void eliminarAeronave() {
        Aeronave seleccionada = tableAeronaves.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAdvertencia("Selección requerida", "Por favor, selecciona una aeronave para eliminar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar esta aeronave?");
        confirmacion.setContentText("Matrícula: " + seleccionada.getMatricula() + "\nModelo: " + seleccionada.getModelo());

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            service.eliminar(seleccionada);
            refrescarTabla();
            mostrarInfo("Éxito", "Aeronave eliminada correctamente");
        }
    }

    @FXML
    public void buscarAeronave() {
        String busqueda = txtBuscar.getText().toLowerCase();
        if (busqueda.isEmpty()) {
            tableAeronaves.setItems(listaCompleta);
        } else {
            List<Aeronave> filtradas = listaCompleta.stream()
                .filter(a -> a.getMatricula().toLowerCase().contains(busqueda) ||
                            a.getModelo().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());
            tableAeronaves.setItems(FXCollections.observableArrayList(filtradas));
        }
        actualizarEstadisticas();
    }

    @FXML
    public void exportarDatos() {
        mostrarInfo("Exportar Datos", "Funcionalidad de exportación en desarrollo");
    }

    private void actualizarEstadisticas() {
        ObservableList<Aeronave> items = tableAeronaves.getItems();
        lblTotal.setText("Total: " + items.size() + " aeronaves");
        
        long operativas = items.stream().filter(a -> "OPERATIVA".equalsIgnoreCase(a.getEstado())).count();
        long mantenimiento = items.stream().filter(a -> "EN MANTENIMIENTO".equalsIgnoreCase(a.getEstado())).count();
        long fueraServicio = items.stream().filter(a -> "FUERA DE SERVICIO".equalsIgnoreCase(a.getEstado())).count();
        
        lblOperativas.setText("Operativas: " + operativas);
        lblMantenimiento.setText("En Mantenimiento: " + mantenimiento);
        lblFueraServicio.setText("Fuera de Servicio: " + fueraServicio);
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
