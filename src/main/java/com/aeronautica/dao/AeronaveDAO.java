package com.aeronautica.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.aeronautica.config.HibernateUtil;
import com.aeronautica.model.Aeronave;

public class AeronaveDAO extends GenericDAO<Aeronave> {

    public AeronaveDAO() {
        super(Aeronave.class);
    }

    public void guardar(Aeronave aeronave) {
        save(aeronave);
    }

    public void actualizar(Aeronave aeronave) {
        update(aeronave);
    }

    public Aeronave buscarPorId(Long id) {
        return findById(id);
    }

    public List<Aeronave> listarTodos() {
        return findAll();
    }

    public long contar() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(*) FROM Aeronave", Long.class);
            Long result = query.uniqueResult();
            return result != null ? result : 0L;
        }
    }

    public Aeronave buscarPorMatricula(String matricula) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Aeronave> query = session.createQuery(
                "FROM Aeronave WHERE matricula = :matricula", Aeronave.class);
            query.setParameter("matricula", matricula);
            return query.uniqueResult();
        }
    }

    public List<Aeronave> buscarPorModelo(String modelo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Aeronave> query = session.createQuery(
                "FROM Aeronave WHERE LOWER(modelo) LIKE LOWER(:modelo)", Aeronave.class);
            query.setParameter("modelo", "%" + modelo + "%");
            return query.list();
        }
    }

    public List<Aeronave> buscarPorHorasVueloMayoresA(double horas) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Aeronave> query = session.createQuery(
                "FROM Aeronave WHERE horasVuelo > :horas ORDER BY horasVuelo DESC", 
                Aeronave.class);
            query.setParameter("horas", horas);
            return query.list();
        }
    }

    public void eliminar(Aeronave aeronave) {
        delete(aeronave);
    }
}