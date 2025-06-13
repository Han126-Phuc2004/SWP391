package controller;

import dao.EmployerApprovalDAO;
import model.EmployerApproval;
import model.MailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "AdminEmployerApprovalServlet", urlPatterns = {"/AdminEmployerApproval"})
public class AdminEmployerApprovalServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            EmployerApprovalDAO dao = new EmployerApprovalDAO();
            List<EmployerApproval> employers = dao.getPendingEmployers();
            request.setAttribute("employers", employers);
            
            // Forward to JSP
            request.getRequestDispatcher("/AdminEmployerApproval.jsp").forward(request, response);
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            
            // Set error message in session for better user experience
            HttpSession session = request.getSession();
            session.setAttribute("error", "Đã xảy ra lỗi khi tải danh sách employer: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/AdminEmployerApproval");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String accIDStr = request.getParameter("accID");
        
        // Validate input parameters
        if (action == null || accIDStr == null) {
            session.setAttribute("error", "Thông tin không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/AdminEmployerApproval");
            return;
        }
        
        try {
            int accID = Integer.parseInt(accIDStr);
            EmployerApprovalDAO dao = new EmployerApprovalDAO();
            
            // Get employer details
            String email = dao.getEmailByAccID(accID);
            List<EmployerApproval> employers = dao.getPendingEmployers();
            String companyName = "";
            for (EmployerApproval emp : employers) {
                if (emp.getAccID() == accID) {
                    companyName = emp.getCompanyName();
                    break;
                }
            }
            
            if (companyName.isEmpty()) {
                session.setAttribute("error", "Không tìm thấy thông tin employer!");
                response.sendRedirect(request.getContextPath() + "/AdminEmployerApproval");
                return;
            }
            
            switch (action) {
                case "approve":
                    // Generate verify token
                    String verifyToken = UUID.randomUUID().toString();
                    
                    System.out.println("Approving employer:");
                    System.out.println("AccID: " + accID);
                    System.out.println("Email: " + email);
                    System.out.println("Company Name: " + companyName);
                    System.out.println("Verify Token: " + verifyToken);
                    
                    // Update account status and token
                    dao.approveEmployer(accID, verifyToken);
                    
                    // Send approval email with verification link
                    MailUtil.sendEmployerApprovalEmail(email, companyName, verifyToken);
                    
                    session.setAttribute("success", String.format("Đã duyệt thành công employer %s và gửi email xác thực!", companyName));
                    break;
                    
                case "reject":
                    String reason = request.getParameter("reason");
                    if (reason == null || reason.trim().isEmpty()) {
                        reason = "Thông tin không đầy đủ hoặc không hợp lệ";
                    }
                    
                    // Send rejection email
                    MailUtil.sendEmployerRejectionEmail(email, companyName, reason);
                    
                    // Delete the employer
                    dao.rejectEmployer(accID);
                    
                    session.setAttribute("success", String.format("Đã từ chối employer %s và gửi email thông báo!", companyName));
                    break;
                    
                default:
                    session.setAttribute("error", "Hành động không hợp lệ!");
                    break;
            }
            
        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID employer không hợp lệ!");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
        }
        
        // Redirect back to the approval page
        response.sendRedirect(request.getContextPath() + "/AdminEmployerApproval");
    }
} 