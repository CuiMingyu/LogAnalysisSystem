package main.java.service;

import main.java.dao.ByteSeriesDAO;
import main.java.model.ByteSeries;
import main.java.util.Sqldb;

import java.sql.Connection;
import java.util.List;

/**
 * Created by root on 9/18/17.
 */
public class ByteSeriesService {
    public List<ByteSeries> SelectByUserName(String name) {
        Connection conn = null;
        List<ByteSeries> list = null;
        try {
            conn = Sqldb.getDefaultConnection();
            list = ByteSeriesDAO.SelectByUserName(conn, name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Sqldb.closeConnection(conn);
        }
        return list;
    }

    public static void main(String args[]){
        ByteSeriesService bss=new ByteSeriesService();
        List<ByteSeries> list=bss.SelectByUserName("13005267670");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}
