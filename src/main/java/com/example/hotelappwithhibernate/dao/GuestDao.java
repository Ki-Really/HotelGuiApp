package com.example.hotelappwithhibernate.dao;

import com.example.hotelappwithhibernate.models.Guest;
import com.example.hotelappwithhibernate.models.Room;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.Date;
import java.util.List;

public class GuestDao {
    private final SessionFactory sessionFactory;

    public GuestDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Guest> index(){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Guest> res =session.createQuery("from Guest",Guest.class).getResultList();
        transaction.commit();
        return res;
    }

    public boolean isRoomPresent(Room room){
        List<Guest> guests = index();
        for(Guest guest : guests){
            if(guest.getRoom().getNumber() == room.getNumber()){
                System.out.println("Комната найдена, не удалять.");
                return true;
            }
        }
        return false;
    }

    public void save(Guest guest){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        AddressDao addressDao = new AddressDao(sessionFactory);
        addressDao.save(guest.getAddress());
        PassportDao passportDao = new PassportDao(sessionFactory);
        passportDao.save(guest.getPassport());
        session.persist(guest);
        transaction.commit();
    }

    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guest = session.get(Guest.class,id);

        ServiceDao serviceDao = new ServiceDao(sessionFactory);
        serviceDao.delete(guest.getId());

        AddressDao addressDao = new AddressDao(sessionFactory);
        PassportDao passportDao = new PassportDao(sessionFactory);

        session.remove(session.get(Guest.class,id));
        addressDao.delete(guest.getAddress().getId());
        passportDao.delete(guest.getPassport().getId());

        transaction.commit();
    }

    public void updateName(int id, String name){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        guestToBeUpdated.setName(name);
        transaction.commit();
    }

    public void updateSurname(int id, String surname){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        guestToBeUpdated.setSurname(surname);
        transaction.commit();
    }

    public void updatePatronymic(int id, String patronymic){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        guestToBeUpdated.setPatronymic(patronymic);
        transaction.commit();
    }

    public void updateGender(int id, String gender){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        guestToBeUpdated.setGender(gender);
        transaction.commit();
    }

    public void updateBirth(int id, Date date){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        guestToBeUpdated.setBirth_date(date);
        transaction.commit();
    }

    public void updateCountry(int id, String country){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        AddressDao addressDao = new AddressDao(sessionFactory);
        addressDao.updateCountry(guestToBeUpdated.getAddress().getId(), country);
        transaction.commit();
    }

    public void updateParking(int id, int nParking){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        guestToBeUpdated.setParking_lot_number(nParking);
        transaction.commit();
    }

    public void updateCity(int id, String city){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        AddressDao addressDao = new AddressDao(sessionFactory);
        addressDao.updateCity(guestToBeUpdated.getAddress().getId(), city);
        transaction.commit();
    }

    public void updateStreet(int id, String street){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        AddressDao addressDao = new AddressDao(sessionFactory);
        addressDao.updateStreet(guestToBeUpdated.getAddress().getId(), street);
        transaction.commit();
    }

    public void updateBuilding(int id, String building){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        AddressDao addressDao = new AddressDao(sessionFactory);
        addressDao.updateBuilding(guestToBeUpdated.getAddress().getId(), building);
        transaction.commit();
    }

    public void updateNPassport(int id, int number){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        PassportDao passportDao = new PassportDao(sessionFactory);
        passportDao.updateNPassport(guestToBeUpdated.getPassport().getId(), number);
        transaction.commit();
    }

    public void updateIssuance(int id, Date date){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        PassportDao passportDao = new PassportDao(sessionFactory);
        passportDao.updateIssuance(guestToBeUpdated.getPassport().getId(), date);
        transaction.commit();
    }

    public void updateGivenBy(int id, String string){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        PassportDao passportDao = new PassportDao(sessionFactory);
        passportDao.updateGivenBy(guestToBeUpdated.getPassport().getId(), string);
        transaction.commit();
    }

    public void updateDEntry(int id, Date date) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class, id);
        guestToBeUpdated.setDate_of_entry(date);
        transaction.commit();
    }

    public void updateDDepartment(int id, Date date) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class, id);
        guestToBeUpdated.setDeparture_date(date);
        transaction.commit();
    }

    public void updateNAuto(int id, String string){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Guest guestToBeUpdated = session.get(Guest.class,id);
        guestToBeUpdated.setAuto_number(string);
        transaction.commit();
    }

    public void updateNRoom(int id, int number){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        RoomDao roomDao = new RoomDao(this.sessionFactory);
        Guest guestToBeUpdated = session.get(Guest.class,id);
        Room room = roomDao.findRoomByNumber(number);
        guestToBeUpdated.setRoom(room);
        transaction.commit();
    }

    public List<Guest> findByFields(String fieldsToFind){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Guest g JOIN g.address WHERE " +
                " g.name LIKE '%" + fieldsToFind + "%' OR g.surname LIKE '%" + fieldsToFind + "%' " +
                "OR g.patronymic LIKE '%" + fieldsToFind + "%' OR g.address.country LIKE '%" + fieldsToFind + "%'" +
                "OR g.address.city LIKE '%" + fieldsToFind + "%' OR g.address.street LIKE '%" + fieldsToFind + "%'" +
                "OR g.address.building LIKE '%" + fieldsToFind + "%' OR g.gender LIKE '%" + fieldsToFind + "%'" +
                "OR g.auto_number LIKE '%" + fieldsToFind + "%' OR cast(g.passport.number as string ) = '" + fieldsToFind + "' " +
                "OR g.passport.givenBy LIKE '%" + fieldsToFind + "%' OR to_char(g.passport.issuance,'DD-MM-YYYY') " +
                "LIKE '%" +  fieldsToFind + "%' " +
                "OR cast(g.parking_lot_number as string) = '" + fieldsToFind + "' OR to_char(g.date_of_entry,'DD-MM-YYYY') " +
                "LIKE '%" + fieldsToFind + "%' " +
                "OR to_char(g.departure_date,'DD-MM-YYYY') LIKE '%" + fieldsToFind + "%' " +
                "OR to_char(g.birth_date,'DD-MM-YYYY')" + " LIKE '%" + fieldsToFind + "%' " +
                "OR cast(g.room.number as string) = '" + fieldsToFind + "' ";
        Query query = session.createQuery(hql, Guest.class);
        List<Guest> results = query.getResultList();
        transaction.commit();
        return results;
    }
}
