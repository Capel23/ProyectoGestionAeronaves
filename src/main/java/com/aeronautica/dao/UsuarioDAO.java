package com.aeronautica.dao;

import com.aeronautica.model.Usuario;
import com.aeronautica.config.HibernateUtil;
import org.hibernate.Session;

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
            session.saveOrUpdate(usuario);
            session.getTransaction().commit();
        }
    }
}
