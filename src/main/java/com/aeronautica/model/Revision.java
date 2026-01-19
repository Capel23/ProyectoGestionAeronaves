package com.aeronautica.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "revisiones")
public class Revision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aeronave_id", nullable = false)
    private Aeronave aeronave;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mecanico_id", nullable = false)
    private Mecanico mecanico;

    @Column(name = "fecha_revision")
    private LocalDateTime fechaRevision;

    @Column(name = "tipo_revision")
    private String tipoRevision;

    @Column(name = "horas_acumuladas")
    private double horasAcumuladas;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "firmado_jefe")
    private boolean firmadoJefe;

    @ManyToMany
    @JoinTable(
        name = "revision_piezas",
        joinColumns = @JoinColumn(name = "revision_id"),
        inverseJoinColumns = @JoinColumn(name = "pieza_id")
    )
    private List<Pieza> piezasReemplazadas;

    public Revision() {}

    public Revision(Aeronave aeronave, Mecanico mecanico, String tipoRevision, double horasAcumuladas, String observaciones) {
        this.aeronave = aeronave;
        this.mecanico = mecanico;
        this.fechaRevision = LocalDateTime.now();
        this.tipoRevision = tipoRevision;
        this.horasAcumuladas = horasAcumuladas;
        this.observaciones = observaciones;
        this.firmadoJefe = false;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Aeronave getAeronave() { return aeronave; }
    public void setAeronave(Aeronave aeronave) { this.aeronave = aeronave; }

    public Mecanico getMecanico() { return mecanico; }
    public void setMecanico(Mecanico mecanico) { this.mecanico = mecanico; }

    public LocalDateTime getFechaRevision() { return fechaRevision; }
    public void setFechaRevision(LocalDateTime fechaRevision) { this.fechaRevision = fechaRevision; }

    public String getTipoRevision() { return tipoRevision; }
    public void setTipoRevision(String tipoRevision) { this.tipoRevision = tipoRevision; }

    public double getHorasAcumuladas() { return horasAcumuladas; }
    public void setHorasAcumuladas(double horasAcumuladas) { this.horasAcumuladas = horasAcumuladas; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public boolean isFirmadoJefe() { return firmadoJefe; }
    public void setFirmadoJefe(boolean firmadoJefe) { this.firmadoJefe = firmadoJefe; }

    public List<Pieza> getPiezasReemplazadas() { return piezasReemplazadas; }
    public void setPiezasReemplazadas(List<Pieza> piezasReemplazadas) { this.piezasReemplazadas = piezasReemplazadas; }

    @Override
    public String toString() {
        return "Revision{" +
                "id=" + id +
                ", aeronave=" + aeronave.getMatricula() +
                ", mecanico=" + mecanico.getNombre() +
                ", fecha=" + fechaRevision +
                ", tipo='" + tipoRevision + '\'' +
                ", horas=" + horasAcumuladas +
                ", firmado=" + firmadoJefe +
                '}';
    }
}