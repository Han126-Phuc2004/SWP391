<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { font-family: Arial, sans-serif; background: #f5f6fa; }
        .container { max-width: 600px; margin: 100px auto; background: #fff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.08); padding: 40px; text-align: center; }
        h2 { color: #0984e3; margin-bottom: 30px; }
        .btn-group { display: flex; gap: 20px; justify-content: center; }
        .btn { 
            flex: 1;
            max-width: 200px;
            padding: 15px 30px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .btn-jobseeker { background: #0984e3; color: #fff; }
        .btn-employer { background: #00b894; color: #fff; }
        .btn:hover { transform: translateY(-2px); box-shadow: 0 4px 12px rgba(0,0,0,0.15); }
        .login-link { margin-top: 30px; color: #636e72; }
        .login-link a { color: #0984e3; text-decoration: none; font-weight: bold; }
        .login-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Chọn loại tài khoản</h2>
        <div class="btn-group">
            <a href="<%=request.getContextPath()%>/RegisterJobSeeker.jsp" class="btn btn-jobseeker">Job Seeker</a>
            <a href="<%=request.getContextPath()%>/RegisterEmployer.jsp" class="btn btn-employer">Employer</a>
        </div>
        <p class="login-link">
            Đã có tài khoản? <a href="<%=request.getContextPath()%>/Login.jsp">Đăng nhập ngay</a>
        </p>
    </div>
</body>
</html> 