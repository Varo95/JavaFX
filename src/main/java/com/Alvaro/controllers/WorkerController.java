package com.Alvaro.controllers;

import com.Alvaro.App;
import com.Alvaro.model.DAO.WorkerDAO;
import com.Alvaro.utilities.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WorkerController extends Controllers {
    @FXML
    private TextField workername;
    @FXML
    private TextField workersurnames;
    @FXML
    private TextField workeraddress;
    @FXML
    private TextField workerphone;
    @FXML
    private Button addbutton;

    private static WorkerDAO worker;

    private boolean editingworker;

    @FXML
    protected void initialize() {
        workerphone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                workerphone.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if (worker.getId() != -1) {
            addbutton.setText("Actualizar");
            workername.setText(worker.getName());
            workersurnames.setText(worker.getSurnames());
            workeraddress.setText(worker.getAddress());
            workerphone.setText(worker.getPhone());
            editingworker = false;
        } else {
            addbutton.setText("Añadir");
            editingworker = true;
        }
    }

    @FXML
    private void addOrEditWorker() {
        if (!workername.getText().equals("") && !workername.getText().equals(worker.getName()))
            worker.setName(workername.getText());
        if (!workersurnames.getText().equals("") && !workersurnames.getText().equals(worker.getSurnames()))
            worker.setSurnames(workersurnames.getText());
        if (!workeraddress.getText().equals("") && !workeraddress.getText().equals(worker.getAddress()))
            worker.setAddress(workeraddress.getText());
        if (!workerphone.getText().equals("") && !workerphone.getText().equals(worker.getPhone()))
            worker.setPhone(workerphone.getText());
        if (!workername.getText().equals("") && !workersurnames.getText().equals("") && !workeraddress.getText().equals("") && !workerphone.getText().equals("")) {
            worker.save();
            if (editingworker)
                Dialog.showInformation("", "Trabajadora añadida", "Se ha añadido correctamente a la base de datos");
            else
                Dialog.showInformation("", "Trabajadora editada", "Se ha editado correctamente en la base de datos");
            App.closeScene((Stage) addbutton.getScene().getWindow());
        } else if (editingworker)
            Dialog.showWarning("Imposible editar", "Error al editar", "¡No puedes dejar ningún campo en blanco!");
        else
            Dialog.showWarning("Imposible añadir", "Error al añadir", "¡No puedes dejar ningún campo en blanco!");
    }

    public static void setWorker(WorkerDAO w) {
        worker = w;
    }
}
