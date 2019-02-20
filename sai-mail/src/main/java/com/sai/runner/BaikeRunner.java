package com.sai.runner;

import com.sai.core.ApplicationRunner;
import com.sai.util.HttpUtils;
import com.sai.util.MACUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: zyq
 * @date: 2019/2/20
 */
public class BaikeRunner implements ApplicationRunner {
    private static final Logger logger = LogManager.getLogger(BaikeRunner.class);

    private static final String BAIDU_URL = "https://baikeapi.baidu.com/opendata/";
    private static final String API_VIDEO = "v1/secondknow/searchvideo";
    private static final String API_HELLO = "v1/sample/hello?";

    private static final String AK = "abcdef0123456789abcdef0123456789";
    private static final String SK = "9876543210fedcba9876543210fedcba";

    private static final String SIGN_KEY = "baikeapi-auth/%s/%d/%d";
    private static final String AUTH_SIGN = "%s/%s";

    private static final String UTF8 = "UTF-8";

    // 秒
    private static final int expiration = 600;


    @Override
    public void run(String[] args) throws Exception {
        doHello();
    }

    public void doHello() throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("count", "3");

        String urlParam = generateUrlParam(param);

        String sign = sign(urlParam);

        String url = new StringBuilder(BAIDU_URL).append(API_HELLO).append(urlParam).toString();

        logger.debug("send hello url: {}", url);

        String result = HttpUtils.doPostSSL(url, req->{
           req.setHeader("Authorization", sign);
        });

        logger.info("get result: {}", result);

    }

    public static void main(String[] args) throws Exception {
        BaikeRunner runner = new BaikeRunner();
        runner.run(args);
    }

    private String sign(String param)
        throws Exception {
        int timestamp = (int) (Instant.now().toEpochMilli() / 1000L);
        String authprefix = String.format(SIGN_KEY, AK, timestamp, expiration);
        String signKey = MACUtils.encodeHmacSHA256(authprefix, SK);
        logger.debug("data: {}, generate signKey: {}", authprefix, signKey);
        String signature = MACUtils.encodeHmacSHA256(param, signKey);
        logger.debug("data: {}, generate signature: {}", param, signature);
        return String.format(AUTH_SIGN, authprefix, signature);
    }


    public static String generateUrlParam(Map<String, String> param)
        throws UnsupportedEncodingException {
        Set<String> keySet = param.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            String v = URLEncoder.encode(param.get(k).trim(), "utf-8");
            sb.append(k).append("=").append(v).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}
