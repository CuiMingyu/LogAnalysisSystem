package DAO;

import model.CityRateData;
import model.TimeRateData;
import util.Sqldb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxy on 9/9/17.
 */
public class TimeRateDataDAO {
    private Connection con= Sqldb.getConnection();

    //add data into table timerate
    public void addTimeRate(Date date,int time,int PV,int UV){
        PreparedStatement ps=null;
        ResultSet rs=null;

        try{
            String sql="INSERT INTO timerate(date,time,PV,UV) values(?,?,?,?)";
            ps=con.prepareStatement(sql);
            ps.setDate(1,date);
            ps.setInt(2,time);
            ps.setInt(3,PV);
            ps.setInt(4,UV);
            ps.executeUpdate();
            System.out.println("Insert successfully");
            System.out.println("Date:"+date+" time:"+time+" PV:"+PV+" UV:"+UV);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Sqldb.closeConection();
        }
    }

    public List<TimeRateData> SelectByDate(Date date){
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<TimeRateData> trdList=new ArrayList<>();
        try{
            ps=con.prepareStatement("select * from timerate where date= ?");
            ps.setDate(1,date);
            rs=ps.executeQuery();

            while(rs.next()){
                TimeRateData trd=new TimeRateData();
                trd.setTimeRateData(rs.getDate("date"),rs.getInt("time"),rs.getInt("PV"),rs.getInt("UV"));
                trdList.add(trd);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.closeConection();
        }
        return trdList;
    }

    public static void main(String args[]){
        TimeRateDataDAO trdDAO=new TimeRateDataDAO();
        List<TimeRateData> trdList=trdDAO.SelectByDate(new Date(117,0,1));
        for(int i=0;i<trdList.size();i++){
            System.out.println(trdList.get(i));
        }
    }
}
