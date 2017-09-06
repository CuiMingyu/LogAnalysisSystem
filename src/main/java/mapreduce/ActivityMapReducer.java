package mapreduce;

import mapreduce.writable.DateCityWritable;
import mapreduce.writable.IntPairWritable;
import org.apache.hadoop.hive.serde2.io.DateWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by root on 9/6/17.
 */
public class ActivityMapReducer {
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
}
