package com.Alvaro.model.task;

import com.Alvaro.model.worker.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO extends Task {

    public TaskDAO(long id, String user_com, String address, LocalDate date, double hours, double ehours , boolean festive, boolean night, long id_worker) {
        super(id, user_com, address, date, hours, ehours, festive, night, id_worker);
    }

    private static ObservableList<Task> list;

    public static ObservableList<Task> listAll(){
        if(list ==null){
            list = FXCollections.observableArrayList(new ArrayList<>());
            exampledata();
        }
        return list;
    }

    public static boolean addTask(Task t){
        boolean result=false;
        if(t!=null && !list.contains(t)) {
            list.add(t);
            result=true;
        }
        return result;
    }

    public static boolean loadList(List<Task> t){
        boolean result=false;
        if(t!=null && t.size()>=1){
            list.addAll(t);
            result=true;
        }
        return result;
    }
    public static boolean removeTask(Task t){
        boolean result=false;
        if(t!=null && list.contains(t)){
            list.remove(t);
            result=true;
        }
        return result;
    }

    public static void exampledata(){
        addTask(new Task(1,"Holiwi","Villafranca", LocalDate.now(),2.0,0,false,false,-1));
        addTask(new Task(2,"Holiwi","Villafranca", LocalDate.now(),2.0,0,true,true,-1));
    }
}
