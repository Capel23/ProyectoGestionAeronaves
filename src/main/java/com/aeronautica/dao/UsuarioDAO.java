package com.aeronautica.dao;

import org.hibernate.Session;

import com.aeronautica.config.HibernateUtil;
import com.aeronautica.model.Usuario;

public class UsuarioDAO {

    public Usuario buscarPorUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Usuario WHERE username = :username", Usuario.class)
                    .setParameter("username", username)
                    .uniqueResult();
        }
    }

    public void guardar(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(usuario);
            session.getTransaction().commit();
        }
    }
}
