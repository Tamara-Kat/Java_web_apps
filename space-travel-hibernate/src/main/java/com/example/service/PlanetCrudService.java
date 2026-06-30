package com.example.service;

import com.example.entity.Planet;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlanetCrudService {

    public Planet create(String id, String name) {
        validateId(id);
        validateName(name);

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Planet existingPlanet = session.get(Planet.class, id.trim());

            if (existingPlanet != null) {
                throw new IllegalArgumentException("Planet with id " + id + " already exists");
            }

            Planet planet = new Planet(id.trim(), name.trim());
            session.persist(planet);

            transaction.commit();
            return planet;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create planet", e);
        }
    }

    public Planet getById(String id) {
        validateId(id);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Planet planet = session.get(Planet.class, id.trim());

            if (planet == null) {
                throw new IllegalArgumentException("Planet with id " + id + " not found");
            }

            return planet;
        }
    }

    public void update(String id, String newName) {
        validateId(id);
        validateName(newName);

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Planet planet = session.get(Planet.class, id.trim());

            if (planet == null) {
                throw new IllegalArgumentException("Planet with id " + id + " not found");
            }

            planet.setName(newName.trim());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update planet", e);
        }
    }

    public void deleteById(String id) {
        validateId(id);

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Planet planet = session.get(Planet.class, id.trim());

            if (planet == null) {
                throw new IllegalArgumentException("Planet with id " + id + " not found");
            }

            session.remove(planet);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete planet", e);
        }
    }

    public List<Planet> listAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Planet", Planet.class).list();
        }
    }

    private void validateId(String id) {
        if (id == null || id.trim().isEmpty() || id.length() > 50 || !id.matches("[A-Z0-9]+")) {
            throw new IllegalArgumentException(
                    "Planet id must contain only uppercase Latin letters and digits"
            );
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty() || name.trim().length() > 500) {
            throw new IllegalArgumentException(
                    "Planet name must contain from 1 to 500 characters"
            );
        }
    }
}
