package com.example.hotelappwithhibernate.controllers;

import com.example.hotelappwithhibernate.DateToStringConverter;
import com.example.hotelappwithhibernate.dao.GuestDao;
import com.example.hotelappwithhibernate.dao.RoomDao;
import com.example.hotelappwithhibernate.models.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.util.converter.IntegerStringConverter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GuestController {

    public TextField findTextField;
    public TextField guestDDepartmentAdd;
    public TextField guestGivenByAdd;
    public TextField guestDEntryAdd;
    public TextField guestParkingAdd;
    public TextField guestNAutoAdd;
    public TextField guestIssuanceAdd;
    public TextField guestBirthAdd;
    public TextField guestNPassportAdd;
    public TextField guestBuildingAdd;
    public TextField guestStreetAdd;
    public TextField guestCityAdd;
    public TextField guestCountryAdd;
    public TextField guestPatronymicAdd;
    public TextField guestSurnameAdd;
    public TextField guestNameAdd;
    public ComboBox<Room> guestNRoomAdd;

    public TableColumn<Guest, Integer> tableId;
    public TableColumn<Guest, String> tableName;
    public TableColumn<Guest, String> tableSurname;
    public TableColumn<Guest, String> tablePatronymic;
    public TableColumn<Guest, String> tableGender;
    public TableColumn<Guest, Date> tableBirthDate;
    public TableColumn<Guest, String> tableCountry;
    public TableColumn<Guest, String> tableCity;
    public TableColumn<Guest, String> tableStreet;
    public TableColumn<Guest, String> tableBuilding;
    public TableColumn<Guest, Integer> tableNPassport;
    public TableColumn<Guest, Date> tableIssuance;
    public TableColumn<Guest, String> tableGivenBy;
    public TableColumn<Guest, String> tableNAuto;
    public TableColumn<Guest, Integer> tableParking;
    public TableColumn<Guest, Integer> tableNRoom;
    public TableColumn<Guest, Date> tableDEntry;
    public TableColumn<Guest, Date> tableDDepartment;

    public TableColumn<Guest, String> tableDelete;

    public TableView<Guest> table;

    public TitledPane titledPane;
    public TextField guestGenderAdd;
    public ObservableList<Guest> obsGuestList = FXCollections.observableArrayList();
    ObservableList<Room> rooms = FXCollections.observableArrayList();

    Configuration configuration = new Configuration().addAnnotatedClass(Guest.class)
            .addAnnotatedClass(Address.class)
            .addAnnotatedClass(Passport.class)
            .addAnnotatedClass(Room.class)
            .addAnnotatedClass(Service.class)
            .addAnnotatedClass(Maid.class)
            .addAnnotatedClass(Schedule.class)
            .addAnnotatedClass(Service.class);
    SessionFactory sessionFactory = configuration.buildSessionFactory();
    private final GuestDao guestDao  = new GuestDao(sessionFactory);

    @FXML
    private void initialize(){
    titledPaneAnimation();
    tableCreation();
    getRoom();
    table.setEditable(true);
    table.getSelectionModel().setCellSelectionEnabled(true);
}
    private void titledPaneAnimation(){
        titledPane.setAnimated(true);
    }

    private Date dateWork(String str){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = df.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void create() {
        table.getItems().clear();
        if(!guestNameAdd.getText().isEmpty() && !guestSurnameAdd.getText().isEmpty()
                && !guestPatronymicAdd.getText().isEmpty() && !guestCountryAdd.getText().isEmpty()
                && !guestCityAdd.getText().isEmpty() && !guestStreetAdd.getText().isEmpty()
                && !guestBuildingAdd.getText().isEmpty() && !guestNPassportAdd.getText().isEmpty()
                && !guestIssuanceAdd.getText().isEmpty() && !guestGivenByAdd.getText().isEmpty()
                && !guestGenderAdd.getText().isEmpty()
                && !guestBirthAdd.getText().isEmpty() && !guestNAutoAdd.getText().isEmpty()
                && !guestParkingAdd.getText().isEmpty() && !guestDEntryAdd.getText().isEmpty()
                && !guestDDepartmentAdd.getText().isEmpty()){
            Date dateBirth = dateWork(guestBirthAdd.getText());
            Date dateEntry = dateWork(guestDEntryAdd.getText());
            Date dateDepart = dateWork(guestDDepartmentAdd.getText());

            Guest guest = new Guest(guestNameAdd.getText(),guestSurnameAdd.getText(),
                    guestPatronymicAdd.getText(),guestGenderAdd.getText(),dateBirth,
                    Integer.parseInt(guestParkingAdd.getText()),guestNAutoAdd.getText(),dateEntry,
                    dateDepart);

            Address address = new Address(guestCountryAdd.getText(),guestCityAdd.getText(),
                    guestStreetAdd.getText(),guestBuildingAdd.getText());
            address.setGuest(guest);
            guest.setAddress(address);
            Date date = dateWork(guestIssuanceAdd.getText());
            Passport passport = new Passport(Integer.parseInt(guestNPassportAdd.getText()),
                    date, guestGivenByAdd.getText());
            passport.setGuest(guest);
            guest.setPassport(passport);
            guest.setRoom(guestNRoomAdd.getValue());
            guest.setAddress(address);
            guest.setPassport(passport);
            guestDao.save(guest);
            tableCreation();
        }
    }

    private void getRoom(){
        RoomDao roomDao = new RoomDao(sessionFactory);
        rooms.setAll(roomDao.index());
        guestNRoomAdd.setItems(rooms);
    }



    public void tableCreation() {
        table.getItems().clear();

        List<Guest> guestList = guestDao.index();
        obsGuestList.addAll(guestList);

        tableId.setCellValueFactory(new PropertyValueFactory<>("Id"));

        tableName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tableName.setCellFactory(TextFieldTableCell.forTableColumn());

        tableSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        tableSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        tablePatronymic.setCellValueFactory(new PropertyValueFactory<>("Patronymic"));
        tablePatronymic.setCellFactory(TextFieldTableCell.forTableColumn());

        tableGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        tableGender.setCellFactory(TextFieldTableCell.forTableColumn());

        tableNAuto.setCellValueFactory(new PropertyValueFactory<>("auto_number"));
        tableNAuto.setCellFactory(TextFieldTableCell.forTableColumn());

        tableParking.setCellValueFactory(new PropertyValueFactory<>("parking_lot_number"));
        tableParking.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        tableCountry.setCellValueFactory(g -> new SimpleStringProperty( g.getValue().getAddress().getCountry()));
        tableCountry.setCellFactory(TextFieldTableCell.forTableColumn());

        tableCity.setCellValueFactory(g -> new SimpleStringProperty( g.getValue().getAddress().getCity()));
        tableCity.setCellFactory(TextFieldTableCell.forTableColumn());

        tableStreet.setCellFactory(TextFieldTableCell.forTableColumn());
        tableStreet.setCellValueFactory(g -> new SimpleStringProperty( g.getValue().getAddress().getStreet()));

        tableBuilding.setCellFactory(TextFieldTableCell.forTableColumn());
        tableBuilding.setCellValueFactory(g -> new SimpleStringProperty(g.getValue().getAddress().getBuilding()));

        tableNPassport.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableNPassport.setCellValueFactory(g -> new SimpleObjectProperty<>(g.getValue().getPassport().getNumber()));

        tableIssuance.setCellFactory(TextFieldTableCell.forTableColumn(new DateToStringConverter()));
        tableIssuance.setCellValueFactory(g -> new SimpleObjectProperty<>(g.getValue().getPassport().getIssuance()));

        tableBirthDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateToStringConverter()));
        tableBirthDate.setCellValueFactory(g -> new SimpleObjectProperty<>(g.getValue().getBirth_date()));

        tableDEntry.setCellFactory(TextFieldTableCell.forTableColumn(new DateToStringConverter()));
        tableDEntry.setCellValueFactory(g -> new SimpleObjectProperty<>(g.getValue().getDate_of_entry()));

        tableDDepartment.setCellFactory(TextFieldTableCell.forTableColumn(new DateToStringConverter()));
        tableDDepartment.setCellValueFactory(g -> new SimpleObjectProperty<>(g.getValue().getDeparture_date()));

        tableGivenBy.setCellFactory(TextFieldTableCell.forTableColumn());
        tableGivenBy.setCellValueFactory(g -> new SimpleStringProperty(g.getValue().getPassport().getGivenBy()));

        ObservableList<Integer> roomNumbers = FXCollections.observableArrayList();
        getRoom();
        for(Room room : rooms){
            roomNumbers.add(room.getNumber());
        }
        tableNRoom.setCellFactory(ComboBoxTableCell.forTableColumn(roomNumbers));
        tableNRoom.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getRoom().getNumber()));


        Callback<TableColumn<Guest, String>, TableCell<Guest, String>> cellDeleteFactory = (param) -> {
            final TableCell<Guest, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction((event) -> {
                            Guest guest = getTableView().getItems().get(getIndex());

                            guestDao.delete(guest.getId());
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
        table.setItems(obsGuestList);
    }

    public void onNameChange(TableColumn.CellEditEvent<Guest, String> guestStringCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateName(guest.getId(),guestStringCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onSurnameChange(TableColumn.CellEditEvent<Guest, String> guestStringCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateSurname(guest.getId(),guestStringCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onPatronymicChange(TableColumn.CellEditEvent<Guest, String> guestStringCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updatePatronymic(guest.getId(),guestStringCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onGenderChange(TableColumn.CellEditEvent<Guest, String> guestStringCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateGender(guest.getId(),guestStringCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onBirthChange(TableColumn.CellEditEvent<Guest, Date> guestDateCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateBirth(guest.getId(), guestDateCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onCountryChange(TableColumn.CellEditEvent<Guest, String> guestStringCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateCountry(guest.getId(),guestStringCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onCityChange(TableColumn.CellEditEvent<Guest, String> guestStringCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateCity(guest.getId(), guestStringCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onStreetChange(TableColumn.CellEditEvent<Guest, String> guestStringCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateStreet(guest.getId(), guestStringCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onBuildingChange(TableColumn.CellEditEvent<Guest, String> guestStringCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateBuilding(guest.getId(), guestStringCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onNPassportChange(TableColumn.CellEditEvent<Guest, Integer> cellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateNPassport(guest.getId(), cellEditEvent.getNewValue());
        tableCreation();
    }

    public void onIssuanceChange(TableColumn.CellEditEvent<Guest, Date> guestDateCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateIssuance(guest.getId(), guestDateCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onGivenByChange(TableColumn.CellEditEvent<Guest, String> guestStringCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateGivenBy(guest.getId(), guestStringCellEditEvent.getNewValue());
        tableCreation();
    }
    public void onParkingChange(TableColumn.CellEditEvent<Guest, Integer> guestIntCellEditEvent){
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateParking(guest.getId(), guestIntCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onNAutoChange(TableColumn.CellEditEvent<Guest, String> guestStringCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateNAuto(guest.getId(), guestStringCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onNRoomChange() {
        tableNRoom.setOnEditCommit((TableColumn.CellEditEvent<Guest, Integer> event) -> {
            TablePosition<Guest, Integer> pos = event.getTablePosition();
            int newNRoom = event.getNewValue();
            int row = pos.getRow();
            Guest guest = event.getTableView().getItems().get(row);
            guestDao.updateNRoom(guest.getId(),newNRoom);
        });
        tableCreation();
    }

    public void onDEntryChange(TableColumn.CellEditEvent<Guest, Date> guestDateCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateDEntry(guest.getId(), guestDateCellEditEvent.getNewValue());
        tableCreation();
    }

    public void onDDepartmentChange(TableColumn.CellEditEvent<Guest, Date> guestDateCellEditEvent) {
        Guest guest = table.getSelectionModel().getSelectedItem();
        guestDao.updateDDepartment(guest.getId(), guestDateCellEditEvent.getNewValue());
        tableCreation();
    }


    public void backAction(ActionEvent event)throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com.example.hotelappwithhibernate/scenes/app.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 910, 510);
        stage.setScene(scene);
        stage.show();
    }

    public void findInTable() {
        if(findTextField.getText().isEmpty()){
            tableCreation();
            return;
        }
        List<Guest> resList = guestDao.findByFields(findTextField.getText());
        obsGuestList.clear();
        obsGuestList.addAll(resList);
        table.setItems(obsGuestList);
    }
}
