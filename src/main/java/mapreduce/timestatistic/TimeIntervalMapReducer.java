package main.java.mapreduce.timestatistic;

import main.java.mapreduce.devicestatistic.DFPDMapReducer;
import main.java.mapreduce.writable.DateWritable;
import main.java.mapreduce.writable.IntPairWritable;
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
import main.java.util.DateUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by root on 9/8/17.
 */
public class TimeIntervalMapReducer {
    static private String JobName;
    static private String inputPath;
    static private String outputPath;
    static private int gmt;
    static private String dateFormatPattern;

    static {
        setJobName("Time interval statistic");
        String currentDir=System.getProperty("user.dir");
        setInputPath(currentDir);
        setOutputPath(currentDir+"/TimeIntervalOutput");
        setGmt(8);
        setDateFormatPattern("yyyy-MM-dd\tHH");
    }

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
        TimeIntervalMapReducer.inputPath = inputPath;
    }

    public static String getOutputPath() {
        return outputPath;
    }

    public static void setOutputPath(String outputPath) {
        TimeIntervalMapReducer.outputPath = outputPath;
    }

    public static int getGmt() {
        return gmt;
    }

    public static void setGmt(int gmt) {
        TimeIntervalMapReducer.gmt = gmt;
    }

    public static String getDateFormatPattern() {
        return dateFormatPattern;
    }

    public static void setDateFormatPattern(String dateFormatPattern) {
        TimeIntervalMapReducer.dateFormatPattern = dateFormatPattern;
    }

    static class TimeIntervalMapper extends Mapper<LongWritable,Text,DateWritable,Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line=value.toString();
            String[] parts=line.split("\t");
            SimpleDateFormat format=new SimpleDateFormat(dateFormatPattern);
            Date d=new Date(Long.parseLong(parts[0]));
            String phone=parts[1];
            //System.out.println(parts[0]+"\t"+parts[1]);
            context.write(new DateWritable(DateUtil.transform(d,gmt),dateFormatPattern),new Text(phone));
        }
    }
    static class TimeIntervalReducer extends Reducer<DateWritable,Text,DateWritable,IntPairWritable> {

        /**
         * merge the counter of each (date,dev) pair;
         * @param key
         * @param values
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void reduce(DateWritable key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            int pv=0,uv=0;
            Set<String> set=new TreeSet<String>();
            for(Text value:values){
                String phone=value.toString();
                pv++;
                if(!set.contains(phone)){
                    uv++;
                    set.add(phone);
                }
            }
            //System.out.println(key+"\t"+pv+"\t"+uv);
            context.write(key,new IntPairWritable(new IntWritable(pv),new IntWritable(uv)));
        }
    }
    static public void run(Configuration conf)
            throws IOException,InterruptedException,ClassNotFoundException{
        FileSystem fs = FileSystem.get(conf);
        fs.delete(new Path(getOutputPath()), true);
        fs.close();
        Job job=Job.getInstance(conf);
        job.setJarByClass(DFPDMapReducer.class);
        job.setJobName(getJobName());
        job.setOutputKeyClass(DateWritable.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setMapOutputKeyClass(DateWritable.class);
        job.setMapperClass(TimeIntervalMapper.class);
        job.setReducerClass(TimeIntervalReducer.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job,new Path(getInputPath()));
        FileOutputFormat.setOutputPath(job,new Path(getOutputPath()));
        job.waitForCompletion(true);
    }
    public static void main(String[] args) {
        Configuration conf=new Configuration();
        conf.set("fs.default.name", "hdfs://scm001:9000");
        conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        try {
            run(conf);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
