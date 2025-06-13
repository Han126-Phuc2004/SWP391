package controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/Register"})
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String role = request.getParameter("role");
        if (role != null) {
            switch (role.toLowerCase()) {
                case "jobseeker":
                    response.sendRedirect("RegisterJobSeeker.jsp");
                    break;
                case "employer":
                    response.sendRedirect("RegisterEmployer.jsp");
                    break;
                default:
                    response.sendRedirect("Register.jsp");
            }
        } else {
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }
} 