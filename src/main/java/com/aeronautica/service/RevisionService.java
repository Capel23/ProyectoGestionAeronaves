package com.aeronautica.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.aeronautica.dao.RevisionDAO;
import com.aeronautica.model.Aeronave;
import com.aeronautica.model.Revision;

public class RevisionService {

    private final RevisionDAO dao = new RevisionDAO();

    public static final double INTERVALO_REVISION_100H = 100.0;
    public static final double INTERVALO_REVISION_ANUAL = 8760.0;
    public static final double TOLERANCIA_HORAS = 5.0;

    public void guardar(Revision r) {
        dao.save(r);
    }

    public void actualizar(Revision r) {
        dao.update(r);
    }

    public void eliminar(Revision r) {
        dao.delete(r);
    }

    public List<Revision> listar() {
        return dao.findAll();
    }

    public Revision buscarPorId(Long id) {
        return dao.findById(id);
    } 

    /**
     * Verifica si una aeronave requiere revisión basándose en las horas de vuelo.
     * 
     * @param aeronave La aeronave a verificar
     * @param ultimaRevision La última revisión realizada (puede ser null)
     * @return true si requiere revisión, false en caso contrario
     */
    public static boolean requiereRevision(Aeronave aeronave, Revision ultimaRevision) {
        if (ultimaRevision == null) {
            return true;
        }

        double horasDesdeUltimaRevision = aeronave.getHorasVuelo() - ultimaRevision.getHorasAcumuladas();
        
        return horasDesdeUltimaRevision >= INTERVALO_REVISION_100H;
    }

    /**
     * Calcula las horas restantes hasta la próxima revisión.
     * 
     * @param aeronave La aeronave
     * @param ultimaRevision La última revisión
     * @return Horas restantes (puede ser negativo si está vencida)
     */
    public static double horasHastaProximaRevision(Aeronave aeronave, Revision ultimaRevision) {
        if (ultimaRevision == null) {
            return 0.0; // Requiere revisión inmediata
        }

        double horasDesdeUltimaRevision = aeronave.getHorasVuelo() - ultimaRevision.getHorasAcumuladas();
        return INTERVALO_REVISION_100H - horasDesdeUltimaRevision;
    }

    /**
     * Verifica si una aeronave está en el margen de tolerancia para revisión.
     * 
     * @param aeronave La aeronave
     * @param ultimaRevision La última revisión
     * @return true si está dentro del margen de tolerancia
     */
    public static boolean enMargenTolerancia(Aeronave aeronave, Revision ultimaRevision) {
        double horasRestantes = horasHastaProximaRevision(aeronave, ultimaRevision);
        return horasRestantes >= 0 && horasRestantes <= TOLERANCIA_HORAS;
    }

    /**
     * Genera un reporte del estado de revisión de una aeronave.
     */
    public static String generarReporteEstado(Aeronave aeronave, Revision ultimaRevision) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("═══════════════════════════════════════════════\n");
        reporte.append("  REPORTE DE ESTADO - ").append(aeronave.getMatricula()).append("\n");
        reporte.append("═══════════════════════════════════════════════\n\n");
        
        reporte.append("Modelo: ").append(aeronave.getModelo()).append("\n");
        reporte.append("Horas de vuelo totales: ").append(aeronave.getHorasVuelo()).append(" h\n\n");

        if (ultimaRevision == null) {
            reporte.append("⚠️  SIN REVISIONES REGISTRADAS\n");
            reporte.append("Estado: REQUIERE REVISIÓN INMEDIATA\n");
        } else {
            reporte.append("Última revisión:\n");
            reporte.append("  - Fecha: ").append(ultimaRevision.getFechaRevision()).append("\n");
            reporte.append("  - Tipo: ").append(ultimaRevision.getTipoRevision()).append("\n");
            reporte.append("  - Horas acumuladas: ").append(ultimaRevision.getHorasAcumuladas()).append(" h\n");
            reporte.append("  - Mecánico: ").append(ultimaRevision.getMecanico().getNombre()).append("\n\n");

            double horasDesdeRevision = aeronave.getHorasVuelo() - ultimaRevision.getHorasAcumuladas();
            double horasRestantes = horasHastaProximaRevision(aeronave, ultimaRevision);

            reporte.append("Horas desde última revisión: ").append(horasDesdeRevision).append(" h\n");
            
            if (requiereRevision(aeronave, ultimaRevision)) {
                reporte.append("\n⛔ ESTADO: REVISIÓN VENCIDA\n");
                reporte.append("❗ Horas excedidas: ").append(Math.abs(horasRestantes)).append(" h\n");
                reporte.append("⚠️  LA AERONAVE NO DEBE VOLAR HASTA SER REVISADA\n");
            } else if (enMargenTolerancia(aeronave, ultimaRevision)) {
                reporte.append("\n⚠️  ESTADO: PRÓXIMA A REVISIÓN\n");
                reporte.append("Horas restantes: ").append(horasRestantes).append(" h\n");
                reporte.append("👉 PROGRAMAR REVISIÓN PRONTO\n");
            } else {
                reporte.append("\n✅ ESTADO: APTA PARA VOLAR\n");
                reporte.append("Horas hasta próxima revisión: ").append(horasRestantes).append(" h\n");
            }
        }

        reporte.append("\n═══════════════════════════════════════════════\n");
        return reporte.toString();
    }

    /**
     * Determina el tipo de revisión requerida basándose en las horas de vuelo.
     */
    public static String determinarTipoRevision(double horasVuelo) {
        if (horasVuelo % 500 < INTERVALO_REVISION_100H) {
            return "500h - Mayor";
        } else if (horasVuelo % 300 < INTERVALO_REVISION_100H) {
            return "300h - Intermedia";
        } else if (horasVuelo % 100 < INTERVALO_REVISION_100H) {
            return "100h - Estándar";
        } else {
            return "100h - Estándar";
        }
    }

    /**
     * Valida si una revisión cumple con los requisitos mínimos.
     */
    public static List<String> validarRevision(Revision revision) {
        List<String> errores = new ArrayList<>();

        if (revision.getAeronave() == null) {
            errores.add("La revisión debe estar asociada a una aeronave");
        }

        if (revision.getMecanico() == null) {
            errores.add("La revisión debe tener un mecánico asignado");
        }

        if (revision.getTipoRevision() == null || revision.getTipoRevision().isEmpty()) {
            errores.add("Debe especificar el tipo de revisión");
        }

        if (revision.getHorasAcumuladas() < 0) {
            errores.add("Las horas acumuladas no pueden ser negativas");
        }

        if (revision.getAeronave() != null && 
            revision.getHorasAcumuladas() > revision.getAeronave().getHorasVuelo()) {
            errores.add("Las horas de revisión no pueden exceder las horas totales de la aeronave");
        }

        if (!revision.isFirmadoJefe()) {
            errores.add("La revisión debe estar firmada por el jefe de mantenimiento");
        }

        return errores;
    }

    /**
     * Calcula días aproximados desde la última revisión.
     */
    public static long diasDesdeUltimaRevision(Revision ultimaRevision) {
        if (ultimaRevision == null || ultimaRevision.getFechaRevision() == null) {
            return -1;
        }
        return ChronoUnit.DAYS.between(ultimaRevision.getFechaRevision(), LocalDateTime.now());
    }

    /**
     * Verifica si una revisión está caducada por tiempo (más de 1 año).
     */
    public static boolean revisionCaducadaPorTiempo(Revision ultimaRevision) {
        return diasDesdeUltimaRevision(ultimaRevision) > 365;
    }
}
