<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký Employer</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f8f9fa;
            color: #2c3e50;
            line-height: 1.6;
            min-height: 100vh;
            display: flex;
            align-items: center;
            padding: 20px;
        }
        .container {
            max-width: 500px;
            margin: 0 auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.05);
            padding: 30px;
        }
        .page-title {
            color: #2c3e50;
            text-align: center;
            font-weight: 600;
            font-size: 24px;
            margin-bottom: 25px;
            padding-bottom: 15px;
            border-bottom: 2px solid #e9ecef;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-label {
            font-weight: 500;
            color: #34495e;
            margin-bottom: 8px;
            font-size: 14px;
        }
        .form-control {
            border: 1px solid #e9ecef;
            border-radius: 6px;
            padding: 10px 12px;
            font-size: 14px;
            transition: all 0.3s ease;
            height: 42px;
        }
        .form-control:focus {
            border-color: #3498db;
            box-shadow: 0 0 0 0.2rem rgba(52,152,219,0.15);
        }
        .form-control.is-valid {
            border-color: #2ecc71;
            padding-right: 12px;
            background-image: none;
        }
        .submit-btn {
            width: 100%;
            padding: 12px;
            background: #3498db;
            border: none;
            border-radius: 6px;
            color: white;
            font-size: 15px;
            font-weight: 500;
            transition: all 0.3s ease;
            margin-top: 15px;
            height: 45px;
        }
        .submit-btn:hover {
            background: #2980b9;
            transform: translateY(-1px);
            box-shadow: 0 3px 10px rgba(0,0,0,0.1);
        }
        .login-link {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
        }
        .login-link a {
            color: #3498db;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
        }
        .login-link a:hover {
            color: #2980b9;
            text-decoration: underline;
        }
        .input-group {
            position: relative;
            margin-top: 5px;
        }
        .input-group i {
            display: none;
        }
        .form-control::placeholder {
            color: #95a5a6;
            opacity: 0.8;
        }
        .invalid-feedback {
            font-size: 12px;
            color: #dc3545;
            margin-top: 5px;
        }
        .alert {
            border-radius: 6px;
            margin-bottom: 20px;
            font-size: 14px;
            padding: 12px 15px;
        }
        .alert-danger {
            background-color: #fff5f5;
            border-color: #fee2e2;
            color: #dc2626;
        }
        @media (max-width: 576px) {
            .container {
                padding: 20px;
            }
            .page-title {
                font-size: 22px;
                margin-bottom: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="page-title">Đăng ký Employer</h2>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <% if (request.getAttribute("message") != null) { %>
            <div class="alert alert-success" role="alert">
                <i class="fas fa-check-circle me-2"></i>
                <%= request.getAttribute("message") %>
            </div>
        <% } %>

        <form action="RegisterEmployer" method="POST" class="needs-validation" novalidate>
            <div class="form-group">
                <label for="representName" class="form-label">Tên đại diện</label>
                <div class="input-group">
                    <i class="fas fa-building"></i>
                    <input type="text" class="form-control" id="representName" name="representName" 
                           required minlength="2">
                </div>
                <div class="invalid-feedback">
                    Tên đại diện không được để trống
                </div>
            </div>
            
            <div class="form-group">
                <label for="email" class="form-label">Email</label>
                <div class="input-group">
                    <i class="fas fa-envelope"></i>
                    <input type="email" class="form-control" id="email" name="email" 
                           required pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$">
                </div>
                <div class="invalid-feedback">
                    Vui lòng nhập email hợp lệ
                </div>
            </div>
            
            <div class="form-group">
                <label for="password" class="form-label">Mật khẩu</label>
                <div class="input-group">
                    <i class="fas fa-lock"></i>
                    <input type="password" class="form-control" id="password" name="password" 
                           required minlength="8">
                </div>
                <div class="invalid-feedback">
                    Mật khẩu phải có ít nhất 8 ký tự
                </div>
            </div>
            
            <div class="form-group">
                <label for="address" class="form-label">Địa chỉ</label>
                <div class="input-group">
                    <i class="fas fa-map-marker-alt"></i>
                    <input type="text" class="form-control" id="address" name="address" required>
                </div>
                <div class="invalid-feedback">
                    Vui lòng nhập địa chỉ
                </div>
            </div>
            
            <div class="form-group">
                <label for="website" class="form-label">Website</label>
                <div class="input-group">
                    <i class="fas fa-globe"></i>
                    <input type="url" class="form-control" id="website" name="website" 
                           required pattern="https?://.+">
                </div>
                <div class="invalid-feedback">
                    Vui lòng nhập URL website hợp lệ (bắt đầu bằng http:// hoặc https://)
                </div>
            </div>
            
            <button type="submit" class="submit-btn">
                <i class="fas fa-user-plus me-2"></i>Đăng ký
            </button>
            
            <div class="login-link">
                <span>Đã có tài khoản? </span>
                <a href="Login">Đăng nhập</a>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Form validation
        (function () {
            'use strict'
            const forms = document.querySelectorAll('.needs-validation')
            
            Array.from(forms).forEach(form => {
                form.addEventListener('submit', event => {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
                
                // Real-time validation
                const inputs = form.querySelectorAll('input')
                inputs.forEach(input => {
                    input.addEventListener('input', () => {
                        if (input.checkValidity()) {
                            input.classList.add('is-valid')
                            input.classList.remove('is-invalid')
                        } else {
                            input.classList.remove('is-valid')
                            input.classList.add('is-invalid')
                        }
                    })
                })
            })
        })()
    </script>
</body>
</html> 