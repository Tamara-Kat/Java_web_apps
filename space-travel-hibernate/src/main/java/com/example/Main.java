package com.example;

import com.example.entity.Ticket;
import com.example.service.TicketCrudService;
import com.example.util.HibernateUtil;

public class Main {

    public static void main(String[] args) {
        TicketCrudService ticketService = new TicketCrudService();

        System.out.println("All tickets:");
        ticketService.listAll().forEach(System.out::println);

        Ticket createdTicket = ticketService.create(1L, "EARTH", "MARS");
        System.out.println("\nCreated ticket:");
        System.out.println(createdTicket);

        Ticket foundTicket = ticketService.getById(createdTicket.getId());
        System.out.println("\nFound ticket:");
        System.out.println(foundTicket);

        ticketService.update(createdTicket.getId(), 2L, "MARS", "EARTH");
        System.out.println("\nUpdated ticket:");
        System.out.println(ticketService.getById(createdTicket.getId()));

        ticketService.deleteById(createdTicket.getId());
        System.out.println("\nTicket deleted.");

        System.out.println("\nAll tickets after delete:");
        ticketService.listAll().forEach(System.out::println);

        System.out.println("\nValidation tests:");

        try {
            ticketService.create(null, "EARTH", "MARS");
        } catch (Exception e) {
            System.out.println("Cannot create ticket with null client: OK");
        }

        try {
            ticketService.create(999L, "EARTH", "MARS");
        } catch (Exception e) {
            System.out.println("Cannot create ticket with non-existing client: OK");
        }

        try {
            ticketService.create(1L, null, "MARS");
        } catch (Exception e) {
            System.out.println("Cannot create ticket with null from planet: OK");
        }

        try {
            ticketService.create(1L, "UNKNOWN", "MARS");
        } catch (Exception e) {
            System.out.println("Cannot create ticket with non-existing planet: OK");
        }
        try {
            ticketService.create(1L, "EARTH", null);
        } catch (Exception e) {
            System.out.println("Cannot create ticket with null to planet: OK");
        }

        try {
            ticketService.create(1L, "EARTH", "UNKNOWN");
        } catch (Exception e) {
            System.out.println("Cannot create ticket with non-existing to planet: OK");
        }

        try {
            ticketService.create(1L, "EARTH", "EARTH");
        } catch (Exception e) {
            System.out.println("Cannot create ticket with same from/to planet: OK");
        }

        HibernateUtil.close();
    }
}