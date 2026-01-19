package com.aeronautica.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "aeronaves")
public class Aeronave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricula", unique = true, nullable = false)
    private String matricula;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "horas_vuelo")
    private double horasVuelo;

    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "aeronave", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Revision> revisiones;

    public Aeronave() {}

    public Aeronave(String matricula, String modelo, double horasVuelo) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.horasVuelo = horasVuelo;
        this.estado = "OPERATIVA";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public double getHorasVuelo() { return horasVuelo; }
    public void setHorasVuelo(double horasVuelo) { this.horasVuelo = horasVuelo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<Revision> getRevisiones() { return revisiones; }
    public void setRevisiones(List<Revision> revisiones) { this.revisiones = revisiones; }

    @Override
    public String toString() {
        return "Aeronave{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", modelo='" + modelo + '\'' +
                ", horasVuelo=" + horasVuelo +
                ", estado='" + estado + '\'' +
                '}';
    }
}