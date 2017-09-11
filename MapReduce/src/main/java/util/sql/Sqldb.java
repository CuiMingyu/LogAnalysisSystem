package main.java.util.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by root on 9/10/17.
 */
abstract public class Sqldb {
    protected static Connection conn=null;
    public static Connection getConnection(String url,String username,String password){
        return conn;
    }
    public static void closeConnection(){
        if(conn!=null){
            try{
                conn.close();
                conn=null;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
