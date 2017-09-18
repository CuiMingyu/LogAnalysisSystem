package main.java.service;

import main.java.dao.TimeRateDataDAO;
import main.java.dao.UserPreferenceDAO;
import main.java.model.TimeRateData;
import main.java.model.UserPreference;
import main.java.util.Sqldb;

import java.sql.Connection;
import java.util.List;

/**
 * Created by root on 9/18/17.
 */
public class UserPreferenceService {
    public static List<UserPreference> SelectByUser(String phone){
        Connection conn = null;
        List<UserPreference> list = null;
        try {
            conn = Sqldb.getDefaultConnection();
            list = UserPreferenceDAO.GetPreferenceByUser(conn, phone);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Sqldb.closeConnection(conn);
        }
        return list;
    }
    public static List<UserPreference> SelectByLabelname(String labelname){
        Connection conn = null;
        List<UserPreference> list = null;
        try {
            conn = Sqldb.getDefaultConnection();
            list = UserPreferenceDAO.GetUserByPreference(conn, labelname);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Sqldb.closeConnection(conn);
        }
        return list;
    }
    public static List<UserPreference> SelectByLabel(int label){
        Connection conn = null;
        List<UserPreference> list = null;
        try {
            conn = Sqldb.getDefaultConnection();
            list = UserPreferenceDAO.GetUserByPreference(conn, label);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Sqldb.closeConnection(conn);
        }
        return list;
    }
}
