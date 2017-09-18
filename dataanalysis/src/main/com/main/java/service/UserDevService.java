package main.java.service;

import main.java.dao.UserDevDAO;
import main.java.dao.UserPreferenceDAO;
import main.java.model.UserDev;
import main.java.model.UserPreference;
import main.java.util.Sqldb;

import java.sql.Connection;
import java.util.List;

/**
 * Created by root on 9/18/17.
 */
public class UserDevService {
    public static List<UserDev> SelectByPhone(String phone){
        Connection conn = null;
        List<UserDev> list = null;
        try {
            conn = Sqldb.getDefaultConnection();
            list = UserDevDAO.GetDeviceByPhone(conn, phone);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Sqldb.closeConnection(conn);
        }
        return list;
    }
}
