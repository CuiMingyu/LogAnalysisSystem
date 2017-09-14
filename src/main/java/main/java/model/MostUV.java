package main.java.model;

/**
 * Created by yxy on 9/12/17.
 */
public class MostUV {
    private String urlname=null;
    private int UV=0;

    public void setMostUV(String urlname,int UV){
        this.urlname=urlname;
        this.UV=UV;
    }
    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public int getUV() {
        return UV;
    }

    public void setUV(int UV) {
        this.UV = UV;
    }

    public String toString(){
        return urlname+'\t'+String.valueOf(UV);
    }
}
