package mapreduce;

import mapreduce.writable.DateCityWritable;
import mapreduce.writable.IntPairWritable;
import org.apache.calcite.util.mapping.IntPair;
import org.apache.hadoop.conf.Configuration;
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
    static private String inputPath="";
    static private String outputPath="";
    static class ActivityMapper extends Mapper<LongWritable,Text,DateCityWritable,Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line=value.toString();
            String[] parts=line.split("\t");
            Date d=new Date(Long.parseLong(parts[0]));
            Integer cid=Integer.parseInt(parts[2]);
            String mac=parts[5];
            context.write(new DateCityWritable(new DateWritable(d),new IntWritable(cid)),new Text(mac));
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
        Job job=new Job(conf);
        job.setJarByClass(ActivityMapReducer.class);
        job.setJobName("ActivityAnalysis");
        job.setOutputKeyClass(DateCityWritable.class);
        job.setOutputValueClass(IntPairWritable.class);
        job.setMapperClass(ActivityMapper.class);
        job.setReducerClass(ActivityReducer.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job,new Path(inputPath));
        FileOutputFormat.setOutputPath(job,new Path(outputPath));
        job.waitForCompletion(true);
    }
    public static void main(String[] args)
            throws IOException,InterruptedException,ClassNotFoundException{
        run();
    }
}
