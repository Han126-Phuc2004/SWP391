/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import dao.EmployerDAO;
import model.Account;
import model.Employer;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author phamd
 */
@WebServlet(name = "EmployerProfileServlet", urlPatterns = {"/EmployerProfile"})
public class EmployerProfileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        // Thêm debug log
        System.out.println("Debug - Session ID: " + session.getId());
        System.out.println("Debug - Account from session: " + (account == null ? "null" : account.getUsername()));

        // Nếu chưa đăng nhập thì redirect về login
        if (account == null) {
            System.out.println("Debug - No account found in session, redirecting to Login.jsp");
            response.sendRedirect("Login.jsp");
            return;
        }

        // Kiểm tra role (3 là role của employer)
        if (account.getRoleID() != 3) {
            System.out.println("Debug - Invalid role: " + account.getRoleID() + ", redirecting to Home");
            response.sendRedirect("Home");
            return;
        }

        try {
            // Khởi tạo DAO
            AccountDAO accountDAO = new AccountDAO();
            EmployerDAO employerDAO = new EmployerDAO();

            // Lấy thông tin chi tiết từ DB
            Account detailedAccount = accountDAO.getAccountById(account.getAccID());
            System.out.println("Debug - Detailed account: " + (detailedAccount == null ? "null" : detailedAccount.getUsername()));
            
            Employer employer = employerDAO.getEmployerById(account.getAccID());
            System.out.println("Debug - Employer data: " + (employer == null ? "null" : "found"));

            // Gửi dữ liệu lên JSP
            request.setAttribute("username", detailedAccount.getUsername());
            request.setAttribute("email", detailedAccount.getEmail());
            request.setAttribute("employer", employer);

            // Chuyển đến trang JSP
            request.getRequestDispatcher("EmployerProfile.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Error in EmployerProfileServlet: " + e.getMessage());
            e.printStackTrace(); // In ra stack trace để debug
            session.setAttribute("error", "Có lỗi xảy ra khi tải thông tin profile: " + e.getMessage());
            response.sendRedirect("error.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        if (account == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        try {
            // Lấy dữ liệu từ form
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String website = request.getParameter("website");

            // Debug log
            System.out.println("Debug - Received form data:");
            System.out.println("Name: " + name);
            System.out.println("Address: " + address);
            System.out.println("Phone: " + phone);
            System.out.println("Email: " + email);
            System.out.println("Website: " + website);

            // Khởi tạo DAO và cập nhật thông tin
            EmployerDAO employerDAO = new EmployerDAO();
            Employer updatedEmployer = new Employer(account.getAccID(), name, address, email, phone, website);
            
            boolean success = employerDAO.updateEmployer(updatedEmployer);
            if (success) {
                session.setAttribute("message", "Cập nhật hồ sơ thành công");
            } else {
                session.setAttribute("error", "Cập nhật hồ sơ thất bại");
            }
            
        } catch (Exception e) {
            System.out.println("Error updating profile: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("error", "Có lỗi xảy ra khi cập nhật hồ sơ: " + e.getMessage());
        }
        
        response.sendRedirect("EmployerProfile");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Employer Profile Servlet";
    }// </editor-fold>

}
