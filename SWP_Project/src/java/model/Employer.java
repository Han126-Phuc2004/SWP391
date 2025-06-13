package model;

public class Employer {
    private int accID;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String website;

    // Default constructor
    public Employer() {
    }

    // Full constructor
    public Employer(int accID, String name, String address, String email, 
            String phone, String website) {
        this.accID = accID;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    // Getters and Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Employer{" + 
                "accID=" + accID + 
                ", name='" + name + '\'' + 
                ", address='" + address + '\'' + 
                ", email='" + email + '\'' + 
                ", phone='" + phone + '\'' + 
                ", website='" + website + '\'' + 
                '}';
    }
} 