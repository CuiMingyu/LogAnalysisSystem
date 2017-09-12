package service;

import dao.MostPVDAO;
import model.MostPV;
import util.Sqldb;

import java.sql.Connection;
import java.util.List;

/**
 * Created by yxy on 9/12/17.
 */
public class MostPVService {
    //前端给查询数字，返回相应数量的PV排序值 number<=20
    public List<MostPV> GetMostPV(int number) {
        Connection conn=null;
        List<MostPV> list=null;
        try {
            conn = Sqldb.getDefaultConnection();
            list = MostPVDAO.GetMostPV(conn, number);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Sqldb.closeConnection(conn);
        }
        return list;
    }
}
