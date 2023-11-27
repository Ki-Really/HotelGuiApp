package com.example.hotelappwithhibernate.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name ="guest")
public class Guest {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name ="patronymic")
    private String patronymic;
    @Column(name = "gender")
    private String gender;
    @Column(name = "birth_date")
    private Date birth_date;
    @OneToOne
    @JoinColumn(name ="address_id",referencedColumnName = "id")
    private Address address;
    @OneToOne
    @JoinColumn(name ="passport_id",referencedColumnName = "id")
    private Passport passport;
    @ManyToOne
    @JoinColumn(name ="room_id",referencedColumnName = "id")
    private Room room;
    @Column(name = "parking_lot_number")
    private int parking_lot_number;
    @Column(name = "auto_number")
    private String auto_number;
    @Column(name = "date_of_entry")
    private Date date_of_entry;
    @Column(name = "departure_date")
    private Date departure_date;
    @ManyToMany(mappedBy="guests",fetch = FetchType.EAGER)
    private List<Service> services;


    @Override
    public String toString() {
        return
                name +
                " " + passport;
    }

    public Guest(String name, String surname, String patronymic, String gender, Date birth_date,
                 int parking_lot_number,
                 String auto_number, Date date_of_entry, Date departure_date) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.gender = gender;
        this.birth_date = birth_date;

        this.parking_lot_number = parking_lot_number;
        this.auto_number = auto_number;
        this.date_of_entry = date_of_entry;
        this.departure_date = departure_date;
    }

    public Guest() {
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getParking_lot_number() {
        return parking_lot_number;
    }

    public void setParking_lot_number(int parking_lot_number) {
        this.parking_lot_number = parking_lot_number;
    }

    public String getAuto_number() {
        return auto_number;
    }

    public void setAuto_number(String auto_number) {
        this.auto_number = auto_number;
    }

    public Date getDate_of_entry() {
        return date_of_entry;
    }

    public void setDate_of_entry(Date date_of_entry) {
        this.date_of_entry = date_of_entry;
    }

    public Date getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(Date departure_date) {
        this.departure_date = departure_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return id == guest.id && parking_lot_number == guest.parking_lot_number && Objects.equals(name, guest.name) && Objects.equals(surname, guest.surname) && Objects.equals(patronymic, guest.patronymic) && Objects.equals(gender, guest.gender) && Objects.equals(birth_date, guest.birth_date) && Objects.equals(address, guest.address) && Objects.equals(passport, guest.passport) && Objects.equals(room, guest.room) && Objects.equals(auto_number, guest.auto_number) && Objects.equals(date_of_entry, guest.date_of_entry) && Objects.equals(departure_date, guest.departure_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, patronymic, gender, birth_date, address, passport, room, parking_lot_number, auto_number, date_of_entry, departure_date);
    }
}
