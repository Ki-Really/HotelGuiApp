package com.example.hotelappwithhibernate.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "number")
    private int number;

    @Column(name = "people_count")
    private int people_count;

    @OneToMany(mappedBy = "room")
    private List<Guest> guests;
    @OneToMany(mappedBy = "room")
    private List<Schedule> schedules;

    public Room(int number, int people_count) {
        this.number = number;
        this.people_count = people_count;
    }

    public Room() {
    }
    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPeople_count() {
        return people_count;
    }

    public void setPeople_count(int people_count) {
        this.people_count = people_count;
    }

    @Override
    public String toString() {
        return  "number=" + number +
                ", people_count=" + people_count;
    }
}
