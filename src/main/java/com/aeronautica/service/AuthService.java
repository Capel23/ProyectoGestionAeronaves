package com.aeronautica.service;

import com.aeronautica.dao.UsuarioDAO;
import com.aeronautica.model.Usuario;

public class AuthService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario login(String username, String password) {
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }
}
