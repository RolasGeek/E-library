
package com.studies.service;

import java.io.File;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class MailService {

private static String USER_NAME = "rolandasjasiunas@gmail.com";  // GMail user name (just the part before "@gmail.com")
private static String PASSWORD = "enjoymylife"; // GMail password

private static String RECIPIENT = "rolandas.jasiunas@gmail.com";

public void sendMail(String link, String to) {
    String from = USER_NAME;
    String pass = PASSWORD;
    String subject = "Purchase from E-library";
    String body = "Thank you for your purchase, here is yours file";

    sendFromGMail(from, pass, to, subject, body, link);
}

public void sendMail(String link, String to, String subject, String body){
    String from = USER_NAME;
    String pass = PASSWORD;
    sendFromGMail(from, pass, to, subject, body, link);
}


private static void sendFromGMail(String from, String pass, String to, String subject, String body, String link) {
    Properties props = System.getProperties();
  String host = "smtp.gmail.com";

    props.put("mail.smtp.starttls.enable", "true");

    props.put("mail.smtp.ssl.trust", host);
    props.put("mail.smtp.user", from);
    props.put("mail.smtp.password", pass);
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");


    Session session = Session.getDefaultInstance(props);
    MimeMessage message = new MimeMessage(session);

    try {


        message.setFrom(new InternetAddress(from));
        InternetAddress toAddress = new InternetAddress(to);

        message.addRecipient(Message.RecipientType.TO, toAddress);
        message.setSubject(subject);
     // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();
        
        // Now set the actual message
        messageBodyPart.setText(body);

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        if (link != null){
            messageBodyPart = new MimeBodyPart();
            String filename = link;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
        }

        // Send the complete message parts
        message.setContent(multipart);


        Transport transport = session.getTransport("smtp");


        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        System.out.println("Sent message successfully....");

    }
    catch (AddressException ae) {
        ae.printStackTrace();
    }
    catch (MessagingException me) {
        me.printStackTrace();
    }
    }
   } 