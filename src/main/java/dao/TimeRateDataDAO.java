package dao;

import model.TimeRateData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxy on 9/9/17.
 */
public class TimeRateDataDAO {
    //add data into table timerate
    public static void addTimeRate(Connection con,Date date,int time,int PV,int UV) throws SQLException{
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="INSERT INTO timerate(date,time,PV,UV) values(?,?,?,?)";
        ps=con.prepareStatement(sql);
        ps.setDate(1,date);
        ps.setInt(2,time);
        ps.setInt(3,PV);
        ps.setInt(4,UV);
        ps.executeUpdate();
        System.out.println("Insert successfully");
        System.out.println("Date:"+date+" time:"+time+" PV:"+PV+" UV:"+UV);
    }

    public static List<TimeRateData> SelectByDate(Connection con,Date date)
            throws SQLException{
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<TimeRateData> trdList=new ArrayList<>();
        ps=con.prepareStatement("select * from timerate where date= ?");
        ps.setDate(1,date);
        rs=ps.executeQuery();

        while(rs.next()){
            TimeRateData trd=new TimeRateData();
            trd.setTimeRateData(rs.getDate("date"),rs.getInt("time"),rs.getInt("PV"),rs.getInt("UV"));
            trdList.add(trd);
        }
        return trdList;
    }
/*
    public static void main(String args[]){
        TimeRateDataDAO trdDAO=new TimeRateDataDAO();
        List<TimeRateData> trdList=trdDAO.SelectByDate(new Date(117,0,1));
        for(int i=0;i<trdList.size();i++){
            System.out.println(trdList.get(i));
        }
    }*/
}
