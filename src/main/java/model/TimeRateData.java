package model;

import java.sql.Date;

/**
 * Created by yxy on 9/9/17.
 */
public class TimeRateData {
    private Date date;
    private int time=0;
    private int PV=0;
    private int UV=0;

    public void setTimeRateData(Date date,int time,int PV,int UV){
        this.date=date;
        this.time=time;
        this.PV=PV;
        this.UV=UV;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPV() {
        return PV;
    }

    public void setPV(int PV) {
        this.PV = PV;
    }

    public int getUV() {
        return UV;
    }

    public void setUV(int UV) {
        this.UV = UV;
    }

    public String toString(){
        return date.toString()+'\t'+String.valueOf(time)+'\t'+String.valueOf(PV)+'\t'+String.valueOf(UV);
    }
}
