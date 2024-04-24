package com.example.hotelappwithhibernate.controllers;

import com.example.hotelappwithhibernate.dao.GuestDao;
import com.example.hotelappwithhibernate.dao.RoomDao;
import com.example.hotelappwithhibernate.dao.ScheduleDao;
import com.example.hotelappwithhibernate.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class RoomController {
    public Label errorLbl;
    Configuration configuration = new Configuration().addAnnotatedClass(Room.class)
            .addAnnotatedClass(Guest.class)
            .addAnnotatedClass(Address.class)
            .addAnnotatedClass(Passport.class)
            .addAnnotatedClass(Service.class)
            .addAnnotatedClass(Maid.class)
            .addAnnotatedClass(Schedule.class)
            .addAnnotatedClass(Service.class);
    SessionFactory sessionFactory = configuration.buildSessionFactory();
    private final RoomDao roomDao = new RoomDao(sessionFactory);

    public TextField findTextField;
    public TextField roomCountAdd;
    public TextField roomNumberAdd;

    public TitledPane titledPane;

    public TableView<Room> table;

    public TableColumn<Room,Integer> tableId;
    public TableColumn<Room, String> tableNumber;
    public TableColumn<Room,Integer> tableCount;

    public TableColumn<Room,String> tableDelete;
    public ObservableList<Room> obsRoomList = FXCollections.observableArrayList();
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
        errorLbl.setVisible(false);
        if(!roomNumberAdd.getText().isEmpty() && !roomCountAdd.getText().isEmpty()){
            Room room = new Room(Integer.parseInt(roomNumberAdd.getText()),Integer.parseInt(roomCountAdd.getText()));
            roomDao.save(room);
            tableCreation();
        }
        else{
            errorLbl.setVisible(true);
        }
    }

    private void titledPaneAnimation(){
        titledPane.setAnimated(true);
    }

    public void tableCreation() {
        table.getItems().clear();
        List<Room> roomList = roomDao.index();
        obsRoomList.addAll(roomList);
        tableId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
        tableCount.setCellValueFactory(new PropertyValueFactory<>("people_count"));

        tableCount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        Callback<TableColumn<Room, String>, TableCell<Room, String>> cellDeleteFactory = (param) -> {
            final TableCell<Room, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction((event) -> {
                            Room room = getTableView().getItems().get(getIndex());
                            GuestDao guestDao = new GuestDao(sessionFactory);
                            ScheduleDao scheduleDao = new ScheduleDao(sessionFactory);
                            if (guestDao.isRoomPresent(room) || scheduleDao.isRoomPresent(room)) {
                                return;
                            } else {
                                System.out.println(guestDao.isRoomPresent(room));
                                System.out.println(scheduleDao.isRoomPresent(room));
                                roomDao.delete(room.getId());
                            }

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
        table.setItems(obsRoomList);
    }

@FXML
    public void onCountChange(TableColumn.CellEditEvent<Room, Integer> roomStringCellEditEvent) {
        Room room = table.getSelectionModel().getSelectedItem();
        roomDao.update(room.getId(),roomStringCellEditEvent.getNewValue());
        room.setPeople_count(roomStringCellEditEvent.getNewValue());
        tableCreation();
    }

    public void findInTable() {
        if(findTextField.getText().isEmpty()){
            tableCreation();
            return;
        }
        List<Room> resList = roomDao.findByFields(Integer.parseInt(findTextField.getText()));
        obsRoomList.clear();
        obsRoomList.addAll(resList);
        table.setItems(obsRoomList);
    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
                "/com.example.hotelappwithhibernate/scenes/app.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 910, 510);
        stage.setScene(scene);
        stage.show();
    }
}
