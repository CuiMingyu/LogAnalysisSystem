package main.java.model;

/**
 * Created by yxy on 9/8/17.
 */
public class NewDeviceStyle {
    private String stylename=null;
    private int oldnum=0;
    private int newnum=0;
    private int allnum=0;

    public void setNewDeviceStyle(String stylename,int oldnum,int newnum,int allnum){
        this.stylename=stylename;
        this.oldnum=oldnum;
        this.newnum=newnum;
        this.allnum=allnum;
    }

    public String getStylename() {
        return stylename;
    }

    public void setStylename(String stylename) {
        this.stylename = stylename;
    }

    public int getOldnum() {
        return oldnum;
    }

    public void setOldnum(int oldnum) {
        this.oldnum = oldnum;
    }

    public int getNewnum() {
        return newnum;
    }

    public void setNewnum(int newnum) {
        this.newnum = newnum;
    }

    public int getAllnum() {
        return allnum;
    }

    public void setAllnum(int allnum) {
        this.allnum = allnum;
    }

    public String toString(){
        return stylename+'\t'+String.valueOf(oldnum)+'\t'+String.valueOf(newnum)+'\t'+String.valueOf(allnum);
    }
}
