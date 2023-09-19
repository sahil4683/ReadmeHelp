package com.as.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailHandler {

	@Autowired
	private JavaMailSender sender;

	public void sendEmailNotification(String userEmail, String userSubject, String userMessage) {
		MimeMessage message = sender.createMimeMessage();

		try {
			// Enable the multipart flag!
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(userEmail);
			helper.setText("<html><body>"
					+ "<strong> "+userMessage+"</strong>"
					+ "<br><br><br>"
					+ "<address>Navasri Nagarpalika<b><br>"
					+ "navasari gujarat,2345464<br>"
					+ "Support At: 1822342344523<br>"
					+ "<img width=186px height=100px style='background-color: darkcyan; border: outset;'  src='cid:id101'/><body></html>", true);
			helper.setSubject(userSubject);

			ClassPathResource file = new ClassPathResource("mailFooter.png");
			helper.addInline("id101", file);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		sender.send(message);
	}
}
