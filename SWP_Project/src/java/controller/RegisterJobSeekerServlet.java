package controller;

import java.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RegisterDAO;
import model.Account;
import model.MailUtil;
import model.SecurityUtil;

@WebServlet(name = "RegisterJobSeekerServlet", urlPatterns = {"/RegisterJobSeeker"})
public class RegisterJobSeekerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate input
        StringBuilder errors = new StringBuilder();
        if (username == null || username.trim().length() < 3) {
            errors.append("Username must be at least 3 characters long. ");
        }
        if (!SecurityUtil.validateEmail(email)) {
            errors.append("Invalid email format. ");
        }
        if (!SecurityUtil.validatePassword(password)) {
            errors.append("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.");
        }
        if (!password.equals(confirmPassword)) {
            errors.append("Confirm password does not match. ");
        }

        if (errors.length() > 0) {
            request.setAttribute("error", errors.toString());
            request.getRequestDispatcher("RegisterJobSeeker.jsp").forward(request, response);
            return;
        }

        try {
            RegisterDAO dao = new RegisterDAO();

            // Check if email already exists
            if (dao.isEmailExists(email)) {
                request.setAttribute("error", "Email already exists in the system!");
                request.getRequestDispatcher("RegisterJobSeeker.jsp").forward(request, response);
                return;
            }

            // Hash password and generate verification token
            String hashedPassword = SecurityUtil.hashPassword(password);
            String verifyToken = SecurityUtil.generateToken();

            // Create Account object
            Account acc = new Account();
            acc.setUsername(username);
            acc.setEmail(email);
            acc.setPassword(hashedPassword);
            acc.setVerified(false);
            acc.setVerifyToken(verifyToken);
            acc.setRoleID(2); // Job Seeker

            boolean success = dao.registerAccount(acc);
            if (success) {
                // Create verification link
                String verifyLink = String.format("%s://%s:%s%s/Verify?token=%s",
                    request.getScheme(),
                    request.getServerName(),
                    request.getServerPort(),
                    request.getContextPath(),
                    verifyToken);

                try {
                    // Send verification email immediately
                    MailUtil.sendVerificationEmail(email, username, verifyLink);
                    request.setAttribute("message", 
                        "Registration successful! A verification email has been sent to your email address. " +
                        "Please check your inbox (and spam folder) to verify your account.");
                } catch (MessagingException e) {
                    System.out.println("Error sending verification email: " + e.getMessage());
                    request.setAttribute("error", 
                        "Registration successful, but failed to send verification email. " +
                        "Please contact admin for support.");
                }
            } else {
                request.setAttribute("error", "Registration failed! Please try again later.");
            }
        } catch (Exception e) {
            System.out.println("Error in RegisterJobSeekerServlet: " + e.getMessage());
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        }

        request.getRequestDispatcher("RegisterJobSeeker.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("RegisterJobSeeker.jsp").forward(request, response);
    }
}
