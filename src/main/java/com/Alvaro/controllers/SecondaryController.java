package com.Alvaro.controllers;

import java.io.IOException;

import com.Alvaro.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}