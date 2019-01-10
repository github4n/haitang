package com.sai.mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author: zyq
 * @date: 2019/1/2
 */
public class AbstractMailSender implements MailSender {

    private static final String PROTOCAL_SMTP = "smtp";

    private Properties getMailProperties(MailConfig config) {
        Properties defaults = new Properties();
        String protocol = config.getProtocol();
        if (!PROTOCAL_SMTP.equals(protocol)) {
            throw new IllegalStateException(String.format("sender can not support protocal: %s, for mail config: %s", protocol, config));
        }

        defaults.setProperty("mail.smtp.host", config.getHost());
        defaults.setProperty("mail.smtp.port", String.valueOf(config.getPort()));
        defaults.setProperty("mail.transport.protocol", protocol);

        // 需要请求认证
        defaults.setProperty("mail.smtp.auth", "true");

        if (config.getPort() == 465) {
            defaults.setProperty("mail.smtp.port", "465");
            defaults.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            defaults.setProperty("mail.smtp.socketFactory.fallback", "false");
            defaults.setProperty("mail.smtp.socketFactory.port", "465");
        }

        return defaults;
    }

    public Authenticator getAuthenticator(MailConfig config) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPasswd());
            }
        };
    }
}
