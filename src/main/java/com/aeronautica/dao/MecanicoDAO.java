package com.aeronautica.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.aeronautica.config.HibernateUtil;
import com.aeronautica.model.Mecanico;

public class MecanicoDAO extends GenericDAO<Mecanico> {

    public MecanicoDAO() {
        super(Mecanico.class);
    }

    public List<Mecanico> buscarPorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Mecanico> query = session.createQuery(
                "FROM Mecanico WHERE LOWER(nombre) LIKE LOWER(:nombre)", Mecanico.class);
            query.setParameter("nombre", "%" + nombre + "%");
            return query.list();
        }
    }

    public List<Mecanico> buscarPorCertificacion(String certificacion) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Mecanico> query = session.createQuery(
                "FROM Mecanico WHERE certificacion = :cert", Mecanico.class);
            query.setParameter("cert", certificacion);
            return query.list();
        }
    }

    public List<String> listarCertificaciones() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                "SELECT DISTINCT certificacion FROM Mecanico ORDER BY certificacion", String.class);
            return query.list();
        }
    }

    public long contarPorCertificacion(String certificacion) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(*) FROM Mecanico WHERE certificacion = :cert", Long.class);
            query.setParameter("cert", certificacion);
            Long result = query.uniqueResult();
            return result != null ? result : 0L;
        }
    }
}
