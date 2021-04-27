package com.Alvaro.model.worker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class WorkerDAO extends Worker {

    private final String getAllWorkers="SELECT name, surnames, address, phone FROM worker";

    public WorkerDAO(long id, String name, String surnames, String address, String phone) {
        super(id, name, surnames, address, phone);
    }

    private static ObservableList<Worker> list;

    public static ObservableList<Worker> listAll(){
        if(list ==null){
            list = FXCollections.observableArrayList(new ArrayList<>());
            exampledata();
        }
        return list;
    }

    public static boolean addWorker(Worker w){
        boolean result=false;
        if(w!=null && !list.contains(w)) {
            list.add(w);
            result=true;
        }
        return result;
    }

    public static boolean loadList(List<Worker> w){
        boolean result=false;
        if(w!=null && w.size()>=1){
            list.addAll(w);
            result=true;
        }
        return result;
    }
    public static boolean removeWorker(Worker p){
        boolean result=false;
        if(p!=null && list.contains(p)){
            list.remove(p);
            result=true;
        }
        return result;
    }
    public static void exampledata(){
        addWorker(new Worker(1,"Conchi", "Urbano Rodriguez", "Villafranca nº 4","649 98 45 27"));
        addWorker(new Worker(2,"Noelia", "Canosa Ugalde", "Pintor Zurbarán nº 4","649 98 45 27"));
        addWorker(new Worker(3,"María Dolores", "Aliaga Quintero", "Pintor Velazquez nº 8","642 74 35 34"));
        addWorker(new Worker(4,"Purificación", "Colmenero Bayon", "Av/ Pablo Iglesias","791 75 27 91"));
        addWorker(new Worker(5,"Eva", "Millan Urbano", "Unknow","697 31 28 51"));
    }
}
