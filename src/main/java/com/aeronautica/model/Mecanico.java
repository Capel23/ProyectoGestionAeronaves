package com.aeronautica.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Mecanico {

    private StringProperty nombre;
    private StringProperty especialidad;
    private int horasTrabajadas;

    public Mecanico() {
        this.nombre = new SimpleStringProperty();
        this.especialidad = new SimpleStringProperty();
    }

    public Mecanico(String nombre, String especialidad, int horasTrabajadas) {
        this.nombre = new SimpleStringProperty(nombre);
        this.especialidad = new SimpleStringProperty(especialidad);
        this.horasTrabajadas = horasTrabajadas;
    }

    // Getters
    public String getNombre() { return nombre.get(); }
    public String getEspecialidad() { return especialidad.get(); }
    public int getHorasTrabajadas() { return horasTrabajadas; }

    // Setters
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public void setEspecialidad(String especialidad) { this.especialidad.set(especialidad); }
    public void setHorasTrabajadas(int horasTrabajadas) { this.horasTrabajadas = horasTrabajadas; }

    // Propiedades (para JavaFX)
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty especialidadProperty() { return especialidad; }
}
