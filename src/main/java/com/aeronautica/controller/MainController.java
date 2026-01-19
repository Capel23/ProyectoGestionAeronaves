package com.aeronautica.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aeronautica.model.Aeronave;
import com.aeronautica.model.Mecanico;
import com.aeronautica.model.Pieza;
import com.aeronautica.model.Revision;
import com.aeronautica.model.Rol;
import com.aeronautica.model.Usuario;
import com.aeronautica.service.AeronaveService;
import com.aeronautica.service.MecanicoService;
import com.aeronautica.service.PiezaService;
import com.aeronautica.service.RevisionService;
import com.aeronautica.util.InventarioPiezasJSONGenerator;

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
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainController {

    // Tabs
    @FXML private Tab tabAeronaves;
    @FXML private Tab tabMecanicos;
    @FXML private Tab tabPiezas;
    @FXML private Tab tabRevisiones;

    // Tables
    @FXML private TableView<Aeronave> tableAeronaves;
    @FXML private TableColumn<Aeronave, Long> colId;
    @FXML private TableColumn<Aeronave, String> colMatricula;
    @FXML private TableColumn<Aeronave, String> colModelo;
    @FXML private TableColumn<Aeronave, Double> colHorasVuelo;
    @FXML private TableColumn<Aeronave, String> colEstado;

    @FXML private TableView<Mecanico> tableMecanicos;
    @FXML private TableColumn<Mecanico, Long> colMecId;
    @FXML private TableColumn<Mecanico, String> colMecNombre;
    @FXML private TableColumn<Mecanico, String> colMecCertificacion;

    @FXML private TableView<Pieza> tablePiezas;
    @FXML private TableColumn<Pieza, Long> colPiezaId;
    @FXML private TableColumn<Pieza, String> colPiezaCodigo;
    @FXML private TableColumn<Pieza, String> colPiezaDescripcion;
    @FXML private TableColumn<Pieza, Integer> colPiezaStock;

    @FXML private TableView<Revision> tableRevisiones;
    @FXML private TableColumn<Revision, Long> colRevId;
    @FXML private TableColumn<Revision, String> colRevAeronave;
    @FXML private TableColumn<Revision, String> colRevMecanico;
    @FXML private TableColumn<Revision, String> colRevFecha;
    @FXML private TableColumn<Revision, String> colRevTipo;
    @FXML private TableColumn<Revision, Double> colRevHoras;

    @FXML private Label lblUsuario;
    @FXML private Button btnCerrarSesion;
    @FXML private TextField txtBuscarAeronave;
    @FXML private TextField txtBuscarMecanico;
    @FXML private TextField txtBuscarPieza;
    @FXML private Label lblTotal;
    @FXML private Label lblOperativas;
    @FXML private Label lblMantenimiento;
    @FXML private Label lblFueraServicio;
    @FXML private Label lblTotalMecanicos;
    @FXML private Label lblTotalPiezas;
    @FXML private Label lblStockBajo;
    @FXML private Label lblTotalRevisiones;

    // Botones Aeronaves
    @FXML private Button btnNuevaAeronave;
    @FXML private Button btnEditarAeronave;
    @FXML private Button btnEliminarAeronave;

    // Botones Mecánicos
    @FXML private Button btnNuevoMecanico;
    @FXML private Button btnEditarMecanico;
    @FXML private Button btnEliminarMecanico;

    // Botones Piezas
    @FXML private Button btnNuevaPieza;
    @FXML private Button btnEditarPieza;
    @FXML private Button btnEliminarPieza;
    @FXML private Button btnGenerarJSON;

    // Botones Revisiones
    @FXML private Button btnNuevaRevision;
    @FXML private Button btnEliminarRevision;
    @FXML private Button btnGenerarCertificado;

    private final AeronaveService aeronaveService = new AeronaveService();
    private final MecanicoService mecanicoService = new MecanicoService();
    private final PiezaService piezaService = new PiezaService();
    private final RevisionService revisionService = new RevisionService();
    
    private ObservableList<Aeronave> listaAeronaves;
    private ObservableList<Mecanico> listaMecanicos;
    private ObservableList<Pieza> listaPiezas;
    private ObservableList<Revision> listaRevisiones;
    
    private Usuario usuarioActual;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        lblUsuario.setText("👤 " + usuario.getUsername() + " (" + usuario.getRol() + ")");
        configurarPermisosPorRol(usuario.getRol());
    }

    private void configurarPermisosPorRol(Rol rol) {
        switch (rol) {
            case ADMIN:
                // Admin tiene todos los permisos (todo activado por defecto)
                break;
                
            case MECANICO:
                // Mecánico solo puede ver y editar piezas y revisiones
                // Deshabilitar pestañas de aeronaves y mecánicos
                tabAeronaves.setDisable(true);
                tabMecanicos.setDisable(true);
                
                // Piezas: permitir ver, crear y editar (no eliminar)
                btnEliminarPieza.setDisable(true);
                
                // Revisiones: permitir ver, crear y editar (no eliminar)
                btnEliminarRevision.setDisable(true);
                break;
                
            case PILOTO:
                // Piloto solo puede ver, sin editar nada
                // Deshabilitar todos los botones de acción
                
                // Aeronaves
                btnNuevaAeronave.setDisable(true);
                btnEditarAeronave.setDisable(true);
                btnEliminarAeronave.setDisable(true);
                
                // Mecánicos
                btnNuevoMecanico.setDisable(true);
                btnEditarMecanico.setDisable(true);
                btnEliminarMecanico.setDisable(true);
                
                // Piezas
                btnNuevaPieza.setDisable(true);
                btnEditarPieza.setDisable(true);
                btnEliminarPieza.setDisable(true);
                btnGenerarJSON.setDisable(true);
                
                // Revisiones
                btnNuevaRevision.setDisable(true);
                btnEliminarRevision.setDisable(true);
                btnGenerarCertificado.setDisable(true);
                break;
        }
    }

    @FXML
    public void initialize() {
        inicializarAeronaves();
        inicializarMecanicos();
        inicializarPiezas();
        inicializarRevisiones();
    }

    private void inicializarAeronaves() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colHorasVuelo.setCellValueFactory(new PropertyValueFactory<>("horasVuelo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        colEstado.setCellFactory(column -> new TableCell<>() {
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

    private void inicializarMecanicos() {
        colMecId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMecNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colMecCertificacion.setCellValueFactory(new PropertyValueFactory<>("certificacion"));
        refrescarMecanicos();
    }

    private void inicializarPiezas() {
        colPiezaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPiezaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colPiezaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPiezaStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        colPiezaStock.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer stock, boolean empty) {
                super.updateItem(stock, empty);
                if (empty || stock == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(stock));
                    if (stock == 0) {
                        setStyle("-fx-background-color: #f8d7da; -fx-text-fill: #721c24; -fx-font-weight: bold;");
                    } else if (stock < 10) {
                        setStyle("-fx-background-color: #fff3cd; -fx-text-fill: #856404;");
                    } else {
                        setStyle("-fx-text-fill: #155724;");
                    }
                }
            }
        });

        refrescarPiezas();
    }

    private void inicializarRevisiones() {
        colRevId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRevAeronave.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getAeronave() != null ? data.getValue().getAeronave().getMatricula() : "N/A"
            )
        );
        colRevMecanico.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getMecanico() != null ? data.getValue().getMecanico().getNombre() : "N/A"
            )
        );
        colRevFecha.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getFechaRevision() != null ? 
                    data.getValue().getFechaRevision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "N/A"
            )
        );
        colRevTipo.setCellValueFactory(new PropertyValueFactory<>("tipoRevision"));
        colRevHoras.setCellValueFactory(new PropertyValueFactory<>("horasAcumuladas"));
        refrescarRevisiones();
    }

    @FXML
    public void refrescarTabla() {
        listaAeronaves = FXCollections.observableArrayList(aeronaveService.listar());
        tableAeronaves.setItems(listaAeronaves);
        actualizarEstadisticas();
    }

    @FXML
    public void refrescarMecanicos() {
        listaMecanicos = FXCollections.observableArrayList(mecanicoService.listar());
        tableMecanicos.setItems(listaMecanicos);
        lblTotalMecanicos.setText("Total: " + listaMecanicos.size() + " mecánicos");
    }

    @FXML
    public void refrescarPiezas() {
        listaPiezas = FXCollections.observableArrayList(piezaService.listar());
        tablePiezas.setItems(listaPiezas);
        long stockBajo = listaPiezas.stream().filter(p -> p.getStock() < 10).count();
        lblTotalPiezas.setText("Total: " + listaPiezas.size() + " piezas");
        lblStockBajo.setText("Stock Bajo: " + stockBajo);
    }

    @FXML
    public void refrescarRevisiones() {
        listaRevisiones = FXCollections.observableArrayList(revisionService.listar());
        tableRevisiones.setItems(listaRevisiones);
        lblTotalRevisiones.setText("Total: " + listaRevisiones.size() + " revisiones");
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
            aeronaveService.guardar(aeronave);
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
            aeronaveService.actualizar(aeronave);
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
            aeronaveService.eliminar(seleccionada);
            refrescarTabla();
            mostrarInfo("Éxito", "Aeronave eliminada correctamente");
        }
    }

    @FXML
    public void buscarAeronave() {
        String busqueda = txtBuscarAeronave.getText().toLowerCase();
        if (busqueda.isEmpty()) {
            tableAeronaves.setItems(listaAeronaves);
        } else {
            List<Aeronave> filtradas = listaAeronaves.stream()
                .filter(a -> a.getMatricula().toLowerCase().contains(busqueda) ||
                            a.getModelo().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());
            tableAeronaves.setItems(FXCollections.observableArrayList(filtradas));
        }
        actualizarEstadisticas();
    }

    @FXML
    public void nuevoMecanico() {
        Dialog<Mecanico> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Mecánico");
        dialog.setHeaderText("Registrar nuevo mecánico");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre completo");
        TextField txtCertificacion = new TextField();
        txtCertificacion.setPromptText("A1, B2, C");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Certificación:"), 0, 1);
        grid.add(txtCertificacion, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                return new Mecanico(txtNombre.getText(), txtCertificacion.getText());
            }
            return null;
        });

        Optional<Mecanico> resultado = dialog.showAndWait();
        resultado.ifPresent(mecanico -> {
            mecanicoService.guardar(mecanico);
            refrescarMecanicos();
            mostrarInfo("Éxito", "Mecánico registrado correctamente");
        });
    }

    @FXML
    public void editarMecanico() {
        Mecanico seleccionado = tableMecanicos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAdvertencia("Selección requerida", "Por favor, selecciona un mecánico para editar");
            return;
        }

        Dialog<Mecanico> dialog = new Dialog<>();
        dialog.setTitle("Editar Mecánico");
        dialog.setHeaderText("Modificar datos del mecánico");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtNombre = new TextField(seleccionado.getNombre());
        TextField txtCertificacion = new TextField(seleccionado.getCertificacion());

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Certificación:"), 0, 1);
        grid.add(txtCertificacion, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                seleccionado.setNombre(txtNombre.getText());
                seleccionado.setCertificacion(txtCertificacion.getText());
                return seleccionado;
            }
            return null;
        });

        Optional<Mecanico> resultado = dialog.showAndWait();
        resultado.ifPresent(mecanico -> {
            mecanicoService.actualizar(mecanico);
            refrescarMecanicos();
            mostrarInfo("Éxito", "Mecánico actualizado correctamente");
        });
    }

    @FXML
    public void eliminarMecanico() {
        Mecanico seleccionado = tableMecanicos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAdvertencia("Selección requerida", "Por favor, selecciona un mecánico para eliminar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar este mecánico?");
        confirmacion.setContentText("Nombre: " + seleccionado.getNombre());

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            mecanicoService.eliminar(seleccionado);
            refrescarMecanicos();
            mostrarInfo("Éxito", "Mecánico eliminado correctamente");
        }
    }

    @FXML
    public void buscarMecanico() {
        String busqueda = txtBuscarMecanico.getText().toLowerCase();
        if (busqueda.isEmpty()) {
            tableMecanicos.setItems(listaMecanicos);
        } else {
            List<Mecanico> filtrados = listaMecanicos.stream()
                .filter(m -> m.getNombre().toLowerCase().contains(busqueda) ||
                            m.getCertificacion().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());
            tableMecanicos.setItems(FXCollections.observableArrayList(filtrados));
        }
    }

    @FXML
    public void nuevaPieza() {
        Dialog<Pieza> dialog = new Dialog<>();
        dialog.setTitle("Nueva Pieza");
        dialog.setHeaderText("Registrar nueva pieza");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("P-001");
        TextField txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción");
        TextField txtStock = new TextField();
        txtStock.setPromptText("0");

        grid.add(new Label("Código:"), 0, 0);
        grid.add(txtCodigo, 1, 0);
        grid.add(new Label("Descripción:"), 0, 1);
        grid.add(txtDescripcion, 1, 1);
        grid.add(new Label("Stock:"), 0, 2);
        grid.add(txtStock, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    return new Pieza(txtCodigo.getText(), txtDescripcion.getText(), Integer.parseInt(txtStock.getText()));
                } catch (NumberFormatException e) {
                    mostrarError("Error", "El stock debe ser un número válido");
                    return null;
                }
            }
            return null;
        });

        Optional<Pieza> resultado = dialog.showAndWait();
        resultado.ifPresent(pieza -> {
            piezaService.guardar(pieza);
            refrescarPiezas();
            mostrarInfo("Éxito", "Pieza registrada correctamente");
        });
    }

    @FXML
    public void editarPieza() {
        Pieza seleccionada = tablePiezas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAdvertencia("Selección requerida", "Por favor, selecciona una pieza para editar");
            return;
        }

        Dialog<Pieza> dialog = new Dialog<>();
        dialog.setTitle("Editar Pieza");
        dialog.setHeaderText("Modificar datos de la pieza");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtCodigo = new TextField(seleccionada.getCodigo());
        TextField txtDescripcion = new TextField(seleccionada.getDescripcion());
        TextField txtStock = new TextField(String.valueOf(seleccionada.getStock()));

        grid.add(new Label("Código:"), 0, 0);
        grid.add(txtCodigo, 1, 0);
        grid.add(new Label("Descripción:"), 0, 1);
        grid.add(txtDescripcion, 1, 1);
        grid.add(new Label("Stock:"), 0, 2);
        grid.add(txtStock, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    seleccionada.setCodigo(txtCodigo.getText());
                    seleccionada.setDescripcion(txtDescripcion.getText());
                    seleccionada.setStock(Integer.parseInt(txtStock.getText()));
                    return seleccionada;
                } catch (NumberFormatException e) {
                    mostrarError("Error", "El stock debe ser un número válido");
                    return null;
                }
            }
            return null;
        });

        Optional<Pieza> resultado = dialog.showAndWait();
        resultado.ifPresent(pieza -> {
            piezaService.actualizar(pieza);
            refrescarPiezas();
            mostrarInfo("Éxito", "Pieza actualizada correctamente");
        });
    }

    @FXML
    public void eliminarPieza() {
        Pieza seleccionada = tablePiezas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAdvertencia("Selección requerida", "Por favor, selecciona una pieza para eliminar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar esta pieza?");
        confirmacion.setContentText("Código: " + seleccionada.getCodigo());

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            piezaService.eliminar(seleccionada);
            refrescarPiezas();
            mostrarInfo("Éxito", "Pieza eliminada correctamente");
        }
    }

    @FXML
    public void buscarPieza() {
        String busqueda = txtBuscarPieza.getText().toLowerCase();
        if (busqueda.isEmpty()) {
            tablePiezas.setItems(listaPiezas);
        } else {
            List<Pieza> filtradas = listaPiezas.stream()
                .filter(p -> p.getCodigo().toLowerCase().contains(busqueda) ||
                            p.getDescripcion().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());
            tablePiezas.setItems(FXCollections.observableArrayList(filtradas));
        }
    }

    @FXML
    public void generarInventarioJSON() {
        try {
            List<Pieza> piezas = piezaService.listar();
            InventarioPiezasJSONGenerator.generarInventario(piezas, "inventario_piezas.json");
            mostrarInfo("Éxito", "Inventario JSON generado correctamente en: inventario_piezas.json");
        } catch (Exception e) {
            mostrarError("Error", "No se pudo generar el inventario JSON: " + e.getMessage());
        }
    }

    @FXML
    public void nuevaRevision() {
        Dialog<Revision> dialog = new Dialog<>();
        dialog.setTitle("Nueva Revisión");
        dialog.setHeaderText("Registrar nueva revisión");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Aeronave> cmbAeronave = new ComboBox<>();
        cmbAeronave.setItems(FXCollections.observableArrayList(aeronaveService.listar()));
        cmbAeronave.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Aeronave a) {
                return a != null ? a.getMatricula() + " - " + a.getModelo() : "";
            }
            @Override
            public Aeronave fromString(String string) {
                return null;
            }
        });

        ComboBox<Mecanico> cmbMecanico = new ComboBox<>();
        cmbMecanico.setItems(FXCollections.observableArrayList(mecanicoService.listar()));
        cmbMecanico.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Mecanico m) {
                return m != null ? m.getNombre() + " (" + m.getCertificacion() + ")" : "";
            }
            @Override
            public Mecanico fromString(String string) {
                return null;
            }
        });

        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("50 horas", "100 horas", "Anual", "Pre-vuelo", "Correctiva");
        cmbTipo.setValue("50 horas");

        TextField txtHoras = new TextField();
        txtHoras.setPromptText("0.0");

        TextArea txtObservaciones = new TextArea();
        txtObservaciones.setPrefRowCount(3);
        txtObservaciones.setPromptText("Observaciones...");

        grid.add(new Label("Aeronave:"), 0, 0);
        grid.add(cmbAeronave, 1, 0);
        grid.add(new Label("Mecánico:"), 0, 1);
        grid.add(cmbMecanico, 1, 1);
        grid.add(new Label("Tipo:"), 0, 2);
        grid.add(cmbTipo, 1, 2);
        grid.add(new Label("Horas acum.:"), 0, 3);
        grid.add(txtHoras, 1, 3);
        grid.add(new Label("Observaciones:"), 0, 4);
        grid.add(txtObservaciones, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    Revision rev = new Revision();
                    rev.setAeronave(cmbAeronave.getValue());
                    rev.setMecanico(cmbMecanico.getValue());
                    rev.setTipoRevision(cmbTipo.getValue());
                    rev.setHorasAcumuladas(Double.parseDouble(txtHoras.getText()));
                    rev.setObservaciones(txtObservaciones.getText());
                    rev.setFechaRevision(LocalDateTime.now());
                    return rev;
                } catch (NumberFormatException e) {
                    mostrarError("Error", "Las horas deben ser un número válido");
                    return null;
                }
            }
            return null;
        });

        Optional<Revision> resultado = dialog.showAndWait();
        resultado.ifPresent(revision -> {
            try {
                System.out.println("Guardando revisión...");
                System.out.println("Aeronave: " + (revision.getAeronave() != null ? revision.getAeronave().getMatricula() : "NULL"));
                System.out.println("Mecánico: " + (revision.getMecanico() != null ? revision.getMecanico().getNombre() : "NULL"));
                
                revisionService.guardar(revision);
                System.out.println("Revisión guardada, refrescando tabla...");
                
                // Forzar refresco completo
                refrescarRevisiones();
                
                System.out.println("Tabla refrescada. Total revisiones: " + listaRevisiones.size());
                mostrarInfo("Éxito", "Revisión registrada correctamente");
            } catch (Exception e) {
                System.err.println("Error al guardar revisión: " + e.getMessage());
                mostrarError("Error", "No se pudo guardar la revisión: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void verDetallesRevision() {
        Revision seleccionada = tableRevisiones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAdvertencia("Selección requerida", "Por favor, selecciona una revisión");
            return;
        }

        Alert detalles = new Alert(Alert.AlertType.INFORMATION);
        detalles.setTitle("Detalles de Revisión");
        detalles.setHeaderText("Revisión #" + seleccionada.getId());
        detalles.setContentText(
            "Aeronave: " + (seleccionada.getAeronave() != null ? seleccionada.getAeronave().getMatricula() : "N/A") + "\n" +
            "Mecánico: " + (seleccionada.getMecanico() != null ? seleccionada.getMecanico().getNombre() : "N/A") + "\n" +
            "Tipo: " + seleccionada.getTipoRevision() + "\n" +
            "Fecha: " + (seleccionada.getFechaRevision() != null ? 
                seleccionada.getFechaRevision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "N/A") + "\n" +
            "Horas: " + seleccionada.getHorasAcumuladas() + "\n" +
            "Observaciones: " + seleccionada.getObservaciones()
        );
        detalles.showAndWait();
    }

    @FXML
    public void eliminarRevision() {
        Revision seleccionada = tableRevisiones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAdvertencia("Selección requerida", "Por favor, selecciona una revisión para eliminar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar esta revisión?");
        confirmacion.setContentText("Revisión #" + seleccionada.getId());

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            revisionService.eliminar(seleccionada);
            refrescarRevisiones();
            mostrarInfo("Éxito", "Revisión eliminada correctamente");
        }
    }

    @FXML
    public void generarCertificadoXML() {
        Revision seleccionada = tableRevisiones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAdvertencia("Selección requerida", "Por favor, selecciona una revisión para generar el certificado");
            return;
        }

        mostrarInfo("Certificado XML", "Funcionalidad de certificado XML en desarrollo.\nRevisión #" + seleccionada.getId());
    }

    private void actualizarEstadisticas() {
        ObservableList<Aeronave> items = tableAeronaves.getItems();
        lblTotal.setText("Total: " + items.size());
        
        long operativas = items.stream().filter(a -> "OPERATIVA".equalsIgnoreCase(a.getEstado())).count();
        long mantenimiento = items.stream().filter(a -> "EN MANTENIMIENTO".equalsIgnoreCase(a.getEstado())).count();
        long fueraServicio = items.stream().filter(a -> "FUERA DE SERVICIO".equalsIgnoreCase(a.getEstado())).count();
        
        lblOperativas.setText("Operativas: " + operativas);
        lblMantenimiento.setText("Mantenimiento: " + mantenimiento);
        lblFueraServicio.setText("Fuera Servicio: " + fueraServicio);
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
