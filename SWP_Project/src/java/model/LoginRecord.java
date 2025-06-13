package model;

import java.sql.Timestamp;

public class LoginRecord {
    private int id;
    private int accID;
    private String username;
    private Timestamp loginTime;
    private Timestamp logoutTime;

    public LoginRecord() {
    }

    public LoginRecord(int id, int accID, String username, Timestamp loginTime, Timestamp logoutTime) {
        this.id = id;
        this.accID = accID;
        this.username = username;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }

}