package util;

import model.CityRateData;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Sqldb {
    static private String dbUrl="scm001:3306/loganalysis";
    static private String username="admin";
    static private String password="123456";
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

    /*
    public void CreateTable(){
        Connection con=null;
        PreparedStatement ps=null;
        PreparedStatement ps2=null;
        ResultSet rs=null;
        try{
            con=Sqldb.getConnection();
            ps=con.prepareStatement("select timestamp,city_id,MAC from log ");
            rs=ps.executeQuery();

            while(rs.next()){
                long time=Long.parseLong(rs.getString("timestamp"));
                java.util.Date date=DateUtils.getDate(time);
                java.sql.Date date1=new java.sql.Date(date.getTime());
                String sql="INSERT INTO temp(date,city_num,MAC) values(?,?,?)";
                ps2=con.prepareStatement(sql);
                ps2.setDate(1,date1);
                ps2.setString(2,rs.getString("city_id"));
                ps2.setString(3,rs.getString("MAC"));
                ps2.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.Free(rs, con, ps);
        }
    }*/

}
