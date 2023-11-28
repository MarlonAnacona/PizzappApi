package com.API.Pizzapp.Services.Impl;

import com.API.Pizzapp.Services.EmarilServiceI;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmarilServiceI {
    @Autowired
    private JavaMailSender mailSender;

    private String htmlTemplate = "<!DOCTYPE html>\n" +
            "<html lang=\"es\">\n" +
            "<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "<style>\n" +
            "  body {\n" +
            "    font-family: 'Arial', sans-serif;\n" +
            "    margin: 0;\n" +
            "    padding: 0;\n" +
            "    background-color: #FAFAFA;\n" +
            "    color: #333;\n" +
            "  }\n" +
            "  .email-container {\n" +
            "    max-width: 600px;\n" +
            "    margin: 20px auto;\n" +
            "    padding: 20px;\n" +
            "    background-color: #FFF;\n" +
            "    border: 1px solid #DDD;\n" +
            "    border-radius: 5px;\n" +
            "    text-align: center;\n" +
            "  }\n" +
            "  .header {\n" +
            "    background-color: #D32F2F;\n" +
            "    color: white;\n" +
            "    padding: 10px;\n" +
            "    border-top-left-radius: 5px;\n" +
            "    border-top-right-radius: 5px;\n" +
            "  }\n" +
            "  .header img {\n" +
            "    width: 100px;\n" +
            "    height: auto;\n" +
            "  }\n" +
            "  .code-container {\n" +
            "    background-color: #000;\n" +
            "    color: #FFF;\n" +
            "    padding: 20px;\n" +
            "    margin: 20px 0;\n" +
            "    font-size: 24px;\n" +
            "    font-weight: bold;\n" +
            "  }\n" +
            "  .footer {\n" +
            "    font-size: 14px;\n" +
            "    color: #555;\n" +
            "    padding: 10px;\n" +
            "  }\n" +
            "  .validity {\n" +
            "    font-size: 12px;\n" +
            "    color: #888;\n" +
            "    margin-top: 10px;\n" +
            "  }\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div class=\"email-container\">\n" +
            "  <div class=\"header\">\n" +
            "    <a href=\"https://imgur.com/6bwCXbV\"><img src=\"https://i.imgur.com/6bwCXbV.png\" title=\"source: imgur.com\" /></a>\n" +
            "    <h1>Restablecimiento de Contraseña</h1>\n" +
            "  </div>\n" +
            "  <p>Estimado usuario,</p>\n" +
            "  <p>Has solicitado restablecer tu contraseña. Por favor, introduce el siguiente código en la aplicación:</p>\n" +
            "  <div class=\"code-container\">\n" +
            "    CÓDIGO: %s\n" +
            "  </div>\n" +
            "  <p class=\"validity\">Este código es válido por 15 minutos. Pasado este tiempo, necesitarás solicitar uno nuevo.</p>\n" +
            "  <div class=\"footer\">\n" +
            "    <p>Si no has solicitado un restablecimiento de contraseña, por favor ignora este mensaje.</p>\n" +
            "  </div>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    @Override
    public void sendEmail(String to, String subject, String code) {
        String htmlContent = String.format(htmlTemplate, code);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("pizzaappnoreply@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        }  catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

