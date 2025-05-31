package com.library.util;

import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    private static final String url= "jdbc:postgresql://localhost:5432/library_db";
    private static final String username = "postgres";
    private static final String password = "college";

    private static Connection connection;

    public static Connection getConnection() throws SQLException{
        if (connection==null || connection.isClosed()){
            try{
                Class.forName("org.postgresql.Driver");
                connection= DriverManager.getConnection(url,username,password);
            }
            catch (ClassNotFoundException e){
                System.out.println("PostgreSQL JDBC Driver not found!");
                e.printStackTrace();
            }
            catch (SQLException e){
                System.out.println("Connection Failed");
                e.printStackTrace();
                throw e;
            }

            }
        return connection;
        }
    }


