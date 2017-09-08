package mapreduce.devicestatistic;

import mapreduce.writable.DateWritable;
import mapreduce.writable.PhoneDevWritable;
import mapreduce.writable.TextComparable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
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

/**
 * Created by root on 9/7/17.
 *
 * calculate New Device amount of each Date;
 */
public class NDDMapReducer {
    static private String MRName="Device's earliest present date";
    static private String inputPath="/LogAnalysisSystem/DFPD/output";
    static private String outputPath="/LogAnalysisSystem/NDD/output";
    static private String hdfsURL="hdfs://scm001:9000";
    static private int gmt=8;
    static private String dateFormatPattern="yyyy-MM-dd";
    static class NDDMapper extends Mapper<LongWritable,Text,PhoneDevWritable,DateWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line=value.toString();
            String[] parts=line.split("\t");
            Date d=new Date(Long.parseLong(parts[0]));
            String phone=parts[1];
            String dev=parts[4];
            System.out.println(parts[1]+"\t"+parts[4]);
            context.write(new PhoneDevWritable(new TextComparable(phone),new TextComparable(dev)),
                    new DateWritable(DateUtil.transform(d,gmt),new SimpleDateFormat(dateFormatPattern)));
        }
    }
    static class DFPDReducer extends Reducer<PhoneDevWritable,DateWritable,PhoneDevWritable,DateWritable> {

        /**
         * find the earliest Date in values
         * @param key
         * @param values
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void reduce(PhoneDevWritable key, Iterable<DateWritable> values, Context context)
                throws IOException, InterruptedException {
            java.util.Date earliest=null;
            for(DateWritable dw:values){
                java.util.Date d=dw.getDate();
                if(earliest==null||d.compareTo(earliest)<0)
                    earliest=d;
            }
            context.write(key,new DateWritable(earliest,new SimpleDateFormat(dateFormatPattern)));
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
        job.setOutputKeyClass(PhoneDevWritable.class);
        job.setOutputValueClass(DateWritable.class);
        job.setMapOutputValueClass(DateWritable.class);
        job.setMapOutputKeyClass(PhoneDevWritable.class);
        job.setMapperClass(DFPDMapReducer.DFPDMapper.class);
        job.setReducerClass(DFPDMapReducer.DFPDReducer.class);
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
