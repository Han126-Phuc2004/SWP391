<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký tài khoản Job Seeker - Job Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .register-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 30px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .form-floating {
            margin-bottom: 15px;
        }
        .btn-register {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            font-weight: 500;
        }
        .password-requirements {
            font-size: 0.875rem;
            color: #6c757d;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="register-container">
            <h2 class="text-center mb-4">Đăng ký tài khoản Job Seeker</h2>
            
            <!-- Hiển thị thông báo lỗi nếu có -->
            <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("error") %>
            </div>
            <% } %>
            
            <!-- Hiển thị thông báo thành công nếu có -->
            <% if (request.getAttribute("message") != null) { %>
            <div class="alert alert-success" role="alert">
                <%= request.getAttribute("message") %>
            </div>
            <% } %>
            
            <form action="RegisterJobSeeker" method="POST" class="needs-validation" novalidate>
                <div class="form-floating">
                    <input type="text" class="form-control" id="username" name="username" 
                           placeholder="Họ và tên" required minlength="3">
                    <label for="username">Họ và tên</label>
                    <div class="invalid-feedback">
                        Vui lòng nhập họ tên (ít nhất 3 ký tự)
                    </div>
                </div>
                
                <div class="form-floating">
                    <input type="email" class="form-control" id="email" name="email" 
                           placeholder="name@example.com" required>
                    <label for="email">Email</label>
                    <div class="invalid-feedback">
                        Vui lòng nhập email hợp lệ
                    </div>
                </div>
                
                <div class="form-floating">
                    <input type="password" class="form-control" id="password" name="password" 
                           placeholder="Mật khẩu" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$">
                    <label for="password">Mật khẩu</label>
                </div>
                
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" 
                           placeholder="Xác nhận mật khẩu" required>
                    <label for="confirmPassword">Xác nhận mật khẩu</label>
                    <div class="invalid-feedback">
                        Mật khẩu xác nhận không khớp
                    </div>
                </div>
                
                <div class="password-requirements">
                    <p class="mb-1"><i class="fas fa-info-circle me-2"></i>Yêu cầu mật khẩu:</p>
                    <ul class="mb-0">
                        <li>Ít nhất 8 ký tự</li>
                        <li>Bao gồm chữ hoa và chữ thường</li>
                        <li>Ít nhất một số</li>
                    </ul>
                </div>
                
                <button type="submit" class="btn btn-primary btn-register">
                    <i class="fas fa-user-plus me-2"></i>Đăng ký
                </button>
                
                <div class="text-center mt-3">
                    <p>Đã có tài khoản? <a href="Login.jsp" class="text-decoration-none">Đăng nhập ngay</a></p>
                </div>
            </form>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Form validation
        (function () {
            'use strict'
            var forms = document.querySelectorAll('.needs-validation')
            Array.prototype.slice.call(forms).forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    
                    // Kiểm tra mật khẩu xác nhận
                    var password = document.getElementById('password')
                    var confirm = document.getElementById('confirmPassword')
                    if (password.value !== confirm.value) {
                        confirm.setCustomValidity('Mật khẩu xác nhận không khớp')
                        event.preventDefault()
                        event.stopPropagation()
                    } else {
                        confirm.setCustomValidity('')
                    }
                    
                    form.classList.add('was-validated')
                }, false)
            })
        })()
    </script>
</body>
</html> 