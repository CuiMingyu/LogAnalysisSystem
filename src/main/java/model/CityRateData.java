package main.java.model;

import main.java.util.DateUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class CityRateData{
    private Date date;
    private String name=null;
    private int PV=0;
    private int UV=0;
    private double rate=0;

    public void setCityRateData(Date date,String name,int PV,int UV, double rate){
        this.date=date;
        this.name=name;
        this.PV=PV;
        this.UV=UV;
        this.rate=rate;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date=date;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public int getPV(){
        return PV;
    }

    public void setPV(int PV){
        this.PV=PV;
    }

    public int getUV(){
        return UV;
    }

    public void setUV(int UV){
        this.UV=UV;
    }

    public double getRate(){
        return rate;
    }

    public void setRate(double rate){
        this.rate=rate;
    }

    @Override
    public String toString(){
        return new SimpleDateFormat("yyyy-MM-dd").format(date)+'\t'+name+'\t'+String.valueOf(PV)+'\t'+String.valueOf(UV)+'\t'+Double.toString(rate);
    }
}