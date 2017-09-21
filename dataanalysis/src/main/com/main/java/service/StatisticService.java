package main.java.service;

import main.java.dao.LoadDataDAO;
import main.java.mapreduce.activitystatistic.ActivityMapReducer;
import main.java.mapreduce.devicestatistic.DFPDMapReducer;
import main.java.mapreduce.devicestatistic.NDDMapReducer;
import main.java.mapreduce.timestatistic.TimeIntervalMapReducer;
import main.java.util.FileUtil;
import main.java.util.Sqldb;
import main.scala.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by root on 9/8/17.
 */
public class StatisticService {
    static private String inputPath = Global.rawDataPath();
    static private String outputPath = Global.outputRoot();
    static private String hdfsUrl =Global.hdfsUrl();
    static private String localPath = Global.localPath();
    static private String activityStatisticDir = Global.activityStatisticDir();
    static private String DFPDStatisticDir = Global.DFPDStatisticDir();
    static private String NDDStatisticDir = Global.NDDStatisticDir();
    static private String TIStatisticDir = Global.TIStatisticDir();
    static private String PVStatisticDir = Global.PVStatisticDir();
    static private String UVStatisticDir = Global.UVStatisticDir();
    static private String IPStatisticDir = Global.IPStatisticDir();
    static private String UserAnalysisDir=Global.UserAnalysisDir();
    static private String UserDevAnalysisDir=Global.UserDevAnalysisDir();
    static private String ASOutputPath = outputPath + activityStatisticDir;
    static private String DFPDOutputPath = outputPath + DFPDStatisticDir;
    static private String NDDOutputPath = outputPath + NDDStatisticDir;
    static private String TIOutputPath = outputPath + TIStatisticDir;
    static private String clusteringPath=outputPath+Global.clusteringDir();

    static private Configuration conf = new Configuration();

    public static void ActivityStatistic() {
        ActivityMapReducer.setInputPath(inputPath);
        ActivityMapReducer.setOutputPath(ASOutputPath);
        try {
            ActivityMapReducer.run(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DeviceStatistic() {
        DFPDMapReducer.setInputPath(inputPath);
        DFPDMapReducer.setOutputPath(DFPDOutputPath);
        try {
            DFPDMapReducer.run(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }

        NDDMapReducer.setInputPath(DFPDOutputPath);
        NDDMapReducer.setOutputPath(NDDOutputPath);
        try {
            NDDMapReducer.run(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void TimeIntervalStatistic() {
        TimeIntervalMapReducer.setInputPath(inputPath);
        TimeIntervalMapReducer.setOutputPath(TIOutputPath);
        try {
            TimeIntervalMapReducer.run(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void TopSiteStatistic()
            throws IOException {
        System.out.println("Starting PV Statistic...");
        FileSystem fs = FileSystem.get(conf);
        fs.delete(new Path(hdfsUrl + outputPath + PVStatisticDir), true);
        PVCounter.run(hdfsUrl + inputPath, hdfsUrl + outputPath + PVStatisticDir, 20);
        System.out.println("Completed.");
        System.out.println("Starting UV Statistic...");
        fs.delete(new Path(hdfsUrl + outputPath + UVStatisticDir), true);
        UVCounter.run(hdfsUrl + inputPath, hdfsUrl + outputPath + UVStatisticDir, 20);
        System.out.println("Completed.");
        System.out.println("Starting IP Statistic...");
        fs.delete(new Path(hdfsUrl + outputPath + IPStatisticDir), true);
        IPCounter.run(hdfsUrl + inputPath, hdfsUrl + outputPath + IPStatisticDir, 20);
        System.out.println("Completed.");
        fs.close();
    }

    public static void runStatistic()
            throws IOException {
        System.out.println("Starting Activity Statistic...");
        ActivityStatistic();
        System.out.println("Activity Statistic ended");
        System.out.println("Starting Device Statistic...");
        DeviceStatistic();
        System.out.println("Device Statistic ended");
        System.out.println("Starting Time Interval Statistic...");
        TimeIntervalStatistic();
        System.out.println("Time Interval Statistic ended");
        TopSiteStatistic();
        runUserStatistic();
    }
    public static void runUserStatistic()
            throws IOException{
        System.out.println("Starting User Preference Statistic...");
        UserAnalysis.run(inputPath,outputPath,clusteringPath,20);
        System.out.println("User Preference Statistic ended");
        System.out.println("Starting User Dev  Statistic...");
        UserDevAnalysis.run(inputPath,outputPath);
        System.out.println("User Dev Statistic ended");
    }
    public static void loadIntoMysql() throws SQLException {
        Connection conn = null;
        try {
            conn = Sqldb.getDefaultConnection();
            System.out.println("Load ASOutput into database...");
            LoadDataDAO.loadIntoASTable(conn, localPath + activityStatisticDir + "/part-r-00000");
            LoadDataDAO.transferASTable(conn);
            System.out.println("Completed.");

            System.out.println("Load NDDOutput into database...");
            LoadDataDAO.loadIntoNDDTable(conn, localPath + NDDStatisticDir + "/part-r-00000");
            System.out.println("Completed.");

            System.out.println("Load TIOutput into database...");
            LoadDataDAO.loadIntoTITable(conn, localPath + TIStatisticDir + "/part-r-00000");
            System.out.println("Completed.");

            System.out.println("Load PVOutput into database...");
            LoadDataDAO.loadIntoPVTable(conn, localPath + PVStatisticDir + "/part-00000");
            System.out.println("Completed.");

            System.out.println("Load UVOutput into database...");
            LoadDataDAO.loadIntoUVTable(conn, localPath + UVStatisticDir + "/part-00000");
            System.out.println("Completed.");
            System.out.println("Load IPOutput into database...");
            LoadDataDAO.loadIntoIPTable(conn, localPath + IPStatisticDir + "/part-00000");
            System.out.println("Completed.");
            System.out.println("Load UserAnalysis Output into database...");
            LoadDataDAO.loadIntoPreferenceTable(conn, localPath + UserAnalysisDir + "/*");
            System.out.println("Completed.");
            System.out.println("Load UserDev Output into database...");
            LoadDataDAO.loadIntoUserTable(conn, localPath + UserDevAnalysisDir + "/*");
            System.out.println("Completed.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Sqldb.closeConnection(conn);
        }
    }

    public static void HDFSTolocal(FileSystem fs, String oldpath, String newpath) {
        Path srcPath = new Path(oldpath);
        Path dstPath = new Path(newpath);
        try {
            FileUtil.deleteFile(newpath);
            fs.copyToLocalFile(false, srcPath, dstPath);
            System.out.println("Completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyTolocal()
            throws IOException {
        FileSystem fs = FileSystem.get(conf);
        System.out.println("Starting for copying ASOutput to local..");
        HDFSTolocal(fs, ASOutputPath, localPath + activityStatisticDir);
        System.out.println("Starting for copying NDDOutput to local..");
        HDFSTolocal(fs, NDDOutputPath, localPath + NDDStatisticDir);
        System.out.println("Starting for copying TIOutput to local..");
        HDFSTolocal(fs, TIOutputPath, localPath + TIStatisticDir);
        System.out.println("Starting for copying PVOutput to local..");
        HDFSTolocal(fs, outputPath + PVStatisticDir, localPath + PVStatisticDir);
        System.out.println("Starting for copying UVOutput to local..");
        HDFSTolocal(fs, outputPath + UVStatisticDir, localPath + UVStatisticDir);
        System.out.println("Starting for copying IPOutput to local..");
        HDFSTolocal(fs, outputPath + IPStatisticDir, localPath + IPStatisticDir);
        System.out.println("Starting for copying UserAnalysis Output to local..");
        HDFSTolocal(fs,outputPath+UserAnalysisDir,localPath+UserAnalysisDir);
        System.out.println("Starting for copying UserDevAnalysis Output to local..");
        HDFSTolocal(fs,outputPath+UserDevAnalysisDir,localPath+UserDevAnalysisDir);
        fs.close();
    }

    public static void init() {
        conf.set("fs.default.name", hdfsUrl);
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
    }

    public static void main(String[] args) {
        try {
            init();
            runStatistic();
            copyTolocal();
            loadIntoMysql();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
