package controller;

import dao.LoginHistoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Servlet xử lý logout, cập nhật logoutTime cho bản ghi mới nhất của người dùng
 * trong bảng LoginHistory dựa trên accID, sau đó hủy session và chuyển hướng về trang login.
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/Logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy session hiện tại (false để không tạo session mới nếu không tồn tại)
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Lấy đối tượng Account từ session để lấy accID
            Account account = (Account) session.getAttribute("account");
            if (account != null) {
                int accID = account.getAccID();
                try {
                    // Tạo timestamp cho thời gian logout
                    Timestamp logoutTime = new Timestamp(System.currentTimeMillis());
                    LocalDateTime logoutDateTime = logoutTime.toInstant()
                            .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                            .toLocalDateTime();
                    System.out.println("Logout attempted at: " + logoutDateTime + " for accID: " + accID);

                    // Sử dụng LoginHistoryDAO để cập nhật logoutTime
                    LoginHistoryDAO historyDAO = new LoginHistoryDAO();
                    boolean updateSuccess = historyDAO.updateLogoutTimeByAccID(accID, logoutTime);

                    if (updateSuccess) {
                        System.out.println("Logout time updated successfully for accID: " + accID);
                    } else {
                        System.out.println("No matching record found to update logoutTime for accID: " + accID);
                    }
                } catch (Exception e) {
                    System.out.println("Error updating logoutTime for accID " + accID + ": " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("No account found in session during logout.");
            }

            // Hủy session sau khi xử lý
            session.invalidate();
            System.out.println("Session invalidated at: " + LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        } else {
            System.out.println("No active session found for logout.");
        }

        // Chuyển hướng về trang login
        response.sendRedirect("Login.jsp");
    }
}