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

    private boolean addingtask;

    private static TaskDAO task;

    @FXML
    protected void initialize() {
        taskhours.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d*\\.?\\d*")) {
                taskhours.setText(newValue.replaceAll("[^\\d*.?]", ""));
            }
        });
        taske_hours.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d*\\.?\\d*")) {
                taske_hours.setText(newValue.replaceAll("[^\\d*.?]", ""));
            }
        });
        taskdatepicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            if(newValue.getDayOfWeek().getValue()==7){
                festivecb.setSelected(true);
                festivecb.setDisable(true);
            }else{
                festivecb.setSelected(false);
                festivecb.setDisable(false);
            }
        });
        if (task.getId() != -1) {
            addbutton.setText("Actualizar");
            taskuser_com.setText(task.getUser_com());
            taskaddress.setText(task.getAddress());
            taskdatepicker.setValue(task.getDate());
            taskhours.setText(task.getHours() + "");
            festivecb.setSelected(task.isFestive());
            nightcb.setSelected(task.isNight());
            taske_hours.setText(task.getEhours() + "");
            addingtask = false;
        } else {
            addbutton.setText("A??adir");
            addingtask = true;
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
            if (addingtask)
                Dialog.showInformation("", "Tarea a??adida", "Se ha a??adido correctamente a la base de datos");
            else
                Dialog.showInformation("", "Tarea editada", "Se ha editado correctamente en la base de datos");
            App.closeScene((Stage) addbutton.getScene().getWindow());
        } else if (addingtask)
            Dialog.showWarning("Imposible a??adir", "Error al a??adir", "??No puedes dejar ning??n campo en blanco!");
        else
            Dialog.showWarning("Imposible editar", "Error al editar", "??No puedes dejar ning??n campo en blanco!");
    }

    public static void setTask(TaskDAO t) {
        task = t;
    }
}
