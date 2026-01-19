package com.aeronautica.service;

import java.util.List;

import com.aeronautica.dao.AeronaveDAO;
import com.aeronautica.model.Aeronave;

public class AeronaveService {

    private final AeronaveDAO dao = new AeronaveDAO();

    public void guardar(Aeronave a) {
        dao.guardar(a);
    }

    public void eliminar(Aeronave a) {
        dao.eliminar(a);
    }

    public List<Aeronave> listar() {
        return dao.listarTodos();
    }
}
