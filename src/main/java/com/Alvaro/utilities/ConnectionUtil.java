package com.Alvaro.utilities;


import com.Alvaro.model.beans.DataConnection;

import java.sql.*;
import java.util.List;

public class ConnectionUtil {

    public static Connection connect(DataConnection c) throws SQLException {
        Connection conn = null;
        if (c == null) {
            return null;
        }
        conn = DriverManager.getConnection("jdbc:mysql://" + c.getHost() + "/" + c.getDb(), c.getUser(), c.getPassword());
        return conn;
    }

    public static ResultSet execQuery(Connection con, String q, List<Object> params) throws SQLException {
        ResultSet result = null;
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

    public static PreparedStatement prepareQuery(Connection con, String q, List params) throws SQLException {
        PreparedStatement ps = null;
        ps = con.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
        if (params != null) {
            int i = 1;
            for (Object o : params) {
                switch (is(params)) {
                    case 0:
                        ps.setInt(i++, (Integer) o);
                        break;
                    case 1:
                        ps.setFloat(i++, (Float) o);
                        break;
                    case 2:
                        ps.setDouble(i++, (Double) o);
                        break;
                    case 3:
                        ps.setBoolean(i++, (Boolean) o);
                        break;
                    case 4:
                        ps.setString(i++, (String) o);
                        break;
                    case 5:
                        ps.setArray(i++, (Array) o);
                        break;
                    default:
                        ps.setObject(i++, o);
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
}
