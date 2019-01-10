package com.sai.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
public class SimpleMailReceiver extends AbstractMailReceiver {

    private static final Logger logger = LogManager.getLogger(SimpleMailReceiver.class);

    @Override
    public List<MailMessage> pull(Message[] messages, boolean attachment,
        Function<MailMessage, Boolean> mailFilter) {
        logger.info("start parse fetch mails...");
        List<MailMessage> mailMessages = MessageParser.parse(mailFilter, attachment, messages);
        logger.info("finished parse fetch mails.");
        return mailMessages;
    }
}
