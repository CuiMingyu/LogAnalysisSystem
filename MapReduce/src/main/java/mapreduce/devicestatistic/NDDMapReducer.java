package mapreduce.devicestatistic;

/**
 * Created by root on 9/7/17.
 *
 * calculate New Device amount of each Date;
 */
public class NDDMapReducer {
    static private String MRName="Device's earliest present date";
    static private String inputPath="/user/hive/warehouse/log";
    static private String outputPath="/LogAnalysisSystem/DFPD/output";
    static private String hdfsURL="hdfs://scm001:9000";
    static private int gmt=8;
    static private String dateFormatPattern="yyyy-MM-dd";
}
