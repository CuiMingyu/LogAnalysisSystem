package mapreduce.writable;


import org.apache.hadoop.hive.serde2.io.DateWritable;
import org.apache.hadoop.io.IntWritable;

/**
 * Created by root on 9/6/17.
 */
public class DateCityWritable extends PairWritable<DateWritable,IntWritable> {

    public DateCityWritable(){
        super();
        first=new DateWritable();
        second=new IntWritable();
    }

    public DateCityWritable(DateWritable first, IntWritable second) {
        super(first, second);
    }
}
