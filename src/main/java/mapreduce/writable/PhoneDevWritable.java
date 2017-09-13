package main.java.mapreduce.writable;

/**
 * Created by root on 9/7/17.
 */
public class PhoneDevWritable extends PairWritable<TextComparable,TextComparable>{
    public PhoneDevWritable() {
        super();
        first=new TextComparable();
        second=new TextComparable();
    }

    public PhoneDevWritable(TextComparable first, TextComparable second) {
        super(first, second);
    }
}
