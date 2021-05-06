package com.Alvaro.utilities;


import com.Alvaro.model.beans.DataConnection;

import java.sql.*;
import java.util.List;

public class ConnectionUtil {

    public static Connection connect(DataConnection c) throws SQLException {
        Connection conn;
        if (c == null) {
            return null;
        }
        conn = DriverManager.getConnection("jdbc:mysql://" + c.getHost() + "/" + c.getDb(), c.getUser(), c.getPassword());
        //checkStructure(conn);
        return conn;
    }

    public static ResultSet execQuery(Connection con, String q, List<Object> params) throws SQLException {
        ResultSet result;
        if (con == null) {
            return null;
        }
        PreparedStatement ps = prepareQuery(con, q, params);
        result = ps.executeQuery();
        return result;
    }

    public static int is(Integer n) {
        return 0;
    }

    public static int is(Float n) {
        return 1;
    }

    public static int is(Double n) {
        return 2;
    }

    public static int is(Boolean n) {
        return 3;
    }

    public static int is(String n) {
        return 4;
    }

    public static int is(Array n) {
        return 5;
    }

    public static int is(Object n) {
        return 6;
    }

    public static PreparedStatement prepareQuery(Connection con, String q, List<Object> params) throws SQLException {
        PreparedStatement ps;
        ps = con.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
        if (params != null) {
            int i = 1;
            for (Object o : params) {
                switch (is(params)) {
                    case 0 -> ps.setInt(i++, (Integer) o);
                    case 1 -> ps.setFloat(i++, (Float) o);
                    case 2 -> ps.setDouble(i++, (Double) o);
                    case 3 -> ps.setBoolean(i++, (Boolean) o);
                    case 4 -> ps.setString(i++, (String) o);
                    case 5 -> ps.setArray(i++, (Array) o);
                    default -> ps.setObject(i++, o);
                }
            }
        }
        return ps;
    }

    public static int execUpdate(Connection con, String q, List<Object> params, boolean insert) throws SQLException {
        if (con == null) {
            return -1;
        }
        PreparedStatement ps = prepareQuery(con, q, params);
        int result = ps.executeUpdate();
        if (insert) {
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }
        } else {
            return result;
        }

    }

    public static void checkStructure(Connection con) {
        try {
            String sql1,sql2,sql3;
                sql2 = "CREATE TABLE IF NOT EXISTS `task` (" +
                        "  `id` bigint(20) NOT NULL AUTO_INCREMENT," +
                        "  `user_com` varchar(256) NOT NULL," +
                        "  `address` varchar(256) NOT NULL," +
                        "  `day` date NOT NULL," +
                        "  `hours` double NOT NULL," +
                        "  `festive` tinyint(1) NOT NULL," +
                        "  `night` tinyint(1) NOT NULL," +
                        "  `hours_extra` double NOT NULL," +
                        "  `id_worker` bigint(11) NOT NULL," +
                        "  PRIMARY KEY (`id`)," +
                        "  KEY `id_worker` (`id_worker`)" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;";
                sql1= "CREATE TABLE IF NOT EXISTS `worker` (" +
                        "  `id` bigint(20) NOT NULL AUTO_INCREMENT," +
                        "  `name` varchar(256) NOT NULL," +
                        "  `surnames` varchar(256) NOT NULL," +
                        "  `address` varchar(256) NOT NULL," +
                        "  `phone` varchar(256) NOT NULL," +
                        "  PRIMARY KEY (`id`)" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;";
                 sql3 = "ALTER TABLE `task`" +
                         "  ADD CONSTRAINT `task_worker` FOREIGN KEY (`id_worker`) REFERENCES `worker` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;";
            con.setAutoCommit(false);
            ConnectionUtil.execUpdate(con, sql1, null, false);
            ConnectionUtil.execUpdate(con, sql2, null, false);
            ConnectionUtil.execUpdate(con,sql3,null,false);
            con.commit();
            con.setAutoCommit(true);

        } catch (SQLException ex) {
            Dialog.showError("ERROR", "Error creando tablas", ex.toString());
        }
    }
}
