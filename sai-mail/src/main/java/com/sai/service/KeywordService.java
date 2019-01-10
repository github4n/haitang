package com.sai.service;

import java.io.InputStream;

/**
 * @author: zyq
 * @date: 2019/1/2
 */
public interface KeywordService {

    /**
     * 拉取邮件附件，并上传关键字
     */
    void pullMailToUpload();
}
