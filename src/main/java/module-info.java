module com.example.hotelappwithhibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    opens com.example.hotelappwithhibernate.models;

    opens com.example.hotelappwithhibernate to javafx.fxml;
    exports com.example.hotelappwithhibernate;
    exports com.example.hotelappwithhibernate.controllers;
    opens com.example.hotelappwithhibernate.controllers to javafx.fxml;
    exports com.example.hotelappwithhibernate.models;

}