package com.aeronautica;

import com.aeronautica.dao.UsuarioDAO;
import com.aeronautica.model.Rol;
import com.aeronautica.model.Usuario;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inicializar usuarios si no existen
        initializeUsers();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Login - Sistema de Gestión de Aeronaves");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Crea usuarios por defecto si no existen en la base de datos
     */
    private void initializeUsers() {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            // Verificar si ya existen usuarios
            Usuario admin = usuarioDAO.buscarPorUsername("admin");
            if (admin == null) {
                System.out.println("Creando usuarios por defecto...");
                usuarioDAO.guardar(new Usuario("admin", "admin123", Rol.ADMIN));
                usuarioDAO.guardar(new Usuario("mecanico", "mec123", Rol.MECANICO));
                usuarioDAO.guardar(new Usuario("piloto", "pilot123", Rol.PILOTO));
                System.out.println("✓ Usuarios creados: admin/admin123, mecanico/mec123, piloto/pilot123");
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar usuarios: " + e.getMessage());
        }
    }
//Clase Principal
    public static void main(String[] args) {
        launch(args);
    }
}
