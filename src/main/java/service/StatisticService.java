package service;

import dao.LoadDataDAO;
import mapreduce.activitystatistic.ActivityMapReducer;
import mapreduce.devicestatistic.DFPDMapReducer;
import mapreduce.devicestatistic.NDDMapReducer;
import mapreduce.timestatistic.TimeIntervalMapReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import util.FileUtil;
import util.Sqldb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by root on 9/8/17.
 */
public class StatisticService {
    static private String inputPath="/user/hive/warehouse/loganalysis.db/log";
    static private String outputPath="/LogAnalysisSystem";
    static private String hdfsUrl="hdfs://scm001:9000";
    static private String localPath="/tmp/LogAnalysisSystem";
    static private String activityStatisticDir="/ASOutput";
    static private String DFPDStatisticDir="/DFPDOutput";
    static private String NDDStatisticDir="/NDDOutput";
    static private String TIStatisticDir="/TimeIntervalOutput";
    static private String ASOutputPath=outputPath+activityStatisticDir;
    static private String DFPDOutputPath=outputPath+DFPDStatisticDir;
    static private String NDDOutputPath=outputPath+NDDStatisticDir;
    static private String TIOutputPath=outputPath+TIStatisticDir;

    static private Configuration conf=new Configuration();
    public static void ActivityStatistic(){
        ActivityMapReducer.setInputPath(inputPath);
        ActivityMapReducer.setOutputPath(ASOutputPath);
        try {
            ActivityMapReducer.run(conf);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void DeviceStatistic(){
        DFPDMapReducer.setInputPath(inputPath);
        DFPDMapReducer.setOutputPath(DFPDOutputPath);
        try{
            DFPDMapReducer.run(conf);
        }catch(Exception e){
            e.printStackTrace();
        }

        NDDMapReducer.setInputPath(DFPDOutputPath);
        NDDMapReducer.setOutputPath(NDDOutputPath);
        try{
            NDDMapReducer.run(conf);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void TimeIntervalStatistic(){
        TimeIntervalMapReducer.setInputPath(inputPath);
        TimeIntervalMapReducer.setOutputPath(TIOutputPath);
        try{
            TimeIntervalMapReducer.run(conf);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void runStatistic(){
        System.out.println("Starting Activity Statistic...");
        ActivityStatistic();
        System.out.println("Activity Statistic ended");
        System.out.println("Starting Device Statistic...");
        DeviceStatistic();
        System.out.println("Device Statistic ended");
        System.out.println("Starting Time Interval Statistic...");
        TimeIntervalStatistic();
        System.out.println("Time Interval Statistic ended");
    }
    public static void loadIntoMysql() throws SQLException{
        Connection conn=null;
        try {
            conn= Sqldb.getDefaultConnection();
            System.out.println("Load ASOutput into database...");
            LoadDataDAO.loadIntoASTable(conn,localPath + activityStatisticDir + "/part-r-00000");
            LoadDataDAO.transferASTable(conn);
            System.out.println("Completed.");

            System.out.println("Load NDDOutput into database...");
            LoadDataDAO.loadIntoNDDTable(conn,localPath+NDDStatisticDir+"/part-r-00000");
            System.out.println("Completed.");

            System.out.println("Load TIOutput into database...");
            LoadDataDAO.loadIntoTITable(conn,localPath+TIStatisticDir+"/part-r-00000");
            System.out.println("Completed.");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Sqldb.closeConnection(conn);
        }
    }
    public static void copyTolocal()
        throws IOException{
        FileSystem fs=FileSystem.get(conf);
        System.out.println("Starting for copying ASOutput to local..");
        Path srcPath=new Path(ASOutputPath);
        Path dstPath=new Path(localPath+activityStatisticDir);
        try {
            FileUtil.deleteFile(localPath+activityStatisticDir);
            fs.copyToLocalFile(false, srcPath, dstPath);
            System.out.println("Completed.");
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Starting for copying NDDOutput to local..");
        dstPath=new Path(localPath+NDDStatisticDir);
        srcPath=new Path(NDDOutputPath);
        try {
            FileUtil.deleteFile(localPath+NDDStatisticDir);
            fs.copyToLocalFile(false, srcPath, dstPath);
            System.out.println("Completed.");
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Starting for copying TIOutput to local..");
        dstPath=new Path(localPath+TIStatisticDir);
        srcPath=new Path(TIOutputPath);
        try {
            FileUtil.deleteFile(localPath+TIStatisticDir);
            fs.copyToLocalFile(false, srcPath, dstPath);
            System.out.println("Completed.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void init(){
        conf.set("fs.default.name", hdfsUrl);
        conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
    }
    public static void  main(String[] args){
        try {
            init();
            runStatistic();
            copyTolocal();
            loadIntoMysql();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
