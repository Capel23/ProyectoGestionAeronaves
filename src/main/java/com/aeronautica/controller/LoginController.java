package com.aeronautica.controller;

import com.aeronautica.model.Usuario;
import com.aeronautica.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

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
                login(null);
            }
        });
    }

    @FXML
    private void login(ActionEvent event) {
        try {
            String user = txtUser.getText();
            String pass = txtPass.getText();

            Usuario usuario = authService.login(user, pass);

            if (usuario != null) {
                cargarPantallaPrincipal(usuario, event);
            } else {
                lblError.setText("Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            lblError.setText("Error de conexión con la base de datos");
            e.printStackTrace();
        }
    }

    private void cargarPantallaPrincipal(Usuario usuario, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/main.fxml")
        );
        Scene scene = new Scene(loader.load());

        MainController controller = loader.getController();
        controller.setUsuario(usuario);

        Stage stage = (Stage) ((Node) txtUser).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sistema - " + usuario.getRol());
        stage.show();
    }
}
