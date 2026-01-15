package com.aeronautica.service;

import com.aeronautica.dao.UsuarioDAO;
import com.aeronautica.model.Usuario;

public class UsuarioService {

    private final UsuarioDAO dao = new UsuarioDAO();

    public Usuario login(String username, String password) {
        Usuario u = dao.buscarPorUsername(username);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    public void guardarUsuario(Usuario u) {
        dao.guardar(u);
    }
}
