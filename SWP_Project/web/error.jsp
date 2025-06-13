<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background: #f4f7fc;
            font-family: 'Segoe UI', sans-serif;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .error-container {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        .error-box {
            background: #fff;
            border-radius: 10px;
            padding: 30px;
            text-align: center;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            max-width: 500px;
            width: 100%;
        }
        .error-icon {
            font-size: 48px;
            color: #dc3545;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <!-- Include Header -->
    <jsp:include page="Header.jsp" />

    <div class="error-container">
        <div class="error-box">
            <div class="error-icon">
                <i class="fas fa-exclamation-circle"></i>
            </div>
            <h3 class="mb-4">Đã xảy ra lỗi</h3>
            
            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-danger">
                    ${sessionScope.error}
                </div>
                <% session.removeAttribute("error"); %>
            </c:if>
            
            <!-- Nếu không có thông báo lỗi cụ thể -->
            <c:if test="${empty sessionScope.error}">
                <div class="alert alert-danger">
                    Đã xảy ra lỗi không mong muốn. Vui lòng thử lại sau.
                </div>
            </c:if>
            
            <div class="mt-4">
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Về trang chủ</a>
            </div>
        </div>
    </div>

    <!-- Include Footer -->
    <jsp:include page="Footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</body>
</html> 