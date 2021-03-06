package com.Alvaro.controllers;

import com.Alvaro.App;
import com.Alvaro.model.DAO.WorkerDAO;
import com.Alvaro.model.beans.DataConnection;
import com.Alvaro.model.beans.Task;
import com.Alvaro.model.beans.Worker;
import com.Alvaro.utilities.ConnectionUtil;
import com.Alvaro.utilities.Dialog;
import com.Alvaro.utilities.XMLUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador de la vista principal, donde cargan todas las trabajadoras
 */
public class PrimaryController extends Controllers {

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
    private Label resumelabel;
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
        System.out.println("Cargando vista principal...");
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
            Dialog.showError("Error BBDD", "Error al establecer la conexi??n", e.toString());
        }
        //Cargar de la base de datos!
        list = FXCollections.observableArrayList(WorkerDAO.listAll(con));
        workerTable.setItems(list);
        workerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showInfo(newValue));
    }

    /**
     * Carga la informaci??n en la tabla de la izquierda
     */
    void configureTable() {
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

    /**
     * Muestra la informaci??n detallada de un elemento seleccionado de la tabla
     *
     * @param w trabajadora a mostrar informaci??n adicional
     */
    private void showInfo(Worker w) {
        if (w != null) {
            addresslabel.setText(w.getAddress());
            phonelabel.setText(w.getPhone());
            tasksbutton.setDisable(false);
            deleteWorker.setDisable(false);
            editWorker.setDisable(false);
            datepickerini.setDisable(false);
            datepickerend.setDisable(true);
            resumebutton.setDisable(true);
            datepickerini.valueProperty().addListener((ov, oldValue, newValue) -> {
                datepickerend.setValue(newValue.plusDays(30));
                datepickerend.setDisable(false);
                datepickerend.setDayCellFactory(picker -> new DateCell() {
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.getDayOfYear() < datepickerini.getValue().getDayOfYear())
                            setDisable(true);
                    }
                });
                resumebutton.setDisable(false);
            });
            resumelabel.setText("??Resumen no actualizado!");
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
            resumelabel.setText("");
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
            App.loadScene(new Stage(), "worker", "A??adiendo trabajadora");
            if (!w.getName().equals("")) {
                list.add(w);
            }
            workerTable.refresh();
        } catch (IOException e) {
            Dialog.showError("Error en la vista", "No se pudo cargar la vista de a??adir trabajadora", e.toString());
        }
    }

    @FXML
    private void editWorker() {
        if (workerTable.getSelectionModel().getSelectedItem() != null) {
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
    }

    @FXML
    private void removeWorker() {
        if (workerTable.getSelectionModel().getSelectedItem() != null) {
            boolean confirm = Dialog.showConfirmation("Aviso", "Est?? apunto de borrar una trabajadora", "Este cambio no se puede deshacer, se borrar??n todas las tareas asignadas a ella, ??Est?? seguro?");
            if (confirm) {
                WorkerDAO p = new WorkerDAO(workerTable.getSelectionModel().getSelectedItem().getId());
                p.remove();
                list.remove(workerTable.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    private void show_worker_tasks() {
        if (workerTable.getSelectionModel().getSelectedItem() != null) {
            String workerName = workerTable.getSelectionModel().selectedItemProperty().get().getName();
            String workerSurname = workerTable.getSelectionModel().selectedItemProperty().get().getSurnames();
            try {
                SecondaryController.setWorker(new WorkerDAO(workerTable.getSelectionModel().getSelectedItem().getId()));
                App.loadScene(new Stage(), "secondary", "Tareas de " + workerName + " " + workerSurname);
            } catch (IOException e) {
                Dialog.showError("Error en la vista", "No se pudo cargar la vista de tareas trabajadora", e.toString());
            }
        }
    }

    @FXML
    private void resume() {
        if (workerTable.getSelectionModel().getSelectedItem() != null) {
            long id_worker = workerTable.getSelectionModel().getSelectedItem().getId();
            LocalDate ini = datepickerini.getValue();
            LocalDate end = datepickerend.getValue();
            List<Task> resumed = WorkerDAO.getResumeHours(con, id_worker, ini, end);
            double office_hours = 0;
            double festive_hours = 0;
            double night_hours = 0;
            double festive_and_night_hours = 0;
            double extra_hours = 0;
            for (Task t : resumed) {
                if (t.isFestive() && t.isNight()) {
                    festive_and_night_hours += t.getHours();
                } else if (t.isFestive()) {
                    festive_hours += t.getHours();
                } else if (t.isNight()) {
                    night_hours += t.getHours();
                } else {
                    office_hours += t.getHours();
                }
                extra_hours += t.getEhours();
            }
            String workerName = workerTable.getSelectionModel().selectedItemProperty().get().getName();
            //String workerSurnames = workerTable.getSelectionModel().selectedItemProperty().get().getSurnames();
            resumelabel.setText("Resumen de " + workerName);
            hnormal_label.setText(office_hours + " horas");
            hfestive_label.setText(festive_hours + " horas");
            hnight_label.setText(night_hours + " horas");
            hnfestives_label.setText(festive_and_night_hours + " horas");
            hextras_label.setText(extra_hours + " horas");
        }
    }

    @FXML
    private void about() {
        try {
            App.loadScene(new Stage(), "about", "Sobre la App");
        } catch (IOException e) {
            Dialog.showError("Error en la vista", "No se pudo cargar la vista de sobre la app", e.toString());
        }
    }

    private void splitpanelnotdraggable() {
        final double pos = 0.34;
        primarySplitPane.getDividers().get(0).positionProperty().addListener(
                (observable, oldValue, newValue) ->
                        primarySplitPane.setDividerPosition(0,pos));
    }
}
