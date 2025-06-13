package dao;

import connect.DBConnect;
import model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobSeekerDAO {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public List<Account> getAllJobSeekers() {
        List<Account> jobSeekers = new ArrayList<>();
        String sql = "SELECT * FROM Accounts WHERE roleID = 2"; // JobSeeker roleID = 2
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                jobSeekers.add(extractAccountFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getAllJobSeekers: " + e.getMessage());
        } finally {
            closeResources();
        }
        return jobSeekers;
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
        return acc;
    }

    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm main test trong chính file DAO này
    public static void main(String[] args) {
        JobSeekerDAO jobSeekerDAO = new JobSeekerDAO();
        System.out.println("===== List of Job Seekers =====");
        List<Account> jobSeekers = jobSeekerDAO.getAllJobSeekers();
        for (Account j : jobSeekers) {
            System.out.println(j);
        }
    }
}
