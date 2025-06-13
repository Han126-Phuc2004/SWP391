<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/Home">
            <img src="${pageContext.request.contextPath}/assets/avatar/logo.png" alt="Job Portal Logo" height="100">
        </a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/JobSearch">Tìm việc làm</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/Companies">Công ty</a>
                </li>
            </ul>

            <div class="d-flex align-items-center">
                <c:choose>
                    <c:when test="${sessionScope.account != null}">
                        <!-- User is logged in -->
                        <div class="dropdown">
                            <button class="btn btn-link text-dark text-decoration-none dropdown-toggle" type="button" data-bs-toggle="dropdown">
                                <i class="fas fa-user-circle me-1"></i>
                                ${sessionScope.account.username}
                            </button>
                           <ul class="dropdown-menu dropdown-menu-end">
    <li>
        <c:choose>
            <%-- Admin Role --%>
            <c:when test="${sessionScope.account.roleID == 1}">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/Profile">
                    <i class="fas fa-user me-2"></i>Hồ sơ
                </a>
            </c:when>
            
            <%-- Job Seeker Role --%>
            <c:when test="${sessionScope.account.roleID == 2}">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/Profile">
                    <i class="fas fa-user me-2"></i>Hồ sơ
                </a>
            </c:when>
            
            <%-- Employer Role --%>
            <c:when test="${sessionScope.account.roleID == 3}">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/Profile">
                    <i class="fas fa-user me-2"></i>Hồ sơ
                </a>
            </c:when>
            
            <c:otherwise>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/Profile">
                    <i class="fas fa-user me-2"></i>Hồ sơ
                </a>
            </c:otherwise>
        </c:choose>
    </li>
    <li>
        <a class="dropdown-item" href="ChangePassword">
            <i class="fas fa-key me-2"></i>Đổi mật khẩu
        </a>
    </li>
    <li><hr class="dropdown-divider"></li>
    <li>
        <form action="${pageContext.request.contextPath}/Logout" method="post" style="display: inline;">
            <button type="submit" class="dropdown-item text-danger">
                <i class="fas fa-sign-out-alt me-2"></i>Đăng xuất
            </button>
        </form>
    </li>
</ul>

                        </div>
                    </c:when>
                    <c:otherwise>
                        <!-- User is not logged in -->
                        <a href="${pageContext.request.contextPath}/Login" class="btn btn-outline-primary me-2">
                            <i class="fas fa-sign-in-alt me-1"></i>Đăng nhập
                        </a>
                        <a href="${pageContext.request.contextPath}/RegisterJobSeeker" class="btn btn-primary">
                            <i class="fas fa-user-plus me-1"></i>Đăng ký
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav> 