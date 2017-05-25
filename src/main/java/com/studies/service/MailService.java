
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
    String subject = "Java send mail example";
    String body = "hi ....,!";

    sendFromGMail(from, pass, to, subject, body);
}

private static void sendFromGMail(String from, String pass, String to, String subject, String body) {
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
        message.setSubject("That you for purchasing our book");
     // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();
        
        // Now set the actual message
        messageBodyPart.setText("You have succesfully purchased book, here is your pdf file");

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        String filename = "D:/Project/Uploaded/rolas - Rolas.pdf";
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);

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