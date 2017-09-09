package mapreduce.devicestatistic;

import mapreduce.writable.DateDevWritable;
import mapreduce.writable.DateWritable;
import mapreduce.writable.PhoneDevWritable;
import mapreduce.writable.TextComparable;
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
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by root on 9/7/17.
 *
 * calculate New Device amount of each Date;
 */
public class NDDMapReducer {
    static private String JobName;
    static private String inputPath;
    static private String outputPath;
    static private String hdfsURL;
    static private int gmt;
    static private String dateFormatPattern;
    static {
        setJobName("Calculate New Device Amount of Date");
        String currentDir=System.getProperty("user.dir");
        setInputPath(currentDir);
        setOutputPath(currentDir+"/NDDoutput");
        setHdfsURL("hdfs://localhost:9000");
        setGmt(8);
        setDateFormatPattern("yyyy-MM-dd");
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
        NDDMapReducer.inputPath = inputPath;
    }

    public static String getOutputPath() {
        return outputPath;
    }

    public static void setOutputPath(String outputPath) {
        NDDMapReducer.outputPath = outputPath;
    }

    public static String getHdfsURL() {
        return hdfsURL;
    }

    public static void setHdfsURL(String hdfsURL) {
        NDDMapReducer.hdfsURL = hdfsURL;
    }

    public static int getGmt() {
        return gmt;
    }

    public static void setGmt(int gmt) {
        NDDMapReducer.gmt = gmt;
    }

    public static String getDateFormatPattern() {
        return dateFormatPattern;
    }

    public static void setDateFormatPattern(String dateFormatPattern) {
        NDDMapReducer.dateFormatPattern = dateFormatPattern;
    }

    static class NDDMapper extends Mapper<LongWritable,Text,DateDevWritable,IntWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line=value.toString();
            String[] parts=line.split("\t");
            SimpleDateFormat format=new SimpleDateFormat(dateFormatPattern);
            Date d;
            try {
                d = format.parse(parts[2]);
            }catch(Exception e){
                e.printStackTrace();
                d=new Date();
            }
            String dev=parts[1];
            //System.out.println(parts[2]+"\t"+parts[1]);
            context.write(new DateDevWritable(new DateWritable(d,dateFormatPattern),new TextComparable(dev)),new IntWritable(1));
        }
    }
    static class NDDReducer extends Reducer<DateDevWritable,IntWritable,DateDevWritable,IntWritable> {

        /**
         * merge the counter of each (date,dev) pair;
         * @param key
         * @param values
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void reduce(DateDevWritable key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int sum=0;
            for(IntWritable cnt:values){
                sum+=cnt.get();
            }
            context.write(key,new IntWritable(sum));
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
        job.setJarByClass(DFPDMapReducer.class);
        job.setJobName(JobName);
        job.setOutputKeyClass(DateDevWritable.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setMapOutputKeyClass(DateDevWritable.class);
        job.setMapperClass(NDDMapper.class);
        job.setReducerClass(NDDReducer.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job,new Path(inputPath));
        FileOutputFormat.setOutputPath(job,new Path(outputPath));
        job.waitForCompletion(true);
    }
    public static void main(String[] args) {
        try {
            new NDDMapReducer().run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
