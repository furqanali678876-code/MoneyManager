package in.furqanali.MoneyManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service

public class EmailService {
    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;
private final JavaMailSender mailSender;
public void sendEmail(String to,String subject,String body){
    try{
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
    } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
    }
}
}
