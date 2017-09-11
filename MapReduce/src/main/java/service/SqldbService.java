package service;

import org.apache.directory.api.util.DateUtils;

import java.sql.*;

/**
 * Created by root on 9/10/17.
 */
public class SqldbService {
    private static String astable="cityrate";
    private static String nddtable="newstyle";
    private static String titable="timerate";
    public static boolean createRateInfo(Connection conn)
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
    public static int loadIntoRateInfo(Connection conn,String inputPath)
            throws SQLException{
        createRateInfo(conn);
        Statement stat=conn.createStatement();
        int result=stat.executeUpdate("LOAD DATA LOCAL INFILE '" +
                inputPath+
                "' INTO TABLE "+astable+
                " FIELDS TERMINATED BY '\\t' " +
                "(time,city_id,pv,uv) " +
                ";");
        return result;
    }
    public static boolean createNewStyle(Connection conn)
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
    public static int loadIntoNewStyle(Connection conn,String inputPath)
            throws SQLException{
        createNewStyle(conn);
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
    public static boolean createTimeRate(Connection conn)
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
    public static int loadIntoTimeRate(Connection conn,String inputPath)
            throws SQLException{
        createTimeRate(conn);
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
}
