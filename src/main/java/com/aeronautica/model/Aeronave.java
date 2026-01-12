package com.aeronautica.model;

public class Aeronave {

    private String matricula;
    private String modelo;
    private String estado;

    public Aeronave(String matricula, String modelo, String estado) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.estado = estado;
    }

    // Getters y setters
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
