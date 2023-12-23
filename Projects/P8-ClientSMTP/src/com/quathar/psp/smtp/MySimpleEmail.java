package com.quathar.psp.smtp;

import java.io.BufferedReader;

/**
 * <h1>1. Simple Email</h1>
 *
 * @since 2022-12-13
 * @version 1.0
 * @author Q
 */
public class MySimpleEmail extends MailConstants {

    // <<-CONSTANTS->>
    private static final String SUBJECT = "Simple email";
    private static final String MSG = "This is a simple email test";

    // <<-METHOD->>
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in))) {
            org.apache.commons.mail.Email email = new org.apache.commons.mail.SimpleEmail();
            email.setHostName(HOSTNAME);
            email.setSmtpPort(TLS_PORT);
            System.out.println("Enter your username:");
            String user = br.readLine();
            System.out.println("Enter your password:");
            String passwd = br.readLine();
            email.setAuthenticator(new org.apache.commons.mail.DefaultAuthenticator(user, passwd));
            email.setSSLOnConnect(true)
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
