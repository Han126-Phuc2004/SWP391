package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.VerifyDAO;
import model.Account;

@WebServlet(name = "VerifyServlet", urlPatterns = {"/Verify"})
public class VerifyServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        
        if (token == null || token.trim().isEmpty()) {
            request.setAttribute("error", "Token xác thực không hợp lệ!");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        VerifyDAO dao = new VerifyDAO();
        
        // Lấy accID từ token
        int accId = dao.getAccountIdByToken(token);
        
        if (accId == -1) {
            request.setAttribute("error", "Token xác thực không tồn tại hoặc tài khoản đã được xác thực!");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        // Xác thực tài khoản
        boolean success = dao.verifyAccount(accId);
        if (success) {
            request.setAttribute("message", "Xác thực email thành công! Vui lòng đăng nhập.");
        } else {
            request.setAttribute("error", "Xác thực thất bại! Vui lòng thử lại sau.");
        }
        
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }
}
