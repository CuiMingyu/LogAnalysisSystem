package model;

/**
 * Created by yxy on 9/8/17.
 */
public class NewDeviceMachine {
    private String machinename=null;
    private int oldnum=0;
    private int newnum=0;
    private int allnum=0;

    public void setNewDeviceMachine(String machinename,int oldnum,int newnum,int allnum){
        this.machinename=machinename;
        this.oldnum=oldnum;
        this.newnum=newnum;
        this.allnum=allnum;
    }


    public String getMachinename() {
        return machinename;
    }

    public void setMachinename(String machinename) {
        this.machinename = machinename;
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
        return machinename+'\t'+String.valueOf(oldnum)+'\t'+String.valueOf(newnum)+'\t'+String.valueOf(allnum);
    }
}
