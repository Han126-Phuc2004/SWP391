<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng nhập - Job Portal</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
                min-height: 100vh;
                display: flex;
                align-items: center;
            }
            .login-container {
                max-width: 400px;
                margin: 0 auto;
                padding: 30px;
                background: white;
                border-radius: 10px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
            }
            .login-logo {
                text-align: center;
                margin-bottom: 30px;
            }
            .login-logo img {
                max-width: 150px;
                height: auto;
            }
            .form-floating {
                margin-bottom: 15px;
            }
            .btn-login {
                width: 100%;
                padding: 12px;
                font-size: 16px;
                font-weight: 500;
            }
            .register-links {
                text-align: center;
                margin-top: 20px;
                padding-top: 20px;
                border-top: 1px solid #dee2e6;
            }
            .alert {
                margin-bottom: 20px;
            }
            .register-links .btn {
                transition: all 0.3s ease;
            }
            .register-links .btn:hover {
                transform: translateY(-2px);
            }
            .or-divider {
                text-align: center;
                margin: 20px 0;
                position: relative;
            }
            .or-divider:before,
            .or-divider:after {
                content: "";
                position: absolute;
                top: 50%;
                width: 45%;
                height: 1px;
                background: #dee2e6;
            }
            .or-divider:before {
                left: 0;
            }
            .or-divider:after {
                right: 0;
            }
            .or-divider span {
                background: white;
                padding: 0 15px;
                color: #6c757d;
                font-size: 14px;
            }
            .btn-google {
                width: 100%;
                background: #fff;
                border: 1px solid #ddd;
                padding: 12px;
                border-radius: 4px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-weight: 500;
                color: #333;
                text-decoration: none;
                transition: all 0.3s ease;
            }
            .btn-google:hover {
                background: #f8f9fa;
                border-color: #ddd;
                box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            }
            .btn-google img {
                width: 20px;
                margin-right: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="login-container">
                <div class="login-logo">
                    <img src="assets/images/logo.png" alt="Logo" onerror="this.src='https://via.placeholder.com/150x50?text=Your+Logo'">
                </div>
                
                <!-- Hiển thị thông báo lỗi nếu có -->
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <%= request.getAttribute("error") %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                <% } %>
                
                <!-- Hiển thị thông báo thành công nếu có -->
                <% if (request.getAttribute("message") != null) { %>
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <%= request.getAttribute("message") %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                <% } %>
                
                <form id="loginForm" action="Login" method="POST">
                    <div class="form-floating">
                        <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com" required>
                        <label for="email">Email</label>
                    </div>
                    <div class="form-floating">
                        <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                        <label for="password">Mật khẩu</label>
                    </div>
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="remember" name="remember">
                            <label class="form-check-label" for="remember">Ghi nhớ đăng nhập</label>
                        </div>
                        <a href="ForgotPassword" class="text-decoration-none">Quên mật khẩu?</a>
                    </div>
                    <button type="submit" class="btn btn-primary btn-login">
                        Đăng nhập
                    </button>
                </form>

                <div class="or-divider">
                    <span>Hoặc</span>
                </div>

                <a href="<%=request.getContextPath()%>/google-login" class="btn-google">
                    <img src="<%=request.getContextPath()%>/assets/images/google-icon.png" alt="Google" 
                         onerror="this.src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAFISURBVDhPrZQ9SwNBEIYnMaKCYqONjZ2NjYU2/gIbsVRBrCwEK8HaQhBE8AcIYmejhZ2VhZ2iYCGiRbAJQhrRIpj4Hb67zF3u9i57E3jwYWZ2Z97M3l4u+q9SSi2jR7SCrqJBU+4f6Jf0HY2hs+g7OmE2/w49Mz3yJ/ofDdKjP6NeiUJHxFk0hBbQYXSsDYZQH5pAF9AztGQ2PIbeoUtoAV1HDzX4Ds2iB+g9GiB7C72wF8tKxFk0i86h2+ghGkVv0Qz6iK6hRyjVd0dEXEK30Gm0ik6idbSJDqBxbdBCO+gE2tK1X6KQiCfQJbSG5Mib6Dkqz3YePUE70Yy9WFYizqF76DHqR9/QCvqCZtAWeoNuo0/2QjtEbEK7kTxUeRnkD5Qz7EHyQOXhD6E9tBvtR3J2t9BxU/5bRdEv/UPwMxYuRZUAAAAASUVORK5CYII='">
                    Đăng nhập bằng Google
                </a>
                
                <div class="register-links">
                    <p class="mb-2">Bạn chưa có tài khoản?</p>
                    <div class="d-grid gap-2">
                        <a href="Register?role=jobseeker" class="btn btn-outline-primary">
                            <i class="fas fa-user-graduate me-2"></i>Đăng ký tìm việc
                        </a>
                        <a href="Register?role=employer" class="btn btn-outline-success">
                            <i class="fas fa-building me-2"></i>Đăng ký tuyển dụng
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html> 