package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.*;
import util.Sqldb;

public class CityRateDataDAO {
    private Connection con=Sqldb.getConnection();

    //添加一行数据到rateinfo表中
    public void addRateInfo(Date date, String city, int PV, int UV, double rate){
        PreparedStatement ps=null;
        ResultSet rs=null;

        try{
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
            Sqldb.closeConection();
        }
    }

    //通过CityData实体类获得rateinfo中的数据id
    public int getrateid(CityRateData cd){
        Statement st=null;
        ResultSet rs=null;
        int num=0;
        try{
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
            Sqldb.closeConection();
        }
        return num;
    }

    //通过城市名称选择相应数据
    public List<CityRateData> SelectByCityName(String cityName){
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<CityRateData> cdList=new ArrayList<CityRateData>();
        try{
            ps=con.prepareStatement("select * from rateinfo where city= ?");
            ps.setString(1,cityName);
            rs=ps.executeQuery();

            while(rs.next()){
                CityRateData cd=new CityRateData();
                cd.setCityRateData(rs.getDate("date"),rs.getString("city"),rs.getInt("PV"),rs.getInt("UV"),
                        rs.getDouble("rate"));
                cdList.add(cd);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.closeConection();
        }
        return cdList;
    }

    //通过日期选择相应数据
    public List<CityRateData> SelectByDate(Date date){
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<CityRateData> cdList=new ArrayList<CityRateData>();
        try{
            ps=con.prepareStatement("select * from rateinfo where date= ?");
            ps.setDate(1,date);
            rs=ps.executeQuery();

            while(rs.next()){
                CityRateData cd=new CityRateData();
                cd.setCityRateData(rs.getDate("date"),rs.getString("city"),rs.getInt("PV"),rs.getInt("UV"),
                        rs.getDouble("rate"));
                cdList.add(cd);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.closeConection();
        }
        return cdList;
    }

    //通过城市编码得到城市名称
    public String SelectCityByID(String id){
        PreparedStatement ps=null;
        ResultSet rs=null;
        String city=null;
        try{
            ps=con.prepareStatement("select * from cityid where id= ?");
            ps.setString(1,id);
            rs=ps.executeQuery();

            while(rs.next()){
                city=rs.getString("city");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.closeConection();
        }
        return city;
    }

    //测试函数
    /*public static void main(String args[]){
        CityRateDataDAO crda2=new CityRateDataDAO();
        CityRateDataDAO crdd=new CityRateDataDAO();
        Date date=Date.valueOf("2010-12-01");
        CityRateData cd=new CityRateData();
        cd.setCityRateData(date,"南京",10,20,0.5);
        //System.out.println(sqldb.getrateid(cd));
        CityRateDataDAO crda=new CityRateDataDAO();
        List<CityRateData> ls= crda.SelectByCityName("南京");
        for(int i=0;i<ls.size();i++){
            System.out.println(ls.get(i));
        }
        List<CityRateData> ds= crdd.SelectByDate(Date.valueOf("2010-12-01"));
        for(int i=0;i<ds.size();i++){
            System.out.println(ds.get(i));
        }
        System.out.println(crda2.SelectCityByID("10"));
    }*/

    /*public void transfertable(){
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<CityRateData> cdList=new ArrayList<CityRateData>();
        try{
            ps=con.prepareStatement("select * from cityrate");
            rs=ps.executeQuery();

            while(rs.next()){
                CityRateData cd=new CityRateData();
                double rate=(rs.getInt("PV")/rs.getInt("UV"));
                CityRateDataDAO dao=new CityRateDataDAO();
                String city=dao.SelectCityByID(rs.getString("city_id"));
                CityRateDataDAO dao2=new CityRateDataDAO();
                dao2.addRateInfo(Date.valueOf(rs.getString("time")),city,rs.getInt("PV"),rs.getInt("UV"),rate);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.closeConection();
        }
    }

    public static void main(String args[]){
        CityRateDataDAO crda=new CityRateDataDAO();
        crda.transfertable();

    }*/
}
