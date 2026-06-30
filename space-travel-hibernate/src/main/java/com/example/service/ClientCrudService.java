package com.example.service;

import com.example.entity.Client;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClientCrudService {

    public Client create(String name) {
        validateName(name);

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Client client = new Client(name.trim());
            session.persist(client);

            transaction.commit();
            return client;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create client", e);
        }
    }

    public Client getById(Long id) {
        validateId(id);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Client client = session.get(Client.class, id);

            if (client == null) {
                throw new IllegalArgumentException("Client with id " + id + " not found");
            }

            return client;
        }
    }

    public void update(Long id, String newName) {
        validateId(id);
        validateName(newName);

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Client client = session.get(Client.class, id);

            if (client == null) {
                throw new IllegalArgumentException("Client with id " + id + " not found");
            }

            client.setName(newName.trim());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update client", e);
        }
    }

    public void deleteById(Long id) {
        validateId(id);

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Client client = session.get(Client.class, id);

            if (client == null) {
                throw new IllegalArgumentException("Client with id " + id + " not found");
            }

            session.remove(client);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete client", e);
        }
    }

    public List<Client> listAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Client", Client.class).list();
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Client id must be positive");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().length() < 3 || name.trim().length() > 200) {
            throw new IllegalArgumentException("Client name must contain from 3 to 200 characters");
        }
    }
}