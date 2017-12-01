package utilities;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/*
 * This class implements the sending email function. It sends an email to each customer object that subscribes to the rental item.
 */
public class Emailer {
	private String to;
	private String from;
	private String host;
	private String content = "This movie is now available! ";

	// Recipient's email ID needs to be mentioned.
	public Emailer(String to, String from, String host, Object arg) {
		this.to = to;
		this.from = from;
		this.host = host;
		this.content.concat((String)arg);

	}

	public void sendEmail() {
		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("A Movie You Like Is Available!!!");

			// Now set the actual message
			message.setText(content);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
