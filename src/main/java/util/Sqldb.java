package util;

import model.CityRateData;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Sqldb {
    private static Connection conn=null;
    //连接数据库
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn=DriverManager.getConnection("jdbc:mysql://192.168.157.1/city","demo","5428");
            if(!conn.isClosed())
                System.out.println("Successfully connected to MySQL server...");
        }catch(Exception e){
            System.err.println("Exception:"+e.getMessage());
        }
        return conn;
    }

    //断开连接
    public static void closeConection(){
        if(conn!=null){
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

}
