package com.aeronautica.util;

import com.aeronautica.model.Pieza;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generador de Inventario de Piezas en formato JSON.
 */
public class InventarioPiezasJSONGenerator {

    /**
     * Genera un archivo JSON con el inventario completo de piezas.
     * 
     * @param piezas Lista de piezas a incluir en el inventario
     * @param outputPath Ruta donde guardar el archivo JSON
     * @return true si se generó correctamente, false en caso contrario
     */
    public static boolean generarInventario(List<Pieza> piezas, String outputPath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Crear estructura JSON
            Map<String, Object> inventario = new HashMap<>();
            
            // Metadata
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("fechaGeneracion", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            metadata.put("totalPiezas", piezas.size());
            metadata.put("sistema", "Sistema de Gestión Aeronáutica");
            metadata.put("version", "1.0");
            
            inventario.put("metadata", metadata);
            
            // Estadísticas
            Map<String, Object> estadisticas = new HashMap<>();
            int stockTotal = piezas.stream().mapToInt(Pieza::getStock).sum();
            long piezasAgotadas = piezas.stream().filter(p -> p.getStock() == 0).count();
            long piezasBajoStock = piezas.stream().filter(p -> p.getStock() > 0 && p.getStock() < 10).count();
            
            estadisticas.put("stockTotal", stockTotal);
            estadisticas.put("piezasAgotadas", piezasAgotadas);
            estadisticas.put("piezasBajoStock", piezasBajoStock);
            
            inventario.put("estadisticas", estadisticas);
            
            // Lista de piezas
            inventario.put("piezas", piezas);
            
            // Alertas
            Map<String, Object> alertas = new HashMap<>();
            alertas.put("requierenReabastecimiento", 
                piezas.stream()
                    .filter(p -> p.getStock() < 10)
                    .map(p -> Map.of(
                        "codigo", p.getCodigo(),
                        "descripcion", p.getDescripcion(),
                        "stockActual", p.getStock()
                    ))
                    .toList()
            );
            
            inventario.put("alertas", alertas);

            // Escribir el archivo JSON
            mapper.writeValue(new File(outputPath), inventario);

            System.out.println("✓ Inventario JSON generado: " + outputPath);
            return true;

        } catch (IOException e) {
            System.err.println("✗ Error al generar inventario JSON: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Genera un JSON simplificado con solo las piezas (sin metadata).
     */
    public static boolean generarInventarioSimple(List<Pieza> piezas, String outputPath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            
            mapper.writeValue(new File(outputPath), piezas);

            System.out.println("✓ Inventario simple JSON generado: " + outputPath);
            return true;

        } catch (IOException e) {
            System.err.println("✗ Error al generar inventario simple JSON: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Genera el nombre de archivo estándar para el inventario.
     */
    public static String generarNombreArchivo() {
        return "inventario_piezas_" + 
               LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".json";
    }

    /**
     * Lee un inventario desde un archivo JSON.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> leerInventario(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            System.err.println("✗ Error al leer inventario JSON: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
