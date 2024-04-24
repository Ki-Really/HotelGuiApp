package com.example.hotelappwithhibernate.dao;

import com.example.hotelappwithhibernate.models.Maid;
import com.example.hotelappwithhibernate.models.Room;
import com.example.hotelappwithhibernate.models.Schedule;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ScheduleDao {
    private final SessionFactory sessionFactory;

    public ScheduleDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Schedule> index(){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Schedule> res =session.createQuery("from Schedule", Schedule.class).getResultList();
        transaction.commit();
        return res;
    }

    public boolean isRoomPresent(Room room){
        List<Schedule> schedules = index();
        for(Schedule schedule : schedules){
            if(schedule.getRoom().getNumber()==room.getNumber()){
                System.out.println("Комната найдена, не удалять.");
                return true;
            }
        }
        return false;
    }

    public void save(Schedule schedule, Room room, Maid maid){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        schedule.setMaid(maid);
        schedule.setRoom(room);
        session.persist(schedule);
        transaction.commit();
    }

    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.remove(session.get(Schedule.class,id));
        transaction.commit();
    }

    public void updateDay(int id, String day){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Schedule schedule = session.get(Schedule.class,id);
        schedule.setDay(day);
        transaction.commit();
    }

    public void updateTime(int id, String time){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Schedule schedule = session.get(Schedule.class,id);
        schedule.setTime(time);
        transaction.commit();
    }

    public void updateNRoom(int id, int number){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Schedule scheduleToUpdate = session.get(Schedule.class,id);
        RoomDao roomDao = new RoomDao(this.sessionFactory);
        Room room = roomDao.findRoomByNumber(number);
        scheduleToUpdate.setRoom(room);
        transaction.commit();
    }

    public void updateMaid(int id, Maid maid){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Schedule scheduleToUpdate = session.get(Schedule.class,id);
        scheduleToUpdate.setMaid(maid);
        transaction.commit();
    }

    public List<Schedule> findByFields(String fieldsToFind){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Schedule s WHERE " +
                " s.day LIKE '%" + fieldsToFind + "%' OR s.time LIKE '%" + fieldsToFind + "%' " +
                "OR s.maid.name LIKE '%" + fieldsToFind + "%' OR s.maid.surname LIKE '%" + fieldsToFind + "%'" +
                "OR cast(s.room.number as string) = '" + fieldsToFind + "' ";
        Query query = session.createQuery(hql, Schedule.class);
        List<Schedule> results = query.getResultList();
        transaction.commit();
        return results;
    }
}
