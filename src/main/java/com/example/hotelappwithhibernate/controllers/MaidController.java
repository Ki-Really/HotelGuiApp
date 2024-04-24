package com.example.hotelappwithhibernate.controllers;


import com.example.hotelappwithhibernate.dao.MaidDao;
import com.example.hotelappwithhibernate.models.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MaidController {
    public TextField findTextField;
    public TextField maidBuildingAdd;
    public TextField maidStreetAdd;
    public TextField maidCityAdd;
    public TextField maidCountryAdd;
    public TextField maidPatronymicAdd;
    public TextField maidSurnameAdd;
    Configuration configuration = new Configuration().addAnnotatedClass(Maid.class)
            .addAnnotatedClass(Address.class)
            .addAnnotatedClass(Guest.class)
            .addAnnotatedClass(Passport.class)
            .addAnnotatedClass(Room.class)
            .addAnnotatedClass(Service.class)
            .addAnnotatedClass(Schedule.class)
            .addAnnotatedClass(Service.class);

    SessionFactory sessionFactory = configuration.buildSessionFactory();
    private final MaidDao maidDao  = new MaidDao(sessionFactory);

    public TableView<Maid> table;

    public TitledPane titledPane;
    public TextField maidNameAdd;
    public TableColumn<Maid,Integer> tableId;
    public TableColumn<Maid, String> tableName;
    public TableColumn<Maid,String> tableSurname;

    public TableColumn<Maid, String> tablePatronymic;
    public TableColumn<Maid,String> tableCountry;
    public TableColumn<Maid, String> tableCity;
    public TableColumn<Maid, String> tableStreet;
    public TableColumn<Maid, String> tableBuilding;

    public TableColumn<Maid,String> tableDelete;

    public ObservableList<Maid> obsMaidList = FXCollections.observableArrayList();


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
        if(!maidNameAdd.getText().isEmpty() && !maidSurnameAdd.getText().isEmpty()
                && !maidPatronymicAdd.getText().isEmpty() && !maidCountryAdd.getText().isEmpty()
                && !maidCityAdd.getText().isEmpty() && !maidStreetAdd.getText().isEmpty()
                && !maidBuildingAdd.getText().isEmpty()){
            Maid maid = new Maid(maidNameAdd.getText(),maidSurnameAdd.getText(),maidPatronymicAdd.getText());
            Address address = new Address(maidCountryAdd.getText(),maidCityAdd.getText(),
                    maidStreetAdd.getText(),maidBuildingAdd.getText());
            address.setMaid(maid);
            maid.setAddress(address);
            maidDao.save(maid);
            tableCreation();
        }
    }


    private void titledPaneAnimation(){
        titledPane.setAnimated(true);
    }

    public void tableCreation() {
        table.getItems().clear();

        List<Maid> maidList = maidDao.index();

        obsMaidList.addAll(maidList);

        tableId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tableName.setCellFactory(TextFieldTableCell.forTableColumn());

        tableSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        tableSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        tablePatronymic.setCellValueFactory(new PropertyValueFactory<>("Patronymic"));
        tablePatronymic.setCellFactory(TextFieldTableCell.forTableColumn());

        tableCountry.setCellValueFactory(m -> new SimpleStringProperty(m.getValue().getAddress().getCountry()));
        tableCountry.setCellFactory(TextFieldTableCell.forTableColumn());
        tableCity.setCellValueFactory(m -> new SimpleStringProperty( m.getValue().getAddress().getCity()));
        tableCity.setCellFactory(TextFieldTableCell.forTableColumn());
        tableStreet.setCellFactory(TextFieldTableCell.forTableColumn());
        tableStreet.setCellValueFactory(m -> new SimpleStringProperty(m.getValue().getAddress().getStreet()));

        tableBuilding.setCellFactory(TextFieldTableCell.forTableColumn());
        tableBuilding.setCellValueFactory(m -> new SimpleStringProperty(m.getValue().getAddress().getBuilding()));

        Callback<TableColumn<Maid, String>, TableCell<Maid, String>> cellDeleteFactory = (param) -> {
            final TableCell<Maid, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction((event) -> {
                            Maid maid = getTableView().getItems().get(getIndex());

                            maidDao.delete(maid.getId());
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
        table.setItems(obsMaidList);
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
    @FXML
    public void onNameChange(TableColumn.CellEditEvent<Maid, String> maidStringCellEditEvent) {
        Maid maid = table.getSelectionModel().getSelectedItem();
        maidDao.updateName(maid.getId(),maidStringCellEditEvent.getNewValue());
        tableCreation();
    }

    @FXML
    public void onSurnameChange(TableColumn.CellEditEvent<Maid, String> maidStringCellEditEvent) {
        Maid maid = table.getSelectionModel().getSelectedItem();
        maidDao.updateSurname(maid.getId(),maidStringCellEditEvent.getNewValue());
        tableCreation();
    }
    @FXML
    public void onPatronymicChange(TableColumn.CellEditEvent<Maid, String> maidStringCellEditEvent) {
        Maid maid = table.getSelectionModel().getSelectedItem();
        maidDao.updatePatronymic(maid.getId(),maidStringCellEditEvent.getNewValue());
        tableCreation();
    }
    @FXML
    public void onCountryChange(TableColumn.CellEditEvent<Maid, String> maidStringCellEditEvent) {
        Maid maid = table.getSelectionModel().getSelectedItem();
        maidDao.updateCountry(maid.getId(),maidStringCellEditEvent.getNewValue());
        tableCreation();
    }
    @FXML
    public void onCityChange(TableColumn.CellEditEvent<Maid, String> maidStringCellEditEvent) {
        Maid maid = table.getSelectionModel().getSelectedItem();
        maidDao.updateCity(maid.getId(),maidStringCellEditEvent.getNewValue());
        tableCreation();
    }
    @FXML
    public void onStreetChange(TableColumn.CellEditEvent<Maid, String> maidStringCellEditEvent) {
        Maid maid = table.getSelectionModel().getSelectedItem();
        maidDao.updateStreet(maid.getId(),maidStringCellEditEvent.getNewValue());
        tableCreation();
    }
    @FXML
    public void onBuildingChange(TableColumn.CellEditEvent<Maid, String> maidStringCellEditEvent) {
        Maid maid = table.getSelectionModel().getSelectedItem();
        maidDao.updateBuilding(maid.getId(),maidStringCellEditEvent.getNewValue());
        tableCreation();
    }

    public void findInTable() {
        if(findTextField.getText().isEmpty()){
            tableCreation();
            return;
        }
        List<Maid> resList = maidDao.findByFields(findTextField.getText());
        obsMaidList.clear();
        obsMaidList.addAll(resList);
        table.setItems(obsMaidList);
    }
}
