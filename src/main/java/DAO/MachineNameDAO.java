package DAO;

import util.Sqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxy on 9/8/17.
 */
public class MachineNameDAO {
    private Connection con= Sqldb.getConnection();

    //get all style name
    public List<String> GetMachineName(){
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<String> styleList=new ArrayList<String>();
        try{
            ps=con.prepareStatement("select * from style");
            rs=ps.executeQuery();

            while(rs.next()){
                styleList.add(rs.getString("style"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            Sqldb.closeConection();
        }
        return styleList;
    }

    public static void main(String args[]){
        MachineNameDAO snDAO=new MachineNameDAO();
        List<String> list=snDAO.GetMachineName();
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}
