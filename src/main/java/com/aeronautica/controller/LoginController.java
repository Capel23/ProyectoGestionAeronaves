package com.aeronautica.controller;

import java.io.IOException;

import com.aeronautica.model.Usuario;
import com.aeronautica.service.AuthService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPass;

    @FXML
    private Label lblError;

    private final AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        txtPass.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                login();
            }
        });
    }

    @FXML
    private void login() {
        try {
            String user = txtUser.getText();
            String pass = txtPass.getText();
            
            System.out.println("Intentando login con usuario: " + user);

            Usuario usuario = authService.login(user, pass);

            if (usuario != null) {
                System.out.println("Login exitoso! Usuario: " + usuario.getUsername() + ", Rol: " + usuario.getRol());
                try {
                    cargarPantallaPrincipal(usuario);
                } catch (IOException e) {
                    lblError.setText("Error al cargar la pantalla principal");
                    System.err.println("Error cargando pantalla principal: " + e.getMessage());
                }
            } else {
                System.out.println("Login fallido: usuario o contraseña incorrectos");
                lblError.setText("Usuario o contraseña incorrectos");
            }
        } catch (RuntimeException e) {
            lblError.setText("Error de conexión con la base de datos");
            System.err.println("Error en login: " + e.getMessage());
        }
    }

    private void cargarPantallaPrincipal(Usuario usuario) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());

        MainController controller = loader.getController();
        controller.setUsuario(usuario);

        Stage stage = (Stage) txtUser.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistema de Gestión - " + usuario.getRol());
        stage.setMaximized(true);
        stage.show();
    }
}
