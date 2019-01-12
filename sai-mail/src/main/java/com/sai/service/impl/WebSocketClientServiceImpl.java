package com.sai.service.impl;

import com.sai.service.WebSocketClientService;
import com.sai.util.RandomStringUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static java.lang.Math.min;

/**
 * @author: zyq
 * @date: 2019/1/12
 */
public class WebSocketClientServiceImpl implements WebSocketClientService {

    public static final String defaultUri = "ws://127.0.0.1:9090/nlu/wss/sa";

    private static final int ONE_HUNDRED_MILLISECOND = 2048;

    private WebSocketClient client;

    public  void sendData(final String... filePath)
        throws Exception {
        // Path path = Paths.get("/Users/ceshannon/test1.pcm");
        for (int k = 0; k < filePath.length; k++) {
            Path path = Paths.get(filePath[k]);

            byte[] data = new byte[0];
            try {
                data = Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map<String, Object> add = new HashMap<>();
            add.put("gift", "wwwwddddd=");
            add.put("realback", "false");
            String deviceId = RandomStringUtils.randomAlphabetic(12);
            Object request = null;

            String header = "";//JSON.toJSONString(request);
            Future<Void> fut = null;
            byte[] dest = null;
            System.out.println("send header: " + header);
            client.send(header);

            //DialogContext context = new AsrDialogContext(1000, 0, 0);

            dest = new byte[ONE_HUNDRED_MILLISECOND];
            for (int i = 0; i * ONE_HUNDRED_MILLISECOND < data.length; i++) {
                Arrays.fill(dest, (byte) 0);
                System.arraycopy(data, i * ONE_HUNDRED_MILLISECOND, dest, 0,
                    min(ONE_HUNDRED_MILLISECOND, data.length - i * ONE_HUNDRED_MILLISECOND));

                //context.mergeDataAndUpdateState(dest, null);


                System.out.println(LocalDateTime.now().toString()+"send data:" + i + " len :" + dest.length);
                Thread.sleep(64);
                client.send(ByteBuffer.wrap(dest));
            }

            client.send(ByteBuffer.wrap("".getBytes()));
            Thread.sleep(1000);

        }

        client.close();
    }

    private WebSocketClient createClient(String[] args) throws URISyntaxException {
        final String[] files = Arrays.copyOfRange(args, 0, args.length);
        URI echoUri = new URI(defaultUri);
        final WebSocketClient client = new WebSocketClient(echoUri, new Draft_6455(), null, 2000) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendData(files);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                new Thread(r).start();
            }

            @Override
            public void onMessage(String s) {
                System.out.println("on message:" + s);
                try {
//                    JSONObject obj = JSON.parseObject(s);
//                    int eof = obj.getInteger("eof");
//                    if (eof == 1) {
//                        String ret = obj.getString("result");
//                        System.out.println(ret);
//                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                System.out.println(String.format("websocket closed! code: {%s}, reason: {%s}, remote: {%s}", i, s, b));
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        };

        System.out.println(args.length + "  : " + args[0]);

        try {
            this.client = client;
            client.connect();
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return client;
    }

    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    static class miTM implements TrustManager,X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] certs, String authType)
            throws CertificateException {
            return;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] certs, String authType)
            throws CertificateException {
            return;
        }
    }

}
