<%-- 
    Document   : AdminDashboard
    Created on : Jun 3, 2025, 1:57:52 AM
    Author     : phamd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard - Job Portal</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
        <style>
            .dashboard-card {
                transition: transform 0.2s;
                cursor: pointer;
            }
            .dashboard-card:hover {
                transform: translateY(-5px);
            }
            .stat-card {
                border-radius: 15px;
                border: none;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }
            .stat-icon {
                font-size: 2.5rem;
                margin-bottom: 1rem;
            }
            .action-card {
                background: #fff;
                border-radius: 15px;
                padding: 20px;
                margin-bottom: 20px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body class="bg-light">
        <!-- Include Header -->
        <jsp:include page="Header.jsp" />

        <div class="container mt-4">
            <div class="row mb-4">
                <div class="col">
                    <h2><i class="fas fa-tachometer-alt me-2"></i>Admin Dashboard</h2>
                </div>
            </div>

            

            <!-- Quick Actions -->
            <div class="row">
                <div class="col-md-6">
                    <div class="action-card">
                        <h4 class="mb-4"><i class="fas fa-tasks me-2"></i>Quản lý tài khoản</h4>
                        <div class="list-group">
                            <a href="AdminProfile.jsp" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                Thông tin cá nhân
                                <i class="fas fa-user"></i>
                            </a>
                            <a href="UserManage.jsp" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                Quản lý người dùng
                                <i class="fas fa-chevron-right"></i>
                            </a>
                            <a href="AdminEmployerApproval" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                Duyệt nhà tuyển dụng
                                <span class="badge bg-primary rounded-pill">${pendingEmployers}</span>
                            </a>
                            <a href="admin/role-management" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                Quản lý phân quyền
                                <i class="fas fa-chevron-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-6">
                    <div class="action-card">
                        <h4 class="mb-4"><i class="fas fa-cog me-2"></i>Quản lý nội dung</h4>
                        <div class="list-group">
                            <a href="${pageContext.request.contextPath}/AdminJobApproval" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                Quản lý tin tuyển dụng
                                <i class="fas fa-chevron-right"></i>
                            </a>
                            <a href="admin/category-management" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                Quản lý danh mục
                                <i class="fas fa-chevron-right"></i>
                            </a>
                            <a href="admin/report-management" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                Xử lý báo cáo
                                <span class="badge bg-danger rounded-pill">${pendingReports}</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Recent Activities -->
            <div class="row mt-4">
                <div class="col-12">
                    <div class="action-card">
                        <h4 class="mb-4"><i class="fas fa-history me-2"></i>Hoạt động gần đây</h4>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Thời gian</th>
                                        <th>Người dùng</th>
                                        <th>Hoạt động</th>
                                        <th>Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${recentActivities}" var="activity">
                                        <tr>
                                            <td>${activity.timestamp}</td>
                                            <td>${activity.username}</td>
                                            <td>${activity.action}</td>
                                            <td>
                                                <span class="badge bg-${activity.status == 'success' ? 'success' : 'danger'}">
                                                    ${activity.status}
                                                </span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Include Footer -->
        <jsp:include page="Footer.jsp" />

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
