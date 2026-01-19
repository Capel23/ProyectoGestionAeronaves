package com.aeronautica.service;

import java.util.List;

import com.aeronautica.dao.PiezaDAO;
import com.aeronautica.model.Pieza;

public class PiezaService {

    private final PiezaDAO dao = new PiezaDAO();

    public void guardar(Pieza p) {
        dao.save(p);
    }

    public void actualizar(Pieza p) {
        dao.update(p);
    }

    public void eliminar(Pieza p) {
        dao.delete(p);
    }

    public List<Pieza> listar() {
        return dao.findAll();
    }

    public Pieza buscarPorId(Long id) {
        return dao.findById(id);
    }

    public Pieza buscarPorCodigo(String codigo) {
        return dao.buscarPorCodigo(codigo);
    }

    public List<Pieza> buscarStockBajo(int limite) {
        return dao.buscarStockBajo(limite);
    }

    public boolean actualizarStock(Long piezaId, int nuevoStock) {
        return dao.actualizarStock(piezaId, nuevoStock);
    }
}
