package main.java.service;

import main.java.dao.UserDevDAO;
import main.java.model.UserDev;
import main.java.util.Sqldb;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/18/17.
 */
public class UserDevService {
    public static UserDev SelectByPhone(String phone){
        Connection conn = null;
        UserDev dev = null;
        try {
            conn = Sqldb.getDefaultConnection();
            dev = UserDevDAO.GetDeviceByPhone(conn, phone);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Sqldb.closeConnection(conn);
        }
        return dev;
    }

    public static List<UserDev> SelectByPhoneList(List<String> phones){
        Connection conn = null;
        List<UserDev> list = new ArrayList<UserDev>();
        try {
            conn = Sqldb.getDefaultConnection();
            for(String phone:phones){
                list.add(UserDevDAO.GetDeviceByPhone(conn, phone));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Sqldb.closeConnection(conn);
        }
        return list;
    }
}
