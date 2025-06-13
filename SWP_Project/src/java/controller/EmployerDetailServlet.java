package controller;

import dao.EmployerApprovalDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "EmployerDetailServlet", urlPatterns = {"/employer-detail"})
public class EmployerDetailServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            int accID = Integer.parseInt(request.getParameter("accID"));
            EmployerApprovalDAO dao = new EmployerApprovalDAO();
            Map<String, String> details = dao.getEmployerDetail(accID);
            
            if (!details.isEmpty()) {
                // Đặt các thuộc tính vào request
                request.setAttribute("accID", details.get("accID"));
                request.setAttribute("email", details.get("email"));
                request.setAttribute("companyName", details.get("name"));
                request.setAttribute("website", details.get("website") != null ? details.get("website") : "Chưa cập nhật");
                request.setAttribute("address", details.get("address") != null ? details.get("address") : "Chưa cập nhật");
                request.setAttribute("phone", details.get("phone") != null ? details.get("phone") : "Chưa cập nhật");
                
                // Forward đến trang chi tiết
                request.getRequestDispatcher("/AdminEmployerDetail.jsp").forward(request, response);
            } else {
                // Nếu không tìm thấy thông tin, quay lại trang danh sách
                response.sendRedirect(request.getContextPath() + "/AdminEmployerApproval.jsp");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/AdminEmployerApproval.jsp");
        }
    }
} 