package mapreduce.timestatistic;

import mapreduce.devicestatistic.DFPDMapReducer;
import mapreduce.devicestatistic.NDDMapReducer;
import mapreduce.writable.DateDevWritable;
import mapreduce.writable.DateWritable;
import mapreduce.writable.IntPairWritable;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by root on 9/8/17.
 */
public class TimeIntervalMapReducer {
    static private String MRName="TimeIntervalStatistic";
    static private String inputPath="/user/hive/warehouse/log";
    static private String outputPath="/LogAnalysisSystem/TimeIntervalStatistic/output";
    static private String hdfsURL="hdfs://scm001:9000";
    static private int gmt=8;
    static private String dateFormatPattern="yyyy-MM-dd\tHH";
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
            System.out.println(key+"\t"+pv+"\t"+uv);
            context.write(key,new IntPairWritable(new IntWritable(pv),new IntWritable(uv)));
        }
    }
    public static void run()
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
        job.setJobName(MRName);
        job.setOutputKeyClass(DateWritable.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setMapOutputKeyClass(DateWritable.class);
        job.setMapperClass(TimeIntervalMapper.class);
        job.setReducerClass(TimeIntervalReducer.class);
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