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
    private String dateFormatPattern;
    private SimpleDateFormat format;
    public DateWritable(Date date, String pattern) {
        this();
        this.setDate(date);
        this.setDateFormatPattern(pattern);
    }

    public DateWritable() {
        this.date = new Date(0);
        this.setDate(new Date());
        this.setDateFormatPattern("yyyy-MM-dd");
        this.format =new SimpleDateFormat(dateFormatPattern);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getDateFormatPattern() {
        return dateFormatPattern;
    }

    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
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
        format.applyPattern(getDateFormatPattern());
        dataOutput.writeUTF(dateFormatPattern);
        dataOutput.writeUTF(format.format(date));

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        dateFormatPattern=dataInput.readUTF();
        String datestr=dataInput.readUTF();
        //System.out.println(datestr);
        format.applyPattern(dateFormatPattern);
        try {
            date = format.parse(datestr);
        }catch(Exception e){
            e.printStackTrace();
            date=new Date();
        }
    }

    @Override
    public String toString() {
        format.applyPattern(dateFormatPattern);
        return format.format(date);
    }
}
