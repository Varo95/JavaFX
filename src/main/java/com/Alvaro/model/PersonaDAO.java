package com.Alvaro.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO extends Persona implements Serializable {

    private static List<Persona> lista;

    public static List<Persona> listarTodas(){
        if(lista==null){
            lista=new ArrayList<>();
            exampledata();
        }
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

    public static boolean removePersona(Persona p){
        boolean result=false;
        if(p!=null && lista.contains(p)){
            lista.remove(p);
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
