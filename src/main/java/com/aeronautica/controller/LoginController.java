package com.aeronautica.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPass;

    @FXML
    private Label lblError;

    @FXML
    private void initialize() {
        // Permitir presionar Enter en el campo de contraseña
        txtPass.setOnAction(event -> {
            try {
                login(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        String user = txtUser.getText();
        String pass = txtPass.getText();

        // Credenciales de prueba
        if(user.equals("admin") && pass.equals("1234")) {
            // Cargar la ventana principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Sistema de Gestión de Aeronaves");
        } else {
            lblError.setText("Usuario o contraseña incorrectos");
        }
    }
}
