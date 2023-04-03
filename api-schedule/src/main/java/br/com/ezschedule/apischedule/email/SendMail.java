package br.com.ezschedule.apischedule.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMail {

    @Autowired
    private final JavaMailSender sendMailFromJava;

    public SendMail(JavaMailSender sendMailFromJava) {
        this.sendMailFromJava = sendMailFromJava;
    }

    public void send(String to, String title, String content){

        var message = new SimpleMailMessage();

        message.setFrom("ezSchedule@outlook.com");
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);

        sendMailFromJava.send(message);
    }
}
