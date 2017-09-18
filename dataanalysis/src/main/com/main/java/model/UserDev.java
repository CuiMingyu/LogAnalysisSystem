package main.java.model;

/**
 * Created by root on 9/18/17.
 */
public class UserDev {
    private String phone;
    private String device;

    public UserDev(String phone, String device) {
        this.phone = phone;
        this.device = device;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
