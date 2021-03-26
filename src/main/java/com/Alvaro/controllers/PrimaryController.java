package com.Alvaro.controllers;

import com.Alvaro.model.Persona;
import com.Alvaro.model.PersonaDAO;
import com.Alvaro.utilities.RepositoryUtilities;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class PrimaryController {

    @FXML
    private Label dnilabel;
    @FXML
    private Label nombrelabel;
    @FXML
    private Label descripcionlabel;

    @FXML
    private TableView<Persona> tablapersonas;
    @FXML
    private TableColumn<Persona,String> dniColumna;
    @FXML
    private TableColumn<Persona,String> nombreColumna;

    @FXML
    protected void initialize(){
        System.out.println("Cargando...");
        muestraInfo(null);
        configurarTabla();
        //Cargar de la base de datos!
        List<Persona> todas=PersonaDAO.listarTodas();
        tablapersonas.setItems(FXCollections.observableArrayList(todas));
        tablapersonas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            muestraInfo(newValue);
        });
    }

    private void configurarTabla(){
        dniColumna.setCellValueFactory(cadapersona->{
            SimpleStringProperty v=new SimpleStringProperty();
            v.setValue(cadapersona.getValue().getDni());
            return v;
        });
        nombreColumna.setCellValueFactory(cadapersona->{
            SimpleStringProperty v=new SimpleStringProperty();
            v.setValue(cadapersona.getValue().getNombre());
            return v;
        });
    }
    private void muestraInfo(Persona p){
        if(p!=null) {
            dnilabel.setText(p.getDni());
            nombrelabel.setText(p.getNombre());
            descripcionlabel.setText(p.getDescripcion());
        }else{
            dnilabel.setText("");
            nombrelabel.setText("");
            descripcionlabel.setText("");
        }
    }
    @FXML
    private void guardar(){
        RepositoryUtilities r=new RepositoryUtilities();
        r.saveFile("lista.xml");
    }
    @FXML
    private void addPersona(Persona p){
        PersonaDAO.addPersona(p);
    }
    @FXML
    private void removePersona(){
        Persona p=tablapersonas.getSelectionModel().getSelectedItem();
        System.out.println(PersonaDAO.removePersona(p));
    }
}
