package dao;

import connect.DBConnect;
import model.Account;
import model.SecurityUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            conn = new DBConnect().getConnection();
            String sql = "SELECT * FROM Accounts";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = extractAccountFromResultSet(rs);
                accounts.add(acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getAllAccounts: " + e.getMessage());
        } finally {
            closeResources();
        }
        return accounts;
    }

    public List<Account> getAllEmployers() {
        List<Account> employers = new ArrayList<>();
        try {
            conn = new DBConnect().getConnection();
            String sql = "SELECT * FROM Accounts WHERE roleID = 3";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = extractAccountFromResultSet(rs);
                employers.add(acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getAllEmployers: " + e.getMessage());
        } finally {
            closeResources();
        }
        return employers;
    }

    public List<Account> getAllJobSeekers() {
        List<Account> jobSeekers = new ArrayList<>();
        try {
            conn = new DBConnect().getConnection();
            String sql = "SELECT * FROM Accounts WHERE roleID = 2";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = extractAccountFromResultSet(rs);
                jobSeekers.add(acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getAllJobSeekers: " + e.getMessage());
        } finally {
            closeResources();
        }
        return jobSeekers;
    }

    public Account findByVerifyToken(String token) {
        String sql = "SELECT * FROM Accounts WHERE verifyToken = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, token);
            rs = ps.executeQuery();
            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    public boolean verifyAccount(String token) {
        String sql = "UPDATE Accounts SET isVerified = 1, verifyToken = NULL, verifiedAt = ? WHERE verifyToken = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(2, token);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    public Account getAccountById(int accID) {
        String sql = "SELECT * FROM Accounts WHERE accID = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    public Account findByEmail(String email) {
        String sql = "SELECT * FROM Accounts WHERE email = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    public boolean updatePassword(int accID, String hashedPassword) {
        String sql = "UPDATE Accounts SET password = ? WHERE accID = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, hashedPassword);
            ps.setInt(2, accID);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    public boolean updateResetToken(int accID, String resetToken) {
        String sql = "UPDATE Accounts SET resetToken = ?, resetTokenExpiry = ? WHERE accID = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, resetToken);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().plusHours(24)));
            ps.setInt(3, accID);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    public Account findByResetToken(String resetToken) {
        String sql = "SELECT * FROM Accounts WHERE resetToken = ? AND resetTokenExpiry > ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, resetToken);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            rs = ps.executeQuery();
            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    public boolean clearResetToken(int accID) {
        String sql = "UPDATE Accounts SET resetToken = NULL, resetTokenExpiry = NULL WHERE accID = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accID);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    public boolean changePassword(String email, String currentPassword, String newPassword) {
        String sql = "SELECT accID, password FROM Accounts WHERE email = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                int accID = rs.getInt("accID");

                String hashedCurrentPassword = SecurityUtil.hashPassword(currentPassword);
                if (!storedPassword.equals(hashedCurrentPassword)) {
                    return false;
                }

                String hashedNewPassword = SecurityUtil.hashPassword(newPassword);
                return updatePassword(accID, hashedNewPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    public boolean updateAccountStatus(int accID, boolean isActive) {
        String sql = "UPDATE Accounts SET isActive = ? WHERE accID = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, isActive);
            ps.setInt(2, accID);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    public String getEmailByAccID(int accID) throws SQLException {
        Account account = getAccountById(accID);
        return account != null ? account.getEmail() : null;
    }

    public String getUsernameByAccID(int accID) throws SQLException {
        Account account = getAccountById(accID);
        return account != null ? account.getUsername() : null;
    }

    private Account extractAccountFromResultSet(ResultSet rs) throws SQLException {
        Account acc = new Account();
        acc.setAccID(rs.getInt("accID"));
        acc.setUsername(rs.getString("username"));
        acc.setEmail(rs.getString("email"));
        acc.setPassword(rs.getString("password"));
        acc.setVerified(rs.getBoolean("isVerified"));
        acc.setVerifyToken(rs.getString("verifyToken"));
        acc.setRoleID(rs.getInt("roleID"));
        acc.setIsActive(rs.getBoolean("isActive"));
        acc.setAvatar(rs.getString("avatar"));
        return acc;
    }

    // Change avatar
    public boolean updateAvatar(int accID, String avatarPath) {
        String sql = "UPDATE Accounts SET avatar = ? WHERE accID = ?";
        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, avatarPath);
            ps.setInt(2, accID);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Đóng kết nối
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();

        // Test getAllEmployers
        System.out.println("List of Employers:");
        List<Account> employers = dao.getAllEmployers();
        if (employers.isEmpty()) {
            System.out.println("No employers found.");
        } else {
            for (Account acc : employers) {
                System.out.println("ID: " + acc.getAccID());
                System.out.println("Username: " + acc.getUsername());
                System.out.println("Email: " + acc.getEmail());
                System.out.println("Verified: " + acc.isVerified());
                System.out.println("Active: " + acc.isActive());
                System.out.println("Role ID: " + acc.getRoleID());
                System.out.println("----------");
            }
        }

        // Test getAllJobSeekers
        System.out.println("\nList of Job Seekers:");
        List<Account> jobSeekers = dao.getAllJobSeekers();
        if (jobSeekers.isEmpty()) {
            System.out.println("No job seekers found.");
        } else {
            for (Account acc : jobSeekers) {
                System.out.println("ID: " + acc.getAccID());
                System.out.println("Username: " + acc.getUsername());
                System.out.println("Email: " + acc.getEmail());
                System.out.println("Verified: " + acc.isVerified());
                System.out.println("Active: " + acc.isActive());
                System.out.println("Role ID: " + acc.getRoleID());
                System.out.println("----------");
            }
        }

        // Test getEmailByAccID and getUsernameByAccID
        try {
            int testAccID = 1; // Replace with a valid accID for testing
            System.out.println("\nTesting getEmailByAccID for accID " + testAccID + ":");
            String email = dao.getEmailByAccID(testAccID);
            System.out.println("Email: " + (email != null ? email : "Not found"));

            System.out.println("\nTesting getUsernameByAccID for accID " + testAccID + ":");
            String username = dao.getUsernameByAccID(testAccID);
            System.out.println("Username: " + (username != null ? username : "Not found"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
