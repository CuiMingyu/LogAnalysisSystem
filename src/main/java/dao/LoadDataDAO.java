package dao;

import model.CityRateData;
import model.MostPV;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/10/17.
 */
public class LoadDataDAO {
    private static String astable="cityrate";
    private static String nddtable="newstyle";
    private static String titable="timerate";
    private static String rateinfo="rateinfo";
    private static String mostpv="mostpv";
    private static String mostuv="mostuv";
    private static String mostip="mostip";
    public static boolean createASTable(Connection conn)
            throws SQLException{
        Statement stat=conn.createStatement();
        boolean result=stat.execute("DROP TABLE IF EXISTS "+astable);
        result&=stat.execute("CREATE TABLE IF NOT EXISTS "+astable+"(" +
                "time VARCHAR(255)," +
                "city_id VARCHAR(255)," +
                "PV INT," +
                "UV INT" +
                ");");
        return result;
    }
    public static int loadIntoASTable(Connection conn,String inputPath)
            throws SQLException{
        createASTable(conn);
        Statement stat=conn.createStatement();
        int result=stat.executeUpdate("LOAD DATA LOCAL INFILE '" +
                inputPath+
                "' INTO TABLE "+astable+
                " FIELDS TERMINATED BY '\\t' " +
                "(time,city_id,pv,uv) " +
                ";");
        return result;
    }
    public static boolean createNDDTable(Connection conn)
            throws SQLException{
        Statement stat=conn.createStatement();
        boolean result=stat.execute("DROP TABLE IF EXISTS "+nddtable);
        result&=stat.execute("CREATE TABLE IF NOT EXISTS "+nddtable+"(" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "date DATE," +
                "style VARCHAR(40)," +
                "number INT" +
                ");");
        return result;
    }
    public static int loadIntoNDDTable(Connection conn,String inputPath)
            throws SQLException{
        createNDDTable(conn);
        Statement stat=conn.createStatement();
        int result=stat.executeUpdate("LOAD DATA LOCAL INFILE '" +
                inputPath+
                "' INTO TABLE "+nddtable+
                " FIELDS TERMINATED BY '\\t' " +
                "(@date,style,number) " +
                "set date=STR_TO_DATE(@date,'%Y-%m-%d') " +
                ";");
        return result;
    }
    public static boolean createTITable(Connection conn)
            throws SQLException{
        Statement stat=conn.createStatement();
        boolean result=stat.execute("DROP TABLE IF EXISTS "+titable);
        result&=stat.execute("CREATE TABLE IF NOT EXISTS "+titable+"(" +
                "date DATE," +
                "time INT," +
                "PV INT," +
                "UV INT" +
                ");");
        return result;
    }
    public static int loadIntoTITable(Connection conn,String inputPath)
            throws SQLException{
        createTITable(conn);
        Statement stat=conn.createStatement();
        int result=stat.executeUpdate("LOAD DATA LOCAL INFILE '" +
                inputPath+
                "' INTO TABLE "+titable+
                " FIELDS TERMINATED BY '\\t' " +
                "(@date,time,PV,UV) " +
                "set date=STR_TO_DATE(@date,'%Y-%m-%d') " +
                ";");
        return result;
    }
    private static boolean createRateInfo(Connection conn)
            throws SQLException{
        Statement stat=conn.createStatement();
        boolean result=stat.execute("DROP TABLE IF EXISTS "+rateinfo);
        result&=stat.execute("CREATE TABLE IF NOT EXISTS "+rateinfo +"(" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "date DATE," +
                "city VARCHAR(20)," +
                "PV INT," +
                "UV INT," +
                "rate DOUBLE);");
        return result;
    }
    public static void transferASTable(Connection conn)
            throws SQLException{
            createRateInfo(conn);
            PreparedStatement ps=null;
            ResultSet rs=null;
            List<CityRateData> cdList=new ArrayList<CityRateData>();
            ps=conn.prepareStatement("select * from cityrate");
            rs=ps.executeQuery();

            while(rs.next()){
                CityRateData cd=new CityRateData();
                double rate=(rs.getInt("PV")/rs.getInt("UV"));
                String city=CityRateDataDAO.SelectCityByID(conn,rs.getString("city_id"));
                CityRateDataDAO.addRateInfo(conn,Date.valueOf(rs.getString("time")),city,rs.getInt("PV"),rs.getInt("UV"),rate);
            }
    }
    private static boolean createMostPVTable(Connection conn)
            throws SQLException{
        Statement stat=conn.createStatement();
        boolean result=stat.execute("DROP TABLE IF EXISTS "+mostpv);
        result&=stat.execute("CREATE TABLE IF NOT EXISTS "+mostpv +"(" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "urlname VARCHAR(20)," +
                "PV INT);");
        return result;
    }
    public static int loadIntoPVTable(Connection conn,String inputPath)
            throws SQLException{
        createMostPVTable(conn);
        Statement stat=conn.createStatement();
        int result=stat.executeUpdate("LOAD DATA LOCAL INFILE '" +
                inputPath+
                "' INTO TABLE "+mostpv+
                " FIELDS TERMINATED BY '\\t' " +
                "(urlname,PV) " +
                ";");
        return result;
    }
    private static boolean createMostUVTable(Connection conn)
            throws SQLException{
        Statement stat=conn.createStatement();
        boolean result=stat.execute("DROP TABLE IF EXISTS "+mostuv);
        result&=stat.execute("CREATE TABLE IF NOT EXISTS "+mostuv +"(" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "urlname VARCHAR(20)," +
                "UV INT);");
        return result;
    }
    public static int loadIntoUVTable(Connection conn,String inputPath)
            throws SQLException{
        createMostUVTable(conn);
        Statement stat=conn.createStatement();
        int result=stat.executeUpdate("LOAD DATA LOCAL INFILE '" +
                inputPath+
                "' INTO TABLE "+mostpv+
                " FIELDS TERMINATED BY '\\t' " +
                "(urlname,UV) " +
                ";");
        return result;
    }
    private static boolean createMostIPTable(Connection conn)
            throws SQLException{
        Statement stat=conn.createStatement();
        boolean result=stat.execute("DROP TABLE IF EXISTS "+mostip);
        result&=stat.execute("CREATE TABLE IF NOT EXISTS "+mostip +"(" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "urlname VARCHAR(20)," +
                "IP INT);");
        return result;
    }
    public static int loadIntoIPTable(Connection conn,String inputPath)
            throws SQLException{
        createMostIPTable(conn);
        Statement stat=conn.createStatement();
        int result=stat.executeUpdate("LOAD DATA LOCAL INFILE '" +
                inputPath+
                "' INTO TABLE "+mostip+
                " FIELDS TERMINATED BY '\\t' " +
                "(urlname,IP) " +
                ";");
        return result;
    }
}
