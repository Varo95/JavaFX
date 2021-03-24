package com.Alvaro.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement(name="repository")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonaDAO extends Persona implements Serializable {

    @XmlElement(name="persona")
    private static List<Persona> lista;

//-----------------------Singleton-------------------
    private static PersonaDAO instance;
    public static PersonaDAO getInstance(){
        if(instance==null){
            instance=new PersonaDAO();
        }
        return instance;
    }
    private PersonaDAO(){
        lista=new ArrayList<Persona>();
    }
//---------------------------------------------------

    public static List<Persona> listarTodas(){
        exampledata();
        return lista;
    }

    public static boolean addPersona(Persona p){
        boolean result=false;
        if(p!=null && !lista.contains(p)) {
            lista.add(p);
            result=true;
        }
        return result;
    }

    public static boolean loadlista(List<Persona> e){
        boolean result=false;
        if(e!=null && e.size()>=1){
            lista.addAll(e);
            result=true;
        }
        return result;
    }

    public static void exampledata(){
            addPersona(new Persona("1", "Pepe", "Profesor"));
            addPersona(new Persona("2", "Lola", "Estudiante"));
            addPersona(new Persona("3", "Martes", "Tocahuevos"));
            addPersona(new Persona("4", "Iluso", "Estami"));
            addPersona(new Persona("5", "Tio", "Jajajaja"));
    }
}
