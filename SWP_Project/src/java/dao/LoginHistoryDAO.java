package dao;

import connect.DBConnect;
import model.LoginRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginHistoryDAO {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    // Insert a new login record and return its ID
    public int insertLoginRecord(int accID, Timestamp loginTime) {
        String sql = "INSERT INTO LoginHistory (accID, loginTime) VALUES (?, ?)";
        String getLastIdSql = "SELECT SCOPE_IDENTITY() AS id"; // SQL Server

        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accID);
            ps.setTimestamp(2, loginTime);
            int rows = ps.executeUpdate();
            System.out.println("Insert into LoginHistory for accID " + accID + ", rows affected: " + rows);

            ps = conn.prepareStatement(getLastIdSql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int newId = rs.getInt("id");
                System.out.println("New loginHistoryID: " + newId);
                return newId;
            } else {
                System.out.println("Error: Could not retrieve new loginHistoryID for accID: " + accID);
                return -1;
            }
        } catch (Exception e) {
            System.out.println("Error inserting login history for accID " + accID + ": " + e.getMessage());
            e.printStackTrace();
            return -1;
        } finally {
            closeResources();
        }
    }

    // Update logout time for a login record
    public boolean updateLogoutTime(int id, Timestamp logoutTime) {
        String sql = "UPDATE LoginHistory SET logoutTime = ? WHERE id = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, logoutTime);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            System.out.println("Update logoutTime for id " + id + ", rows affected: " + rows);
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Error updating logoutTime for id " + id + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    // Update logout time for the latest login session of an accID
    public boolean updateLogoutTimeByAccID(int accID, Timestamp logoutTime) {
        String sql = "UPDATE LoginHistory SET logoutTime = ? WHERE accID = ? AND logoutTime IS NULL AND id = (SELECT MAX(id) FROM LoginHistory WHERE accID = ? AND logoutTime IS NULL)";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, logoutTime);
            ps.setInt(2, accID);
            ps.setInt(3, accID);
            int rows = ps.executeUpdate();
            System.out.println("Update logoutTime for accID " + accID + ", rows affected: " + rows);
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Error updating logoutTime for accID " + accID + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    // Get all login records (for admin)
    public List<LoginRecord> getAllLoginRecords() {
        List<LoginRecord> list = new ArrayList<>();
        String sql = "SELECT lh.id, lh.accID, a.username, lh.loginTime, lh.logoutTime " +
                     "FROM LoginHistory lh " +
                     "INNER JOIN Accounts a ON lh.accID = a.accID " +
                     "ORDER BY lh.loginTime DESC";

        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                LoginRecord record = new LoginRecord(
                        rs.getInt("id"),
                        rs.getInt("accID"),
                        rs.getString("username"),
                        rs.getTimestamp("loginTime"),
                        rs.getTimestamp("logoutTime")
                );
                list.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return list;
    }

    // Get login records by accID (for specific user)
    public List<LoginRecord> getLoginRecordsByAccID(int accID) {
        List<LoginRecord> list = new ArrayList<>();
        String sql = "SELECT lh.id, lh.accID, a.username, lh.loginTime, lh.logoutTime " +
                     "FROM LoginHistory lh " +
                     "INNER JOIN Accounts a ON lh.accID = a.accID " +
                     "WHERE lh.accID = ? ORDER BY lh.loginTime DESC";

        try {
            conn = new DBConnect().getConnection();
            System.out.println("Debug - Connected to database for accID: " + accID);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accID);
            rs = ps.executeQuery();
            System.out.println("Debug - Executed query for accID: " + accID);

            while (rs.next()) {
                LoginRecord record = new LoginRecord(
                        rs.getInt("id"),
                        rs.getInt("accID"),
                        rs.getString("username"),
                        rs.getTimestamp("loginTime"),
                        rs.getTimestamp("logoutTime")
                );
                list.add(record);
                System.out.println("Debug - Found record: id=" + record.getId() + ", accID=" + record.getAccID() + ", username=" + record.getUsername());
            }
        } catch (Exception e) {
            System.out.println("Error in getLoginRecordsByAccID for accID " + accID + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return list;
    }

    // Close database resources
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method to test login/logout history retrieval
    public static void main(String[] args) {
        LoginHistoryDAO dao = new LoginHistoryDAO();

        // Test getAllLoginRecords
        System.out.println("All Login Records:");
        List<LoginRecord> allRecords = dao.getAllLoginRecords();
        if (allRecords.isEmpty()) {
            System.out.println("No login records found.");
        } else {
            for (LoginRecord record : allRecords) {
                System.out.println("ID: " + record.getId() +
                        ", accID: " + record.getAccID() +
                        ", Username: " + record.getUsername() +
                        ", Login Time: " + record.getLoginTime() +
                        ", Logout Time: " + (record.getLogoutTime() != null ? record.getLogoutTime() : "Not logged out"));
            }
        }

        // Test getLoginRecordsByAccID
        int testAccID = 6; // Replace with a valid accID for testing
        System.out.println("\nLogin/Logout History for accID " + testAccID + ":");
        List<LoginRecord> userRecords = dao.getLoginRecordsByAccID(testAccID);
        if (userRecords.isEmpty()) {
            System.out.println("No login records found for accID " + testAccID);
        } else {
            for (LoginRecord record : userRecords) {
                System.out.println("ID: " + record.getId() +
                        ", accID: " + record.getAccID() +
                        ", Username: " + record.getUsername() +
                        ", Login Time: " + record.getLoginTime() +
                        ", Logout Time: " + (record.getLogoutTime() != null ? record.getLogoutTime() : "Not logged out"));
            }
        }
    }
}