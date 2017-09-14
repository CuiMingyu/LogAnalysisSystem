package main.java.service;

import main.java.dao.MostUVDAO;
import main.java.model.MostUV;
import main.java.util.Sqldb;

import java.sql.Connection;
import java.util.List;

/**
 * Created by yxy on 9/12/17.
 */
public class MostUVService {
    //前端给查询数字，返回相应数量的UV排序值 number<=20
    public List<MostUV> GetMostUV(int number) {
        Connection conn=null;
        List<MostUV> list=null;
        try {
            conn = Sqldb.getDefaultConnection();
            list = MostUVDAO.GetMostUV(conn, number);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Sqldb.closeConnection(conn);
        }
        return list;
    }
}
