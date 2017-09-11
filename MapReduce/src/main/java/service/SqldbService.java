package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by root on 9/10/17.
 */
public class SqldbService {
    private static String rateinfo="rateinfo";
    public static boolean createRateInfo(Connection conn)
            throws SQLException{
        Statement stat=conn.createStatement();
        boolean result=stat.execute("CREATE TABLE IF NOT EXISTS "+rateinfo+"(" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "date DATE," +
                "city VARCHAR(20)," +
                "PV INT," +
                "UV INT," +
                "rate DOUBLE);");
        return result;
    }
    public static int loadIntoRateInfo(Connection conn,String inputPath)
            throws SQLException{
        createRateInfo(conn);
        Statement stat=conn.createStatement();
        int result=stat.executeUpdate("LOAD DATA LOCAL INFILE '" +
                inputPath+
                "' INTO TABLE "+rateinfo+
                " FIELDS TERMINATED BY '\\t' " +
                "(@date,city,pv,uv) " +
                "set date=STR_TO_DATE(@date,'%Y-%m-%d') " +
                ";");
        return result;
    }

}
