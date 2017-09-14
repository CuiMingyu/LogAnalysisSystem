package main.java.dao;

import main.java.model.MostPV;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxy on 9/11/17.
 */
public class MostPVDAO {
    //添加一行数据到mostpv表中
    public static void addMostPV(Connection con, String urlname, int PV)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "INSERT INTO mostpv(urlname,PV) values(?,?)";
        ps = con.prepareStatement(sql);
        ps.setString(1, urlname);
        ps.setInt(2, PV);
        ps.executeUpdate();
        //System.out.println("Insert successfully");
        //System.out.println("Date:"+date+" city:"+city+" PV:"+PV+" UV:"+UV+" rate:"+rate);

    }

    //前端给查询数字，返回相应数量的PV排序值 number<=20
    public static List<MostPV> GetMostPV(Connection con, int number)
            throws SQLException {
        if (number > 20) number = 20;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MostPV> mpvList = new ArrayList<>();
        ps = con.prepareStatement("select * from mostpv");
        rs = ps.executeQuery();

        while (rs.next() && number >= 0) {
            number--;
            MostPV mpv = new MostPV();
            mpv.setMostPV(rs.getString("urlname"), rs.getInt("PV"));
            mpvList.add(mpv);
        }
        return mpvList;
    }
}
