package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/Profile"})
public class ProfileServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        // Kiểm tra đăng nhập
        if (account == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        // Chuyển hướng dựa trên roleID
        switch (account.getRoleID()) {
            case 1: // Admin
                request.getRequestDispatcher("AdminProfile.jsp").forward(request, response);
                break;
            case 2: // JobSeeker
                response.sendRedirect("JSKProfile"); // Chuyển hướng sang JSKProfileServlet
                break;
            case 3: // Employer
                response.sendRedirect("EmployerProfile"); // Chuyển hướng sang EmployerProfileServlet
                break;
            default:
                session.setAttribute("error", "Không thể xác định loại tài khoản!");
                response.sendRedirect("error.jsp");
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
} 