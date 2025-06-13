package model;

public class EmployerApproval {
    private int accID;
    private String email;
    private String companyName;
    private String website;

    public EmployerApproval() {
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "EmployerApproval{" + "accID=" + accID + ", email=" + email + ", companyName=" + companyName + ", website=" + website + '}';
    }
    
} 