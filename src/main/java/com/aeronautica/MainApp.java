package com.aeronautica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.aeronautica.dao.AeronaveDAO;
import com.aeronautica.dao.MecanicoDAO;
import com.aeronautica.dao.PiezaDAO;
import com.aeronautica.dao.RevisionDAO;
import com.aeronautica.dao.UsuarioDAO;
import com.aeronautica.model.Aeronave;
import com.aeronautica.model.Mecanico;
import com.aeronautica.model.Pieza;
import com.aeronautica.model.Revision;
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
        // Inicializar usuarios y datos de ejemplo si no existen
        initializeUsers();
        initializeDemoData();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Login - Sistema de Gestión de Aeronaves");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
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
    
    private void initializeDemoData() {
        try {
            AeronaveDAO aeronaveDAO = new AeronaveDAO();
            MecanicoDAO mecanicoDAO = new MecanicoDAO();
            PiezaDAO piezaDAO = new PiezaDAO();
            RevisionDAO revisionDAO = new RevisionDAO();
            
            // Verificar si ya existen los datos de demostración buscando una aeronave específica
            List<Aeronave> existentes = aeronaveDAO.listarTodos();
            boolean datosYaExisten = existentes.stream()
                .anyMatch(a -> a.getMatricula().equals("SW-1977"));
            
            if (!datosYaExisten) {
                System.out.println("=== Creando datos de demostración ===");
                
                // === AERONAVES (Star Wars y Películas) ===
                List<Aeronave> aeronaves = new ArrayList<>();
                aeronaves.add(new Aeronave("SW-1977", "Millennium Falcon (Star Wars)", 12450.5));
                aeronaves.add(new Aeronave("SW-1980", "X-Wing T-65 (The Empire Strikes Back)", 8920.0));
                aeronaves.add(new Aeronave("SW-1983", "TIE Interceptor (Return of the Jedi)", 6750.3));
                aeronaves.add(new Aeronave("SW-1999", "Naboo N-1 Starfighter (The Phantom Menace)", 3200.0));
                aeronaves.add(new Aeronave("SW-2002", "Slave I (Attack of the Clones)", 9870.5));
                aeronaves.add(new Aeronave("TOP-1986", "F-14 Tomcat (Top Gun)", 15600.8));
                aeronaves.add(new Aeronave("INT-2014", "Endurance (Interstellar)", 4500.0));
                aeronaves.add(new Aeronave("MAD-1979", "Interceptor V8 (Mad Max)", 2100.5));
                aeronaves.add(new Aeronave("BTTF-1985", "DeLorean DMC-12 (Back to the Future)", 8800.0));
                aeronaves.add(new Aeronave("MAV-2015", "Ares III MAV (The Martian)", 1200.0));
                
                System.out.println("Guardando aeronaves...");
                for (Aeronave a : aeronaves) {
                    a.setEstado("OPERATIVA");
                    aeronaveDAO.guardar(a);
                }
                System.out.println("✓ " + aeronaves.size() + " aeronaves creadas");
                
                // === MECÁNICOS ===
                List<Mecanico> mecanicos = new ArrayList<>();
                mecanicos.add(new Mecanico("Han Solo", "Certificación Galaxia - Nivel A"));
                mecanicos.add(new Mecanico("Chewbacca", "Ingeniero Wookiee - Maestro"));
                mecanicos.add(new Mecanico("Anakin Skywalker", "Mecánico Jedi - Experto"));
                mecanicos.add(new Mecanico("Tony Stark", "Ingeniero Aeroespacial - Genio"));
                
                System.out.println("Guardando mecánicos...");
                for (Mecanico m : mecanicos) {
                    mecanicoDAO.guardar(m);
                }
                System.out.println("✓ " + mecanicos.size() + " mecánicos creados");
                
                // === PIEZAS ===
                List<Pieza> piezas = new ArrayList<>();
                piezas.add(new Pieza("P-001", "Motor Hiperpropulsor Clase 0.5", 2));
                piezas.add(new Pieza("P-002", "Escudo Deflector Generador MK-II", 5));
                piezas.add(new Pieza("P-003", "Sistema de Navegación Astromecánico", 8));
                piezas.add(new Pieza("P-004", "Cañón Láser Quad-Turbo", 3));
                piezas.add(new Pieza("P-005", "Células de Energía Dilithium", 15));
                piezas.add(new Pieza("P-006", "Computadora de Vuelo R2-D2", 4));
                piezas.add(new Pieza("P-007", "Tren de Aterrizaje Hidráulico", 12));
                piezas.add(new Pieza("P-008", "Sistema de Soporte Vital Clase A", 6));
                piezas.add(new Pieza("P-009", "Módulo de Comunicaciones Intergalácticas", 9));
                piezas.add(new Pieza("P-010", "Blindaje Durasteel Reforzado", 1));
                
                System.out.println("Guardando piezas...");
                for (Pieza p : piezas) {
                    piezaDAO.guardar(p);
                }
                System.out.println("✓ " + piezas.size() + " piezas creadas");
                
                // === REVISIONES ===
                System.out.println("Cargando datos para revisiones...");
                List<Aeronave> listaAeronaves = aeronaveDAO.listarTodos();
                List<Mecanico> listaMecanicos = mecanicoDAO.listarTodos();
                
                System.out.println("Aeronaves disponibles: " + listaAeronaves.size());
                System.out.println("Mecánicos disponibles: " + listaMecanicos.size());
                
                if (!listaAeronaves.isEmpty() && !listaMecanicos.isEmpty()) {
                    System.out.println("Creando revisiones...");
                    
                    // Buscar las aeronaves específicas por matrícula
                    Aeronave millenniumFalcon = listaAeronaves.stream()
                        .filter(a -> a.getMatricula().equals("SW-1977")).findFirst().orElse(listaAeronaves.get(0));
                    Aeronave xWing = listaAeronaves.stream()
                        .filter(a -> a.getMatricula().equals("SW-1980")).findFirst().orElse(listaAeronaves.get(1));
                    Aeronave endurance = listaAeronaves.stream()
                        .filter(a -> a.getMatricula().equals("INT-2014")).findFirst().orElse(listaAeronaves.get(6));
                    
                    // Crear revisiones
                    Revision rev1 = new Revision();
                    rev1.setAeronave(millenniumFalcon);
                    rev1.setMecanico(listaMecanicos.get(0));
                    rev1.setTipoRevision("100 horas");
                    rev1.setHorasAcumuladas(millenniumFalcon.getHorasVuelo());
                    rev1.setFechaRevision(LocalDateTime.now().minusDays(5));
                    rev1.setObservaciones("Revisión completa del Millennium Falcon. Sistema hiperpropulsor verificado.");
                    rev1.setFirmadoJefe(true);
                    revisionDAO.guardar(rev1);
                    
                    Revision rev2 = new Revision();
                    rev2.setAeronave(xWing);
                    rev2.setMecanico(listaMecanicos.get(2));
                    rev2.setTipoRevision("50 horas");
                    rev2.setHorasAcumuladas(xWing.getHorasVuelo());
                    rev2.setFechaRevision(LocalDateTime.now().minusDays(2));
                    rev2.setObservaciones("Inspección del X-Wing. Cañones láser calibrados correctamente.");
                    rev2.setFirmadoJefe(true);
                    revisionDAO.guardar(rev2);
                    
                    Revision rev3 = new Revision();
                    rev3.setAeronave(endurance);
                    rev3.setMecanico(listaMecanicos.get(3));
                    rev3.setTipoRevision("Anual");
                    rev3.setHorasAcumuladas(endurance.getHorasVuelo());
                    rev3.setFechaRevision(LocalDateTime.now().minusDays(10));
                    rev3.setObservaciones("Revisión anual del Endurance. Sistemas de soporte vital óptimos.");
                    rev3.setFirmadoJefe(true);
                    revisionDAO.guardar(rev3);
                    
                    System.out.println("✓ 3 revisiones creadas");
                }
                
                System.out.println("========================================");
                System.out.println("✓ Datos de demostración cargados exitosamente!");
                System.out.println("========================================");
            } else {
                System.out.println("Los datos de demostración ya existen. Omitiendo creación.");
            }
        } catch (Exception e) {
            System.err.println("ERROR al inicializar datos de demostración: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
//Clase Principal
    public static void main(String[] args) {
        launch(args);
    }
}
