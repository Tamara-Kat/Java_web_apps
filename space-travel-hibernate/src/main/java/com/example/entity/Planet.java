package com.example.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planet")
public class Planet {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @OneToMany(mappedBy = "fromPlanet")
    private List<Ticket> departureTickets = new ArrayList<>();

    @OneToMany(mappedBy = "toPlanet")
    private List<Ticket> arrivalTickets = new ArrayList<>();

    public List<Ticket> getDepartureTickets() {
        return departureTickets;
    }

    public List<Ticket> getArrivalTickets() {
        return arrivalTickets;
    }

    public Planet() {
    }

    public Planet(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
