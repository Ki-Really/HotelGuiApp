package com.example.hotelappwithhibernate.dao;

import com.example.hotelappwithhibernate.models.Maid;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class MaidDao {
    private final SessionFactory sessionFactory;

    public MaidDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Maid> index(){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Maid> res =session.createQuery("from Maid",Maid.class).getResultList();
        transaction.commit();
        return res;
    }

    public void save(Maid maid){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        AddressDao addressDao = new AddressDao(sessionFactory);
        addressDao.save(maid.getAddress());
        session.persist(maid);
        transaction.commit();
    }

    public void updateName(int id, String name){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Maid maidToBeUpdated = session.get(Maid.class,id);
        maidToBeUpdated.setName(name);
        transaction.commit();
    }

    public void updateSurname(int id, String surname){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Maid maidToBeUpdated = session.get(Maid.class,id);
        maidToBeUpdated.setSurname(surname);
        transaction.commit();
    }

    public void updatePatronymic(int id, String patronymic){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Maid maidToBeUpdated = session.get(Maid.class,id);
        maidToBeUpdated.setPatronymic(patronymic);
        transaction.commit();
    }

    public void updateCountry(int id, String country){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Maid maidToBeUpdated = session.get(Maid.class,id);
        AddressDao addressDao = new AddressDao(sessionFactory);
        addressDao.updateCountry(maidToBeUpdated.getAddress().getId(),country);
        transaction.commit();
    }

    public void updateCity(int id, String city){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Maid maidToBeUpdated = session.get(Maid.class,id);
        AddressDao addressDao = new AddressDao(sessionFactory);
        addressDao.updateCity(maidToBeUpdated.getAddress().getId(),city);
        transaction.commit();
    }

    public void updateStreet(int id, String street){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Maid maidToBeUpdated = session.get(Maid.class,id);
        AddressDao addressDao = new AddressDao(sessionFactory);
        addressDao.updateStreet(maidToBeUpdated.getAddress().getId(),street);
        transaction.commit();
    }

    public void updateBuilding(int id, String building){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Maid maidToBeUpdated = session.get(Maid.class,id);
        AddressDao addressDao = new AddressDao(sessionFactory);
        addressDao.updateBuilding(maidToBeUpdated.getAddress().getId(),building);
        transaction.commit();
    }

    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Maid maid = session.get(Maid.class,id);
        AddressDao addressDao = new AddressDao(sessionFactory);
        maid.getSchedules().forEach(session::remove);
        session.remove(session.get(Maid.class,id));
        addressDao.delete(maid.getAddress().getId());
        transaction.commit();
    }

    public List<Maid> findByFields(String fieldsToFind){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Maid m JOIN m.address WHERE " +
                " m.name LIKE '%" + fieldsToFind + "%' OR m.surname LIKE '%" + fieldsToFind + "%' " +
                "OR m.patronymic LIKE '%" + fieldsToFind + "%' OR m.address.country LIKE '%" + fieldsToFind + "%'" +
                "OR m.address.city LIKE '%" + fieldsToFind + "%' OR m.address.street LIKE '%" + fieldsToFind + "%'" +
                "OR m.address.building LIKE '%" + fieldsToFind + "%'  ";
        Query query = session.createQuery(hql, Maid.class);
        List<Maid> results = query.getResultList();
        transaction.commit();
        return results;
    }
}
