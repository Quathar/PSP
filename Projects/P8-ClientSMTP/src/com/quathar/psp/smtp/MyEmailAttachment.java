package com.quathar.psp.smtp;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import java.io.BufferedReader;

/**
 * <h1>2. Mail with attachments</h1>
 *
 * @since 2022-12-13
 * @version 1.0
 * @author Q
 */
public class MyEmailAttachment extends MailConstants {

    // <<-CONSTANTS->>
    private static final String SUBJECT = "Mail with attachments";
    private static final String MSG = "This is a test of an email with attachments";

    // <<-METHOD->>
    public static void main(String[] args) {
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(java.nio.file.Path.of(System.getProperty("user.dir"), "resources", "logo.png").toString());
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("This logo is attached to the multipart email");
        attachment.setName("Github Logo");

        try (BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in))) {
            // MultiPartEmail allows to attach images, Email no
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName(HOSTNAME);
            email.setSmtpPort(TLS_PORT);
            System.out.println("Enter your username:");
            String user = br.readLine();
            System.out.println("Enter your password:");
            String passwd = br.readLine();
            email.setAuthenticator(new org.apache.commons.mail.DefaultAuthenticator(user, passwd));
            email.attach(attachment)
                    .setSSLOnConnect(true)
                    .setFrom(SRC, SRC_NAME)
                    .addTo(DSTNY, DSTNY_NAME)
                    .setSubject(SUBJECT)
                    .setMsg(MSG)
                    .send();
        } catch (java.io.IOException e) {
            System.err.println("ERROR: I/O");
        } catch (org.apache.commons.mail.EmailException e) {
            System.err.println("ERROR: Email related");
        }
    }

}
