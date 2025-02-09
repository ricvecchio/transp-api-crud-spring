package com.transportadora.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendEmail(String email, String subject, String body) {
        // Simulação do envio de e-mail (substituir por serviço de e-mail real) // EXCLUIR
        System.out.println("Enviando e-mail para: " + email); // EXCLUIR
        System.out.println("Assunto: " + subject); // EXCLUIR
        System.out.println("Mensagem: " + body); // EXCLUIR

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remetente);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
            return "E-mail enviado!";
        }catch (Exception e) {
            return "Erro ao tentar enviar o e-mail " + e.getLocalizedMessage();
        }
    }

}
