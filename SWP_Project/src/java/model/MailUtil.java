package model;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    private static final String HOST = ConfigUtil.get("email.smtp.host");
    private static final String PORT = ConfigUtil.get("email.smtp.port");
    private static final String USERNAME = ConfigUtil.get("email.username");
    private static final String PASSWORD = ConfigUtil.get("email.password");

    public static void sendMail(String to, String subject, String content) throws MessagingException {
        // Thiết lập các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", HOST);

        // Tạo session với thông tin xác thực
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            // Tạo message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");

            // Gửi email
            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
            throw e;
        }
    }

    public static void sendVerificationEmail(String to, String username, String verifyLink) throws MessagingException {
        String subject = "Xác thực tài khoản";
        String content = "Xin chào " + username + ",<br><br>"
            + "Vui lòng click vào link sau để xác thực tài khoản của bạn:<br>"
            + "<a href='" + verifyLink + "'>" + verifyLink + "</a><br><br>"
            + "Link xác thực có hiệu lực trong 24 giờ.<br>"
            + "Nếu bạn không đăng ký tài khoản này, vui lòng bỏ qua email này.";

        sendMail(to, subject, content);
    }

    public static void sendPasswordResetEmail(String to, String username, String resetLink) throws MessagingException {
        String subject = "Đặt lại mật khẩu";
        String content = "Xin chào " + username + ",<br><br>"
            + "Bạn đã yêu cầu đặt lại mật khẩu. Vui lòng click vào link sau để đặt lại mật khẩu:<br>"
            + "<a href='" + resetLink + "'>" + resetLink + "</a><br><br>"
            + "Link này có hiệu lực trong 24 giờ.<br>"
            + "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.";

        sendMail(to, subject, content);
    }

    public static void sendEmployerApprovalEmail(String to, String companyName, String verifyToken) throws MessagingException {
        String baseURL = "http://localhost:8080";
        String verifyLink = baseURL + "/Verify?token=" + verifyToken + "&email=" + to;
        
        String subject = "Xác thực tài khoản công ty";
        String content = "Xin chào " + companyName + ",<br><br>"
            + "Tài khoản công ty của bạn đã được phê duyệt.<br>"
            + "Vui lòng nhấp vào link dưới đây để xác thực tài khoản:<br><br>"
            + "<a href='" + verifyLink + "' style='padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px;'>Xác thực tài khoản</a><br><br>"
            + "Hoặc bạn có thể copy link sau: <br>"
            + verifyLink + "<br><br>"
            + "Lưu ý: Link xác thực này chỉ có hiệu lực trong vòng 24 giờ.<br><br>"
            + "Trân trọng,<br>"
            + "Ban quản trị";

        sendMail(to, subject, content);
    }

    public static void sendEmployerRejectionEmail(String to, String companyName, String reason) throws MessagingException {
        String subject = "Tài khoản công ty chưa được phê duyệt";
        String content = "Xin chào " + companyName + ",<br><br>"
            + "Rất tiếc, tài khoản công ty của bạn chưa được phê duyệt vì lý do sau:<br>"
            + reason + "<br><br>"
            + "Vui lòng cập nhật thông tin và gửi lại yêu cầu phê duyệt.<br><br>"
            + "Trân trọng,<br>"
            + "Ban quản trị";

        sendMail(to, subject, content);
    }

    public static void sendAccountBanEmail(String to, String username, String reason) throws MessagingException {
        String subject = "Tài khoản của bạn đã bị khóa";
        String content = "Xin chào " + username + ",<br><br>"
            + "Rất tiếc, tài khoản của bạn đã bị khóa vì lý do sau:<br>"
            + reason + "<br><br>"
            + "Nếu bạn có bất kỳ câu hỏi nào hoặc cần hỗ trợ, vui lòng liên hệ với chúng tôi qua email: "
            + USERNAME + ".<br><br>"
            + "Trân trọng,<br>"
            + "Ban quản trị";

        sendMail(to, subject, content);
    }

    public static void sendAccountUnbanEmail(String to, String username) throws MessagingException {
        String subject = "Tài khoản của bạn đã được mở khóa";
        String content = "Xin chào " + username + ",<br><br>"
            + "Tài khoản của bạn đã được mở khóa thành công. Bạn có thể tiếp tục sử dụng các dịch vụ của chúng tôi.<br><br>"
            + "Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua email: "
            + USERNAME + ".<br><br>"
            + "Trân trọng,<br>"
            + "Ban quản trị";

        sendMail(to, subject, content);
    }
}