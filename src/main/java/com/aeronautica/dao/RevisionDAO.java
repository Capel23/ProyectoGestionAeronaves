package com.aeronautica.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.aeronautica.config.HibernateUtil;
import com.aeronautica.model.Revision;

public class RevisionDAO extends GenericDAO<Revision> {

    public RevisionDAO() {
        super(Revision.class);
    }

    public List<Revision> buscarPorAeronave(Long aeronaveId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Revision> query = session.createQuery(
                "FROM Revision WHERE aeronave.id = :id ORDER BY fechaRevision DESC", Revision.class);
            query.setParameter("id", aeronaveId);
            return query.list();
        }
    }

    public Revision buscarUltimaRevisionAeronave(Long aeronaveId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Revision> query = session.createQuery(
                "FROM Revision WHERE aeronave.id = :id ORDER BY fechaRevision DESC", Revision.class);
            query.setParameter("id", aeronaveId);
            query.setMaxResults(1);
            return query.uniqueResult();
        }
    }

    public List<Revision> buscarPorMecanico(Long mecanicoId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Revision> query = session.createQuery(
                "FROM Revision WHERE mecanico.id = :id ORDER BY fechaRevision DESC", Revision.class);
            query.setParameter("id", mecanicoId);
            return query.list();
        }
    }

    public List<Revision> buscarPorTipo(String tipoRevision) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Revision> query = session.createQuery(
                "FROM Revision WHERE tipoRevision = :tipo ORDER BY fechaRevision DESC", Revision.class);
            query.setParameter("tipo", tipoRevision);
            return query.list();
        }
    }

    public List<Revision> buscarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Revision> query = session.createQuery(
                "FROM Revision WHERE fechaRevision BETWEEN :desde AND :hasta ORDER BY fechaRevision DESC", 
                Revision.class);
            query.setParameter("desde", desde);
            query.setParameter("hasta", hasta);
            return query.list();
        }
    }

    public List<Revision> buscarPendientesFirma() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Revision> query = session.createQuery(
                "FROM Revision WHERE firmadoJefe = false ORDER BY fechaRevision DESC", Revision.class);
            return query.list();
        }
    }

    public long contarRevisionesAeronave(Long aeronaveId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(*) FROM Revision WHERE aeronave.id = :id", Long.class);
            query.setParameter("id", aeronaveId);
            Long result = query.uniqueResult();
            return result != null ? result : 0L;
        }
    }

    public long contarRevisionesMecanico(Long mecanicoId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(*) FROM Revision WHERE mecanico.id = :id", Long.class);
            query.setParameter("id", mecanicoId);
            Long result = query.uniqueResult();
            return result != null ? result : 0L;
        }
    }

    public List<Revision> buscarRecientes(int dias) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime fechaLimite = LocalDateTime.now().minusDays(dias);
            Query<Revision> query = session.createQuery(
                "FROM Revision WHERE fechaRevision >= :fecha ORDER BY fechaRevision DESC", 
                Revision.class);
            query.setParameter("fecha", fechaLimite);
            return query.list();
        }
    }
}
