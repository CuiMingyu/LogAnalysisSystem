package main.java.model;

/**
 * Created by root on 9/17/17.
 */
public class ByteSeries {
    private String time;
    private String data;
    private String user;

    public void setByteSeries(String time,String data,String user){
        this.time=time;
        this.data=data;
        this.user=user;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String toString(){
        return time+"\t"+data+"\t"+user;
    }
}
