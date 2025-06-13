package model;

import java.sql.Timestamp;

public class Account {

    private int accID;
    private String username;
    private String email;
    private String password;
    private boolean isVerified;
    private String verifyToken;
    private String resetToken;
    private Timestamp resetTokenExpiry;
    private int roleID;
    private Timestamp verifiedAt;
    private Timestamp lastLoginAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String avatar;
    private boolean isApproved;
    private int loginAttempts;
    private Timestamp lastFailedLogin;
    private boolean isActive;

    public Account() {
    }

    public Account(int accID, String username, String email, String password, boolean isVerified, String verifyToken, int roleID) {
        this.accID = accID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isVerified = isVerified;
        this.verifyToken = verifyToken;
        this.roleID = roleID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Timestamp getResetTokenExpiry() {
        return resetTokenExpiry;
    }

    public void setResetTokenExpiry(Timestamp resetTokenExpiry) {
        this.resetTokenExpiry = resetTokenExpiry;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public Timestamp getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Timestamp verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Timestamp getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Timestamp lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getLastFailedLogin() {
        return lastFailedLogin;
    }

    public void setLastFailedLogin(Timestamp lastFailedLogin) {
        this.lastFailedLogin = lastFailedLogin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Account{"
                + "accID=" + accID
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + ", isVerified=" + isVerified
                + ", roleID=" + roleID
                + ", verifiedAt=" + verifiedAt
                + ", lastLoginAt=" + lastLoginAt
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + '}';
    }
}
