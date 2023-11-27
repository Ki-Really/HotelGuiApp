package com.example.hotelappwithhibernate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AppController {
    private Stage stage;
    private Scene scene;

    @FXML
    private void setGuestScene(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com.example.hotelappwithhibernate/scenes/guestScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,1110,625);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void setRoomScene(ActionEvent event) throws IOException{
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com.example.hotelappwithhibernate/scenes/roomScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,910,510);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void setMaidScene(ActionEvent event) throws IOException{
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/com.example.hotelappwithhibernate/scenes/maidScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,910,510);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void setScheduleScene(ActionEvent event) throws IOException{
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/com.example.hotelappwithhibernate/scenes/scheduleScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,910,510);

        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void setServiceScene(ActionEvent event) throws IOException{
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/com.example.hotelappwithhibernate/scenes/serviceScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,910,510);

        stage.setScene(scene);
        stage.show();
    }
}