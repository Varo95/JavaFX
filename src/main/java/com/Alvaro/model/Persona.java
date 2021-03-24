package com.Alvaro.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="persona")
@XmlAccessorType(XmlAccessType.FIELD)
public class Persona implements Serializable {
    private String dni;
    private String nombre;
    private String descripcion;

    public Persona(String dni, String nombre, String descripcion) {
        if (dni != null && nombre != null && descripcion != null) {
            this.dni = dni;
            this.nombre = nombre;
            this.descripcion = descripcion;
        }
    }

    public Persona() {
        this("", "", "");
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
