package com.aeronautica.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.aeronautica.config.HibernateUtil;
import com.aeronautica.model.Aeronave;

public class AeronaveDAO {

    public void guardar(Aeronave aeronave) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(aeronave);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error guardando aeronave: " + e.getMessage());
        }
    }

    public void eliminar(Aeronave aeronave) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(aeronave);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error eliminando aeronave: " + e.getMessage());
        }
    }

    public List<Aeronave> listarTodas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Aeronave", Aeronave.class).getResultList();
        }
    }
}
