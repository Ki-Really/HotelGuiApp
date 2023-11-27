package com.example.hotelappwithhibernate.controllers;

import com.example.hotelappwithhibernate.dao.GuestDao;
import com.example.hotelappwithhibernate.dao.ServiceDao;
import com.example.hotelappwithhibernate.models.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ServiceController {
    private Stage stage;
    private Scene scene;
    Configuration configuration = new Configuration().addAnnotatedClass(Service.class)
            .addAnnotatedClass(Guest.class)
            .addAnnotatedClass(Address.class)
            .addAnnotatedClass(Passport.class)
            .addAnnotatedClass(Service.class)
            .addAnnotatedClass(Maid.class)
            .addAnnotatedClass(Schedule.class)
            .addAnnotatedClass(Room.class);
    SessionFactory sessionFactory = configuration.buildSessionFactory();
    private ServiceDao serviceDao = new ServiceDao(sessionFactory);

    public TableView<Service> table;
    public TableColumn<Service,Integer> tableId;
    public TableColumn<Service,Guest> tableGuest;
    public TableColumn<Service, String> tableService;
    public TableColumn<Service,String> tableDelete;
    public TextField findTextField;
    public ComboBox<Guest> guestAdd;
    public TextField serviceAdd;
    public TitledPane titledPane;

    public ObservableList<Service> obsServiceList = FXCollections.observableArrayList();
    private ObservableList<Guest> guests = FXCollections.observableArrayList();

    public void backAction(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com.example.hotelappwithhibernate/scenes/app.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,910,510);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize(){
        titledPaneAnimation();
        tableCreation();
        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
    }

    @FXML
    private void create(){
        table.getItems().clear();
        if(!guestAdd.getSelectionModel().isEmpty() && !serviceAdd.getText().isEmpty()){
            Service service = new Service(serviceAdd.getText());
            Guest guest = (Guest)guestAdd.getValue();
            serviceDao.save(service,guest);
            tableCreation();
        }

    }
    private void getGuests(){
        GuestDao guestDao = new GuestDao(sessionFactory);

        guests.setAll(guestDao.index());
        guestAdd.setItems(guests);
    }

    private void titledPaneAnimation(){
        titledPane.setAnimated(true);
    }

    public void tableCreation() {
        table.getItems().clear();

        List<Service> serviceList = serviceDao.index();

        obsServiceList.addAll(serviceList);

        tableId.setCellValueFactory(new PropertyValueFactory<Service, Integer>("Id"));
        tableService.setCellValueFactory(new PropertyValueFactory<Service, String>("Name"));
        tableService.setCellFactory(TextFieldTableCell.forTableColumn());
        getGuests();
        tableGuest.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Service,Guest>, ObservableValue<Guest>>() {
            @Override
            public ObservableValue<Guest> call(TableColumn.CellDataFeatures<Service, Guest> m) {
                return new SimpleObjectProperty<Guest>( m.getValue().getGuests().get(0));
            }
        });
        tableGuest.setCellFactory(ComboBoxTableCell.forTableColumn(guests));


        Callback<TableColumn<Service, String>, TableCell<Service, String>> cellDeleteFactory = (param) -> {
            final TableCell<Service, String> cell = new TableCell<Service, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction((event) -> {
                            Service service = getTableView().getItems().get(getIndex());

                            Guest guest = service.getGuests().get(0);
                            serviceDao.delete(service.getId(),guest);
                            tableCreation();
                        });
                        setGraphic(deleteButton);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        tableDelete.setCellFactory(cellDeleteFactory);
        table.setItems(obsServiceList);

    }

    public void onServiceChange(TableColumn.CellEditEvent<Service,String> cellEditEvent) {
        Service service = table.getSelectionModel().getSelectedItem();
        serviceDao.updateName(service.getId(),cellEditEvent.getNewValue());
        tableCreation();
    }

    public void onGuestChange() {
        tableGuest.setOnEditCommit((TableColumn.CellEditEvent<Service, Guest> event) -> {
            TablePosition<Service, Guest> pos = event.getTablePosition();

            Guest guest = event.getNewValue();

            int row = pos.getRow();
            Service service = event.getTableView().getItems().get(row);
            serviceDao.updateGuest(service.getId(),guest);

        });
        tableCreation();
    }


    public void findInTable() {
        if(findTextField.getText().isEmpty()){
            tableCreation();
            return;
        }
        List<Service> resList = serviceDao.findByFields(findTextField.getText());
        obsServiceList.clear();
        obsServiceList.addAll(resList);
        table.setItems(obsServiceList);
    }
}
