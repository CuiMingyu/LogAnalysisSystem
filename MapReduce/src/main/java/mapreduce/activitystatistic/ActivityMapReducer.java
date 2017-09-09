package mapreduce.activitystatistic;

import mapreduce.writable.DateCityWritable;
import mapreduce.writable.DateWritable;
import mapreduce.writable.IntPairWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import util.DateUtil;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by root on 9/6/17.
 */
public class ActivityMapReducer {
    static private String JobName;
    static private String inputPath;
    static private String outputPath;
    static private String hdfsURL;
    static private int gmt;
    static private String dateFormatPattern;

    public static String getJobName() {
        return JobName;
    }

    public static void setJobName(String jobName) {
        JobName = jobName;
    }

    public static String getInputPath() {
        return inputPath;
    }

    public static void setInputPath(String inputPath) {
        ActivityMapReducer.inputPath = inputPath;
    }

    public static String getOutputPath() {
        return outputPath;
    }

    public static void setOutputPath(String outputPath) {
        ActivityMapReducer.outputPath = outputPath;
    }

    public static String getHdfsURL() {
        return hdfsURL;
    }

    public static void setHdfsURL(String hdfsURL) {
        ActivityMapReducer.hdfsURL = hdfsURL;
    }

    public static int getGmt() {
        return gmt;
    }

    public static void setGmt(int gmt) {
        ActivityMapReducer.gmt = gmt;
    }

    public static String getDateFormatPattern() {
        return dateFormatPattern;
    }

    public static void setDateFormatPattern(String dateFormatPattern) {
        ActivityMapReducer.dateFormatPattern = dateFormatPattern;
    }

    static{

        setJobName("Activity Statistic");
        setInputPath(System.getProperty("user.dir"));
        setOutputPath(inputPath+"/ASoutput");
        setHdfsURL("hdfs:/localhost:9000");
        setGmt(8);
        setDateFormatPattern("yyyy-MM-dd");
    }


    static class ActivityMapper extends Mapper<LongWritable,Text,DateCityWritable,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line=value.toString();
            String[] parts=line.split("\t");
            Date d=new Date(Long.parseLong(parts[0]));
            Integer cid=Integer.parseInt(parts[2]);
            String phone=parts[1];
            System.out.println(phone);
            System.out.println(phone);
            context.write(new DateCityWritable(new DateWritable(DateUtil.transform(d,gmt),dateFormatPattern),
                    new IntWritable(cid)),new Text(phone));
        }
    }
    static class ActivityReducer extends Reducer<DateCityWritable,Text,DateCityWritable,IntPairWritable> {

        @Override
        protected void reduce(DateCityWritable key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            int pv=0,uv=0;
            Set<String> set=new TreeSet<String>();
            for(Text text:values){
                String phone=text.toString();
                pv++;
                if(!set.contains(phone)){
                    uv++;
                    set.add(phone);
                }
            }
            context.write(key,new IntPairWritable(new IntWritable(pv),new IntWritable(uv)));
        }
    }
    static public void run()
            throws IOException,InterruptedException,ClassNotFoundException{
        Configuration conf=new Configuration();
        conf.set("fs.default.name", hdfsURL);
        conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        FileSystem fs = FileSystem.get(conf);
        fs.delete(new Path(outputPath), true);
        fs.close();
        Job job=Job.getInstance(conf);
        job.setJarByClass(ActivityMapReducer.class);
        job.setJobName(JobName);
        job.setOutputKeyClass(DateCityWritable.class);
        job.setOutputValueClass(IntPairWritable.class);
        job.setMapOutputKeyClass(DateCityWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setMapperClass(ActivityMapper.class);
        job.setReducerClass(ActivityReducer.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job,new Path(inputPath));
        FileOutputFormat.setOutputPath(job,new Path(outputPath));
        job.waitForCompletion(true);
    }
    public static void main(String[] args) {
        try {
            run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
