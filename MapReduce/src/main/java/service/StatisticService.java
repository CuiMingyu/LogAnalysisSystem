package service;

import mapreduce.activitystatistic.ActivityMapReducer;
import mapreduce.devicestatistic.DFPDMapReducer;
import mapreduce.devicestatistic.NDDMapReducer;
import mapreduce.timestatistic.TimeIntervalMapReducer;

/**
 * Created by root on 9/8/17.
 */
public class StatisticService {
    static private String inputPath="/user/hive/warehouse/log";
    static private String outputPath="/LogAnalysisSystem";
    static private String activityStatisticDir="/ASOutput";
    static private String DFPDStatisticDir="/DFPDOutput";
    static private String NDDStatisticDir="/NDDOutput";
    static private String TIStatisticDir="/TimeIntervalOutput";
    static private String hdfsUrl="hdfs://scm001:9000";
    public static void ActivityStatistic(){
        String ASOutputPath=outputPath+activityStatisticDir;
        ActivityMapReducer.setHdfsURL(hdfsUrl);
        ActivityMapReducer.setInputPath(inputPath);
        ActivityMapReducer.setOutputPath(ASOutputPath);
        try {
            ActivityMapReducer.run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void DeviceStatistic(){
        String DFPDOutputPath=outputPath+DFPDStatisticDir;
        DFPDMapReducer.setHdfsURL(hdfsUrl);
        DFPDMapReducer.setInputPath(inputPath);
        DFPDMapReducer.setOutputPath(DFPDOutputPath);
        try{
            DFPDMapReducer.run();
        }catch(Exception e){
            e.printStackTrace();
        }
        String NDDOutputPath=outputPath+NDDStatisticDir;
        NDDMapReducer.setHdfsURL(hdfsUrl);
        NDDMapReducer.setInputPath(DFPDOutputPath);
        NDDMapReducer.setOutputPath(NDDOutputPath);
        try{
            NDDMapReducer.run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void TimeIntervalStatistic(){
        String TIOutputPath=outputPath+TIStatisticDir;
        TimeIntervalMapReducer.setHdfsURL(hdfsUrl);
        TimeIntervalMapReducer.setInputPath(inputPath);
        TimeIntervalMapReducer.setOutputPath(TIOutputPath);
        try{
            TimeIntervalMapReducer.run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void run(){
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
    public static void  main(String[] args){
        run();
    }
}
