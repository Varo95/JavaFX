package com.Alvaro.utilities;


import com.Alvaro.model.DataConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection connect(DataConnection c) throws SQLException {
        Connection conn=null;
        if(c==null){
            return null;
        }
        //Class.forName("com.mysql.jdbc.Driver");
        conn=DriverManager.getConnection("jdbc:mysql://"+c.getHost()+"/"+c.getDb(),c.getUser(),c.getPassword());
        return conn;
    }
}
