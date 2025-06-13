<%-- 
    Document   : jobseeker_dashboard
    Created on : Jun 2, 2025, 12:59:37 AM
    Author     : phamd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard - Job Portal</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .dashboard-card {
                transition: transform 0.2s;
            }
            .dashboard-card:hover {
                transform: translateY(-5px);
            }
        </style>
    </head>
    <body>
        <!-- Include TaskBar -->
        <jsp:include page="TaskBar.jsp"></jsp:include>

        <div class="container py-5">
            <div class="row mb-4">
                <div class="col">
                    <h2>Xin chào, ${sessionScope.account.username}!</h2>
                    <p class="text-muted">Chào mừng bạn đến với Job Portal</p>
                </div>
            </div>

            <div class="row g-4">
                <!-- Job Applications Card -->
                <div class="col-md-6 col-lg-4">
                    <div class="card h-100 dashboard-card">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h5 class="card-title mb-0">Việc làm đã ứng tuyển</h5>
                                <i class="fas fa-briefcase fa-2x text-primary"></i>
                            </div>
                            <h2 class="mb-0">0</h2>
                            <p class="text-muted">Đơn ứng tuyển</p>
                            <a href="#" class="btn btn-primary w-100">Xem chi tiết</a>
                        </div>
                    </div>
                </div>

                <!-- Saved Jobs Card -->
                <div class="col-md-6 col-lg-4">
                    <div class="card h-100 dashboard-card">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h5 class="card-title mb-0">Việc làm đã lưu</h5>
                                <i class="fas fa-heart fa-2x text-danger"></i>
                            </div>
                            <h2 class="mb-0">0</h2>
                            <p class="text-muted">Công việc</p>
                            <a href="#" class="btn btn-primary w-100">Xem chi tiết</a>
                        </div>
                    </div>
                </div>

                <!-- Profile Completion Card -->
                <div class="col-md-6 col-lg-4">
                    <div class="card h-100 dashboard-card">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h5 class="card-title mb-0">Hoàn thiện hồ sơ</h5>
                                <i class="fas fa-user-check fa-2x text-success"></i>
                            </div>
                            <div class="progress mb-3" style="height: 10px;">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 25%"></div>
                            </div>
                            <p class="text-muted">25% hoàn thành</p>
                            <a href="${pageContext.request.contextPath}/Profile" class="btn btn-primary w-100">Cập nhật hồ sơ</a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Recent Job Postings -->
            <div class="row mt-5">
                <div class="col-12">
                    <h3 class="mb-4">Việc làm gợi ý cho bạn</h3>
                    <div class="card">
                        <div class="card-body">
                            <div class="text-center py-5">
                                <i class="fas fa-search fa-3x text-muted mb-3"></i>
                                <p class="text-muted">Chúng tôi sẽ gợi ý việc làm phù hợp sau khi bạn hoàn thiện hồ sơ.</p>
                                <a href="${pageContext.request.contextPath}/Profile" class="btn btn-primary">
                                    <i class="fas fa-user-edit me-2"></i>Hoàn thiện hồ sơ ngay
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html> 