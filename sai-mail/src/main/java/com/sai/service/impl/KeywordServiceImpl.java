package com.sai.service.impl;

import com.sai.config.ConfigManager;
import com.sai.mail.MailConfig;
import com.sai.mail.MailConfigEnum;
import com.sai.mail.MailManager;
import com.sai.mail.MailMessage;
import com.sai.mail.MailMessage.MailMessageAttach;
import com.sai.service.KeywordService;
import com.sai.service.ServiceContext;
import com.sai.util.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author: zyq
 * @date: 2019/1/2
 */
public class KeywordServiceImpl implements KeywordService {
    private static final Logger log = LogManager.getLogger(KeywordServiceImpl.class);

    private static final String HTML_TOTAL_SIZE = "total size is ";
    private static final String HTML_FAILED_SIZE = "failed size is";
    private static final String HTML_LI_BEGIN = "<li>";
    private static final String HTML_LI_END = "</li>";

    public static final String MAIL_SUBJECT = "央广节目";
    public static final String DEFAULT_ATTACH_NAME = "MailAttach";
    private static final int BUFFER_SIZE = 8192;

    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    private static final String URL_CONFIG = "keyword.upload.url";


    @Override
    public void pullMailToUpload() {
        log.info("start pull mail and upload");
        List<MailMessage> mails = ServiceContext.getMailService().pullWithAttachment(MailConfigEnum.HUGO, MAIL_SUBJECT, true);

        if(mails.isEmpty()) {
            return;
        }

        MailMessage mail = mails.get(0);

        upload(mail);

        log.info("finished pull mail and upload");
    }

    public void upload(MailMessage mail) {
        String[] urls = ConfigManager.getInstance().getPropertyToArray(URL_CONFIG);

        log.info("start upload mail attach urls: {}", urls);

        if(mail.getAttachList() == null || mail.getAttachList().isEmpty()) {
            log.info("finished upload mail attach. mail attach is empty.");
            return;
        }

        MailMessageAttach attach = mail.getAttachList().get(0);

        doUpload(urls, attach);

        log.info("upload mail attach urls: {}, finished", urls);

    }

    private static long copy(InputStream source, OutputStream sink)
        throws IOException
    {
        long nread = 0L;
        byte[] buf = new byte[BUFFER_SIZE];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
            nread += n;
        }
        return nread;
    }

    /**
     * 一个文件上传多个地址
     * <p/>
     * 只有多个地址都上传成功，表示此次文件上传成功
     */
    private void doUpload( String[] uploadUrls, MailMessageAttach attach) {
        try {
            String attachName = attach.getName() == null ? DEFAULT_ATTACH_NAME : attach.getName();
            for (String url : uploadUrls) {
                if (!upload(url, attachName, attach.getInputStream())) {
                    log.info("error: upload: {}", url);
                } else {
                    log.info("success: upload: {}", url);
                }
            }
        } catch (Exception e) {
            log.error("upload, error", e);
        }
    }

    /**
     * 文件上传
     */
    public static boolean upload(String fileUploadUrl, String attachName, InputStream stream) throws FileNotFoundException {
        HttpPost httpPost = new HttpPost(fileUploadUrl);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setCharset(Charset.forName("utf-8"));
        multipartEntityBuilder.addBinaryBody("file", stream, ContentType.DEFAULT_BINARY, attachName);
        multipartEntityBuilder.addTextBody("wordType", "1");
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);

        log.debug("start, upload url: {}", fileUploadUrl);

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                String body = EntityUtils.toString(response.getEntity());
                log.info("upload word seg url {}, response {}", fileUploadUrl, body);

                response.close();
                return true;
            }

            if (statusCode == 302) {
                HttpGet httpGet = new HttpGet(fileUploadUrl);
                Header[] headers = response.getHeaders("Set-Cookie");
                if (headers.length > 0) {
                    httpGet.setHeader("Cookie", headers[0].getValue());
                }
                response = httpClient.execute(httpGet);
                statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    String body = EntityUtils.toString(response.getEntity());
                    handleUploadResult(body, fileUploadUrl, "MailAttach");
                    response.close();
                    return true;
                }
            }

            response.close();

            log.error("Failed to upload, code {}, url {}, file {}", statusCode, fileUploadUrl,
                "MailAttach");

            return false;
        } catch (Exception e) {
            log.error("Caught exception to upload url {}, file {}", fileUploadUrl, "MailAttach",
                e);
        }

        return false;
    }

    /**
     * 上传结果分析
     */
    private static void handleUploadResult(String result, String url, String file) {
        if (StringUtils.isEmpty(result)) {
            log.warn("Got null body for upload url {}, file {}", url, file);
            return;
        }
        List<String> failedList = new ArrayList<>();
        int totalSize = 0;
        int failedSize = 0;
        String[] strs = result.split("\n");
        for (String str : strs) {
            if (str.contains(HTML_TOTAL_SIZE)) {
                int index = str.indexOf(HTML_TOTAL_SIZE);
                String value = str.substring(HTML_TOTAL_SIZE.length() + index);
                try {
                    totalSize = Integer.valueOf(value.trim());
                } catch (Exception e) {
                    log.warn("Failed to parse total size {}", str);
                }
            } else if (str.contains(HTML_FAILED_SIZE)) {
                String value = str.substring(HTML_FAILED_SIZE.length() + 1);
                try {
                    failedSize = Integer.valueOf(value.trim());
                } catch (Exception e) {
                    log.warn("Failed to parse failed size {}", str);
                }
            } else if (str.contains(HTML_LI_BEGIN)) {
                int index1 = str.indexOf(HTML_LI_BEGIN) + HTML_LI_BEGIN.length();
                int index2 = str.indexOf(HTML_LI_END);
                if (index2 == -1) {
                    log.warn("Failed to find li end for {}", str);
                } else {
                    String value = str.substring(index1, index2).trim();
                    failedList.add(value);
                }
            }
        }

        log.info("upload word seg url {}, file {}, totalSize {}, failedSize {}, failed {}", url,
            file, totalSize, failedSize, failedList);
    }
}
