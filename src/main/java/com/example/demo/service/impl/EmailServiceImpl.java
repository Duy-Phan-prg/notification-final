package com.example.demo.service.impl;

import com.example.demo.entity.EmailLog;
import com.example.demo.entity.EmailTemplate;
import com.example.demo.enums.EmailStatus;
import com.example.demo.enums.EmailType;
import com.example.demo.repository.EmailLogRepository;
import com.example.demo.repository.EmailTemplateRepository;
import com.example.demo.service.EmailService;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.Personalization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailLogRepository emailLogRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;

    private static final String FROM_EMAIL = "phanduy.prg@gmail.com";

    @Override
    public void sendRegisterSuccess(String email, String name) {
        EmailLog log = EmailLog.builder()
                .email(email)
                .name(name)
                .type(EmailType.REGISTER_SUCCESS)
                .build();

        try {
            EmailTemplate template = emailTemplateRepository.findByType(EmailType.REGISTER_SUCCESS).orElse(null);
            String subject = template != null ? template.getSubject() : "Đăng ký thành công";
            String htmlContent = template != null ? buildHtmlFromTemplate(template, name) : getDefaultRegisterTemplate(name);

            sendHtmlEmail(email, subject, htmlContent);
            log.setStatus(EmailStatus.SUCCESS);
            log("Email đăng ký thành công đã được gửi tới: " + email);

        } catch (Exception e) {
            log.setStatus(EmailStatus.FAIL);
            log.setErrorMessage(e.getMessage());
            log("Lỗi gửi email đăng ký tới " + email + ": " + e.getMessage());
        }

        emailLogRepository.save(log);
    }

    @Override
    public void sendPaymentSuccess(String email, String name) {
        EmailLog log = EmailLog.builder()
                .email(email)
                .name(name)
                .type(EmailType.PAYMENT_SUCCESS)
                .build();

        try {
            EmailTemplate template = emailTemplateRepository.findByType(EmailType.PAYMENT_SUCCESS).orElse(null);
            String subject = template != null ? template.getSubject() : "Thanh toán thành công";
            String htmlContent = template != null ? buildHtmlFromTemplate(template, name) : getDefaultPaymentTemplate(name);

            sendHtmlEmail(email, subject, htmlContent);
            log.setStatus(EmailStatus.SUCCESS);
            log("Email thanh toán thành công đã được gửi tới: " + email);

        } catch (Exception e) {
            log.setStatus(EmailStatus.FAIL);
            log.setErrorMessage(e.getMessage());
            log("Lỗi gửi email thanh toán tới " + email + ": " + e.getMessage());
        }

        emailLogRepository.save(log);
    }

    private String buildHtmlFromTemplate(EmailTemplate template, String name) {
        return "<!DOCTYPE html><html lang=\"vi\"><head><meta charset=\"UTF-8\"><style>" +
                "body{font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;line-height:1.6;color:#333;background:#f5f5f5;margin:0;padding:0;}" +
                ".container{max-width:600px;margin:20px auto;background:white;border-radius:8px;box-shadow:0 2px 10px rgba(0,0,0,0.1);overflow:hidden;}" +
                ".header{background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);color:white;padding:40px 20px;text-align:center;}" +
                ".header h1{margin:0;font-size:28px;font-weight:700;}" +
                ".content{padding:40px 30px;}" +
                ".greeting{font-size:18px;font-weight:600;color:#333;margin-bottom:20px;}" +
                ".message{color:#666;margin-bottom:30px;line-height:1.8;}" +
                ".footer{background:#f9f9f9;padding:20px 30px;text-align:center;border-top:1px solid #eee;font-size:12px;color:#999;}" +
                "</style></head><body><div class=\"container\"><div class=\"header\"><h1>" + template.getTitle() + "</h1></div>" +
                "<div class=\"content\"><div class=\"greeting\">Xin chào <strong>" + name + "</strong>,</div>" +
                "<div class=\"message\">" + template.getMessage() + "</div></div>" +
                "<div class=\"footer\"><p>© 2024 Email Notification Service. Tất cả quyền được bảo lưu.</p></div>" +
                "</div></body></html>";
    }

    private String getDefaultRegisterTemplate(String name) {
        return "<!DOCTYPE html><html lang=\"vi\"><head><meta charset=\"UTF-8\"><style>" +
                "body{font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;line-height:1.6;color:#333;background:#f5f5f5;margin:0;padding:0;}" +
                ".container{max-width:600px;margin:20px auto;background:white;border-radius:8px;box-shadow:0 2px 10px rgba(0,0,0,0.1);overflow:hidden;}" +
                ".header{background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);color:white;padding:40px 20px;text-align:center;}" +
                ".header h1{margin:0;font-size:28px;font-weight:700;}" +
                ".content{padding:40px 30px;}" +
                ".greeting{font-size:18px;font-weight:600;color:#333;margin-bottom:20px;}" +
                ".message{color:#666;margin-bottom:30px;line-height:1.8;}" +
                ".footer{background:#f9f9f9;padding:20px 30px;text-align:center;border-top:1px solid #eee;font-size:12px;color:#999;}" +
                "</style></head><body><div class=\"container\"><div class=\"header\"><h1>🎉 Chúc mừng!</h1><p>Đăng ký thành công</p></div>" +
                "<div class=\"content\"><div class=\"greeting\">Xin chào <strong>" + name + "</strong>,</div>" +
                "<div class=\"message\">Cảm ơn bạn đã đăng ký tài khoản với chúng tôi! Chúng tôi rất vui được chào đón bạn gia nhập cộng đồng của mình.</div>" +
                "<div class=\"message\">Bây giờ bạn có thể đăng nhập vào tài khoản và bắt đầu sử dụng dịch vụ của chúng tôi.</div></div>" +
                "<div class=\"footer\"><p>© 2024 Email Notification Service. Tất cả quyền được bảo lưu.</p></div>" +
                "</div></body></html>";
    }

    private String getDefaultPaymentTemplate(String name) {
        return "<!DOCTYPE html><html lang=\"vi\"><head><meta charset=\"UTF-8\"><style>" +
                "body{font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;line-height:1.6;color:#333;background:#f5f5f5;margin:0;padding:0;}" +
                ".container{max-width:600px;margin:20px auto;background:white;border-radius:8px;box-shadow:0 2px 10px rgba(0,0,0,0.1);overflow:hidden;}" +
                ".header{background:linear-gradient(135deg,#10b981 0%,#059669 100%);color:white;padding:40px 20px;text-align:center;}" +
                ".header h1{margin:0;font-size:28px;font-weight:700;}" +
                ".content{padding:40px 30px;}" +
                ".greeting{font-size:18px;font-weight:600;color:#333;margin-bottom:20px;}" +
                ".message{color:#666;margin-bottom:30px;line-height:1.8;}" +
                ".footer{background:#f9f9f9;padding:20px 30px;text-align:center;border-top:1px solid #eee;font-size:12px;color:#999;}" +
                "</style></head><body><div class=\"container\"><div class=\"header\"><h1>✓ Thanh toán thành công!</h1><p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi</p></div>" +
                "<div class=\"content\"><div class=\"greeting\">Xin chào <strong>" + name + "</strong>,</div>" +
                "<div class=\"message\">Chúng tôi xác nhận rằng thanh toán của bạn đã được xử lý thành công. Cảm ơn bạn đã tin tưởng chúng tôi!</div>" +
                "<div class=\"message\">Giao dịch đã được xác nhận.</div></div>" +
                "<div class=\"footer\"><p>© 2024 Email Notification Service. Tất cả quyền được bảo lưu.</p></div>" +
                "</div></body></html>";
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) throws Exception {
        Mail mail = new Mail();
        mail.setFrom(new com.sendgrid.helpers.mail.Email(FROM_EMAIL));
        mail.setSubject(subject);
        
        com.sendgrid.helpers.mail.Personalization personalization = new com.sendgrid.helpers.mail.Personalization();
        personalization.addTo(new com.sendgrid.helpers.mail.Email(to));
        mail.addPersonalization(personalization);
        
        mail.addContent(new com.sendgrid.helpers.mail.Content("text/html", htmlContent));

        SendGrid sg = new SendGrid(sendGridApiKey);
        com.sendgrid.Request request = new com.sendgrid.Request();
        request.setMethod(com.sendgrid.Request.Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        
        com.sendgrid.Response response = sg.api(request);
        if (response.getStatusCode() >= 400) {
            throw new Exception("SendGrid API error: " + response.getStatusCode() + " - " + response.getBody());
        }
    }

    private void log(String message) {
        log.info(message);
    }
}
