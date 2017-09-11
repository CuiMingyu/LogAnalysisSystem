package util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysqldb extends Sqldb{
    //连接数据库
    public static Connection getConnection(String url,String username,String password){
        try{
            System.out.println("Connecting to Mysql server...");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn=DriverManager.getConnection("jdbc:mysql://"+url,username,password);
            if(!conn.isClosed())
                System.out.println("Successfully connected to Mysql server...");
        }catch(Exception e){
            System.err.println("Exception:"+e.getMessage());
        }
        return conn;
    }
    public static void main(String[] args){
        getConnection("scm001:3306/loganalysis","admin","123456");
    }

}
