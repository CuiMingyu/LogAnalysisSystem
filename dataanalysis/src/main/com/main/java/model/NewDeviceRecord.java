package main.java.model;

import java.sql.Date;

/**
 * Created by yxy on 9/7/17.
 */
public class NewDeviceRecord {
    private Date date;
    private String style = null;
    private int number = 0;

    public void setNewDeviceRecord(Date date, String style, int number) {
        this.date = date;
        this.style = style;
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String toString() {
        return date.toString() + '\t' + style + '\t' + String.valueOf(number);
    }
}
