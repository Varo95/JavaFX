package com.Alvaro.model.DAO;

import com.Alvaro.model.beans.Task;
import com.Alvaro.model.interfaces.ITask;
import com.Alvaro.utilities.ConnectionUtil;
import com.Alvaro.utilities.Dialog;
import com.Alvaro.utilities.XMLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO extends Task implements ITask.TaskDAO {

    private Connection con;

//------------------------------------Consultas preparadas para la base de datos----------------------------------------
    enum queries {
        INSERT("INSERT INTO task(user_com,address,day,hours,festive,night,hours_extra,id_worker) VALUES (?,?,?,?,?,?,?,?)"),
        ALL("SELECT * FROM task"),
        ALLBYWORKER("SELECT * FROM task WHERE id_worker=?"),
        GETBYID("SELECT * FROM task WHERE id=?"),
        FINDBYID("SELECT * FROM task WHERE id IN "), //<-- ojo con esta, hay formas más elegantes
        FINDBYNAME("SELECT * FROM task WHERE name LIKE ?"),
        UPDATE("UPDATE task SET user_com=?,address=?,day=?,hours=?,festive=?,night=?,hours_extra=? WHERE id=?"),
        REMOVE("DELETE FROM task WHERE id=?");
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
    public TaskDAO(long id, String user_com, String address, LocalDate date, double hours, double ehours, boolean festive, boolean night, long id_worker) {
        super(id, user_com, address, date, hours, ehours, festive, night, id_worker);
        try {
            con = ConnectionUtil.connect(XMLUtil.loadFile("connection.xml"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public TaskDAO(long i) {
        this();
        List<Object> params = new ArrayList<>();
        params.add(i);
        try {
            ResultSet rs = ConnectionUtil.execQuery(con, queries.GETBYID.getQ(), params);
            if (rs != null) {
                while (rs.next()) {
                    Task t = instanceBuilder(rs);
                    setId(t.getId());
                    setUser_com(t.getUser_com());
                    setAddress(t.getAddress());
                    setDate(t.getDate());
                    setHours(t.getHours());
                    setFestive(t.isFestive());
                    setNight(t.isNight());
                    setEhours(t.getEhours());
                    setId_worker(t.getId_worker());
                }
            }
        } catch (SQLException ex) {
            Dialog.showError("ERROR", "Error cargando la tarea", ex.toString());
        }
    }

    public TaskDAO() {
        super();
        try {
            con = ConnectionUtil.connect(XMLUtil.loadFile("connection.xml"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
//----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------DAO------------------------------------------------------------
    @Override
    public void remove() {
        TaskDAO.queries q = TaskDAO.queries.REMOVE;
        List<Object> params = new ArrayList<>();
        params.add(getId());
        try {
            int rs = ConnectionUtil.execUpdate(con, q.getQ(), params, false);
        } catch (SQLException ex) {
            Dialog.showError("ERROR", "Error eliminando la tarea", ex.toString());
        }
    }

    @Override
    public void save() {
        TaskDAO.queries q;
        List<Object> params = new ArrayList<>();
        params.add(getUser_com());
        params.add(getAddress());
        params.add(getDate());
        params.add(getHours());
        params.add(isFestive());
        params.add(isNight());
        params.add(getEhours());
        if (getId() == -1) {
            q = TaskDAO.queries.INSERT;
            params.add(getId_worker());
        } else {
            q = TaskDAO.queries.UPDATE;
            params.add(getId());
        }
        try {
            con.setAutoCommit(false);
            int rs = ConnectionUtil.execUpdate(con, q.getQ(), params, (q == TaskDAO.queries.INSERT ? true : false));
            if (q == TaskDAO.queries.INSERT) {
                this.setId(rs);
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            Dialog.showError("ERROR", "Error guardando la tarea", ex.toString());
        }
    }
//----------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------UtilidadesDAO----------------------------------------------------------
    public static Task instanceBuilder(ResultSet rs) {
        Task p = new Task();
        if (rs != null) {
            try {
                p.setId(rs.getInt("id"));
                p.setUser_com(rs.getString("user_com"));
                p.setAddress(rs.getString("address"));
                p.setDate(rs.getDate("day").toLocalDate());
                p.setHours(rs.getDouble("hours"));
                p.setFestive(rs.getBoolean("festive"));
                p.setNight(rs.getBoolean("night"));
                p.setEhours(rs.getDouble("hours_extra"));
                p.setId_worker(rs.getLong("id_worker"));
            } catch (SQLException ex) {
                Dialog.showError("Error SQL", "SQL creando tarea", ex.toString());
            }
        }
        return p;
    }

    public static List<Task> getAllfromWorker(Connection con, long id_worker) {
        WorkerDAO w = new WorkerDAO(id_worker);
        List<Task> result = w.getTasks();
        if (result == null) {
            List<Object> params = new ArrayList<>();
            params.add(id_worker);
            try {
                ResultSet rs = ConnectionUtil.execQuery(con, queries.ALLBYWORKER.getQ(), params);
                if (rs != null) {
                    result = new ArrayList<>();
                    while (rs.next()) {
                        Task t = TaskDAO.instanceBuilder(rs);
                        result.add(t);
                    }
                    w.setTasks(result);
                }
            } catch (SQLException ex) {
                Dialog.showError("ERROR", "Error cargando la tareas de la trabajadora", ex.toString());
            }
        }
        return result;
    }
    //Equals
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
