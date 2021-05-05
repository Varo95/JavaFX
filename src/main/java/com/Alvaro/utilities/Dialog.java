package com.Alvaro.utilities;

import com.Alvaro.App;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Dialog {

    public static void showError(String title, String header, String description) {
        showDialog(Alert.AlertType.ERROR, title, header, description);
    }

    public static void showWarning(String title, String header, String description) {
        showDialog(Alert.AlertType.WARNING, title, header, description);
    }

    public static void showInformation(String title, String header, String description) {
        showDialog(Alert.AlertType.INFORMATION, title, header, description);
    }

    public static void showConfirmation(String title, String header, String description) {
        showDialog(Alert.AlertType.CONFIRMATION, title, header, description);
    }

    public static void showDialog(Alert.AlertType type, String title, String header, String description) {
        Alert alert = new Alert(type);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(App.class.getResourceAsStream("vitalicon.png")));
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(description);
        alert.showAndWait();
    }
}
