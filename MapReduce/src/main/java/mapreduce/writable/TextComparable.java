package mapreduce.writable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by root on 9/7/17.
 */
public class TextComparable implements WritableComparable<TextComparable> {
    String content;
    public TextComparable(){
        content="";
    }
    public TextComparable(String content) {
        this.setContent(content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(TextComparable o) {
        return getContent().compareTo(o.getContent());
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(getContent());
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.setContent(dataInput.readUTF());
    }

    @Override
    public String toString() {
        return content;
    }
}
