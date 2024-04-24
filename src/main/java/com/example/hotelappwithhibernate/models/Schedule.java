package com.example.hotelappwithhibernate.models;

import jakarta.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="day")
    private String day;

    @Column(name = "time")
    private String time;

    @ManyToOne
    @JoinColumn(name = "maid_id",referencedColumnName = "id")
    private Maid maid;

    @ManyToOne
    @JoinColumn(name = "room_id",referencedColumnName = "id")
    private Room room;

    public Schedule(String day, String time) {
        this.day = day;
        this.time = time;
    }

    public Schedule() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Maid getMaid() {
        return maid;
    }

    public void setMaid(Maid maid) {
        this.maid = maid;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
