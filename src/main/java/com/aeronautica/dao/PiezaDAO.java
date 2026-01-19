package com.aeronautica.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.aeronautica.config.HibernateUtil;
import com.aeronautica.model.Pieza;

public class PiezaDAO extends GenericDAO<Pieza> {

    public PiezaDAO() {
        super(Pieza.class);
    }

    public Pieza buscarPorCodigo(String codigo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Pieza> query = session.createQuery(
                "FROM Pieza WHERE codigo = :codigo", Pieza.class);
            query.setParameter("codigo", codigo);
            return query.uniqueResult();
        }
    }

    public List<Pieza> buscarStockBajo(int limiteStock) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Pieza> query = session.createQuery(
                "FROM Pieza WHERE stock < :limite ORDER BY stock ASC", Pieza.class);
            query.setParameter("limite", limiteStock);
            return query.list();
        }
    }

    public List<Pieza> buscarAgotadas() {
        return buscarStockBajo(1);
    }

    public boolean actualizarStock(Long piezaId, int nuevoStock) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Pieza pieza = session.get(Pieza.class, piezaId);
            if (pieza != null) {
                pieza.setStock(nuevoStock);
                session.merge(pieza);
                session.getTransaction().commit();
                return true;
            }
            session.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean incrementarStock(Long piezaId, int cantidad) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Pieza pieza = session.get(Pieza.class, piezaId);
            if (pieza != null) {
                pieza.setStock(pieza.getStock() + cantidad);
                session.merge(pieza);
                session.getTransaction().commit();
                return true;
            }
            session.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean decrementarStock(Long piezaId, int cantidad) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Pieza pieza = session.get(Pieza.class, piezaId);
            if (pieza != null && pieza.getStock() >= cantidad) {
                pieza.setStock(pieza.getStock() - cantidad);
                session.merge(pieza);
                session.getTransaction().commit();
                return true;
            }
            session.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long contarStockTotal() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT SUM(stock) FROM Pieza", Long.class);
            Long result = query.uniqueResult();
            return result != null ? result : 0L;
        }
    }
}
