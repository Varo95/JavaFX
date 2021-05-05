package com.Alvaro.model.beans;

import com.Alvaro.model.interfaces.IWorker;

import java.util.List;

public class Worker implements IWorker.Worker {
    private long id;
    private String name;
    private String surnames;
    private String address;
    private String phone;
    private List<Task> tasks;

    public Worker(long id, String name, String surnames, String address, String phone, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.address = address;
        this.phone = phone;
        this.tasks = tasks;
    }

    public Worker() {
        this(-1, "", "", "", "", null);
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Worker worker = (Worker) o;

        return id == worker.id;
    }

}
