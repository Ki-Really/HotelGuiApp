package com.example.hotelappwithhibernate.controllers;


import com.example.hotelappwithhibernate.dao.MaidDao;
import com.example.hotelappwithhibernate.dao.RoomDao;
import com.example.hotelappwithhibernate.dao.ScheduleDao;
import com.example.hotelappwithhibernate.models.*;
import javafx.beans.property.SimpleObjectProperty;
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

public class ScheduleController {
    Configuration configuration = new Configuration().addAnnotatedClass(Schedule.class)
            .addAnnotatedClass(Address.class)
            .addAnnotatedClass(Guest.class)
            .addAnnotatedClass(Passport.class)
            .addAnnotatedClass(Room.class)
            .addAnnotatedClass(Service.class)
            .addAnnotatedClass(Maid.class)
            .addAnnotatedClass(Service.class);

    SessionFactory sessionFactory = configuration.buildSessionFactory();
    private final ScheduleDao scheduleDao  = new ScheduleDao(sessionFactory);
    public TableView<Schedule> table;
    public TextField findTextField;
    public ComboBox<Room> scheduleRoomAdd;
    public ComboBox<Maid> scheduleMaidAdd;
    public TextField scheduleTimeAdd;
    public TextField scheduleDayAdd;
    public TitledPane titledPane;

    public TableColumn<Schedule,Integer> tableId;
    public TableColumn<Schedule, String> tableDay;
    public TableColumn<Schedule, String> tableTime;
    public TableColumn<Schedule, Maid> tableMaid;
    public TableColumn<Schedule, Integer> tableNRoom;
    public TableColumn<Schedule,String> tableDelete;

    public ObservableList<Schedule> obsScheduleList = FXCollections.observableArrayList();
    private final ObservableList<Room> rooms = FXCollections.observableArrayList();
    private final ObservableList<Maid> maids = FXCollections.observableArrayList();


    @FXML
    private void initialize(){
        titledPaneAnimation();
        tableCreation();
        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
    }

    private void getRoom(){
        RoomDao roomDao = new RoomDao(sessionFactory);
        rooms.setAll(roomDao.index());
        scheduleRoomAdd.setItems(rooms);
    }

    private void getMaid(){
        MaidDao maidDao = new MaidDao(sessionFactory);
        maids.setAll(maidDao.index());
        scheduleMaidAdd.setItems(maids);
    }

    private void titledPaneAnimation(){
        titledPane.setAnimated(true);
    }

    public void tableCreation() {
        table.getItems().clear();
        List<Schedule> scheduleList = scheduleDao.index();
        obsScheduleList.addAll(scheduleList);
        tableId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        tableDay.setCellFactory(TextFieldTableCell.forTableColumn());
        tableTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        tableTime.setCellFactory(TextFieldTableCell.forTableColumn());

        ObservableList<Integer> roomNumbers = FXCollections.observableArrayList();
        getRoom();
        for(Room room : rooms){
            roomNumbers.add(room.getNumber());
            System.out.println(room.getNumber());
        }

        tableNRoom.setCellFactory(ComboBoxTableCell.forTableColumn(roomNumbers));
        tableNRoom.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getRoom().getNumber()));

        ObservableList<String> maidNames = FXCollections.observableArrayList();
        getMaid();
        for(Maid maid : maids){
            maidNames.add(maid.getName());
        }

        getMaid();
        tableMaid.setCellFactory(ComboBoxTableCell.forTableColumn(maids));
        tableMaid.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMaid()));
        Callback<TableColumn<Schedule, String>, TableCell<Schedule, String>> cellDeleteFactory = (param) -> {
            final TableCell<Schedule, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction((event) -> {
                            Schedule schedule = getTableView().getItems().get(getIndex());

                            scheduleDao.delete(schedule.getId());
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
        table.setItems(obsScheduleList);
    }

    public void create() {
        table.getItems().clear();
        if(!scheduleDayAdd.getText().isEmpty() && !scheduleTimeAdd.getText().isEmpty()
                && !scheduleRoomAdd.getSelectionModel().isEmpty() && !scheduleMaidAdd.getSelectionModel().isEmpty()){
            Schedule schedule = new Schedule(scheduleDayAdd.getText(),scheduleTimeAdd.getText());
            scheduleDao.save(schedule,scheduleRoomAdd.getValue(),scheduleMaidAdd.getValue());
            tableCreation();
        }
    }

    public void backAction(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
                "/com.example.hotelappwithhibernate/scenes/app.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 910, 510);
        stage.setScene(scene);
        stage.show();
    }

    public void onDayChange(TableColumn.CellEditEvent<Schedule,String> cellEditEvent) {
        Schedule schedule = table.getSelectionModel().getSelectedItem();
        scheduleDao.updateDay(schedule.getId(),cellEditEvent.getNewValue());
        tableCreation();
    }

    public void onTimeChange(TableColumn.CellEditEvent<Schedule,String> cellEditEvent) {
        Schedule schedule = table.getSelectionModel().getSelectedItem();
        scheduleDao.updateTime(schedule.getId(),cellEditEvent.getNewValue());
        tableCreation();
    }

    public void onRoomChange() {
        tableNRoom.setOnEditCommit((TableColumn.CellEditEvent<Schedule, Integer> event) -> {
            TablePosition<Schedule, Integer> pos = event.getTablePosition();

            int newNRoom = event.getNewValue();

            int row = pos.getRow();
            Schedule schedule = event.getTableView().getItems().get(row);
            scheduleDao.updateNRoom(schedule.getId(),newNRoom);

        });
        tableCreation();
    }

    public void onMaidChange() {
        tableMaid.setOnEditCommit((TableColumn.CellEditEvent<Schedule, Maid> event) -> {
            TablePosition<Schedule, Maid> pos = event.getTablePosition();
            Maid newMaid = event.getNewValue();
            int row = pos.getRow();
            Schedule schedule = event.getTableView().getItems().get(row);
            scheduleDao.updateMaid(schedule.getId(),newMaid);
            scheduleMaidAdd.setValue(newMaid);
        });
        tableCreation();
    }

    public void findInTable() {
        if(findTextField.getText().isEmpty()){
            tableCreation();
            return;
        }
        List<Schedule> resList = scheduleDao.findByFields(findTextField.getText());
        obsScheduleList.clear();
        obsScheduleList.addAll(resList);
        table.setItems(obsScheduleList);
    }
}
