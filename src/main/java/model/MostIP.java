package model;

/**
 * Created by yxy on 9/12/17.
 */
public class MostIP {
    private String urlname=null;
    private int IP=0;

    public void setMostIP(String urlname,int IP){
        this.urlname=urlname;
        this.IP=IP;
    }
    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public int getIP() {
        return IP;
    }

    public void setIP(int IP) {
        this.IP = IP;
    }

    public String toString(){
        return urlname+'\t'+String.valueOf(IP);
    }
}

