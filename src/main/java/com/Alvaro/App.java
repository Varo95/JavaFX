package com.Alvaro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("vitalicon.png")));
        stage.setTitle(" Vital Asistencia");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void loadScene(Stage stage, String fxml, String title) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent p=fxmlLoader.load();
        stage.setScene(new Scene(p));
        stage.getIcons().add(new Image(App.class.getResourceAsStream("vitalicon.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }

}

