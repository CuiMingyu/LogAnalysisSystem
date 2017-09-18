package main.java.util;

import main.java.model.CityRateData;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Sqldb {
    static private String dbUrl="45.77.17.214:3306/loganalysis";
    static private String username="admin";
    static private String password="s8xnc22";
    public static Connection getDefaultConnection()
            throws SQLException,ClassNotFoundException,IllegalAccessException,InstantiationException{
        return getConnection(dbUrl,username,password);
    }
    //连接数据库
    public static Connection getConnection(String url,String username,String password)
            throws SQLException,ClassNotFoundException,IllegalAccessException,InstantiationException{
        Connection conn=null;
        System.out.println("Connecting to Mysql server...");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn=DriverManager.getConnection("jdbc:mysql://"+url,username,password);
        if(!conn.isClosed())
            System.out.println("Successfully connected to Mysql server...");
        return conn;
    }
    //断开连接
    public static void closeConnection(Connection conn){
        if(conn!=null){
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

}
