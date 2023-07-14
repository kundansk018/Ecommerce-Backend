package com.restapi.service;

import java.io.File;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.restapi.entity.EmailDetails;
import com.restapi.entity.Order;
import com.restapi.entity.OrderItem;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

//	public String sendSimpleMail(EmailDetails details)
//	{
//
//		try {
//
//		
//			SimpleMailMessage mailMessage = new SimpleMailMessage();
//
//		
//			mailMessage.setFrom(sender);
//			mailMessage.setTo(details.getRecipient());
//			mailMessage.setText(details.getMsgBody());
//			mailMessage.setSubject(details.getSubject());
//
//			
//			javaMailSender.send(mailMessage);
//			return "Mail Sent Successfully...";
//		}
//
//		catch (Exception e) {
//			return "Error while Sending Mail";
//		}
//	}

	public String sendSimpleMail(EmailDetails details) {

		Order order = new Order();

		String header = "<!DOCTYPE html>\n" + "<html>\n" + "\n" + "<body>" + "<h2>Orders Details..</h2>\n" + "\n"
				+ "<table style=\"width:100%;border:1px solid black\">\n" + "  <tr>\n"
				+ "    <th style=\"border:1px solid black\">Product Name</th>\n"
				+ "    <th style=\"border:1px solid black\">Product price  </th>\n"
				+ "    <th style=\"border:1px solid black\">Product Qunatity </th>\n" + "  </tr>";
		String content = "";
		String footer = "</table>\n" + "\n" + "<p>Thank U ..Plz Visit Again..!!</p>" + "</body>\n" + "</html>";

		Set<OrderItem> orderItems = details.getOrderItems();

		for (OrderItem orderData : orderItems) {

			System.out.println("orderItemsData quantity..!!" + orderData.getQuantity());

			content += " <tr>\n" + "    <td style=\"border:1px solid black\">" + orderData.getName() + "</td>\n"
					+ "    <td style=\"border:1px solid black\">" + orderData.getPrice() + "</td>\n"
					+ "    <td style=\"border:1px solid black\">" + orderData.getQuantity() + "</td>\n" + "  </tr>";

		}
		String msg = header.concat(content).concat(footer);
		boolean html = true;
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {

			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(details.getRecipient());
			mimeMessageHelper.setText(details.getMsgBody() + msg, html);
			mimeMessageHelper.setSubject(details.getSubject());

			javaMailSender.send(mimeMessage);
			return "Mail sent Successfully";
		}

		catch (MessagingException e) {

			return "Error while sending mail!!!";
		}
	}

	public String sendMailWithAttachment(EmailDetails details) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {

			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(details.getRecipient());
			mimeMessageHelper.setText(details.getMsgBody());
			mimeMessageHelper.setSubject(details.getSubject());

			FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));

			mimeMessageHelper.addAttachment(file.getFilename(), file);

			javaMailSender.send(mimeMessage);
			return "Mail sent Successfully";
		}

		catch (MessagingException e) {

			return "Error while sending mail!!!";
		}
	}

	@Override
	public void sendMail(String to, String subject, String text) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setFrom(sender);
		mailMessage.setTo(to);
		mailMessage.setText(text);
		mailMessage.setSubject(subject);

		javaMailSender.send(mailMessage);
	}

}
