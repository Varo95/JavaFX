package com.Alvaro.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.Alvaro.App;
import com.Alvaro.model.DataConnection;
import com.Alvaro.model.task.Task;
import com.Alvaro.model.task.TaskDAO;
import com.Alvaro.model.worker.Worker;
import com.Alvaro.model.worker.WorkerDAO;
import com.Alvaro.utilities.RepositoryUtilities;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SecondaryController {

    @FXML
    private Label hour_label;
    @FXML
    private Label hextra_label;
    @FXML
    private DatePicker datePicker;
    @FXML
    private CheckBox festive_checkbox;
    @FXML
    private CheckBox night_checkbox;
    @FXML
    private Button addTask;
    @FXML
    private Button editTask;
    @FXML
    private Button deleteTask;
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, String> user_com_colum;
    @FXML
    private TableColumn<Task, String> address_colum;
    /*@FXML
    private SplitPane secondarySplitPane;*/
    private ObservableList<Task> list;

    private DataConnection dc;

    @FXML
    protected void initialize(){
        System.out.println("Cargando tareas...");
        dc=new DataConnection("localhost","vital","root","");
        /*final double pos = 0.40;
        SplitPane.Divider divider = secondarySplitPane.getDividers().get(0);
        divider.positionProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) ->
                {
                    divider.setPosition(pos);
                });*/
        showInfo(null);
        configureTable();
        list = TaskDAO.listAll();
        taskTable.setItems(list);
        taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showInfo(newValue);
        });
    }

    private void configureTable() {
        user_com_colum.setCellValueFactory(eachWorker -> {
            SimpleStringProperty v = new SimpleStringProperty();
            v.setValue(eachWorker.getValue().getUser_com());
            return v;
        });
        address_colum.setCellValueFactory(eachWorker -> {
            SimpleStringProperty v = new SimpleStringProperty();
            v.setValue(eachWorker.getValue().getAddress());
            return v;
        });
    }

    private void showInfo(Task t){
        datePicker=new DatePicker();
        if (t != null) {
            hour_label.setText(t.getHours()+"");
            hextra_label.setText(t.getEhours()+"");
            datePicker.setValue(t.getDate());
            datePicker.setPromptText(t.getDate().toString());
            deleteTask.setDisable(false);
            editTask.setDisable(false);
            festive_checkbox.setSelected(t.isFestive());
            night_checkbox.setSelected(t.isNight());
        } else {
            hour_label.setText("");
            hextra_label.setText("");
            datePicker.setValue(null);
            deleteTask.setDisable(true);
            editTask.setDisable(true);
            festive_checkbox.setSelected(false);
            night_checkbox.setSelected(false);
        }
    }

    @FXML
    public void addTask(Task t) {
        //TaskDAO.addTask(t);
    }

    @FXML
    public void asdfg(){}

    @FXML
    private void removeTask() {
        Task t = taskTable.getSelectionModel().getSelectedItem();
        //System.out.println(TaskDAO.removeTask(t));
    }

    @FXML
    private void save_xml() {
        RepositoryUtilities r = new RepositoryUtilities();
        r.saveFile("tasks.xml","Task");
    }
}