package com.example.hotelappwithhibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "/com.example.hotelappwithhibernate/scenes/app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 910, 510);
        stage.setTitle("Ki-Really Hotel *****");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}