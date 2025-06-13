<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<footer class="bg-dark text-light mt-5 py-4">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h5>Về chúng tôi</h5>
                <p>Chúng tôi là nền tảng kết nối ứng viên với nhà tuyển dụng, giúp mọi người tìm kiếm công việc phù hợp với đam mê và kỹ năng của mình.</p>
            </div>
            <div class="col-md-4">
                <h5>Liên kết nhanh</h5>
                <ul class="list-unstyled">
                    <li><a href="${pageContext.request.contextPath}/JobSearch" class="text-light">Tìm việc làm</a></li>
                    <li><a href="${pageContext.request.contextPath}/Companies" class="text-light">Danh sách công ty</a></li>
                    <li><a href="${pageContext.request.contextPath}/Profile" class="text-light">Quản lý hồ sơ</a></li>
                </ul>
            </div>
            <div class="col-md-4">
                <h5>Liên hệ</h5>
                <ul class="list-unstyled">
                    <li><i class="fas fa-envelope me-2"></i>Email: contact@jobportal.com</li>
                    <li><i class="fas fa-phone me-2"></i>Điện thoại: (84) 123-456-789</li>
                    <li><i class="fas fa-map-marker-alt me-2"></i>Địa chỉ: TP. Hồ Chí Minh, Việt Nam</li>
                </ul>
            </div>
        </div>
        <div class="text-center mt-4">
            <p class="mb-0">&copy; 2024 Job Portal. All rights reserved.</p>
        </div>
    </div>
</footer> 