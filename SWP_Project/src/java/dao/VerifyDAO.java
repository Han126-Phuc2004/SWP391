package dao;

import connect.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VerifyDAO {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    // Lấy accID từ token nếu token hợp lệ và chưa xác thực
    public int getAccountIdByToken(String token) {
        int accId = -1;
        String query = "SELECT accID FROM Accounts WHERE verifyToken = ? AND isVerified = 0";

        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, token);
            rs = ps.executeQuery();

            if (rs.next()) {
                accId = rs.getInt("accID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return accId;
    }

    // Cập nhật tài khoản là đã xác thực
    public boolean verifyAccount(int accId) {
        boolean success = false;
        String query = "UPDATE Accounts SET isVerified = 1, verifyToken = NULL WHERE accID = ?";

        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, accId);
            int rows = ps.executeUpdate();
            success = rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return success;
    }

    // Đóng các tài nguyên sau khi dùng xong
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm test đơn giản
    public static void main(String[] args) {
        VerifyDAO dao = new VerifyDAO();

        String testToken = "abc123"; // thay bằng token hợp lệ trong DB
        int accId = dao.getAccountIdByToken(testToken);
        System.out.println("Tìm thấy accID: " + accId);

        if (accId != -1) {
            boolean result = dao.verifyAccount(accId);
            System.out.println("Xác thực thành công? " + result);
        } else {
            System.out.println("Token không hợp lệ hoặc tài khoản đã xác thực.");
        }
    }
}
