package dao;

import connect.DBConnect;
import model.EmployerApproval;
import java.sql.*;
import java.util.*;


public class EmployerApprovalDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<EmployerApproval> getPendingEmployers() {
        List<EmployerApproval> list = new ArrayList<>();
        
        try {
            conn = new DBConnect().getConnection();
            String sql = "SELECT a.accID, a.email, c.name as companyName, c.website " +
                        "FROM Accounts a " +
                        "LEFT JOIN Companies c ON a.accID = c.accID " +
                        "WHERE a.isVerified = 0 AND a.roleID = 3";
            System.out.println("🔍 Executing query: " + sql);
            
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                EmployerApproval employer = new EmployerApproval();
                int accID = rs.getInt("accID");
                String email = rs.getString("email");
                String companyName = rs.getString("companyName");
                String website = rs.getString("website");
                
                employer.setAccID(accID);
                employer.setEmail(email);
                employer.setCompanyName(companyName != null ? companyName : "Chưa cập nhật");
                employer.setWebsite(website != null ? website : "Chưa cập nhật");
                
                System.out.println("📋 Found employer: ID=" + accID + ", Email=" + email + 
                                 ", Company=" + companyName + ", Website=" + website);
                
                list.add(employer);
            }
            System.out.println("✅ Total employers found: " + list.size());
            
        } catch (Exception e) {
            System.out.println("❌ Error in getPendingEmployers: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources();
        }
        
        return list;
    }

    public Map<String, String> getEmployerDetail(int accID) {
        Map<String, String> result = new HashMap<>();
        String query = "SELECT a.accID, a.email, c.name, c.website, c.address, c.phone " +
                       "FROM Accounts a JOIN Companies c ON a.accID = c.accID WHERE a.accID = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, accID);
            rs = ps.executeQuery();
            if (rs.next()) {
                result.put("accID", String.valueOf(rs.getInt("accID")));
                result.put("email", rs.getString("email"));
                result.put("name", rs.getString("name"));
                result.put("website", rs.getString("website"));
                result.put("address", rs.getString("address"));
                result.put("phone", rs.getString("phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return result;
    }

    public void approveEmployer(int accID, String verifyToken) {
        try {
            conn = new DBConnect().getConnection();
            conn.setAutoCommit(false);
            
            // Only update verifyToken, isVerified will be set when user clicks email link
            ps = conn.prepareStatement("UPDATE Accounts SET verifyToken = ? WHERE accID = ?");
            ps.setString(1, verifyToken);
            ps.setInt(2, accID);
            ps.executeUpdate();
            
            conn.commit();
            System.out.println("✅ Set verify token for accID = " + accID);
            
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    public void rejectEmployer(int accID) {
        String deleteCompany = "DELETE FROM Companies WHERE accID = ?";
        String deleteAccount = "DELETE FROM Accounts WHERE accID = ?";
        try {
            conn = new DBConnect().getConnection();
            conn.setAutoCommit(false); // rollback nếu cần

            ps = conn.prepareStatement(deleteCompany);
            ps.setInt(1, accID);
            ps.executeUpdate();
            ps.close();

            ps = conn.prepareStatement(deleteAccount);
            ps.setInt(1, accID);
            ps.executeUpdate();

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            closeResources();
        }
    }

    public String getEmailByAccID(int accID) {
        String email = null;
        String query = "SELECT email FROM Accounts WHERE accID = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, accID);
            rs = ps.executeQuery();
            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return email;
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

    public static void main(String[] args) {
        EmployerApprovalDAO dao = new EmployerApprovalDAO();

        System.out.println("\n🔍 TESTING getPendingEmployers()");
        List<EmployerApproval> list = dao.getPendingEmployers();
        if (list.isEmpty()) {
            System.out.println("❌ No pending employers found!");
        } else {
            System.out.println("✅ Found " + list.size() + " pending employers:");
            for (EmployerApproval e : list) {
                System.out.println("   👉 ID: " + e.getAccID() + 
                                 " | Email: " + e.getEmail() +
                                 " | Company: " + e.getCompanyName() + 
                                 " | Website: " + e.getWebsite());
            }
        }
    }
}
