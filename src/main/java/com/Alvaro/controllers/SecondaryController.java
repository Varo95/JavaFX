package com.Alvaro.controllers;

import com.Alvaro.App;
import com.Alvaro.model.DAO.TaskDAO;
import com.Alvaro.model.DAO.WorkerDAO;
import com.Alvaro.model.beans.DataConnection;
import com.Alvaro.model.beans.Task;
import com.Alvaro.utilities.Dialog;
import com.Alvaro.utilities.XMLUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SecondaryController extends Controllers {

    @FXML
    private Label hour_label;
    @FXML
    private Label hextra_label;
    @FXML
    private Label date_label;
    @FXML
    private CheckBox festive_checkbox;
    @FXML
    private CheckBox night_checkbox;
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

    private ObservableList<Task> list;

    private static WorkerDAO worker;

    @FXML
    protected void initialize() {
        System.out.println("Cargando vista secundaria...");
        DataConnection dc = XMLUtil.loadFile("connection.xml");
        showInfo(null);
        configureTable();
        list = FXCollections.observableArrayList(worker.getTasks());
        taskTable.setItems(list);
        taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showInfo(newValue));
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

    private void showInfo(Task t) {
        if (t != null) {
            hour_label.setText(t.getHours() + "");
            hextra_label.setText(t.getEhours() + "");
            date_label.setText(t.getDate().getDayOfMonth() + "/" + t.getDate().getMonthValue() + "/" + t.getDate().getYear());
            deleteTask.setDisable(false);
            editTask.setDisable(false);
            festive_checkbox.setSelected(t.isFestive());
            night_checkbox.setSelected(t.isNight());
        } else {
            hour_label.setText("");
            hextra_label.setText("");
            date_label.setText("");
            deleteTask.setDisable(true);
            editTask.setDisable(true);
            festive_checkbox.setSelected(false);
            night_checkbox.setSelected(false);
        }
    }

    @FXML
    public void addTask() {
        try {
            TaskDAO t = new TaskDAO();
            t.setWorker(worker);
            TaskController.setTask(t);
            App.loadScene(new Stage(), "task", "Añadiendo Tarea");
            if (!t.getUser_com().equals("")) {
                list.add(t);
            }
            taskTable.refresh();
        } catch (IOException e) {
            Dialog.showError("Error en la vista", "Error durante la carga de la vista", "No se pudo cargar la vista de añadir");
        }
    }

    @FXML
    public void editTask() {
        try {
            TaskDAO t = new TaskDAO(taskTable.getSelectionModel().getSelectedItem().getId());
            TaskController.setTask(t);
            App.loadScene(new Stage(), "task", "Editando Tarea");
            for (Task ta : list) {
                if (ta.getId() == t.getId()) {
                    ta.setUser_com(t.getUser_com());
                    ta.setId(t.getId());
                    ta.setUser_com(t.getUser_com());
                    ta.setAddress(t.getAddress());
                    ta.setDate(t.getDate());
                    ta.setHours(t.getHours());
                    ta.setFestive(t.isFestive());
                    ta.setNight(t.isNight());
                    ta.setEhours(t.getEhours());
                    ta.setWorker(t.getWorker());
                }
            }
            taskTable.refresh();
        } catch (IOException e) {
            Dialog.showError("Error en la vista", "Error durante la carga de la vista", "No se pudo cargar la vista de editar");
        }
    }

    @FXML
    private void removeTask() {
        boolean confirm = Dialog.showConfirmation("Aviso", "Está apunto de borrar una tarea", "Este cambio no se puede deshacer, ¿Está seguro?");
        if (confirm) {
            TaskDAO t = new TaskDAO(taskTable.getSelectionModel().getSelectedItem().getId());
            t.remove();
            list.remove(taskTable.getSelectionModel().getSelectedItem());
        }
    }

    public static void setWorker(WorkerDAO worker1) {
        worker = Objects.requireNonNullElseGet(worker1, () -> new WorkerDAO(-1));
    }

}