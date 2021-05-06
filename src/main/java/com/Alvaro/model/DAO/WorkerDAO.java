package com.Alvaro.model.DAO;

import com.Alvaro.model.beans.Task;
import com.Alvaro.model.beans.Worker;
import com.Alvaro.model.interfaces.IWorker;
import com.Alvaro.utilities.ConnectionUtil;
import com.Alvaro.utilities.Dialog;
import com.Alvaro.utilities.XMLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAO extends Worker implements IWorker.WorkerDAO {

    private static Connection con;

//------------------------------------Consultas preparadas para la base de datos----------------------------------------
    enum queries {
        INSERT("INSERT INTO worker (name,surnames, address, phone) VALUES (?,?,?,?)"),
        ALL("SELECT * FROM worker"),
        GETBYID("SELECT * FROM worker WHERE id=?"),
        UPDATE("UPDATE worker SET name = ?, surnames = ?, address = ?, phone = ? WHERE id = ?"),
        //Funcion resumen de horas
        GETRESUMEHOURS("SELECT task.day, task.hours, task.hours_extra, task.festive, task.night FROM task " +
                "WHERE id_worker=? AND task.day BETWEEN ? AND ?"),
        //-----
        REMOVE("DELETE FROM worker WHERE id=?");
        private String q;

        queries(String q) {
            this.q = q;
        }

        public String getQ() {
            return this.q;
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------Constructores-------------------------------------------------------
    public WorkerDAO(long id, String name, String surnames, String address, String phone, List<Task> tasks) {
        super(id, name, surnames, address, phone, tasks);
        try {
            con = ConnectionUtil.connect(XMLUtil.loadFile("connection.xml"));
        } catch (SQLException ex) {
            Dialog.showError("ERROR", "Error cargando la trabajadora", ex.toString());
        }
    }

    public WorkerDAO(long id) {
        this();
        List<Object> params = new ArrayList<>();
        params.add(id);
        try {
            ResultSet rs = ConnectionUtil.execQuery(con, WorkerDAO.queries.GETBYID.getQ(), params);
            if (rs != null) {
                while (rs.next()) {
                    Worker w = instanceBuilder(rs);
                    setId(w.getId());
                    setName(w.getName());
                    setSurnames(w.getSurnames());
                    setAddress(w.getAddress());
                    setPhone(w.getPhone());
                }
            }
        } catch (SQLException ex) {
            Dialog.showError("ERROR", "Error cargando la trabajadora", ex.toString());
        }
    }

    public WorkerDAO() {
        super();
        try {
            con = ConnectionUtil.connect(XMLUtil.loadFile("connection.xml"));
        } catch (SQLException ex) {
            Dialog.showError("ERROR", "Error cargando la trabajadora", ex.toString());
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------DAO------------------------------------------------------------
    @Override
    public void remove() {
        queries q = queries.REMOVE;
        List<Object> params = new ArrayList<>();
        params.add(getId());
        try {
            int rs = ConnectionUtil.execUpdate(con, q.getQ(), params, false);
        } catch (SQLException ex) {
            Dialog.showError("ERROR", "Error eliminando trabajadora", ex.toString());
        }
    }

    @Override
    public void save() {
        queries q;
        List<Object> params = new ArrayList<>();
        params.add(getName());
        params.add(getSurnames());
        params.add(getAddress());
        params.add(getPhone());
        if (getId() == -1) {
            q = queries.INSERT;
        } else {
            q = queries.UPDATE;
            params.add(getId());
        }
        try {
            con.setAutoCommit(false);
            int rs = ConnectionUtil.execUpdate(con, q.getQ(), params, (q == queries.INSERT ? true : false));
            if (q == WorkerDAO.queries.INSERT) {
                this.setId(rs);
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            Dialog.showError("ERROR", "Error guardando trabajadora", ex.toString());
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------UtilidadesDAO----------------------------------------------------------
    public static Worker instanceBuilder(ResultSet rs) {
        Worker w = new Worker();
        if (rs != null) {
            try {
                w.setId(rs.getLong("id"));
                w.setName(rs.getString("name"));
                w.setSurnames(rs.getString("surnames"));
                w.setAddress(rs.getString("address"));
                w.setPhone(rs.getString("phone"));
            } catch (SQLException ex) {
                Dialog.showError("Error SQL", "SQL creando trabajadora", ex.toString());
            }
        }
        return w;
    }

    private static List<Worker> list;

    public static List<Worker> listAll(Connection con) {
        if (list == null) {
            queries q = queries.ALL;
            try {
                ResultSet rs = ConnectionUtil.execQuery(con, q.getQ(), null);
                list = new ArrayList<>();
                while (rs.next()) {
                    Worker w = new Worker();
                    w.setId(rs.getLong(1));
                    w.setName(rs.getString(2));
                    w.setSurnames(rs.getString(3));
                    w.setAddress(rs.getString(4));
                    w.setPhone(rs.getString(5));
                    list.add(w);
                }
            } catch (SQLException ex) {
                Dialog.showError("Error SQL", "SQL cargando trabajadoras", ex.toString());
            }
        }
        return list;
    }

    public static List<Task> getResumeHours(Connection con, long id_worker, LocalDate dateini, LocalDate dateend){
        List<Task> result=null;
        List<Object> params = new ArrayList<>();
        params.add(id_worker);
        params.add(dateini);
        params.add(dateend);
        queries q = queries.GETRESUMEHOURS;
        try{
            ResultSet rs = ConnectionUtil.execQuery(con, q.getQ(),params);
            result=new ArrayList<>();
            while(rs.next()){
                Task t = new Task();
                t.setDate(rs.getDate(1).toLocalDate());
                t.setHours(rs.getDouble(2));
                t.setEhours(rs.getDouble(3));
                t.setFestive(rs.getBoolean(4));
                t.setNight(rs.getBoolean(5));
                result.add(t);
            }
        }catch (SQLException ex){
            Dialog.showError("Error SQL", "SQL cargando el resumen", ex.toString());
        }
        return result;
    }

    //Equals
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
