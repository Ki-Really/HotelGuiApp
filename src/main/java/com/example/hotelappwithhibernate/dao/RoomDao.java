package com.example.hotelappwithhibernate.dao;

import com.example.hotelappwithhibernate.models.Room;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;


public class RoomDao {

    private final SessionFactory sessionFactory;

    public RoomDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Room> index(){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Room> res =session.createQuery("from Room",Room.class).getResultList();
        transaction.commit();
        return res;
    }

    public void save(Room room){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(room);
        transaction.commit();
    }

    public void update(int id, int count){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Room roomToBeUpdated = session.get(Room.class,id);
        roomToBeUpdated.setPeople_count(count);

        transaction.commit();
    }


    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.remove(session.get(Room.class,id));
        transaction.commit();
    }

    public List<Room> findByFields(int fieldsToFind){

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Room r WHERE r.number = "+fieldsToFind+" OR r.people_count = "+fieldsToFind+" ";
        Query query = session.createQuery(hql, Room.class);
        List<Room> results = query.getResultList();



        transaction.commit();
        return results;
    }
    public Room findRoomByNumber(int number){
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Room r WHERE r.number = "+number+" ";
        Query query = session.createQuery(hql, Room.class);
        Room result = (Room)query.getSingleResult();
        return result;
    }

}
