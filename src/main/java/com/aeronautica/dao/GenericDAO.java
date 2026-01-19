package com.aeronautica.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.aeronautica.config.HibernateUtil;

public class GenericDAO<T> {

    private final Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            session.flush();
            tx.commit();
            System.out.println("Entity saved successfully: " + entity.getClass().getSimpleName());
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error saving entity: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar: " + e.getMessage(), e);
        }
    }

    public void guardar(T entity) {
        save(entity);
    }

    public void update(T entity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error updating entity: " + e.getMessage());
        }
    }

    public void actualizar(T entity) {
        update(entity);
    }

    public T findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(entityClass, id);
        }
    }

    public T buscarPorId(Long id) {
        return findById(id);
    }

    public List<T> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
        }
    }

    public List<T> listarTodos() {
        return findAll();
    }

    public void delete(T entity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error deleting entity: " + e.getMessage());
        }
    }

    public void eliminar(T entity) {
        delete(entity);
    }

    public long contar() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(*) FROM " + entityClass.getSimpleName(), Long.class);
            Long result = query.uniqueResult();
            return result != null ? result : 0L;
        }
    }
}
