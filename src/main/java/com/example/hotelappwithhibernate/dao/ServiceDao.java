package com.example.hotelappwithhibernate.dao;

import com.example.hotelappwithhibernate.models.Guest;
import com.example.hotelappwithhibernate.models.Service;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ServiceDao {
    private final SessionFactory sessionFactory;

    public ServiceDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Service> index(){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Service> res =session.createQuery("from Service", Service.class).getResultList();
        transaction.commit();
        return res;
    }

    public void save(Service service, Guest guest){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        service.setGuests(new ArrayList<>(List.of(guest)));
        guest.setServices(new ArrayList<>(List.of(service)));
        session.persist(service);
        transaction.commit();
    }

    public void delete(int id,Guest guest){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Service serviceToRemove = session.get(Service.class,id);
        serviceToRemove.getGuests().remove(guest);
        guest.getServices().remove(serviceToRemove);
        session.remove(serviceToRemove);
        transaction.commit();
    }

    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Guest guest = session.get(Guest.class,id);
        int size = guest.getServices().size();
        for(int i = 0; i<size;i++){
            session.remove(guest.getServices().get(i));
        }
    }

    public void updateGuest(int id, Guest guest){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Service serviceToUpdate = session.get(Service.class,id);
        serviceToUpdate.setGuests(new ArrayList<>(List.of(guest)));
        session.merge(serviceToUpdate);
        transaction.commit();
    }

    public void updateName(int id, String name){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Service service = session.get(Service.class,id);
        service.setName(name);
        transaction.commit();
    }

    public List<Service> findByFields(String fieldsToFind){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Service s join s.guests g WHERE " +
                " s.name LIKE '%" + fieldsToFind + "%' OR g.name LIKE '%" + fieldsToFind + "%' ";
        System.out.println(hql);
        Query query = session.createQuery(hql, Service.class);
        List<Service> results = query.getResultList();
        transaction.commit();
        return results;
    }
}
