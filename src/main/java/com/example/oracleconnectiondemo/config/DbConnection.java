package com.example.oracleconnectiondemo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.OracleDriver");
        return DriverManager.getConnection("jdbc:oracle:thin:@10.20.33.42:1521:TC319P","cb","wasserflasche");
    }
}
