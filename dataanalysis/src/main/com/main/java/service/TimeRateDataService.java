package main.java.service;

import main.java.dao.TimeRateDataDAO;
import main.java.model.TimeRateData;
import main.java.util.Sqldb;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * Created by yxy on 9/9/17.
 */
public class TimeRateDataService {

    //select timeratedata by date
    public List<TimeRateData> SelectByDate(Date date) {
        Connection conn = null;
        List<TimeRateData> list = null;
        try {
            conn = Sqldb.getDefaultConnection();
            java.sql.Date datesql = new java.sql.Date(date.getTime());
            list = TimeRateDataDAO.SelectByDate(conn, datesql);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Sqldb.closeConnection(conn);
        }
        return list;
    }

}
