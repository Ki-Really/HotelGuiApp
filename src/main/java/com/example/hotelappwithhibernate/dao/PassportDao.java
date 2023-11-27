package com.example.hotelappwithhibernate.dao;

import com.example.hotelappwithhibernate.models.Passport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.Date;
import java.util.List;

public class PassportDao {
    private final SessionFactory sessionFactory;

    public PassportDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Passport> index(){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Passport> res =session.createQuery("from Passport",Passport.class).getResultList();
        transaction.commit();
        return res;
    }

    public void save(Passport passport){
        Session session = sessionFactory.getCurrentSession();

        session.persist(passport);

    }

    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();

        session.remove(session.get(Passport.class,id));
    }

    public void updateNPassport(int id, int nPassport){
        Session session = sessionFactory.getCurrentSession();

        Passport passport = session.get(Passport.class,id);
        passport.setNumber(nPassport);
    }
    public void updateIssuance(int id, Date date){

        Session session = sessionFactory.getCurrentSession();

        Passport passport = session.get(Passport.class,id);
        passport.setIssuance(date);
    }

    public void updateGivenBy(int id, String given){
        Session session = sessionFactory.getCurrentSession();

        Passport passport = session.get(Passport.class,id);
        passport.setGivenBy(given);
    }
}
