package com.Alvaro.controllers;

import com.Alvaro.App;
import com.Alvaro.model.DAO.TaskDAO;
import com.Alvaro.utilities.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskController extends Controllers {

    @FXML
    private TextField taskuser_com;
    @FXML
    private TextField taskaddress;
    @FXML
    private DatePicker taskdatepicker;
    @FXML
    private TextField taskhours;
    @FXML
    private CheckBox festivecb;
    @FXML
    private CheckBox nightcb;
    @FXML
    private TextField taske_hours;
    @FXML
    private Button addbutton;

    private boolean editingtask;


    private static TaskDAO task;

    @FXML
    protected void initialize() {
        if (task.getId() != -1) {
            addbutton.setText("Actualizar");
            taskuser_com.setText(task.getUser_com());
            taskaddress.setText(task.getAddress());
            taskdatepicker.setValue(task.getDate());
            taskhours.setText(task.getHours() + "");
            festivecb.setSelected(task.isFestive());
            nightcb.setSelected(task.isNight());
            taske_hours.setText(task.getEhours() + "");
            editingtask = false;
        } else {
            addbutton.setText("Añadir");
            editingtask = true;
        }
    }

    @FXML
    private void addOrEditTask() {
        if (!taskuser_com.getText().equals("") && !taskuser_com.getText().equals(task.getUser_com()))
            task.setUser_com(taskuser_com.getText());
        if (!taskaddress.getText().equals("") && !taskaddress.getText().equals(task.getAddress()))
            task.setAddress(taskaddress.getText());
        if (taskdatepicker.getValue() != null && !taskdatepicker.getValue().equals(task.getDate()))
            task.setDate(taskdatepicker.getValue());
        if (!taskhours.getText().equals("") && Double.parseDouble(taskhours.getText()) != task.getHours())
            task.setHours(Double.parseDouble(taskhours.getText()));
        if (festivecb.isSelected() != task.isFestive())
            task.setFestive(festivecb.isSelected());
        if (nightcb.isSelected() != task.isNight())
            task.setNight(nightcb.isSelected());
        if (!taske_hours.getText().equals("") && Double.parseDouble(taske_hours.getText()) != task.getEhours())
            task.setEhours(Double.parseDouble(taske_hours.getText()));
        if (!taskuser_com.getText().equals("") && !taskaddress.getText().equals("") && taskdatepicker.getValue() != null && !taskhours.getText().equals("") && !taske_hours.getText().equals("")) {
            task.save();
            if (editingtask)
                Dialog.showInformation("", "Tarea añadida", "Se ha añadido correctamente a la base de datos");
            else
                Dialog.showInformation("", "Tarea editada", "Se ha editado correctamente en la base de datos");
            App.closeScene((Stage) addbutton.getScene().getWindow());
        } else if (editingtask)
            Dialog.showWarning("Imposible editar", "Error al editar", "¡No puedes dejar ningún campo en blanco!");
        else
            Dialog.showWarning("Imposible añadir", "Error al añadir", "¡No puedes dejar ningún campo en blanco!");
    }

    public static void setTask(TaskDAO t) {
        task = t;
    }
}
