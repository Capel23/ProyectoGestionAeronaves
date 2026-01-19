package com.aeronautica;

import com.aeronautica.config.HibernateUtil;
import com.aeronautica.dao.*;
import com.aeronautica.model.*;
import com.aeronautica.service.RevisionService;
import com.aeronautica.util.CertificadoAeronavegabilidadXMLGenerator;
import com.aeronautica.util.InventarioPiezasJSONGenerator;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

/**
 * Aplicación de demostración que muestra todas las funcionalidades del sistema.
 * 
 * ¡LISTO PARA COPIAR, PEGAR Y EJECUTAR!
 * 
 * Requisitos:
 * 1. Tener MySQL instalado y ejecutándose (o usar H2 modificando HibernateUtil)
 * 2. Ejecutar el script aeronautica_db.sql para crear la base de datos
 * 3. Ejecutar: mvn clean compile exec:java -Dexec.mainClass="com.aeronautica.DemoApp"
 */
public class DemoApp {

    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN DE MANTENIMIENTO AERONÁUTICO           ║");
        System.out.println("║   Demo Completa - Todas las Funcionalidades                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();

        try {
            // Inicializar DAOs
            AeronaveDAO aeronaveDAO = new AeronaveDAO();
            MecanicoDAO mecanicoDAO = new MecanicoDAO();
            PiezaDAO piezaDAO = new PiezaDAO();
            RevisionDAO revisionDAO = new RevisionDAO();

            // ═══════════════════════════════════════════════════════════
            // 1. CREAR MECÁNICOS
            // ═══════════════════════════════════════════════════════════
            System.out.println("═══ 1. CREANDO MECÁNICOS ═══");
            Mecanico mecanico1 = new Mecanico("Carlos Pérez", "A&P - Airframe & Powerplant");
            Mecanico mecanico2 = new Mecanico("Ana García", "IA - Inspection Authorization");
            Mecanico mecanico3 = new Mecanico("Luis Martínez", "EASA Part-66 B1.1");
            
            mecanicoDAO.guardar(mecanico1);
            mecanicoDAO.guardar(mecanico2);
            mecanicoDAO.guardar(mecanico3);
            System.out.println("✓ Mecánicos creados exitosamente\n");

            // ═══════════════════════════════════════════════════════════
            // 2. CREAR PIEZAS
            // ═══════════════════════════════════════════════════════════
            System.out.println("═══ 2. CREANDO INVENTARIO DE PIEZAS ═══");
            Pieza pieza1 = new Pieza("FLT-001", "Filtro de aceite motor", 25);
            Pieza pieza2 = new Pieza("SPK-042", "Bujías de encendido (set 8)", 15);
            Pieza pieza3 = new Pieza("BRK-101", "Pastillas de freno", 8);
            Pieza pieza4 = new Pieza("HYD-205", "Fluido hidráulico (5L)", 30);
            Pieza pieza5 = new Pieza("TIR-300", "Neumático principal", 4);
            Pieza pieza6 = new Pieza("BAT-450", "Batería 24V", 2); // Stock bajo
            
            piezaDAO.guardar(pieza1);
            piezaDAO.guardar(pieza2);
            piezaDAO.guardar(pieza3);
            piezaDAO.guardar(pieza4);
            piezaDAO.guardar(pieza5);
            piezaDAO.guardar(pieza6);
            System.out.println("✓ Piezas agregadas al inventario\n");

            // ═══════════════════════════════════════════════════════════
            // 3. CREAR AERONAVES
            // ═══════════════════════════════════════════════════════════
            System.out.println("═══ 3. REGISTRANDO AERONAVES ═══");
            Aeronave aeronave1 = new Aeronave("EC-ABC", "Cessna 172 Skyhawk", 450.5);
            Aeronave aeronave2 = new Aeronave("EC-XYZ", "Piper PA-28 Cherokee", 1250.0);
            Aeronave aeronave3 = new Aeronave("EC-DEF", "Beechcraft Baron 58", 95.0); // Necesita revisión
            
            aeronaveDAO.guardar(aeronave1);
            aeronaveDAO.guardar(aeronave2);
            aeronaveDAO.guardar(aeronave3);
            System.out.println("✓ Aeronaves registradas\n");

            // ═══════════════════════════════════════════════════════════
            // 4. CREAR REVISIONES
            // ═══════════════════════════════════════════════════════════
            System.out.println("═══ 4. REGISTRANDO REVISIONES ═══");
            
            // Revisión para aeronave1 (horas actuales: 450.5, última revisión: 400)
            Revision revision1 = new Revision(
                aeronave1, 
                mecanico1, 
                "100h - Estándar",
                400.0,
                "Revisión de 100h completada. Cambio de aceite y filtros. Todo OK."
            );
            revision1.setFirmadoJefe(true);
            revision1.setPiezasReemplazadas(Arrays.asList(pieza1, pieza4));
            revisionDAO.guardar(revision1);
            
            // Revisión para aeronave2 (horas actuales: 1250, última revisión: 1200)
            Revision revision2 = new Revision(
                aeronave2, 
                mecanico2, 
                "100h - Estándar",
                1200.0,
                "Revisión completada. Reemplazo de bujías y ajuste de frenos."
            );
            revision2.setFirmadoJefe(true);
            revision2.setPiezasReemplazadas(Arrays.asList(pieza2, pieza3));
            revisionDAO.guardar(revision2);
            
            System.out.println("✓ Revisiones registradas\n");

            // ═══════════════════════════════════════════════════════════
            // 5. VALIDACIÓN DE REVISIONES (100 HORAS)
            // ═══════════════════════════════════════════════════════════
            System.out.println("═══ 5. VALIDACIÓN DE ESTADO DE REVISIONES ═══\n");
            
            List<Aeronave> todasAeronaves = aeronaveDAO.listarTodos();
            for (Aeronave aeronave : todasAeronaves) {
                Revision ultimaRevision = revisionDAO.buscarUltimaRevisionAeronave(aeronave.getId());
                String reporte = RevisionService.generarReporteEstado(aeronave, ultimaRevision);
                System.out.println(reporte);
            }

            // ═══════════════════════════════════════════════════════════
            // 6. GENERAR CERTIFICADO XML
            // ═══════════════════════════════════════════════════════════
            System.out.println("═══ 6. GENERANDO CERTIFICADOS DE AERONAVEGABILIDAD (XML) ═══");
            
            for (Aeronave aeronave : todasAeronaves) {
                Revision ultimaRev = revisionDAO.buscarUltimaRevisionAeronave(aeronave.getId());
                String nombreArchivo = CertificadoAeronavegabilidadXMLGenerator.generarNombreArchivo(aeronave);
                String rutaArchivo = "target/" + nombreArchivo;
                
                boolean generado = CertificadoAeronavegabilidadXMLGenerator.generarCertificado(
                    aeronave, ultimaRev, rutaArchivo
                );
                
                if (generado) {
                    System.out.println("  → " + aeronave.getMatricula() + ": " + rutaArchivo);
                }
            }
            System.out.println();

            // ═══════════════════════════════════════════════════════════
            // 7. GENERAR INVENTARIO JSON
            // ═══════════════════════════════════════════════════════════
            System.out.println("═══ 7. GENERANDO INVENTARIO DE PIEZAS (JSON) ═══");
            
            List<Pieza> todasPiezas = piezaDAO.listarTodos();
            String nombreInventario = InventarioPiezasJSONGenerator.generarNombreArchivo();
            String rutaInventario = "target/" + nombreInventario;
            
            boolean inventarioGenerado = InventarioPiezasJSONGenerator.generarInventario(
                todasPiezas, rutaInventario
            );
            
            if (inventarioGenerado) {
                System.out.println("  → Inventario completo: " + rutaInventario);
            }
            System.out.println();

            // ═══════════════════════════════════════════════════════════
            // 8. CONSULTAS AVANZADAS
            // ═══════════════════════════════════════════════════════════
            System.out.println("═══ 8. CONSULTAS Y ESTADÍSTICAS ═══\n");
            
            System.out.println("📊 ESTADÍSTICAS GENERALES:");
            System.out.println("  • Total aeronaves: " + aeronaveDAO.contar());
            System.out.println("  • Total mecánicos: " + mecanicoDAO.contar());
            System.out.println("  • Total piezas diferentes: " + piezaDAO.contar());
            System.out.println("  • Stock total de piezas: " + piezaDAO.contarStockTotal());
            System.out.println("  • Total revisiones: " + revisionDAO.contar());
            System.out.println();

            System.out.println("⚠️  ALERTAS DE STOCK:");
            List<Pieza> stockBajo = piezaDAO.buscarStockBajo(10);
            if (stockBajo.isEmpty()) {
                System.out.println("  ✓ No hay piezas con stock bajo");
            } else {
                for (Pieza pieza : stockBajo) {
                    System.out.println("  ⚠️  " + pieza.getCodigo() + " - " + 
                                     pieza.getDescripcion() + " (Stock: " + pieza.getStock() + ")");
                }
            }
            System.out.println();

            System.out.println("🔧 MECÁNICOS Y SUS CERTIFICACIONES:");
            List<String> certificaciones = mecanicoDAO.listarCertificaciones();
            for (String cert : certificaciones) {
                long cantidad = mecanicoDAO.contarPorCertificacion(cert);
                System.out.println("  • " + cert + ": " + cantidad + " mecánico(s)");
            }
            System.out.println();

            System.out.println("📝 REVISIONES PENDIENTES DE FIRMA:");
            List<Revision> pendientes = revisionDAO.buscarPendientesFirma();
            if (pendientes.isEmpty()) {
                System.out.println("  ✓ No hay revisiones pendientes de firma");
            } else {
                for (Revision rev : pendientes) {
                    System.out.println("  • " + rev.getAeronave().getMatricula() + 
                                     " - " + rev.getTipoRevision());
                }
            }
            System.out.println();

            // ═══════════════════════════════════════════════════════════
            // 9. EJEMPLO DE ACTUALIZACIÓN
            // ═══════════════════════════════════════════════════════════
            System.out.println("═══ 9. SIMULANDO ACTUALIZACIÓN DE HORAS DE VUELO ═══");
            
            // Aeronave 3 vuela 10 horas más
            aeronave3.setHorasVuelo(aeronave3.getHorasVuelo() + 10.0);
            aeronaveDAO.actualizar(aeronave3);
            System.out.println("✓ " + aeronave3.getMatricula() + " ahora tiene " + 
                             aeronave3.getHorasVuelo() + " horas");
            
            // Verificar si necesita revisión
            Revision ultRev3 = revisionDAO.buscarUltimaRevisionAeronave(aeronave3.getId());
            if (RevisionService.requiereRevision(aeronave3, ultRev3)) {
                System.out.println("⚠️  " + aeronave3.getMatricula() + " REQUIERE REVISIÓN");
            }
            System.out.println();

            // ═══════════════════════════════════════════════════════════
            // 10. RESUMEN FINAL
            // ═══════════════════════════════════════════════════════════
            System.out.println("╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    DEMO COMPLETADA                            ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("✅ Todas las funcionalidades han sido demostradas:");
            System.out.println("   1. ✓ Gestión de Aeronaves, Mecánicos, Piezas y Revisiones");
            System.out.println("   2. ✓ Validación de revisiones cada 100 horas");
            System.out.println("   3. ✓ Generación de Certificados XML");
            System.out.println("   4. ✓ Generación de Inventarios JSON");
            System.out.println("   5. ✓ Consultas y estadísticas avanzadas");
            System.out.println();
            System.out.println("📁 Archivos generados en la carpeta 'target/':");
            System.out.println("   • Certificados XML de aeronavegabilidad");
            System.out.println("   • Inventario JSON de piezas");
            System.out.println();

        } catch (Exception e) {
            System.err.println("❌ Error durante la ejecución de la demo:");
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
            System.out.println("🔌 Conexión a base de datos cerrada");
            System.out.println();
        }
    }
}
