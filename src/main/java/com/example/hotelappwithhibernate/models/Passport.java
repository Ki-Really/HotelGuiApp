package com.example.hotelappwithhibernate.models;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name ="passport")
public class Passport {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="number")
    private int number;
    @Column(name ="issuance")
    private Date issuance;
    @Column(name = "given_by")
    private String givenBy;
    @OneToOne(mappedBy = "passport")
    private Guest guest;

    public Passport(int number, Date issuance, String givenBy) {
        this.number = number;
        this.issuance = issuance;
        this.givenBy = givenBy;

    }



    public Passport() {
    }
    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
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

    public Date getIssuance() {
        return issuance;
    }

    public void setIssuance(Date issuance) {
        this.issuance = issuance;
    }

    public String getGivenBy() {
        return givenBy;
    }

    @Override
    public String toString() {
        return
                 number+"";
    }

    public void setGivenBy(String givenBy) {
        this.givenBy = givenBy;
    }
}
