package main.java.dao;

import main.java.model.NewDeviceRecord;
import org.apache.ibatis.jdbc.SQL;
import main.java.util.Sqldb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxy on 9/7/17.
 */
public class NewDeviceRecordDAO {
    //add info in newstyle table
    public static void addNewStyle(Connection con,Date date,String style,int number)
            throws SQLException{
        PreparedStatement ps=null;
        ResultSet rs=null;

            String sql="INSERT INTO newstyle(date,style,number) values(?,?,?)";
            ps=con.prepareStatement(sql);
            ps.setDate(1,date);
            ps.setString(2,style);
            ps.setInt(3,number);
            ps.executeUpdate();
            System.out.println("Insert successfully");
            System.out.println("Date:"+date+" style:"+style+" number"+number);
    }

    //get id by model NewDeviceRecord
    public static int getdeviceid(Connection con,NewDeviceRecord ndr)
            throws SQLException{
        Statement st=null;
        ResultSet rs=null;
        int num=0;
            st=con.createStatement();
            rs=st.executeQuery("select * from newstyle");
            while(rs.next()){
                if (rs.getDate("date").equals(ndr.getDate()) && rs.getString("style").equals(ndr.getStyle())
                        && rs.getInt("number")==ndr.getNumber()){
                    num=rs.getInt("id");
                }
            }
        return num;
    }

    public static List<NewDeviceRecord> SelectByDate(Connection con,Date date1,Date date2)
            throws SQLException{
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<NewDeviceRecord> ndrList=new ArrayList<>();
            ps=con.prepareStatement("select * from newstyle where date between ? and ?");
            ps.setDate(1,date1);
            ps.setDate(2,date2);
            rs=ps.executeQuery();

            while(rs.next()) {
                NewDeviceRecord ndr = new NewDeviceRecord();
                ndr.setNewDeviceRecord(rs.getDate("date"), rs.getString("style"), rs.getInt("number"));
                ndrList.add(ndr);
            }
        return ndrList;
    }


    //get NewDeviceRecord list by two date and machine name
    public static List<NewDeviceRecord> SelectByDateAndMachine(Connection con,Date date1,Date date2,String machinename)
            throws SQLException{
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<NewDeviceRecord> ndrList=new ArrayList<>();
        ps=con.prepareStatement("select * from newstyle where style like ? and date between ? and ?");
        ps.setString(1,machinename+"%");
        ps.setDate(2,date1);
        ps.setDate(3,date2);
        rs=ps.executeQuery();

        while(rs.next()){
            NewDeviceRecord ndr=new NewDeviceRecord();
            ndr.setNewDeviceRecord(rs.getDate("date"),rs.getString("style"),rs.getInt("number"));
            ndrList.add(ndr);
        }
        return ndrList;
    }

    //get NewDeviceRecord list by two date and style name
    public static List<NewDeviceRecord> SelectByDateAndStyle(Connection con,Date date1,Date date2,String stylename) throws SQLException{
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<NewDeviceRecord> ndrList=new ArrayList<>();
        ps=con.prepareStatement("select * from newstyle where style=? and date between ? and ?");
        ps.setString(1,stylename);
        ps.setDate(2,date1);
        ps.setDate(3,date2);
        rs=ps.executeQuery();

        while(rs.next()){
            NewDeviceRecord ndr=new NewDeviceRecord();
            ndr.setNewDeviceRecord(rs.getDate("date"),rs.getString("style"),rs.getInt("number"));
            ndrList.add(ndr);
        }
        return ndrList;
    }
/*
    //main
    public static void main(String args[]){
        NewDeviceRecordDAO dao=new NewDeviceRecordDAO();
        Date date1=Date.valueOf("2017-01-01");
        Date date2=Date.valueOf("2017-01-07");
        List<NewDeviceRecord> list=dao.SelectByDateAndMachine(date1,date2,"LG");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }*/
}
