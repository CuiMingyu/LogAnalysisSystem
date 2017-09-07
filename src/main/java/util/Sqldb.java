package util;

import model.CityData;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Sqldb {
    //连接数据库
    public static Connection getConnection(){
        Connection conn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn=DriverManager.getConnection("jdbc:mysql:///city","root","yuan2292605");
            if(!conn.isClosed())
                System.out.println("Successfully connected to MySQL server...");
        }catch(Exception e){
            System.err.println("Exception:"+e.getMessage());
        }
        return conn;
    }

    //断开连接
    public static void Free(ResultSet rs,Connection conn,Statement stmt){
        try{
            if(rs!=null)
                rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                try{
                    if(stmt!=null)
                        stmt.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //添加一行数据到rateinfo表中
    public void addRateInfo(Date date,String city,int PV,int UV,double rate){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try{
            con=Sqldb.getConnection();
            String sql="INSERT INTO rateinfo(date,city,PV,UV,rate) values(?,?,?,?,?)";
            ps=con.prepareStatement(sql);
            ps.setDate(1,date);
            ps.setString(2,city);
            ps.setInt(3,PV);
            ps.setInt(4,UV);
            ps.setDouble(5,rate);
            ps.executeUpdate();
            System.out.println("Insert successfully");
            System.out.println("Date:"+date+" city:"+city+" PV:"+PV+" UV:"+UV+" rate:"+rate);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Sqldb.Free(rs, con, ps);
        }
    }

    //通过CityData实体类获得rateinfo中的数据id
    public int getrateid(CityData cd){
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        int num=0;
        try{
            con=Sqldb.getConnection();
            st=con.createStatement();
            rs=st.executeQuery("select * from rateinfo");
            while(rs.next()){
                if (rs.getDate("date").equals(cd.getDate()) && rs.getString("city").equals(cd.getName())
                        && rs.getInt("PV")==cd.getPV() && rs.getInt("UV")==cd.getUV()
                        && rs.getDouble("rate")==cd.getRate()){
                    num=rs.getInt("id");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.Free(rs, con, st);
        }
        return num;
    }

    //通过城市名称选择相应数据
    public List<CityData> SelectByCityName(String cityName){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<CityData> cdList=new ArrayList<CityData>();
        try{
            con=Sqldb.getConnection();
            ps=con.prepareStatement("select * from rateinfo where city= ?");
            ps.setString(1,cityName);
            rs=ps.executeQuery();

            while(rs.next()){
                CityData cd=new CityData();
                cd.setCityData(rs.getDate("date"),rs.getString("city"),rs.getInt("PV"),rs.getInt("UV"),
                        rs.getDouble("rate"));
                cdList.add(cd);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.Free(rs, con, ps);
        }
        return cdList;
    }

    //通过日期选择相应数据
    public List<CityData> SelectByDate(Date date){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<CityData> cdList=new ArrayList<CityData>();
        try{
            con=Sqldb.getConnection();
            ps=con.prepareStatement("select * from rateinfo where date= ?");
            ps.setDate(1,date);
            rs=ps.executeQuery();

            while(rs.next()){
                CityData cd=new CityData();
                cd.setCityData(rs.getDate("date"),rs.getString("city"),rs.getInt("PV"),rs.getInt("UV"),
                        rs.getDouble("rate"));
                cdList.add(cd);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.Free(rs, con, ps);
        }
        return cdList;
    }

    //通过城市编码得到城市名称
    public String SelectCityByID(String id){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String city=null;
        try{
            con=Sqldb.getConnection();
            ps=con.prepareStatement("select * from cityid where id= ?");
            ps.setString(1,id);
            rs=ps.executeQuery();

            while(rs.next()){
                city=rs.getString("city");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.Free(rs, con, ps);
        }
        return city;
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

    //测试函数
    public static void main(String args[]){
        Sqldb sqldb=new Sqldb();
        Date date=Date.valueOf("2010-12-01");
        CityData cd=new CityData();
        cd.setCityData(date,"南京",10,20,0.5);
        //System.out.println(sqldb.getrateid(cd));
        List<CityData> ls=sqldb.SelectByCityName("南京");
        for(int i=0;i<ls.size();i++){
            System.out.println(ls.get(i));
        }
        List<CityData> ds=sqldb.SelectByDate(Date.valueOf("2010-12-01"));
        for(int i=0;i<ds.size();i++){
            System.out.println(ds.get(i));
        }
        System.out.println(sqldb.SelectCityByID("10"));
        //sqldb.CreateTable();
    }
}
