package com.quathar.psp.smtp;

/**
 * <h1>Mail Constants</h1>
 *
 * @since 2023-02-18
 * @version 1.0
 * @author Q
 */
public abstract class MailConstants {

    protected static final int    TLS_PORT   = 465;
    protected static final String HOSTNAME   = "smtp.educa.madrid.org";
    protected static final String SRC        = "<mail-name>@educa.madrid.org";
    protected static final String SRC_NAME   = "<Source name>";
    protected static final String DSTNY      = "<mail-name>@educa.madrid.org";
    protected static final String DSTNY_NAME = "<Destiny name>";
    protected static final String TEXT_MSG   = "Your email client DOES NOT support HTML messages";

}
