package com.sai.service;

import com.sai.mail.MailConfig;
import com.sai.mail.MailConfigEnum;
import com.sai.mail.MailMessage;

import java.util.List;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
public interface MailService {

    /**
     * 拉取指定主题邮件带附件邮件
     * @param configName
     * @param subject
     * @param sameDay 是否只拉取同一天的邮件
     */
    List<MailMessage> pullWithAttachment(MailConfigEnum configName, String subject, boolean sameDay);

    /**
     * 拉取指定主题邮件带附件邮件
     * @param config
     * @param subject
     * @param sameDay 是否只拉取同一天的邮件
     */
    List<MailMessage> pullWithAttachment(MailConfig config, String subject, boolean sameDay);
}
