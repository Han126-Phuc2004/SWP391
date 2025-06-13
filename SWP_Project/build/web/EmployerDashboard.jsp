<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employer Dashboard</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
    <style>
        body { 
            font-family: Arial, sans-serif; 
            background: #f5f6fa;
            min-height: 100vh;
            display: flex;
        }
        .sidebar {
            width: 280px;
            background: #fff;
            padding: 20px;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            position: fixed;
            height: 100vh;
            left: 0;
            top: 0;
        }
        .main-content {
            flex: 1;
            margin-left: 280px;
            padding: 30px;
        }
        .profile-section {
            text-align: center;
            padding: 20px 0;
            border-bottom: 1px solid #eee;
            margin-bottom: 20px;
        }
        .profile-image {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            margin-bottom: 15px;
            object-fit: cover;
            border: 3px solid #0984e3;
        }
        .nav-link {
            color: #2d3436;
            padding: 12px 20px;
            margin: 8px 0;
            border-radius: 8px;
            transition: all 0.3s;
            display: flex;
            align-items: center;
        }
        .nav-link:hover, .nav-link.active {
            background: #0984e3;
            color: #fff;
            text-decoration: none;
        }
        .nav-link i {
            margin-right: 10px;
            width: 20px;
            text-align: center;
        }
        .stats-card {
            background: #fff;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.05);
            transition: transform 0.3s;
        }
        .stats-card:hover {
            transform: translateY(-5px);
        }
        .stats-card i {
            font-size: 24px;
            margin-bottom: 15px;
            color: #0984e3;
        }
        .stats-card h3 {
            font-size: 24px;
            margin: 10px 0;
            color: #2d3436;
        }
        .stats-card p {
            color: #636e72;
            margin: 0;
        }
        .welcome-banner {
            background: linear-gradient(135deg, #0984e3, #00b894);
            color: white;
            padding: 30px;
            border-radius: 12px;
            margin-bottom: 30px;
        }
        .welcome-banner h2 {
            margin: 0;
            font-size: 24px;
        }
        .welcome-banner p {
            margin: 10px 0 0;
            opacity: 0.9;
        }
        .action-buttons {
            margin-top: 20px;
        }
        .action-button {
            background: #fff;
            color: #0984e3;
            border: none;
            padding: 10px 20px;
            border-radius: 6px;
            margin-right: 10px;
            font-weight: bold;
            transition: all 0.3s;
        }
        .action-button:hover {
            background: rgba(255,255,255,0.9);
            transform: translateY(-2px);
        }
        .recent-activity {
            background: #fff;
            border-radius: 12px;
            padding: 20px;
            margin-top: 30px;
        }
        .activity-item {
            padding: 15px 0;
            border-bottom: 1px solid #eee;
        }
        .activity-item:last-child {
            border-bottom: none;
        }
        .logout-btn {
            background: #d63031;
            color: #fff;
            border: none;
            padding: 8px 18px;
            border-radius: 6px;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s;
            position: absolute;
            top: 20px;
            right: 30px;
        }
        .logout-btn:hover {
            background: #c0392b;
        }
        /* Add taskbar styles */
        .taskbar {
            position: fixed;
            top: 0;
            right: 0;
            left: 280px; /* Same as sidebar width */
            height: 70px;
            background: #fff;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            padding: 0 30px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            z-index: 1000;
        }
        .taskbar-left {
            display: flex;
            align-items: center;
        }
        .search-box {
            position: relative;
            margin-right: 20px;
        }
        .search-box input {
            width: 300px;
            padding: 8px 15px;
            padding-left: 35px;
            border: 1px solid #ddd;
            border-radius: 20px;
            font-size: 14px;
        }
        .search-box i {
            position: absolute;
            left: 12px;
            top: 50%;
            transform: translateY(-50%);
            color: #666;
        }
        .taskbar-right {
            display: flex;
            align-items: center;
            gap: 20px;
        }
        .notification-bell, .messages-icon {
            position: relative;
            color: #2d3436;
            font-size: 20px;
            cursor: pointer;
        }
        .notification-badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background: #e74c3c;
            color: white;
            border-radius: 50%;
            width: 18px;
            height: 18px;
            font-size: 11px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .main-content {
            margin-top: 70px; /* Add margin for taskbar */
        }
        .profile-menu {
            position: relative;
            cursor: pointer;
        }
        .profile-menu img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 10px;
        }
        .profile-dropdown {
            position: absolute;
            top: 100%;
            right: 0;
            background: white;
            box-shadow: 0 3px 10px rgba(0,0,0,0.2);
            border-radius: 8px;
            width: 200px;
            display: none;
            z-index: 1000;
        }
        .profile-dropdown.show {
            display: block;
        }
        .profile-dropdown a {
            padding: 12px 20px;
            display: flex;
            align-items: center;
            color: #2d3436;
            text-decoration: none;
            transition: background 0.3s;
        }
        .profile-dropdown a:hover {
            background: #f5f6fa;
        }
        .profile-dropdown i {
            width: 20px;
            margin-right: 10px;
        }
        .divider {
            height: 1px;
            background: #eee;
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="profile-section">
            <img src="https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp" alt="Company Logo" class="profile-image">
            <h4><%= session.getAttribute("username") != null ? session.getAttribute("username") : "Company Name" %></h4>
            <p class="text-muted">Employer Account</p>
        </div>
        
        <nav>
            <a href="#" class="nav-link active">
                <i class="fas fa-home"></i> Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/create-post" class="nav-link">
                <i class="fas fa-plus-circle"></i> Đăng tin tuyển dụng
            </a>
            <a href="${pageContext.request.contextPath}/posts" class="nav-link">
                <i class="fas fa-briefcase"></i> Quản lý tin tuyển dụng
            </a>
            <a href="${pageContext.request.contextPath}/applications" class="nav-link">
                <i class="fas fa-users"></i> Đơn ứng tuyển
            </a>
            <a href="${pageContext.request.contextPath}/EmployerProfile" class="nav-link">
                <i class="fas fa-user"></i>
                Hồ sơ
            </a>
            <a href="${pageContext.request.contextPath}/settings" class="nav-link">
                <i class="fas fa-cog"></i> Cài đặt
            </a>
        </nav>
    </div>

    <!-- Taskbar -->
    <div class="taskbar">
        <div class="taskbar-left">
            <div class="search-box">
                <i class="fas fa-search"></i>
                <input type="text" placeholder="Tìm kiếm...">
            </div>
        </div>
        <div class="taskbar-right">
            <div class="notification-bell">
                <i class="fas fa-bell"></i>
                <span class="notification-badge">3</span>
            </div>
            <div class="messages-icon">
                <i class="fas fa-envelope"></i>
                <span class="notification-badge">5</span>
            </div>
            <div class="profile-menu" onclick="toggleProfileMenu()">
                <img src="https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp" alt="Profile">
                <div class="profile-dropdown" id="profileDropdown">
                    <c:when test="${sessionScope.account.roleID == 3}">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/Profile">
                    <i class="fas fa-user me-2"></i>Hồ sơ
                </a>
            </c:when>
                    <a href="ChangePassword">
                        <i class="fas fa-key"></i>
                        Đổi mật khẩu
                    </a>
                    <div class="divider"></div>
                    <a href="<%=request.getContextPath()%>/Logout" style="color: #d63031;">
                        <i class="fas fa-sign-out-alt"></i>
                        Đăng xuất
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <!-- Welcome Banner -->
        <div class="welcome-banner">
            <h2>Chào mừng trở lại!</h2>
            <p>Quản lý tin tuyển dụng và tìm ứng viên phù hợp cho công ty của bạn.</p>
            <div class="action-buttons">
                <button class="action-button" onclick="location.href='employer/post-job'">
                    <i class="fas fa-plus-circle"></i> Đăng tin mới
                </button>
                <button class="action-button" onclick="location.href='employer/applications'">
                    <i class="fas fa-users"></i> Xem đơn ứng tuyển
                </button>
            </div>
        </div>

        <!-- Statistics Cards -->
        <div class="row">
            <div class="col-md-3">
                <div class="stats-card text-center">
                    <i class="fas fa-briefcase"></i>
                    <h3>0</h3>
                    <p>Tin đang đăng</p>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stats-card text-center">
                    <i class="fas fa-users"></i>
                    <h3>0</h3>
                    <p>Đơn ứng tuyển mới</p>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stats-card text-center">
                    <i class="fas fa-eye"></i>
                    <h3>0</h3>
                    <p>Lượt xem tin</p>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stats-card text-center">
                    <i class="fas fa-check-circle"></i>
                    <h3>0</h3>
                    <p>Ứng viên đã tuyển</p>
                </div>
            </div>
        </div>

        <!-- Recent Activity -->
        <div class="recent-activity">
            <h4 class="mb-4"><i class="fas fa-history"></i> Hoạt động gần đây</h4>
            <div class="activity-item">
                <p><i class="fas fa-info-circle text-primary"></i> Chưa có hoạt động nào.</p>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function toggleProfileMenu() {
            document.getElementById('profileDropdown').classList.toggle('show');
        }

        // Close the dropdown when clicking outside
        window.onclick = function(event) {
            if (!event.target.closest('.profile-menu')) {
                var dropdown = document.getElementById('profileDropdown');
                if (dropdown.classList.contains('show')) {
                    dropdown.classList.remove('show');
                }
            }
        }
    </script>
</body>
</html> 