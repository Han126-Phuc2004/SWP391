package controller;

import dao.LoginDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.SecurityUtil;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "LoginServlet", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Debug - Login attempt:");
        System.out.println("Email: " + email);
        System.out.println("Password before hash: " + password);

        // Validate email format
        if (!SecurityUtil.validateEmail(email)) {
            request.setAttribute("error", "Invalid email format!");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        // Hash password before checking
        String hashedPassword = SecurityUtil.hashPassword(password);
        System.out.println("Hashed password: " + hashedPassword);

        LoginDAO dao = new LoginDAO();
        try {
            Account account = dao.checkLogin(email, hashedPassword);
            System.out.println("Debug - Login result: " + (account != null ? "Success" : "Failed"));

            if (account == null) {
                request.setAttribute("error", "Incorrect email or password!");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
                return;
            }

            // Check if account is deactivated

            // Check if account is verified
            if (!account.isVerified()) {
                request.setAttribute("error", "Account is not verified!");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
                return;
            }
            if (!account.isActive()) {
                request.setAttribute("error", "Account has been disabled!");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("account", account);

            

            // Redirect based on roleID
            int roleID = account.getRoleID();
            System.out.println("Debug -aminophen: " + roleID);
            switch (roleID) {
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
                    break;
            }
        } catch (Exception e) {
            System.out.println("Debug - Login error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "System error: " + e.getMessage());
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Login.jsp");
    }
}