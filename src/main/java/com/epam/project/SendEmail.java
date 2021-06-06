package com.epam.project;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class SendEmail {
    private String message;
    private String subject;
    private String toAddress;

    public SendEmail(String message, String subject, String toAddress) {
        this.message = message;
        this.subject = subject;
        this.toAddress = toAddress;
    }

    public static void sendEmail(String host, String port,
                                 final String userName, final String password, String toAddress,
                                 String subject, String message) throws
            MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);

        // sends the e-mail
        Transport.send(msg);

    }

    public String getMessage() {
        return message;
    }

    public SendEmail setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public SendEmail setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getToAddress() {
        return toAddress;
    }

    public SendEmail setToAddress(String toAddress) {
        this.toAddress = toAddress;
        return this;
    }
}
