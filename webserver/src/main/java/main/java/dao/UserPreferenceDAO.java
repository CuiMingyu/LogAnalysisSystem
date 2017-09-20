package main.java.dao;

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
public class UserPreferenceDAO {
    /**
     * select preference of user
     * @param con
     * @param phone
     * @return
     * @throws SQLException
     */
    public static List<UserPreference> GetPreferenceByUser(Connection con,String phone)
            throws SQLException {
        PreparedStatement ps = null;
        PreparedStatement labelps = null;
        ResultSet rs = null;
        ResultSet labelrs=null;
        List<UserPreference> preferenceList = new ArrayList<UserPreference>();
        labelps=con.prepareStatement("SELECT words FROM label WHERE label= ?");
        ps = con.prepareStatement("SELECT phone,label,type FROM preference WHERE phone= ?");
        ps.setString(1,phone);
        rs = ps.executeQuery();

        while (rs.next()) {
            labelps.setInt(1,rs.getInt("label"));
            labelrs=labelps.executeQuery();
            if(labelrs.next()){
                preferenceList.add(new UserPreference(phone,labelrs.getString("words"),rs.getInt("type")));
            }
        }
        return preferenceList;
    }

    /**
     * select user preference data by preference
     * @param con
     * @param labelname
     * @return
     * @throws SQLException
     */
    public static List<UserPreference> GetUserByPreference(Connection con,String labelname)
            throws SQLException {
        PreparedStatement ps = null;
        PreparedStatement labelps = null;
        ResultSet rs = null;
        ResultSet labelrs=null;
        List<UserPreference> preferenceList = new ArrayList<UserPreference>();
        labelps=con.prepareStatement("SELECT label FROM label WHERE words= ?");
        labelps.setString(1,labelname);
        labelrs=labelps.executeQuery();
        if(!labelrs.next()){
            return null;
        }
        int label=labelrs.getInt("label");
        ps = con.prepareStatement("SELECT phone,label,type FROM preference WHERE label= ?");
        ps.setInt(1,label);
        rs = ps.executeQuery();

        while (rs.next()) {
            preferenceList.add(new UserPreference(rs.getString("phone"),labelname,rs.getInt("type")));
        }
        return preferenceList;
    }
    public static List<UserPreference> GetUserByPreference(Connection con,int label)
            throws SQLException {
        PreparedStatement ps = null;
        PreparedStatement labelps = null;
        ResultSet rs = null;
        ResultSet labelrs=null;
        List<UserPreference> preferenceList = new ArrayList<UserPreference>();
        labelps=con.prepareStatement("SELECT words FROM label WHERE label= ?");
        labelps.setInt(1,label);
        labelrs=labelps.executeQuery();
        if(!labelrs.next()){
            return null;
        }
        String labelname=labelrs.getString("label");
        ps = con.prepareStatement("SELECT phone,label,type FROM preference WHERE label= ?");
        ps.setInt(1,label);
        rs = ps.executeQuery();

        while (rs.next()) {
            preferenceList.add(new UserPreference(rs.getString("phone"),labelname,rs.getInt("type")));
        }
        return preferenceList;
    }
}
