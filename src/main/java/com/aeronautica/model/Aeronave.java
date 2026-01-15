package com.aeronautica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "aeronaves")
public class Aeronave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String estado;

    public Aeronave() {}

    public Aeronave(String matricula, String modelo, String estado) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.estado = estado;
    }

    // ===== GETTERS Y SETTERS =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
