package Service;

import DAO.CityRateDataDAO;
import DAO.TimeRateDataDAO;
import model.TimeRateData;

import java.util.Date;
import java.util.List;

/**
 * Created by yxy on 9/9/17.
 */
public class TimeRateDataService {

    //select timeratedata by date
    public List<TimeRateData> SelectByDate(Date date){
        TimeRateDataDAO trdd=new TimeRateDataDAO();
        java.sql.Date datesql=new java.sql.Date(date.getTime());
        return trdd.SelectByDate(datesql);
    }

}
