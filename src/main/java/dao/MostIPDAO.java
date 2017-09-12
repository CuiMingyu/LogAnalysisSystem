package dao;

import model.MostIP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxy on 9/12/17.
 */
public class MostIPDAO {
    //添加一行数据到mostip表中
    public static void addMostIP(Connection con, String urlname, int IP)
            throws SQLException {
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="INSERT INTO mostip(urlname,IP) values(?,?)";
        ps=con.prepareStatement(sql);
        ps.setString(1,urlname);
        ps.setInt(2,IP);
        ps.executeUpdate();
        //System.out.println("Insert successfully");
        //System.out.println("Date:"+date+" city:"+city+" PV:"+PV+" UV:"+UV+" rate:"+rate);

    }

    //前端给查询数字，返回相应数量的IP排序值 number<=20
    public static List<MostIP> GetMostIP(Connection con, int number)
            throws SQLException{
        if (number>20) number=20;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<MostIP> mipList=new ArrayList<>();
        ps=con.prepareStatement("select * from mostip");
        rs=ps.executeQuery();

        while(rs.next() && number>=0){
            number--;
            MostIP mip=new MostIP();
            mip.setMostIP(rs.getString("urlname"),rs.getInt("IP"));
            mipList.add(mip);
        }
        return mipList;
    }
}
