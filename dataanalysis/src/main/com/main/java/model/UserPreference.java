package main.java.model;

/**
 * Created by root on 9/18/17.
 */
public class UserPreference {
    private String phone;
    private String label;
    private int type;
    public UserPreference(String phone, String label, int type) {
        this.phone = phone;
        this.label = label;
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
