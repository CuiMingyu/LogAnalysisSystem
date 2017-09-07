package mapreduce;

import mapreduce.writable.DateCityWritable;
import mapreduce.writable.IntPairWritable;
import org.apache.calcite.util.mapping.IntPair;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.serde2.io.DateWritable;
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

import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by root on 9/6/17.
 */
public class ActivityMapReducer {
    static private String inputPath="/user/root/log";
    static private String outputPath="/LogAnalysisSystem/ActivityAnalysis/output";
    static class ActivityMapper extends Mapper<LongWritable,Text,DateCityWritable,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line=value.toString();
            String[] parts=line.split("\t");
            Date d=new Date(Long.parseLong(parts[0]));
            Integer cid=Integer.parseInt(parts[2]);
            String phone=parts[3];
            context.write(new DateCityWritable(new DateWritable(d),new IntWritable(cid)),new Text(phone));
        }
    }
    static class ActivityReducer extends Reducer<DateCityWritable,Text,DateCityWritable,IntPairWritable> {
        Set<String> set;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            set=new TreeSet<String>();
        }

        @Override
        protected void reduce(DateCityWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int pv=0,uv=0;
            for(Text text:values){
                String mac=text.toString();
                pv++;
                if(!set.contains(mac)){
                    uv++;
                    set.add(mac);
                }
            }
            context.write(key,new IntPairWritable(new IntWritable(pv),new IntWritable(uv)));
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
            set.clear();
        }
    }
    public static void run()
            throws IOException,InterruptedException,ClassNotFoundException{
        Configuration conf=new Configuration();
        conf.set("fs.default.name", "hdfs://yxy:9000");
        conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        FileSystem fs = FileSystem.get(conf);
        fs.delete(new Path(outputPath), true);
        fs.close();
        Job job=Job.getInstance(conf);
        job.setJarByClass(ActivityMapReducer.class);
        job.setJobName("ActivityAnalysis");
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
