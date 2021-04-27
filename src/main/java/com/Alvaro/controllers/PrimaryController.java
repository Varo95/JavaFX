package com.Alvaro.controllers;

import com.Alvaro.App;
import com.Alvaro.model.DataConnection;
import com.Alvaro.model.worker.Worker;
import com.Alvaro.model.worker.WorkerDAO;
import com.Alvaro.utilities.RepositoryUtilities;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

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

    private ObservableList<Worker> list;

    private DataConnection dc;

    @FXML
    protected void initialize() {
        System.out.println("Cargando...");
        dc=new DataConnection("localhost","vital","root","");
        //Desactiva el dragg del splitpanel
        final double pos = 0.34;
        SplitPane.Divider divider = primarySplitPane.getDividers().get(0);
        divider.positionProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) ->
                {
                    divider.setPosition(pos);
                });
        //--------
        showInfo(null);
        configureTable();
        //Cargar de la base de datos!
        list = WorkerDAO.listAll();
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

    private void showInfo(Worker p) {
        if (p != null) {
            addresslabel.setText(p.getAddress());
            phonelabel.setText(p.getPhone());
            tasksbutton.setDisable(false);
            deleteWorker.setDisable(false);
            editWorker.setDisable(false);
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
        }
    }

    @FXML
    private void save_xml() {
        RepositoryUtilities r = new RepositoryUtilities();
        r.saveFile("workers.xml","Worker");
    }

    @FXML
    public void addWorker(Worker w) {
        WorkerDAO.addWorker(w);
    }

    @FXML
    private void removeWorker() {
        Worker p = workerTable.getSelectionModel().getSelectedItem();
        System.out.println(WorkerDAO.removeWorker(p));
    }
    @FXML
    private void show_worker_tasks() throws IOException {
        System.out.println("Cargando Tareas");
        String workerName=null;
        String workerSurname=null;
        if(workerTable.getSelectionModel().selectedItemProperty().get()!=null) {
            workerName = workerTable.getSelectionModel().selectedItemProperty().get().getName();
            workerSurname = workerTable.getSelectionModel().selectedItemProperty().get().getSurnames();
        }
        try {
            if(workerName!=null && workerSurname!=null)
            App.loadScene(new Stage(), "secondary", "Tareas de "+workerName+" "+workerSurname);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void about() {
        System.out.println("Cargando About");
        try {
            App.loadScene(new Stage(), "about", "Sobre la App");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
