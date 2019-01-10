package com.sai.service;

import com.sai.service.impl.KeywordServiceImpl;
import com.sai.service.impl.MailServiceImpl;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
public abstract class ServiceContext {


    private static MailService mailService;

    private static KeywordService keywordService;

    public static void init() {
        mailService = new MailServiceImpl();
        keywordService = new KeywordServiceImpl();
    }

    public static MailService getMailService() {
        return mailService;
    }

    public static KeywordService getKeywordService() {
        return keywordService;
    }
}
