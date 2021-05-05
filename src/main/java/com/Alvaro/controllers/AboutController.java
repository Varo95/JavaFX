package com.Alvaro.controllers;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URISyntaxException;

public class AboutController {

    @FXML
    protected void initialize() {

    }

    @FXML
    protected void paypal() {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.net.URI uri = null;
                try {
                    uri = new java.net.URI("https://www.paypal.me/Varo95");
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void github() {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.net.URI uri = null;
                try {
                    uri = new java.net.URI("https://github.com/Varo95/JavaFX");
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
