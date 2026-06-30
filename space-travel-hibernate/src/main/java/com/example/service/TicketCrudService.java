package com.example.service;

import com.example.entity.Client;
import com.example.entity.Planet;
import com.example.entity.Ticket;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class TicketCrudService {

    public Ticket create(Long clientId, String fromPlanetId, String toPlanetId) {
        validateClientId(clientId);
        validatePlanetId(fromPlanetId);
        validatePlanetId(toPlanetId);
        validateDifferentPlanets(fromPlanetId, toPlanetId);

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Client client = findClientOrThrow(session, clientId);
            Planet fromPlanet = findPlanetOrThrow(session, fromPlanetId);
            Planet toPlanet = findPlanetOrThrow(session, toPlanetId);

            Ticket ticket = new Ticket(
                    LocalDateTime.now(Clock.systemUTC()),
                    client,
                    fromPlanet,
                    toPlanet
            );

            session.persist(ticket);

            transaction.commit();
            return ticket;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create ticket", e);
        }
    }

    public Ticket getById(Long id) {
        validateTicketId(id);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Ticket ticket = session.get(Ticket.class, id);

            if (ticket == null) {
                throw new IllegalArgumentException("Ticket with id " + id + " not found");
            }

            return ticket;
        }
    }

    public void update(Long ticketId, Long clientId, String fromPlanetId, String toPlanetId) {
        validateTicketId(ticketId);
        validateClientId(clientId);
        validatePlanetId(fromPlanetId);
        validatePlanetId(toPlanetId);
        validateDifferentPlanets(fromPlanetId, toPlanetId);

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Ticket ticket = session.get(Ticket.class, ticketId);

            if (ticket == null) {
                throw new IllegalArgumentException("Ticket with id " + ticketId + " not found");
            }

            Client client = findClientOrThrow(session, clientId);
            Planet fromPlanet = findPlanetOrThrow(session, fromPlanetId);
            Planet toPlanet = findPlanetOrThrow(session, toPlanetId);

            ticket.setClient(client);
            ticket.setFromPlanet(fromPlanet);
            ticket.setToPlanet(toPlanet);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update ticket", e);
        }
    }

    public void deleteById(Long id) {
        validateTicketId(id);

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Ticket ticket = session.get(Ticket.class, id);

            if (ticket == null) {
                throw new IllegalArgumentException("Ticket with id " + id + " not found");
            }

            session.remove(ticket);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete ticket", e);
        }
    }

    public List<Ticket> listAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Ticket", Ticket.class).list();
        }
    }

    private Client findClientOrThrow(Session session, Long clientId) {
        Client client = session.get(Client.class, clientId);

        if (client == null) {
            throw new IllegalArgumentException("Client with id " + clientId + " does not exist");
        }

        return client;
    }

    private Planet findPlanetOrThrow(Session session, String planetId) {
        Planet planet = session.get(Planet.class, planetId.trim());

        if (planet == null) {
            throw new IllegalArgumentException("Planet with id " + planetId + " does not exist");
        }

        return planet;
    }

    private void validateTicketId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Ticket id must be positive");
        }
    }

    private void validateClientId(Long clientId) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client id must be positive");
        }
    }

    private void validatePlanetId(String planetId) {
        if (planetId == null || planetId.trim().isEmpty() || !planetId.matches("[A-Z0-9]+")) {
            throw new IllegalArgumentException(
                    "Planet id must contain only uppercase Latin letters and digits"
            );
        }
    }

    private void validateDifferentPlanets(String fromPlanetId, String toPlanetId) {
        if (fromPlanetId.trim().equals(toPlanetId.trim())) {
            throw new IllegalArgumentException("From planet and to planet must be different");
        }
    }
}
