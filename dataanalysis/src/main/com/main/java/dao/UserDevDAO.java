package main.java.dao;

import main.java.model.UserDev;
import main.java.model.UserPreference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/18/17.
 */
public class UserDevDAO {
    public static List<UserDev> GetDeviceByPhone(Connection con, String phone)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet labelrs=null;
        List<UserDev> userList = new ArrayList<UserDev>();
        ps = con.prepareStatement("SELECT device FROM user WHERE phone=?");
        ps.setString(1,phone);
        rs = ps.executeQuery();

        while (rs.next()) {
                userList.add(new UserDev(phone,labelrs.getString("device")));
        }
        return userList;
    }
}
