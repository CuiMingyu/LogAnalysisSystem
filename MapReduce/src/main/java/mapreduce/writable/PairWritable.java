package mapreduce.writable;

import org.apache.hadoop.hive.serde2.io.DateWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by root on 9/6/17.
 */
abstract public class PairWritable<FWritable extends WritableComparable<FWritable>,
        SWritable extends WritableComparable<SWritable>>
        implements WritableComparable<PairWritable<FWritable,SWritable>>{

    protected FWritable first;
    protected SWritable second;
    public PairWritable(){
        first=null;
        second=null;
    }
    public PairWritable(FWritable first, SWritable second) {
        this.setFirst(first);
        this.setSecond(second);
    }

    public void setFirst(FWritable first) {
        this.first = first;
    }

    public void setSecond(SWritable second) {
        this.second = second;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        first.write(dataOutput);
        second.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        first.readFields(dataInput);
        second.readFields(dataInput);
    }

    @Override
    public int compareTo(PairWritable<FWritable,SWritable> o) {
        int result=first.compareTo(o.first);
        if(result!=0)
            return result;
        else return second.compareTo(o.second);
    }
//<<<<<<< HEAD
    @Override
    public String toString() {
        return first.toString() +"\t" + second.toString();
    }
}
