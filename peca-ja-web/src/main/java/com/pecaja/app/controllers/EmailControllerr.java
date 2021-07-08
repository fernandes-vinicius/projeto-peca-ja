package com.pecaja.app.controllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.pecaja.app.models.Mail;

@Component
public class EmailControllerr {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
    private TemplateEngine templateEngine;
    
	public void enviar(String destinatario, String remetente,String mensagem) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(destinatario);
		email.setFrom(remetente);
		email.setSubject("Equipe Peça-Já");
		email.setText(mensagem);
		mailSender.send(email);
	}
	
	@Async
    public void sendHtmlMail(Mail mail, Context context, String templateName, String nomerevendedor) throws MessagingException {
        String content = templateEngine.process("emails/"+templateName, context);
		
        MimeMessagePreparator messagePreparator = mimeMessage -> {
	        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
	        messageHelper.setFrom(mail.getFrom());
	        messageHelper.setTo(mail.getTo());
	        messageHelper.setSubject(mail.getSubject());
	        messageHelper.setText(content, true);
	    };
	    try {
	        mailSender.send(messagePreparator);
	    } catch (MailException e) {
	    	System.out.println(e);
	        // runtime exception; compiler will not force you to handle it
	    } 
    }

}