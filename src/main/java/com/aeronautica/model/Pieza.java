package com.aeronautica.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pieza {

    private StringProperty codigo;
    private StringProperty nombre;
    private int cantidad;

    public Pieza() {
        this.codigo = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
    }

    public Pieza(String codigo, String nombre, int cantidad) {
        this.codigo = new SimpleStringProperty(codigo);
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidad = cantidad;
    }

    // Getters
    public String getCodigo() { return codigo.get(); }
    public String getNombre() { return nombre.get(); }
    public int getCantidad() { return cantidad; }

    // Setters
    public void setCodigo(String codigo) { this.codigo.set(codigo); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    // Propiedades
    public StringProperty codigoProperty() { return codigo; }
    public StringProperty nombreProperty() { return nombre; }
}
