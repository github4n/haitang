package com.sai.mail;

import java.io.InputStream;
import java.util.List;

/**
 * @author: zyq
 * @date: 2019/1/2
 */
public class MailMessage {
    // 邮件主题
    private String subject;
    // 邮件发件人
    private String from;
    // 发件时间戳（s）
    private int sendTime;
    // 附件
    private List<MailMessageAttach> attachList;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getSendTime() {
        return sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public List<MailMessageAttach> getAttachList() {
        return attachList;
    }

    public void setAttachList(List<MailMessageAttach> attachList) {
        this.attachList = attachList;
    }

    public static class MailMessageAttach {
        private String name;
        private String contentType;
        private InputStream inputStream;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }
    }

}
