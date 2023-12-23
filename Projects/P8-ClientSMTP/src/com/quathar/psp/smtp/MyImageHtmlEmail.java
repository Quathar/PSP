package com.quathar.psp.smtp;

import org.apache.commons.mail.ImageHtmlEmail;

import java.io.BufferedReader;
import java.net.URL;

/**
 * <h1>4. HTML Email with images</h1>
 *
 * @since 2022-12-13
 * @author Q
 * @version 1.0
 */
public class MyImageHtmlEmail extends MailConstants {

    // <<-CONSTANTS->>
    private static final String PATH = java.nio.file.Path.of(
            System.getProperty("user.dir"), "resources", "email2.html"
    ).toString();
    private static final String SUBJECT = "HTML Email with images";

    // <<-METHOD->>
    public static void main(String[] args) {
        try (
                BufferedReader fReader = new BufferedReader(new java.io.FileReader(PATH));
                BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in))
        ) {
            StringBuilder htmlMsg = new StringBuilder();
            String input;
            while ((input = fReader.readLine()) != null) htmlMsg.append(input);

            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setHostName(HOSTNAME);
            email.setSmtpPort(TLS_PORT);
            System.out.println("Enter your username:");
            String user = br.readLine();
            System.out.println("Enter your password:");
            String passwd = br.readLine();
            email.setAuthenticator(new org.apache.commons.mail.DefaultAuthenticator(user, passwd));
            URL baseUrl = new URL("https://www.campusmvp.es");
            email.setDataSourceResolver(new org.apache.commons.mail.resolver.DataSourceUrlResolver(baseUrl));
            String url = baseUrl + "/recursos/image.axd?picture=/2017/4T/Java.png";
            email.setHtmlMsg(String.format(htmlMsg.toString(), url))
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
