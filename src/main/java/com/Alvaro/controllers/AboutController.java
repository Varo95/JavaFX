package com.Alvaro.controllers;

import com.Alvaro.utilities.Dialog;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Controlador de la vista About
 * El texto está en la propia vista debido a que son los datos del desarrollador.
 */
public class AboutController extends Controllers {

    @FXML
    protected void initialize() {
        System.out.println("Cargando vista de sobre la app...");
    }

    @FXML
    protected void paypal() {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.net.URI uri;
                try {
                    uri = new java.net.URI("https://www.paypal.me/Varo95");
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException e) {
                    Dialog.showError("Error URL", "Se ha producido un error", e.toString());
                }
            }
        }
    }

    @FXML
    private void github() {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.net.URI uri;
                try {
                    uri = new java.net.URI("https://github.com/Varo95/JavaFX");
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException e) {
                    Dialog.showError("Error URL", "Se ha producido un error", e.toString());
                }
            }
        }
    }
}
