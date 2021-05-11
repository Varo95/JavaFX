package com.Alvaro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML("primary"), 640, 480);
        //scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("vitalicon.png"))));
        stage.setTitle(" Vital Asistencia");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void loadScene(Stage stage, String fxml, String title) throws IOException {
        Parent p = loadFXML(fxml);
        Scene s = new Scene(p);
        //s.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(s);
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("vitalicon.png"))));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.showAndWait();
    }

    public static void closeScene(Stage stage) {
        stage.close();
    }

    public static void main(String[] args) {
        launch();
    }

}

