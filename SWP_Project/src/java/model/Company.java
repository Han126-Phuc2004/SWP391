package model;

import java.sql.Timestamp;

public class Company {
    private int companyID;
    private int accID;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private boolean isApproved;

    public Company() {
    }

    public Company(int companyID, int accID, String name, String address, String phone, 
                  String email, String website, boolean isApproved) {
        this.companyID = companyID;
        this.accID = accID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.isApproved = isApproved;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyID=" + companyID +
                ", accID=" + accID +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", isApproved=" + isApproved +
                '}';
    }
} 