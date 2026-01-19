package com.aeronautica.service;

import java.util.List;

import com.aeronautica.dao.MecanicoDAO;
import com.aeronautica.model.Mecanico;

public class MecanicoService {

    private final MecanicoDAO dao = new MecanicoDAO();

    public void guardar(Mecanico m) {
        dao.save(m);
    }

    public void actualizar(Mecanico m) {
        dao.update(m);
    }

    public void eliminar(Mecanico m) {
        dao.delete(m);
    }

    public List<Mecanico> listar() {
        return dao.findAll();
    }

    public Mecanico buscarPorId(Long id) {
        return dao.findById(id);
    }

    public List<Mecanico> buscarPorNombre(String nombre) {
        return dao.buscarPorNombre(nombre);
    }

    public List<String> listarCertificaciones() {
        return dao.listarCertificaciones();
    }
}
