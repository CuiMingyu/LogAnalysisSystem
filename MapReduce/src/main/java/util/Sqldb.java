package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Sqldb {
    private static Connection conn=null;
    //连接数据库
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn=DriverManager.getConnection("jdbc:mysql://192.168.56.1/city","demo","5428");
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
