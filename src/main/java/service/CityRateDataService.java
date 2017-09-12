package main.java.service;

import main.java.dao.CityRateDataDAO;
import main.java.model.CityRateData;
import main.java.util.Sqldb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by yxy on 9/7/17.
 */
public class CityRateDataService {
    //select cityratedata by city name
    public List<CityRateData> SelectByCityName(String name) {
        Connection conn=null;
        List<CityRateData> list=null;
        try {
            conn = Sqldb.getDefaultConnection();
            list = CityRateDataDAO.SelectByCityName(conn, name);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Sqldb.closeConnection(conn);
        }
        return list;
    }

    //select cityratedata by date
    public List<CityRateData> SelectByDate(java.util.Date time) {
        CityRateDataDAO crdd=new CityRateDataDAO();
        java.sql.Date date=new java.sql.Date(time.getTime());
        Connection conn=null;
        List<CityRateData> list=null;
        try {
            conn = Sqldb.getDefaultConnection();
            list = CityRateDataDAO.SelectByDate(conn, date);

        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            Sqldb.closeConnection(conn);
        }
        return list;
    }
 }
