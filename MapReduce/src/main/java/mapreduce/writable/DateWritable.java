package mapreduce.writable;

import org.apache.hadoop.hbase.security.visibility.ParseException;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by root on 9/7/17.
 */
public class DateWritable implements WritableComparable<DateWritable>{

    private Date date;
    private DateFormat format;

    public DateWritable(Date date, DateFormat format) {
        this.setDate(date);
        this.setFormat(format);
    }

    public DateWritable() {
        this.date = new Date(0);
        this.format =new SimpleDateFormat("yyyy-MM-dd");
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public DateFormat getFormat() {
        return format;
    }

    public void setFormat(DateFormat format) {
        this.format = format;
    }

    @Override
    public int compareTo(DateWritable o) {
        long result=date.getTime()-o.getDate().getTime();
        if(result>0)
            return 1;
        else if(result==0)
            return 0;
        else return -1;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        //System.out.println(date.getTime()+"\t"+format.format(date));
        dataOutput.writeUTF(format.format(date));

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        String datestr=dataInput.readUTF();
        //System.out.println(datestr);
        try {
            date = format.parse(datestr);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return format.format(date);
    }
}
