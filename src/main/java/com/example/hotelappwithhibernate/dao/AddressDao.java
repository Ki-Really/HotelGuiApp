package com.example.hotelappwithhibernate.dao;

import com.example.hotelappwithhibernate.models.Address;
import com.example.hotelappwithhibernate.models.Maid;
import com.example.hotelappwithhibernate.models.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AddressDao {
    private final SessionFactory sessionFactory;

    public AddressDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Address> index(){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Address> res =session.createQuery("from Address",Address.class).getResultList();
        transaction.commit();
        return res;
    }

    public void save(Address address){
        Session session = sessionFactory.getCurrentSession();

        session.persist(address);

    }

    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();

        session.remove(session.get(Address.class,id));
    }

    public void updateCountry(int id, String country){
        Session session = sessionFactory.getCurrentSession();

        Address address = session.get(Address.class,id);
        address.setCountry(country);
    }
    public void updateCity(int id, String city){
        Session session = sessionFactory.getCurrentSession();

        Address address = session.get(Address.class,id);
        address.setCity(city);
    }

    public void updateStreet(int id, String street){
        Session session = sessionFactory.getCurrentSession();

        Address address = session.get(Address.class,id);
        address.setStreet(street);
    }

    public void updateBuilding(int id, String building){
        Session session = sessionFactory.getCurrentSession();

        Address address = session.get(Address.class,id);
        address.setBuilding(building);
    }
}
