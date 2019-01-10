package com.sai.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
public abstract class AbstractMailReceiver implements MailReceiver {

    private static final Logger logger = LogManager.getLogger(AbstractMailReceiver.class);

    private static final String PROTOCOL_POP3 = "pop3";
    private static final String PROTOCOL_IMAP = "imap";

    @Override
    public List<MailMessage> pullWithAttachment(MailConfig config,
        Function<MailMessage, Boolean> mailFilter) {
        Message message = null;
        try {
            Message[] messages = fetchInbox(getMailProperties(config), getAuthenticator(config));
            message = messages[0];
            return pull(messages, true, mailFilter);
        } catch (Exception e) {
            logger.error("pull mail: {} error", config, e);
        } finally {
            closeInbox(message);
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 解析邮件内容，并过滤
     *
     * @param messages
     * @param attachment 是否解析邮件附件。解析大的附件会长时间阻塞
     * @param mailFilter
     * @return
     */
    public abstract List<MailMessage> pull(Message[] messages, boolean attachment,
        Function<MailMessage, Boolean> mailFilter);

    /**
     * 收取收件箱里的邮件
     *
     * @param props 为邮件连接所需的必要属性
     * @param authenticator 用户验证器
     * @return Message数组（邮件数组）
     */
    public Message[] fetchInbox(Properties props, Authenticator authenticator) {
        return fetchInbox(props, authenticator, null);
    }

    /**
     * 收取收件箱里的邮件
     *
     * @param props 收取收件箱里的邮件
     * @param authenticator 用户验证器
     * @param protocol 使用的收取邮件协议，有两个值"pop3"或者"imap"
     * @return Message数组（邮件数组）
     */
    public Message[] fetchInbox(Properties props, Authenticator authenticator,
        String protocol) {
        logger.info("start fetch inbox mail...");

        Message[] messages = null;
        Session session = Session.getDefaultInstance(props, authenticator);
        // session.setDebug(true);
        Store store = null;
        Folder folder = null;
        try {
            store = protocol == null || protocol.trim().length() == 0 ? session.getStore()
                : session.getStore(protocol);
            store.connect();
            // 获取收件箱
            folder = store.getFolder("INBOX");
            // 以只读方式打开
            folder.open(Folder.READ_ONLY);
            messages = folder.getMessages();

            logger.info("fetch inbox mail size: {}. success", messages.length);
        } catch (NoSuchProviderException e) {
            logger.error("fetch inbox mail error", e);
        } catch (MessagingException e) {
            logger.error("fetch inbox mail message error", e);
        }
        return messages;
    }

    protected void closeInbox(Message message) {
        if (message != null) {
            Folder folder = message.getFolder();
            if (folder != null) {
                try {
                    Store store = folder.getStore();

                    // 参数false表示对邮件的修改不传送到服务器上
                    folder.close(false);

                    logger.info("mail folder closed.");
                    if (store != null) {
                        store.close();

                        logger.info("mail store connection closed.");
                    }
                } catch (MessagingException e) {
                    logger.error("close mail store error.", e);
                }
            }
        }
    }

    protected Properties getMailProperties(MailConfig config) {
        Properties defaults = new Properties();
        String protocol = config.getProtocol();
        if (PROTOCOL_POP3.equals(protocol)) {
            defaults.setProperty("mail.pop3.host", config.getHost());
            //defaults.setProperty("mail.pop3.port", String.valueOf(config.getPort()));
        } else if (PROTOCOL_IMAP.equals(protocol)) {
            defaults.setProperty("mail.imap.host", config.getHost());
            //defaults.setProperty("mail.imap.port", String.valueOf(config.getPort()));
        } else {
            throw new IllegalStateException(String.format("receiver can not support protocal: %s, for mail config: %s", protocol, config));
        }

        defaults.setProperty("mail.store.protocol", protocol);

        return defaults;
    }

    protected Authenticator getAuthenticator(MailConfig config) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPasswd());
            }
        };
    }
}
