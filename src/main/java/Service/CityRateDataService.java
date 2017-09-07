package Service;

import DAO.CityRateDataDAO;
import model.CityRateData;
import java.util.*;
import util.*;

/**
 * Created by yxy on 9/7/17.
 */
public class CityRateDataService {
    //select cityratedata by city name
    public List<CityRateData> SelectByCityName(String name){
        CityRateDataDAO crdd=new CityRateDataDAO();
        return crdd.SelectByCityName(name);
    }

    //select cityratedata by date
    public List<CityRateData> SelectByDate(java.util.Date time){
        CityRateDataDAO crdd=new CityRateDataDAO();
        java.sql.Date date=new java.sql.Date(time.getTime());
        return crdd.SelectByDate(date);
    }
 }
