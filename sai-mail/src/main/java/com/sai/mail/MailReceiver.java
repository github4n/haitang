package com.sai.mail;

import java.util.List;
import java.util.function.Function;
import javax.mail.Message;

/**
 * @author: zyq
 * @date: 2018/12/27
 */
public interface MailReceiver {

    /**
     * 拉取邮件, 并解析附件
     * @param config
     * @param mailFilter
     * @return
     */
    List<MailMessage> pullWithAttachment(MailConfig config, Function<MailMessage, Boolean> mailFilter);
}
