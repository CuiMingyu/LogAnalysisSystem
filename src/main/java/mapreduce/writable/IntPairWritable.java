package mapreduce.writable;

import org.apache.hadoop.io.IntWritable;

/**
 * Created by root on 9/6/17.
 */
public class IntPairWritable extends PairWritable<IntWritable,IntWritable>{
    public IntPairWritable(){
        super();
        first=new IntWritable();
        second=new IntWritable();
    }

    public IntPairWritable(IntWritable first, IntWritable second) {
        super(first, second);
    }
}
