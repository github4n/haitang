package com.sai.mail;

import com.sai.mail.MailMessage.MailMessageAttach;
import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

/**
 * @author: zyq
 * @date: 2019/1/2
 */
public class MessageParser {
    private static final Logger logger = LogManager.getLogger(MessageParser.class);

    public static List<MailMessage> parse(Function<MailMessage, Boolean> mailFilter, boolean attachement, Message... messages) {
        if (messages == null || messages.length == 0) {
            logger.info("parse empty mails. and return.");
            return Collections.emptyList();
        } else {
            List<MailMessage> result = new ArrayList<>(messages.length);
            for (Message m : messages) {
                MailMessage mail = buildMail(m);

                logger.debug("start parse mail: {}", mail.getSubject());

                if (!mailFilter.apply(mail)) {
                    continue;
                }

                logger.debug("start parse mail attachment: {}", mail.getSubject());

                if(attachement) {
                    parseAttachment(m, mail);
                }

                result.add(mail);
            }
            return result;
        }
    }


    private static MailMessage buildMail(Message message) {
        MailMessage mail = new MailMessage();
        try {
            MimeMessageParser parser = new MimeMessageParser((MimeMessage) message);
            String from = parser.getFrom(); // 获取发件人地址
            List<Address> cc = parser.getCc();// 获取抄送人地址
            List<Address> to = parser.getTo(); // 获取收件人地址
            String replyTo = parser.getReplyTo();// 获取回复邮件时的收件人
            String subject = parser.getSubject(); // 获取邮件主题
            Date date = message.getSentDate();

            mail.setFrom(from);
            mail.setSubject(subject);
            mail.setSendTime(date == null ? 0 : (int) (date.getTime() / 1000L));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mail;
    }


    private static void parseAttachment(Message message, MailMessage mail) {
        try {
            MimeMessageParser parser = new MimeMessageParser((MimeMessage) message).parse();
            //String htmlContent = parser.getHtmlContent(); // 获取Html内容
            //String plainContent = parser.getPlainContent(); // 获取纯文本邮件内容（注：有些邮件不支持html）

            List<DataSource> attachments = parser.getAttachmentList(); // 获取附件，并写入磁盘
            List<MailMessageAttach> attachList = new ArrayList<>(attachments.size());
            for (DataSource ds : attachments) {
                MailMessageAttach attach = new MailMessageAttach();
                attach.setInputStream(ds.getInputStream());
                attach.setContentType(ds.getContentType());
                attach.setName(ds.getName());

                attachList.add(attach);
            }

            mail.setAttachList(attachList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
