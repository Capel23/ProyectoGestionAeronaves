package com.aeronautica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mecanicos")
public class Mecanico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "certificacion", nullable = false)
    private String certificacion;

    public Mecanico() {}

    public Mecanico(String nombre, String certificacion) {
        this.nombre = nombre;
        this.certificacion = certificacion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCertificacion() { return certificacion; }
    public void setCertificacion(String certificacion) { this.certificacion = certificacion; }

    @Override
    public String toString() {
        return "Mecanico{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", certificacion='" + certificacion + '\'' +
                '}';
    }
}