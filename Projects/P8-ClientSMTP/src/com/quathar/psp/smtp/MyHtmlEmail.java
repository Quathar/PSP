package com.quathar.psp.smtp;

import org.apache.commons.mail.HtmlEmail;

import java.io.BufferedReader;

/**
 * <h1>3. HTML Email</h1>
 *
 * @since 2022-12-13
 * @version 1.0
 * @author Q
 */
public class MyHtmlEmail extends MailConstants {

    // <<-CONSTANTS->>
    private static final String PATH = java.nio.file.Path.of(
            System.getProperty("user.dir"), "resources", "email.html"
    ).toString();
    private static final String SUBJECT = "HTML Email";

    // <<-METHOD->>
    public static void main(String[] args) {
        try (
                BufferedReader fReader = new BufferedReader(new java.io.FileReader(PATH));
                BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in))
        ) {
            StringBuilder htmlMsg = new StringBuilder();
            String input;
            while ((input = fReader.readLine()) != null) htmlMsg.append(input);

            HtmlEmail email = new HtmlEmail();
            email.setHostName(HOSTNAME);
            email.setSmtpPort(TLS_PORT);
            System.out.println("Enter your username:");
            String user = br.readLine();
            System.out.println("Enter your password:");
            String passwd = br.readLine();
            email.setAuthenticator(new org.apache.commons.mail.DefaultAuthenticator(user, passwd));
            String cid = email.embed(
                    new java.net.URL("https://www.campusmvp.es/recursos/image.axd?picture=/2017/4T/Java.png"),
                    "Java Logo"
            );
            email.setHtmlMsg(String.format(htmlMsg.toString(), cid))
                    .setTextMsg(TEXT_MSG)
                    .setSSLOnConnect(true)
                    .setFrom(SRC, SRC_NAME)
                    .addTo(DSTNY, DSTNY_NAME)
                    .setSubject(SUBJECT)
                    .send();
        } catch (java.net.MalformedURLException e) {
            System.err.println("ERROR: URL is malformed");
        } catch (java.io.IOException e) {
            System.err.println("ERROR: I/O");
        } catch (org.apache.commons.mail.EmailException e) {
            System.err.println("ERROR: Email related");
        }
    }

}
