package main.java.dao;

import main.java.model.ByteSeries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/17/17.
 */
public class ByteSeriesDAO {
    //添加一行数据到byteseries表中
    public static void addByteSeries(Connection con, String time,String data,String user)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "INSERT INTO byteseries(time,data,user) values(?,?,?)";
        ps = con.prepareStatement(sql);
        ps.setString(1, time);
        ps.setString(2, data);
        ps.setString(3, user);
        ps.executeUpdate();
    }

    public static List<ByteSeries> SelectByUserName(Connection con, String user)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ByteSeries> bsList = new ArrayList<ByteSeries>();
        ps = con.prepareStatement("select * from byteseries where user= ?");
        ps.setString(1, user);
        rs = ps.executeQuery();
        int elength=0;

        while (rs.next()) {
            if(elength==0) elength=rs.getString("data").length()-2;
            ByteSeries bs = new ByteSeries();
            String data=null;
            if(rs.getString("data").charAt(0)=='1')
                data=rs.getString("data")+"E"+String.valueOf(elength);
            else data=rs.getString("data")+"E"+String.valueOf(elength-1);
            bs.setByteSeries(rs.getString("time"), data, rs.getString("user"));
            bsList.add(bs);
        }
        return bsList;
    }

}
