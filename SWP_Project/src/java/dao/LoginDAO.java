package dao;

import connect.DBConnect;
import model.Account;
import java.sql.*;

public class LoginDAO {
    public Account checkLogin(String email, String hashedPassword) throws Exception {
        String sql = "SELECT * FROM Accounts WHERE email = ? AND password = ?";
        
        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, hashedPassword);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account acc = new Account();
                    int roleID = rs.getInt("roleID");
                    System.out.println("LoginDAO - Account found:");
                    System.out.println("- Email: " + rs.getString("email"));
                    System.out.println("- RoleID from DB: " + roleID);
                    
                    acc.setAccID(rs.getInt("accID"));
                    acc.setUsername(rs.getString("username"));
                    acc.setEmail(rs.getString("email"));
                    acc.setVerified(rs.getBoolean("isVerified"));
                    acc.setRoleID(roleID);
                    acc.setAvatar(rs.getString("avatar"));
                    acc.setIsActive(rs.getBoolean("isActive"));
                    
                    System.out.println("- RoleID in Account object: " + acc.getRoleID());
                    return acc;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in LoginDAO: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkEmailExists(String email) throws Exception {
        String sql = "SELECT COUNT(*) FROM Accounts WHERE email = ?";
        
        try (Connection conn = new DBConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking email existence: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return false;
    }
}
