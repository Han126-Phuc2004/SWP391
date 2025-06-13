/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author phamd
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/ChangePassword"})
public class ChangePasswordServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        if (session.getAttribute("account") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }
        request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Get user account from session
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        // Validate passwords
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu mới không khớp");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return;
        }

        if (newPassword.length() < 6) {
            request.setAttribute("error", "Mật khẩu mới phải có ít nhất 6 ký tự");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return;
        }

        try {
            AccountDAO accountDAO = new AccountDAO();
            boolean success = accountDAO.changePassword(account.getEmail(), currentPassword, newPassword);
            
            if (success) {
                // Thêm thông báo thành công và ở lại trang
                request.setAttribute("message", "Đổi mật khẩu thành công!");
                request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Mật khẩu hiện tại không đúng");
                request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Đã xảy ra lỗi khi đổi mật khẩu");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
