package mapreduce.writable;

/**
 * Created by root on 9/7/17.
 */
public class DateDevWritable extends PairWritable<DateWritable,TextComparable> {
    public DateDevWritable() {
        super();
        first=new DateWritable();
        second=new TextComparable();
    }

    public DateDevWritable(DateWritable first, TextComparable second) {
        super(first, second);
    }
}
