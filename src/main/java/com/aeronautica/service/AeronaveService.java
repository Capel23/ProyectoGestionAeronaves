package com.aeronautica.service;

import com.aeronautica.dao.AeronaveDAO;
import com.aeronautica.model.Aeronave;

import java.util.List;

public class AeronaveService {

    private final AeronaveDAO dao = new AeronaveDAO();

    public void guardar(Aeronave a) {
        dao.guardar(a);
    }

    public void eliminar(Aeronave a) {
        dao.eliminar(a);
    }

    public List<Aeronave> listar() {
        return dao.listarTodas();
    }
}
