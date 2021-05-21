package com.Alvaro.controllers;

import com.Alvaro.utilities.Dialog;
import javafx.fxml.FXML;
import java.awt.Desktop;
import java.net.URI;

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
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                URI uri;
                try {
                    uri = new URI("https://www.paypal.me/Varo95");
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException e) {
                    Dialog.showError("Error URL", "Se ha producido un error", e.toString());
                }
            }
        }
    }

    @FXML
    private void github() {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                URI uri;
                try {
                    uri = new URI("https://github.com/Varo95/JavaFX");
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException e) {
                    Dialog.showError("Error URL", "Se ha producido un error", e.toString());
                }
            }
        }
    }
}
