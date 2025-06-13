package dao;

import connect.DBConnect;
import model.Account;
import model.Company;
import java.sql.*;

public class RegisterDAO {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    // Kiểm tra email đã tồn tại chưa
    public boolean isEmailExists(String email) throws Exception {
        String sql = "SELECT COUNT(*) FROM Accounts WHERE email = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error in isEmailExists: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return false;
    }

    // Đăng ký tài khoản thông thường
    public boolean registerAccount(Account account) throws Exception {
        String sql = "INSERT INTO Accounts (username, email, password, isVerified, verifyToken, roleID) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPassword());
            ps.setBoolean(4, account.isVerified());
            ps.setString(5, account.getVerifyToken());
            ps.setInt(6, account.getRoleID());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    account.setAccID(rs.getInt(1));
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in registerAccount: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return false;
    }

    // Đăng ký employer (tài khoản + công ty)
    public boolean registerEmployer(Account account, Company company) throws Exception {
        try {
            conn = new DBConnect().getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Thêm tài khoản vào Accounts
            String accountSql = "INSERT INTO Accounts (username, email, password, isVerified, verifyToken, roleID) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(accountSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPassword());
            ps.setBoolean(4, account.isVerified());
            ps.setString(5, account.getVerifyToken());
            ps.setInt(6, account.getRoleID());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int accID = rs.getInt(1);
                    account.setAccID(accID);

                    // 2. Thêm công ty vào Companies
                    String companySql = "INSERT INTO Companies (accID, name, address, email, phone, website) VALUES (?, ?, ?, ?, ?, ?)";
                    ps = conn.prepareStatement(companySql);
                    ps.setInt(1, accID);
                    ps.setString(2, company.getName());
                    ps.setString(3, company.getAddress());
                    ps.setString(4, account.getEmail());
                    ps.setString(5, company.getPhone());
                    ps.setString(6, company.getWebsite());

                    ps.executeUpdate();
                    conn.commit(); // Commit nếu thành công cả 2 bước
                    return true;
                }
            }

            conn.rollback(); // Rollback nếu xảy ra lỗi ở bất kỳ bước nào
            return false;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Transaction rolled back");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeResources();
        }
    }

    // Đóng tài nguyên
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
