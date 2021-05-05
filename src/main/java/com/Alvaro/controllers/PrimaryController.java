package com.Alvaro.controllers;

import com.Alvaro.App;
import com.Alvaro.model.DAO.TaskDAO;
import com.Alvaro.model.DAO.WorkerDAO;
import com.Alvaro.model.beans.DataConnection;
import com.Alvaro.model.beans.Task;
import com.Alvaro.model.beans.Worker;
import com.Alvaro.utilities.ConnectionUtil;
import com.Alvaro.utilities.Dialog;
import com.Alvaro.utilities.XMLUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PrimaryController {

    @FXML
    private Label addresslabel;
    @FXML
    private Label phonelabel;
    @FXML
    private Label hnormal_label;
    @FXML
    private Label hfestive_label;
    @FXML
    private Label hnight_label;
    @FXML
    private Label hnfestives_label;
    @FXML
    private Label hextras_label;
    @FXML
    private Button tasksbutton;
    @FXML
    private Button editWorker;
    @FXML
    private Button deleteWorker;
    @FXML
    private TableView<Worker> workerTable;
    @FXML
    private TableColumn<Worker, String> surname_colum;
    @FXML
    private TableColumn<Worker, String> name_colum;
    @FXML
    private SplitPane primarySplitPane;
    @FXML
    private DatePicker datepickerini;
    @FXML
    private DatePicker datepickerend;
    @FXML
    private Button resumebutton;

    private ObservableList<Worker> list;

    private DataConnection dc;
    private Connection con;

    @FXML
    protected void initialize() {
        System.out.println("Cargando...");
        dc = XMLUtil.loadFile("connection.xml");
        //Desactiva el dragg del splitpanel
        splitpanelnotdraggable();
        //--------
        showInfo(null);
        configureTable();
        con = null;
        try {
            con = ConnectionUtil.connect(dc);
        } catch (SQLException e) {
            Dialog.showError("Error BBDD", "Error al establecer la conexión", e.toString());
        }
        //Cargar de la base de datos!
        list = FXCollections.observableArrayList(WorkerDAO.listAll(con));
        workerTable.setItems(list);
        workerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showInfo(newValue);
        });
    }

    private void configureTable() {
        name_colum.setCellValueFactory(eachWorker -> {
            SimpleStringProperty v = new SimpleStringProperty();
            v.setValue(eachWorker.getValue().getName());
            return v;
        });
        surname_colum.setCellValueFactory(eachWorker -> {
            SimpleStringProperty v = new SimpleStringProperty();
            v.setValue(eachWorker.getValue().getSurnames());
            return v;
        });
    }

    private void showInfo(Worker w) {
        if (w != null) {
            addresslabel.setText(w.getAddress());
            phonelabel.setText(w.getPhone());
            tasksbutton.setDisable(false);
            deleteWorker.setDisable(false);
            editWorker.setDisable(false);
            datepickerini.setDisable(false);
            datepickerend.setDisable(false);
            resumebutton.setDisable(false);
            //Consultas a las horas del mes que ha realizado?
            /*hnormal_label.setText();
            hfestive_label.setText();
            hnight_label.setText();
            hnfestives_label.setText();
            hextras_label.setText();*/
        } else {
            addresslabel.setText("");
            phonelabel.setText("");
            hnormal_label.setText("");
            hfestive_label.setText("");
            hnight_label.setText("");
            hnfestives_label.setText("");
            hextras_label.setText("");
            tasksbutton.setDisable(true);
            deleteWorker.setDisable(true);
            editWorker.setDisable(true);
            datepickerini.setDisable(true);
            datepickerend.setDisable(true);
            resumebutton.setDisable(true);
        }

    }

    @FXML
    private void save_xml() {
        XMLUtil.saveFile("connection.xml", dc);
    }

    @FXML
    private void addWorker() {
        try {
            WorkerDAO w = new WorkerDAO();
            WorkerController.setWorker(w);
            App.loadScene(new Stage(), "worker", "Añadiendo trabajadora");
            if (!w.getName().equals("")) {
                list.add(w);
            }
            workerTable.refresh();
        } catch (IOException e) {
            Dialog.showError("Error en la vista", "No se pudo cargar la vista de añadir trabajadora", e.toString());
        }
    }

    @FXML
    private void editWorker() {
        try {
            WorkerDAO w = new WorkerDAO(workerTable.getSelectionModel().getSelectedItem().getId());
            WorkerController.setWorker(w);
            App.loadScene(new Stage(), "worker", "Editando trabajadora");
            for (Worker wor : list) {
                if (wor.getId() == w.getId()) {
                    wor.setName(w.getName());
                    wor.setSurnames(w.getSurnames());
                    wor.setAddress(w.getAddress());
                    wor.setPhone(w.getPhone());
                }
            }
            workerTable.refresh();
        } catch (IOException e) {
            Dialog.showError("Error en la vista", "No se pudo cargar la vista de editar trabajadora", e.toString());
        }
    }

    @FXML
    private void removeWorker() {
        WorkerDAO p = new WorkerDAO(workerTable.getSelectionModel().getSelectedItem().getId());
        p.remove();
        list.remove(workerTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void show_worker_tasks() {
        List<Task> tasks = workerTable.getSelectionModel().getSelectedItem().getTasks();
        if (tasks == null) {
            workerTable.getSelectionModel().getSelectedItem().setTasks(TaskDAO.getAllfromWorker(con, workerTable.getSelectionModel().getSelectedItem().getId()));
        }
        String workerName = workerTable.getSelectionModel().selectedItemProperty().get().getName();
        String workerSurname = workerTable.getSelectionModel().selectedItemProperty().get().getSurnames();
        try {
            SecondaryController.setId_worker(workerTable.getSelectionModel().getSelectedItem().getId());
            App.loadScene(new Stage(), "secondary", "Tareas de " + workerName + " " + workerSurname);
        } catch (IOException e) {
            Dialog.showError("Error en la vista", "No se pudo cargar la vista de tareas trabajadora", e.toString());
        }
    }

    @FXML
    private void about() {
        System.out.println("Cargando About");
        try {
            App.loadScene(new Stage(), "about", "Sobre la App");
        } catch (IOException e) {
            Dialog.showError("Error en la vista", "No se pudo cargar la vista de sobre la app", e.toString());
        }
    }

    private void splitpanelnotdraggable() {
        final double pos = 0.34;
        SplitPane.Divider divider = primarySplitPane.getDividers().get(0);
        divider.positionProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) ->
                {
                    divider.setPosition(pos);
                });
    }
}
