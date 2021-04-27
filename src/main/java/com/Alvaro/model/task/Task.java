package com.Alvaro.model.task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task implements Serializable {

    private long id;
    private String user_com;
    private String address;
    private LocalDate date;
    private double hours;
    private double ehours;
    private boolean festive;
    private boolean night;
    private long id_worker;

    public Task(long id, String user_com, String address, LocalDate date, double hours, double ehours, boolean festive, boolean night, long id_worker) {
        this.id = id;
        this.user_com = user_com;
        this.address = address;
        this.date = date;
        this.hours = hours;
        this.ehours = ehours;
        this.festive = festive;
        this.night = night;
        this.id_worker = id_worker;
    }

    public Task() {
        this(-1, "", "", null, -1, -1, false, false, -1);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser_com() {
        return user_com;
    }

    public void setUser_com(String user_com) {
        this.user_com = user_com;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getEhours() {
        return ehours;
    }

    public void setEhours(double ehours) {
        this.ehours = ehours;
    }

    public boolean isFestive() {
        return festive;
    }

    public void setFestive(boolean festive) {
        this.festive = festive;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public long getId_worker() {
        return id_worker;
    }

    public void setId_worker(long id_worker) {
        this.id_worker = id_worker;
    }
}
