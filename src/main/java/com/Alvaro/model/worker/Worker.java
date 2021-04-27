package com.Alvaro.model.worker;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="worker")
@XmlAccessorType(XmlAccessType.FIELD)
public class Worker implements Serializable {
    private long id;
    private String name;
    private String surnames;
    private String address;
    private String phone;

    public Worker(long id, String name, String surnames, String address, String phone) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.address = address;
        this.phone = phone;
    }
    public Worker(){
        this(-1,"","","","");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }
}
