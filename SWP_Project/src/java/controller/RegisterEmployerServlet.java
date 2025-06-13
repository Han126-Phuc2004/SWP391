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
import model.Company;
import model.MailUtil;
import model.SecurityUtil;

@WebServlet(name = "RegisterEmployerServlet", urlPatterns = {"/RegisterEmployer"})
public class RegisterEmployerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get parameters from form
        String representName = request.getParameter("representName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String website = request.getParameter("website");

        // Keep entered values to prefill the form in case of error
        request.setAttribute("representName", representName);
        request.setAttribute("email", email);
        request.setAttribute("address", address);
        request.setAttribute("website", website);

        System.out.println("Debug - Form data received:");
        System.out.println("representName: " + representName);
        System.out.println("email: " + email);
        System.out.println("address: " + address);
        System.out.println("website: " + website);

        // Validate input
        boolean hasError = false;

        // Validate representName
        if (representName == null || representName.trim().isEmpty()) {
            request.setAttribute("representNameError", "Representative name cannot be empty.");
            hasError = true;
        }

        // Validate email
        if (!SecurityUtil.validateEmail(email)) {
            request.setAttribute("emailError", "Invalid email format.");
            hasError = true;
        }

        // Validate password
        if (!SecurityUtil.validatePassword(password)) {
            request.setAttribute("passwordError", "Password must be at least 8 characters long and include uppercase letters, lowercase letters, numbers,and one special character.");
            hasError = true;
        }

        // Validate address
        if (address == null || address.trim().isEmpty()) {
            request.setAttribute("addressError", "Address cannot be empty.");
            hasError = true;
        }

        // Validate website
        if (website == null || !website.matches("https?://.+")) {
            request.setAttribute("websiteError", "Please enter a valid website URL.");
            hasError = true;
        }

        if (hasError) {
            request.getRequestDispatcher("RegisterEmployer.jsp").forward(request, response);
            return;
        }

        RegisterDAO dao = new RegisterDAO();

        try {
            // Check if email already exists
            if (dao.isEmailExists(email)) {
                request.setAttribute("emailError", "Email already exists in the system.");
                request.getRequestDispatcher("RegisterEmployer.jsp").forward(request, response);
                return;
            }

            // Hash password and generate verification token
            String hashedPassword = SecurityUtil.hashPassword(password);
            String verifyToken = SecurityUtil.generateToken();

            // Create Account object
            Account acc = new Account();
            acc.setUsername(representName);
            acc.setEmail(email);
            acc.setPassword(hashedPassword);
            acc.setVerified(false);
            acc.setVerifyToken(verifyToken);
            acc.setRoleID(3); // Employer

            // Create Company object
            Company company = new Company();
            company.setName(representName);
            company.setAddress(address);
            company.setEmail(email);
            company.setPhone("");
            company.setWebsite(website);

            System.out.println("Debug - Before registerEmployer");
            boolean success = dao.registerEmployer(acc, company);
            System.out.println("Debug - After registerEmployer, success: " + success);

            if (success) {
                request.setAttribute("message", "Registration successful! Please wait for admin approval. " +
                    "You will receive an email notification once your account is approved.");
            } else {
                request.setAttribute("error", "Registration failed! Please try again later.");
            }
        } catch (Exception e) {
            System.out.println("Debug - Registration error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        }

        request.getRequestDispatcher("RegisterEmployer.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("RegisterEmployer.jsp").forward(request, response);
    }
}
