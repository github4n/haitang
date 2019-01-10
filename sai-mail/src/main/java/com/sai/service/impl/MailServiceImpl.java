package com.sai.service.impl;

import com.sai.mail.MailConfig;
import com.sai.mail.MailConfigEnum;
import com.sai.mail.MailManager;
import com.sai.mail.MailMessage;
import com.sai.mail.MailReceiver;
import com.sai.mail.SimpleMailReceiver;
import com.sai.service.MailService;
import com.sai.util.TimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
public class MailServiceImpl implements MailService {
    private static final Logger logger = LogManager.getLogger(MailServiceImpl.class);

    private final MailReceiver mailReceiver = new SimpleMailReceiver();

    @Override
    public List<MailMessage> pullWithAttachment(MailConfigEnum configName, String subject,
        boolean sameDay) {
        logger.info("start pull mail: {}", configName);
        MailConfig config = MailManager.getInstance().getConfig(configName);

        return pullWithAttachment(config, subject, sameDay);
    }

    @Override
    public List<MailMessage> pullWithAttachment(MailConfig config, String subject, boolean sameDay) {
        List<MailMessage> mails =  mailReceiver.pullWithAttachment(config, (mail)-> {
            // 标题不同
            if(!subject.equals(mail.getSubject())) {
                return false;
            }
            // 时间不是同一天的
            if(sameDay) {
                LocalDate sendDate = TimeUtils.ofTimeToLocalDate(mail.getSendTime() * 1000L);
                if(!LocalDate.now().isEqual(sendDate)) {
                    return false;
                }
            }

            return true;
        });

        logger.info("pull mail: {}, effect mails size: {}", config, mails.size());

        return mails;
    }
}
