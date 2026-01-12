package com.aeronautica.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

public class Revision {

    private StringProperty idRevision;
    private StringProperty matriculaAeronave;
    private LocalDate fecha;
    private StringProperty tipoRevision;
    private StringProperty observaciones;

    public Revision() {
        this.idRevision = new SimpleStringProperty();
        this.matriculaAeronave = new SimpleStringProperty();
        this.tipoRevision = new SimpleStringProperty();
        this.observaciones = new SimpleStringProperty();
    }

    public Revision(String idRevision, String matriculaAeronave, LocalDate fecha, String tipoRevision, String observaciones) {
        this.idRevision = new SimpleStringProperty(idRevision);
        this.matriculaAeronave = new SimpleStringProperty(matriculaAeronave);
        this.fecha = fecha;
        this.tipoRevision = new SimpleStringProperty(tipoRevision);
        this.observaciones = new SimpleStringProperty(observaciones);
    }

    // Getters
    public String getIdRevision() { return idRevision.get(); }
    public String getMatriculaAeronave() { return matriculaAeronave.get(); }
    public LocalDate getFecha() { return fecha; }
    public String getTipoRevision() { return tipoRevision.get(); }
    public String getObservaciones() { return observaciones.get(); }

    // Setters
    public void setIdRevision(String idRevision) { this.idRevision.set(idRevision); }
    public void setMatriculaAeronave(String matriculaAeronave) { this.matriculaAeronave.set(matriculaAeronave); }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setTipoRevision(String tipoRevision) { this.tipoRevision.set(tipoRevision); }
    public void setObservaciones(String observaciones) { this.observaciones.set(observaciones); }

    // Propiedades
    public StringProperty idRevisionProperty() { return idRevision; }
    public StringProperty matriculaAeronaveProperty() { return matriculaAeronave; }
    public StringProperty tipoRevisionProperty() { return tipoRevision; }
    public StringProperty observacionesProperty() { return observaciones; }
}
