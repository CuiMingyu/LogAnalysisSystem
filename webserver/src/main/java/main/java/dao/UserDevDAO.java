package main.java.dao;

import main.java.model.UserDev;

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
    public static UserDev GetDeviceByPhone(Connection con, String phone)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserDev dev =null;
        ps = con.prepareStatement("SELECT device FROM user WHERE phone=?");
        ps.setString(1,phone);
        rs = ps.executeQuery();

        if (rs.next()) {
                dev=new UserDev(phone,rs.getString("device"));
        }
        return dev;
    }
}
