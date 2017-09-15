package main.java.model;

/**
 * Created by yxy on 9/11/17.
 */
public class MostPV {
    private String urlname=null;
    private int PV=0;

    public void setMostPV(String urlname,int PV){
        this.urlname=urlname;
        this.PV=PV;
    }
    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public int getPV() {
        return PV;
    }

    public void setPV(int PV) {
        this.PV = PV;
    }

    public String toString(){
        return urlname+'\t'+String.valueOf(PV);
    }
}
