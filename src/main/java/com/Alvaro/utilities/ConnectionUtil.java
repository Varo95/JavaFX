package com.Alvaro.utilities;

import com.Alvaro.model.beans.DataConnection;

import java.sql.*;
import java.util.List;

/**
 * Utilidades de la conexión
 */
public class ConnectionUtil {

    private static Connection con;

    public static Connection connect(DataConnection c) throws SQLException {
        if (c == null) {
            return null;
        }
        if (con == null) {
            org.postgresql.Driver a = new org.postgresql.Driver();
            org.h2.Driver b = new org.h2.Driver();
            con = switch (c.getType()) {
                case "mySQL" -> DriverManager.getConnection("jdbc:mysql://" + c.getHost() + "/" + c.getDb(), c.getUser(), c.getPassword());
                case "postgreSQL" -> DriverManager.getConnection("jdbc:postgresql://" + c.getHost() + "/" + c.getDb(), c.getUser(), c.getPassword());
                case "H2" -> DriverManager.getConnection("jdbc:h2:./" + c.getDb() + ";USER=" + c.getUser() + ";PASSWORD=" + c.getPassword());
                default -> null;
            };
        }
        checkStructure(con, c.getType());
        return con;
    }

    /**
     * Esta función ejecuta consultas de un PreparedStatement
     *
     * @param con    Conexión a la BBDD
     * @param q      Sentencia a ejecutar
     * @param params parámetros de la sentencia
     * @return El último id insertado ó el número de filas afectadas de la consulta
     * @throws SQLException en caso de que no se pueda ejecutar bien la sentencia
     */
    public static ResultSet execQuery(Connection con, String q, List<Object> params) throws SQLException {
        ResultSet result;
        if (con != null) {
            PreparedStatement ps = prepareQuery(con, q, params);
            result = ps.executeQuery();
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Este método devuelve PreparedStatements a partir de una lista de objetos que se le pasan como parámetro
     * Esta lista debe estar en orden
     *
     * @param con    Conexión de la que preparar la sentencia
     * @param q      Sentencia con "?"
     * @param params parámetros a sustituir las "?"
     * @return PreparedStatement ya listo
     * @throws SQLException en caso de que haya más parámetros de los debidos
     */
    private static PreparedStatement prepareQuery(Connection con, String q, List<Object> params) throws SQLException {
        PreparedStatement ps;
        ps = con.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
        if (params != null) {
            int i = 1;
            for (Object o : params) {
                ps.setObject(i++, o);
            }
        }
        return ps;
    }

    /**
     * Esta función ejecuta actualizaciones de un PreparedStatement
     *
     * @param con    Conexión a la BBDD
     * @param q      Sentencia a ejecutar
     * @param params parámetros de la sentencia
     * @param insert Si es true devuelve el último id insertado
     * @return El último id insertado ó el número de filas afectadas de la consulta
     * @throws SQLException en caso de que no se haya podido ejecutar la sentencia bien
     */
    public static int execUpdate(Connection con, String q, List<Object> params, boolean insert) throws SQLException {
        int result;
        if (con != null) {
            PreparedStatement ps = prepareQuery(con, q, params);
            result = ps.executeUpdate();
            if (insert) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        result = generatedKeys.getInt(1);
                    } else {
                        result = -1;
                    }
                }
            }
        } else {
            result = -1;
        }
        return result;
    }

    /**
     * Esta función revisa que las tablas estén creadas y en caso contrario, las crea con las claves foráneas.
     * TODO verificar postgreSQL
     *
     * @param con  Conexión a la BBDD
     * @param type Tipo de conexión(H2, mySQL...)
     */
    private static void checkStructure(Connection con, String type) {
        String sql1, sql2;
        if (type.equals("mySQL")) {
            sql1 = "CREATE TABLE IF NOT EXISTS worker (" +
                    " id bigint(20) NOT NULL AUTO_INCREMENT," +
                    " name varchar(256) NOT NULL," +
                    " surnames varchar(256) NOT NULL," +
                    " address varchar(256) NOT NULL," +
                    " phone varchar(256) NOT NULL," +
                    " PRIMARY KEY (id)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
            sql2 = "CREATE TABLE IF NOT EXISTS task (" +
                    " id bigint(20) NOT NULL AUTO_INCREMENT," +
                    " user_com varchar(256) NOT NULL," +
                    " address varchar(256) NOT NULL," +
                    " day date NOT NULL," +
                    " hours double NOT NULL," +
                    " festive tinyint(1) NOT NULL," +
                    " night tinyint(1) NOT NULL," +
                    " hours_extra double NOT NULL," +
                    " id_worker bigint(11) NOT NULL," +
                    " PRIMARY KEY (id)," +
                    " KEY id_worker (id_worker)," +
                    " CONSTRAINT task_worker FOREIGN KEY (id_worker) REFERENCES worker (id)" +
                    " ON DELETE CASCADE ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
        } else if (type.equals("H2")) {
            sql1 = "CREATE TABLE IF NOT EXISTS worker (" +
                    " id LONG PRIMARY KEY auto_increment," +
                    " name VARCHAR(255)," +
                    " surnames VARCHAR(255)," +
                    " address VARCHAR(255)," +
                    " phone VARCHAR(255));";
            sql2 = "CREATE TABLE IF NOT EXISTS task (" +
                    " id LONG PRIMARY KEY auto_increment," +
                    " user_com VARCHAR(255)," +
                    " address VARCHAR(255)," +
                    " day DATE," +
                    " hours DOUBLE," +
                    " festive BOOLEAN," +
                    " night BOOLEAN," +
                    " hours_extra DOUBLE," +
                    " id_worker LONG," +
                    " CONSTRAINT task_worker FOREIGN KEY (id_worker) REFERENCES worker (id)" +
                    " ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";
        } else {
            sql1 = "";
            sql2 = "";
        }
        try {
            con.setAutoCommit(false);
            if (sql1.startsWith("CREATE TABLE IF NOT EXISTS") && sql2.startsWith("CREATE TABLE IF NOT EXISTS")) {
                ConnectionUtil.execUpdate(con, sql1, null, false);
                ConnectionUtil.execUpdate(con, sql2, null, false);
                con.commit();
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            Dialog.showError("ERROR", "Error creando tablas", ex.toString());
        }
    }

}
