package obss.intern.veyis.util;

import lombok.SneakyThrows;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Users;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * This class is intended to be executed by a thread, so it implements Runnable interface.
 */
public class EmailSender implements Runnable {

    private Phase phase;

    public EmailSender(Phase phase) {
        this.phase = phase;
    }


    @SneakyThrows
    @Override
    public void run() {
        emailSender(phase);
    }

    /**
     * This function sets the configurations of smtp server, calls "prepareMessage" function to create messages and sends
     * mails to mentor and mentee.
     */
    private static void emailSender(Phase phase) throws MessagingException {
        String myAccount = "obss.veyis.deneme@gmail.com";
        String password = "WM'*cR4)8m;JHd7}";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, password);
            }
        });
        Message message = prepareMessage(session, myAccount, phase, phase.getProgram().getMentee());
        Message message2 = prepareMessage(session, myAccount, phase, phase.getProgram().getMentor());
        Transport.send(message);
        Transport.send(message2);
    }

    /**
     * This function creates the contents of the mail.
     */
    private static Message prepareMessage(Session session, String myAccount, Phase phase, Users to) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to.getGmail()));
            message.setSubject("Faz Bitişi Hatırlatıcı");
            String html = "<h1> Sayin " + to.getUsername() + ", <b>" + phase.getProgram().getSubject().getSubject_name() + " / "
                    + phase.getProgram().getSubject().getSubsubject_name() + "</b> konulu programinizin, "
                    + phase.getId().getPhase_id() + ". fazinin bitmesine 1 saat var! </h1 > " +
                    "<br/><br/>" +
                    "<h3><b> Mentor: </b>" + phase.getProgram().getMentor().getUsername() + "</h3>" +
                    "<h3><b> Mentee: </b>" + phase.getProgram().getMentee().getUsername() + "</h3>" +
                    "<h3><b> Program Baslangic Tarihi: </b>" + phase.getProgram().getStart_date() + "</h3>" +
                    "</br>";
            BodyPart messageBodyPart = new MimeBodyPart();
            MimeMultipart multipart = new MimeMultipart("related");
            messageBodyPart.setContent(html, "text/html");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
