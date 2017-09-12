package service;

import dao.MostIPDAO;
import model.MostIP;
import util.Sqldb;

import java.sql.Connection;
import java.util.List;

/**
 * Created by yxy on 9/12/17.
 */
public class MostIPService {
    //前端给查询数字，返回相应数量的PV排序值 number<=20
    public List<MostIP> GetMostIP(int number) {
        Connection conn=null;
        List<MostIP> list=null;
        try {
            conn = Sqldb.getDefaultConnection();
            list = MostIPDAO.GetMostIP(conn, number);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Sqldb.closeConnection(conn);
        }
        return list;
    }
}
