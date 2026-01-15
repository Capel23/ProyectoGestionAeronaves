package com.aeronautica.dao;

import com.aeronautica.model.Usuario;
import com.aeronautica.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UsuarioDAO {

    public Usuario buscarPorUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> query = session.createQuery(
                    "FROM Usuario u WHERE u.username = :username AND u.activo = true",
                    Usuario.class
            );
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }
}
