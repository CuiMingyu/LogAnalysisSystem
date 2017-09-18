package main.java.dao;

import main.java.model.MostPV;
import main.java.model.MostUV;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxy on 9/12/17.
 */
public class MostUVDAO {
    //添加一行数据到mostuv表中
    public static void addMostUV(Connection con, String urlname, int UV)
            throws SQLException {
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="INSERT INTO mostuv(urlname,UV) values(?,?)";
        ps=con.prepareStatement(sql);
        ps.setString(1,urlname);
        ps.setInt(2,UV);
        ps.executeUpdate();
        //System.out.println("Insert successfully");
        //System.out.println("Date:"+date+" city:"+city+" PV:"+PV+" UV:"+UV+" rate:"+rate);

    }

    //前端给查询数字，返回相应数量的UV排序值 number<=20
    public static List<MostUV> GetMostUV(Connection con, int number)
            throws SQLException{
        if (number>20) number=20;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<MostUV> muvList=new ArrayList<>();
        ps=con.prepareStatement("select * from mostuv");
        rs=ps.executeQuery();

        while(rs.next() && number>=0){
            number--;
            MostUV muv=new MostUV();
            muv.setMostUV(rs.getString("urlname"),rs.getInt("UV"));
            muvList.add(muv);
        }
        return muvList;
    }
}
