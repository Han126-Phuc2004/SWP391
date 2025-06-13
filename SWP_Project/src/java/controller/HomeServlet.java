package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "HomeServlet", urlPatterns = {"", "/"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session != null && session.getAttribute("account") != null) {
            // Nếu đã đăng nhập, chuyển hướng theo role
            model.Account account = (model.Account) session.getAttribute("account");
            switch (account.getRoleID()) {
                case 1: // Admin
                    response.sendRedirect("AdminDashboard.jsp");
                    break;
                case 2: // Job Seeker
                    response.sendRedirect("JobseekerDashboard.jsp");
                    break;
                case 3: // Employer
                    response.sendRedirect("EmployerDashboard.jsp");
                    break;
                default:
                    response.sendRedirect("Login.jsp");
            }
        } else {
            // Nếu chưa đăng nhập, chuyển về trang login
            response.sendRedirect("HomePage.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
} 