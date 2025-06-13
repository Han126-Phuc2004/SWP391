package dao;

import connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Employer;

public class EmployerDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Lấy tất cả Employers
    public List<Employer> getAllEmployers() {
        List<Employer> list = new ArrayList<>();
        String query = "SELECT * FROM Companies";

        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Employer(
                    rs.getInt("accID"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("website")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return list;
    }

    // Lấy Employer theo accID
    public Employer getEmployerById(int accID) {
        Employer employer = null;
        String sql = "SELECT * FROM Companies WHERE accID = ?";

        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accID);
            rs = ps.executeQuery();

            if (rs.next()) {
                employer = new Employer();
                employer.setAccID(rs.getInt("accID"));
                employer.setName(rs.getString("name"));
                employer.setAddress(rs.getString("address"));
                employer.setEmail(rs.getString("email"));
                employer.setPhone(rs.getString("phone"));
                employer.setWebsite(rs.getString("website"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return employer;
    }

    // Thêm mới Employer
    public boolean insertEmployer(Employer employer) {
        String sql = "INSERT INTO Companies (accID, name, address, email, phone, website) "
                  + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, employer.getAccID());
            ps.setString(2, employer.getName());
            ps.setString(3, employer.getAddress());
            ps.setString(4, employer.getEmail());
            ps.setString(5, employer.getPhone());
            ps.setString(6, employer.getWebsite());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    // Cập nhật Employer
    public boolean updateEmployer(Employer employer) {
        String sql = "UPDATE Companies SET name = ?, address = ?, email = ?, "
                  + "phone = ?, website = ? WHERE accID = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, employer.getName());
            ps.setString(2, employer.getAddress());
            ps.setString(3, employer.getEmail());
            ps.setString(4, employer.getPhone());
            ps.setString(5, employer.getWebsite());
            ps.setInt(6, employer.getAccID());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    // Xóa Employer
    public boolean deleteEmployer(int accID) {
        String sql = "DELETE FROM Companies WHERE accID = ?";
        try {
            conn = new DBConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accID);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    // Đóng kết nối tài nguyên
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm test
    public static void main(String[] args) {
        EmployerDAO dao = new EmployerDAO();

        System.out.println("=== Test getAllEmployers() ===");
        List<Employer> list = dao.getAllEmployers();
        for (Employer emp : list) {
            System.out.println("\nEmployer Details:");
            System.out.println("- Company: " + emp.getName());
            System.out.println("- Address: " + emp.getAddress());
            System.out.println("- Contact: " + emp.getPhone());
            System.out.println("- Email: " + emp.getEmail());
            System.out.println("- Website: " + emp.getWebsite());
        }

        System.out.println("\n=== Test getEmployerById(1019) ===");
        Employer employer = dao.getEmployerById(1019);
        if (employer != null) {
            System.out.println("\nFound Employer:");
            System.out.println("- Company: " + employer.getName());
            System.out.println("- Address: " + employer.getAddress());
            System.out.println("- Contact: " + employer.getPhone());
            System.out.println("- Email: " + employer.getEmail());
            System.out.println("- Website: " + employer.getWebsite());
        } else {
            System.out.println("Không tìm thấy Employer với accID = 1019");
        }

        // Test insert với accID đã tồn tại trong Accounts (1019)
        System.out.println("\n=== Test insertEmployer() ===");
        Employer newEmployer = new Employer(1019, "Test Company", "Test Address", 
            "test@email.com", "0123456789", "http://test.com");
        boolean insertResult = dao.insertEmployer(newEmployer);
        System.out.println("Insert result: " + insertResult);

        // Test update
        if (insertResult) {
            System.out.println("\n=== Test updateEmployer() ===");
            newEmployer.setName("Updated Test Company");
            boolean updateResult = dao.updateEmployer(newEmployer);
            System.out.println("Update result: " + updateResult);

            // Test delete
            System.out.println("\n=== Test deleteEmployer() ===");
            boolean deleteResult = dao.deleteEmployer(newEmployer.getAccID());
            System.out.println("Delete result: " + deleteResult);
        }
    }
} 