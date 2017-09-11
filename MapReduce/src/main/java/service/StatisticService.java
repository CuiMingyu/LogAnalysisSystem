package main.java.service;

import main.java.DAO.loadDataDAO;
import main.java.mapreduce.activitystatistic.ActivityMapReducer;
import main.java.mapreduce.devicestatistic.DFPDMapReducer;
import main.java.mapreduce.devicestatistic.NDDMapReducer;
import main.java.mapreduce.timestatistic.TimeIntervalMapReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import main.java.util.sql.Mysqldb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by root on 9/8/17.
 */
public class StatisticService {
    static private String inputPath="/user/hive/warehouse/log";
    static private String outputPath="/LogAnalysisSystem";
    static private String localPath="/tmp/LogAnalysisSystem";
    static private String activityStatisticDir="/ASOutput";
    static private String DFPDStatisticDir="/DFPDOutput";
    static private String NDDStatisticDir="/NDDOutput";
    static private String TIStatisticDir="/TimeIntervalOutput";
    static private String ASOutputPath=outputPath+activityStatisticDir;
    static private String DFPDOutputPath=outputPath+DFPDStatisticDir;
    static private String NDDOutputPath=outputPath+NDDStatisticDir;
    static private String TIOutputPath=outputPath+TIStatisticDir;
    static private String hdfsUrl="hdfs://scm001:9000";
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
    public static void loadIntoMysql(String url,String username,String password) throws SQLException{
        Connection conn=Mysqldb.getConnection(url,username,password);

        try {
            System.out.println("Load ASOutput into database...");
            loadDataDAO.loadIntoASTable(conn, localPath + activityStatisticDir + "/part-r-00000");
            System.out.println("Completed.");
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            System.out.println("Load NDDOutput into database...");
            loadDataDAO.loadIntoNDDTable(conn,localPath+NDDStatisticDir+"/part-r-00000");
            System.out.println("Completed.");
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            System.out.println("Load TIOutput into database...");
            loadDataDAO.loadIntoTITable(conn,localPath+TIStatisticDir+"/part-r-00000");
            System.out.println("Completed.");
        }catch(Exception e){
            e.printStackTrace();
        }
        Mysqldb.closeConnection();
    }
    public static void copyTolocal()
        throws IOException{
        FileSystem fs=FileSystem.get(conf);
        System.out.println("Starting for copying ASOutput to local..");
        Path srcPath=new Path(ASOutputPath);
        Path dstPath=new Path(localPath+activityStatisticDir);
        try {
            fs.copyToLocalFile(false, srcPath, dstPath);
            System.out.println("Completed.");
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Starting for copying NDDOutput to local..");
        dstPath=new Path(localPath+NDDStatisticDir);
        srcPath=new Path(NDDOutputPath);
        try {
            fs.copyToLocalFile(false, srcPath, dstPath);
            System.out.println("Completed.");
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Starting for copying TIOutput to local..");
        dstPath=new Path(localPath+TIStatisticDir);
        srcPath=new Path(TIOutputPath);
        try {
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
            loadIntoMysql("scm001:3306/loganalysis","admin","123456");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
