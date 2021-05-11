package com.Alvaro.model.beans;

import com.Alvaro.model.interfaces.ITask;

import java.time.LocalDate;

public class Task implements ITask.Task {

    private long id;
    private String user_com;
    private String address;
    private LocalDate date;
    private double hours;
    private double ehours;
    private boolean festive;
    private boolean night;
    private Worker worker;

    public Task(long id, String user_com, String address, LocalDate date, double hours, double ehours, boolean festive, boolean night, Worker worker) {
        this.id = id;
        this.user_com = user_com;
        this.address = address;
        this.date = date;
        this.hours = hours;
        this.ehours = ehours;
        this.festive = festive;
        this.night = night;
        this.worker = worker;
    }

    public Task() {
        this(-1, "", "", null, -1, -1, false, false, null);
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

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
