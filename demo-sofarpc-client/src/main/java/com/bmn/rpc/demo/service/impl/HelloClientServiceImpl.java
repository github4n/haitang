package com.bmn.rpc.demo.service.impl;

import com.alipay.sofa.rpc.api.future.SofaResponseFuture;
import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.invoke.SofaResponseCallback;
import com.alipay.sofa.rpc.core.request.RequestBase;
import com.alipay.sofa.rpc.message.bolt.BoltResponseFuture;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloRequestMsg;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloResponseMsg;
import com.bmn.rpc.demo.callback.SofaTracerResponseCallback;
import com.bmn.rpc.demo.component.DemoRpcClient;
import com.bmn.rpc.demo.service.HelloClientService;
import com.bmn.rpc.demo.util.TracerUtils;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/5/27
 */
@Service
public class HelloClientServiceImpl implements HelloClientService {

    private static final Logger logger = LoggerFactory.getLogger(HelloClientServiceImpl.class);

    private static final String TYPE = "run";
    private static final String DATA = "{\"seed\": 1543,\"at\": [{\"units\": [{\"tid\": 326,\"pos\": 1,\"lv\": 25,\"type\": 3,\"fgType\": 1,\"sex\": 2,\"star\": 1,\"quality\": 3,\"attributes\": [{\"key\": 5,\"value\": 721},{\"key\": 18,\"value\": 416},{\"key\": 69,\"value\": 800},{\"key\": 68,\"value\": 442},{\"key\": 101,\"value\": 86},{\"key\": 203,\"value\": 1163},{\"key\": 6,\"value\": 3365},{\"key\": 4,\"value\": 721},{\"key\": 41,\"value\": 8},{\"key\": 201,\"value\": 37190},{\"key\": 30,\"value\": 120},{\"key\": 103,\"value\": 80},{\"key\": 16,\"value\": 2000},{\"key\": 102,\"value\": 72},{\"key\": 204,\"value\": 1163},{\"key\": 1,\"value\": 37190},{\"key\": 22,\"value\": 300},{\"key\": 42,\"value\": 8},{\"key\": 202,\"value\": 3365},{\"key\": 104,\"value\": 68},{\"key\": 21,\"value\": 1092},{\"key\": 19,\"value\": 400}],\"ss\": [{\"sTid\": 3261,\"giftTid\": 0},{\"sTid\": 3262,\"giftTid\": 0}],\"e\": 0},{\"tid\": 204,\"pos\": 2,\"lv\": 28,\"type\": 2,\"fgType\": 3,\"sex\": 2,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 5,\"value\": 933},{\"key\": 18,\"value\": 560},{\"key\": 69,\"value\": 500},{\"key\": 68,\"value\": 692},{\"key\": 101,\"value\": 83},{\"key\": 11,\"value\": 2800},{\"key\": 203,\"value\": 1625},{\"key\": 6,\"value\": 3379},{\"key\": 4,\"value\": 933},{\"key\": 201,\"value\": 42679},{\"key\": 30,\"value\": 70},{\"key\": 103,\"value\": 81},{\"key\": 102,\"value\": 79},{\"key\": 204,\"value\": 1625},{\"key\": 1,\"value\": 42679},{\"key\": 22,\"value\": 200},{\"key\": 202,\"value\": 3379},{\"key\": 104,\"value\": 78},{\"key\": 21,\"value\": 400},{\"key\": 19,\"value\": 1160}],\"ss\": [{\"sTid\": 2041,\"giftTid\": 0},{\"sTid\": 2042,\"giftTid\": 0},{\"sTid\": 820131,\"giftTid\": 820134}],\"persionalityva\": 0},{\"tid\": 211,\"pos\": 3,\"lv\": 25,\"type\": 3,\"fgType\": 3,\"sex\": 1,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 5,\"value\": 443},{\"key\": 18,\"value\": 480},{\"key\": 69,\"value\": 500},{\"key\": 68,\"value\": 180},{\"key\": 101,\"value\": 89},{\"key\": 11,\"value\": 2800},{\"key\": 203,\"value\": 623},{\"key\": 6,\"value\": 2180},{\"key\": 4,\"value\": 443},{\"key\": 201,\"value\": 26110},{\"key\": 30,\"value\": 70},{\"key\": 103,\"value\": 81},{\"key\": 102,\"value\": 73},{\"key\": 204,\"value\": 623},{\"key\": 1,\"value\": 26110},{\"key\": 10,\"value\": 200},{\"key\": 202,\"value\": 2180},{\"key\": 104,\"value\": 87},{\"key\": 21,\"value\": 540},{\"key\": 19,\"value\": 300}],\"ss\": [{\"sTid\": 2112,\"giftTid\": 0},{\"d\": 2111,\"t\": 0}],\"d\": 0},{\"tid\": 202,\"pos\": 4,\"lv\": 25,\"type\": 3,\"fgType\": 3,\"sex\": 2,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 5,\"value\": 605},{\"key\": 18,\"value\": 396},{\"key\": 69,\"value\": 500},{\"key\": 68,\"value\": 196},{\"key\": 101,\"value\": 80},{\"key\": 11,\"value\": 4800},{\"key\": 203,\"value\": 801},{\"key\": 6,\"value\": 2410},{\"key\": 4,\"value\": 605},{\"key\": 41,\"value\": 4},{\"key\": 201,\"value\": 24551},{\"key\": 30,\"value\": 70},{\"key\": 103,\"value\": 87},{\"key\": 16,\"value\": 2000},{\"key\": 102,\"value\": 83},{\"key\": 204,\"value\": 801},{\"key\": 1,\"value\": 24551},{\"key\": 42,\"value\": 4},{\"key\": 202,\"value\": 2410},{\"key\": 104,\"value\": 79},{\"key\": 21,\"value\": 80},{\"key\": 19,\"value\": 300}],\"ss\": [{\"sTid\": 2021,\"giftTid\": 0},{\"sTid\": 2022,\"giftTid\": 0},{\"sTid\": 820011,\"giftTid\": 820012},{\"sTid\": 820161,\"giftTid\": 820162}],\"persionalityva\": 0},{\"tid\": 310,\"pos\": 5,\"lv\": 26,\"type\": 1,\"fgType\": 1,\"sex\": 2,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 9,\"value\": 200},{\"key\": 5,\"value\": 475},{\"key\": 18,\"value\": 96},{\"key\": 69,\"value\": 800},{\"key\": 68,\"value\": 243},{\"key\": 101,\"value\": 65},{\"key\": 203,\"value\": 556},{\"key\": 6,\"value\": 2212},{\"key\": 4,\"value\": 313},{\"key\": 201,\"value\": 20415},{\"key\": 30,\"value\": 50},{\"key\": 103,\"value\": 81},{\"key\": 16,\"value\": 2000},{\"key\": 102,\"value\": 67},{\"key\": 204,\"value\": 718},{\"key\": 1,\"value\": 20415},{\"key\": 10,\"value\": 800},{\"key\": 22,\"value\": 300},{\"key\": 202,\"value\": 2212},{\"key\": 104,\"value\": 74},{\"key\": 21,\"value\": 380}],\"ss\": [{\"sTid\": 3101,\"giftTid\": 0},{\"sTid\": 3102,\"giftTid\": 0}],\"persionalityva\": 0},{\"tid\": 303,\"pos\": 6,\"lv\": 25,\"type\": 4,\"fgType\": 2,\"sex\": 1,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 5,\"value\": 450},{\"key\": 18,\"value\": 512},{\"key\": 68,\"value\": 311},{\"key\": 101,\"value\": 73},{\"key\": 11,\"value\": 400},{\"key\": 203,\"value\": 761},{\"key\": 6,\"value\": 2312},{\"key\": 4,\"value\": 450},{\"key\": 41,\"value\": 4},{\"key\": 201,\"value\": 22453},{\"key\": 30,\"value\": 70},{\"key\": 103,\"value\": 74},{\"key\": 102,\"value\": 81},{\"key\": 204,\"value\": 761},{\"key\": 1,\"value\": 22453},{\"key\": 202,\"value\": 2312},{\"key\": 104,\"value\": 76},{\"key\": 21,\"value\": 228},{\"key\": 19,\"value\": 300}],\"ss\": [{\"sTid\": 3031,\"giftTid\": 0},{\"sTid\": 3032,\"giftTid\": 0},{\"sTid\": 833031,\"giftTid\": 833033}],\"persionalityva\": 0}],\"fg\": 51114,\"senteValue\": 215,\"assistSkills\": [{\"sTid\": 0,\"sLv\": 0},{\"sTid\": 0,\"sLv\": 0}],\"partnerTidList\": [311,201],\"teamva\": 31}],\"defenders\": [{\"units\": [{\"tid\": 201,\"pos\": 1,\"lv\": 26,\"type\": 2,\"fgType\": 3,\"sex\": 1,\"star\": 1,\"quality\": 3,\"attributes\": [{\"key\": 1,\"value\": 25180},{\"key\": 6,\"value\": 3621},{\"key\": 10,\"value\": 200},{\"key\": 18,\"value\": 1072},{\"key\": 202,\"value\": 3621},{\"key\": 16,\"value\": 2400},{\"key\": 21,\"value\": 1528},{\"key\": 30,\"value\": 120},{\"key\": 104,\"value\": 83},{\"key\": 68,\"value\": 281},{\"key\": 103,\"value\": 86},{\"key\": 203,\"value\": 970},{\"key\": 5,\"value\": 689},{\"key\": 4,\"value\": 689},{\"key\": 101,\"value\": 81},{\"key\": 102,\"value\": 75},{\"key\": 201,\"value\": 25180},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 970}],\"ss\": [{\"sTid\": 2011,\"giftTid\": 0},{\"sTid\": 2012,\"giftTid\": 0}],\"persionalityva\": 0},{\"tid\": 306,\"pos\": 2,\"lv\": 26,\"type\": 3,\"fgType\": 3,\"sex\": 1,\"star\": 1,\"quality\": 3,\"attributes\": [{\"key\": 1,\"value\": 27796},{\"key\": 6,\"value\": 2403},{\"key\": 18,\"value\": 440},{\"key\": 202,\"value\": 2403},{\"key\": 16,\"value\": 400},{\"key\": 19,\"value\": 600},{\"key\": 22,\"value\": 200},{\"key\": 21,\"value\": 80},{\"key\": 30,\"value\": 120},{\"key\": 104,\"value\": 74},{\"key\": 68,\"value\": 170},{\"key\": 103,\"value\": 68},{\"key\": 203,\"value\": 717},{\"key\": 11,\"value\": 2000},{\"key\": 5,\"value\": 689},{\"key\": 4,\"value\": 547},{\"key\": 101,\"value\": 90},{\"key\": 102,\"value\": 71},{\"key\": 201,\"value\": 27796},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 859}],\"ss\": [{\"sTid\": 3061,\"giftTid\": 0},{\"sTid\": 3062,\"giftTid\": 0},{\"sTid\": 820011,\"giftTid\": 820012}],\"persionalityva\": 0},{\"tid\": 210,\"pos\": 3,\"lv\": 26,\"type\": 1,\"fgType\": 2,\"sex\": 1,\"star\": 1,\"quality\": 3,\"attributes\": [{\"key\": 1,\"value\": 30053},{\"key\": 6,\"value\": 3698},{\"key\": 18,\"value\": 1256},{\"key\": 202,\"value\": 3698},{\"key\": 16,\"value\": 400},{\"key\": 21,\"value\": 360},{\"key\": 30,\"value\": 120},{\"key\": 104,\"value\": 88},{\"key\": 68,\"value\": 291},{\"key\": 103,\"value\": 84},{\"key\": 203,\"value\": 980},{\"key\": 11,\"value\": 3400},{\"key\": 5,\"value\": 689},{\"key\": 4,\"value\": 689},{\"key\": 101,\"value\": 69},{\"key\": 102,\"value\": 72},{\"key\": 201,\"value\": 30053},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 980}],\"ss\": [{\"sTid\": 2101,\"giftTid\": 0},{\"sTid\": 2102,\"giftTid\": 0},{\"sTid\": 820161,\"giftTid\": 820162}],\"persionalityva\": 0},{\"tid\": 207,\"pos\": 4,\"lv\": 25,\"type\": 4,\"fgType\": 2,\"sex\": 2,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 1,\"value\": 19573},{\"key\": 6,\"value\": 2180},{\"key\": 18,\"value\": 440},{\"key\": 202,\"value\": 2180},{\"key\": 16,\"value\": 400},{\"key\": 19,\"value\": 300},{\"key\": 21,\"value\": 148},{\"key\": 30,\"value\": 70},{\"key\": 104,\"value\": 78},{\"key\": 9,\"value\": 200},{\"key\": 68,\"value\": 281},{\"key\": 103,\"value\": 81},{\"key\": 203,\"value\": 701},{\"key\": 11,\"value\": 400},{\"key\": 5,\"value\": 420},{\"key\": 4,\"value\": 420},{\"key\": 101,\"value\": 88},{\"key\": 102,\"value\": 70},{\"key\": 201,\"value\": 19573},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 701}],\"ss\": [{\"sTid\": 2071,\"giftTid\": 0},{\"sTid\": 2072,\"giftTid\": 0}],\"persionalityva\": 0},{\"tid\": 310,\"pos\": 5,\"lv\": 26,\"type\": 1,\"fgType\": 1,\"sex\": 2,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 1,\"value\": 21255},{\"key\": 6,\"value\": 2114},{\"key\": 18,\"value\": 140},{\"key\": 202,\"value\": 2114},{\"key\": 19,\"value\": 200},{\"key\": 22,\"value\": 300},{\"key\": 21,\"value\": 480},{\"key\": 30,\"value\": 70},{\"key\": 104,\"value\": 74},{\"key\": 68,\"value\": 392},{\"key\": 103,\"value\": 81},{\"key\": 203,\"value\": 835},{\"key\": 5,\"value\": 443},{\"key\": 4,\"value\": 443},{\"key\": 101,\"value\": 65},{\"key\": 102,\"value\": 67},{\"key\": 201,\"value\": 21255},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 835}],\"ss\": [{\"sTid\": 3101,\"giftTid\": 0},{\"sTid\": 3102,\"giftTid\": 0}],\"persionalityva\": 0},{\"tid\": 301,\"pos\": 6,\"lv\": 26,\"type\": 4,\"fgType\": 2,\"sex\": 1,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 1,\"value\": 25667},{\"key\": 6,\"value\": 2903},{\"key\": 18,\"value\": 408},{\"key\": 202,\"value\": 2903},{\"key\": 16,\"value\": 2400},{\"key\": 19,\"value\": 800},{\"key\": 21,\"value\": 280},{\"key\": 30,\"value\": 70},{\"key\": 104,\"value\": 68},{\"key\": 68,\"value\": 281},{\"key\": 103,\"value\": 76},{\"key\": 203,\"value\": 866},{\"key\": 11,\"value\": 400},{\"key\": 5,\"value\": 585},{\"key\": 4,\"value\": 585},{\"key\": 101,\"value\": 66},{\"key\": 102,\"value\": 63},{\"key\": 201,\"value\": 25667},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 866}],\"ss\": [{\"sTid\": 3011,\"giftTid\": 0},{\"sTid\": 3012,\"giftTid\": 0}],\"persionalityva\": 0}],\"fg\": 47152,\"senteValue\": 180,\"assistSkills\": [{\"sTid\": 0,\"sLv\": 0},{\"sTid\": 0,\"sLv\": 0}],\"partnerTidList\": [308],\"teamva\": 27}],\"bdMode\": 1}";

    @Autowired
    private DemoRpcClient demoRpcClient;


    @Override
    public String callHello() throws ExecutionException, InterruptedException {

        long begin = Instant.now().toEpochMilli();
        try {
            HelloRequestMsg.Builder builder = HelloRequestMsg.newBuilder();
            builder.setType(TYPE);
            builder.setData(DATA);

            logger.info("rpc call start...");

            HelloResponseMsg msg = demoRpcClient.getService().hello(builder.build());

            String result = msg.getResult();

            long end = Instant.now().toEpochMilli();

            logger
                .info("rpc call finish, client_elapse_time: {}, result:{}", (end - begin), result);

        } catch (Exception e) {
            logger.error("hello error", e);

            return "false";
        }

        return "true";
    }

    @Override
    public String callHelloFuture() throws ExecutionException, InterruptedException {
        long begin = Instant.now().toEpochMilli();

        try {

            HelloRequestMsg.Builder builder = HelloRequestMsg.newBuilder();
            builder.setType(TYPE);
            builder.setData(DATA);

            demoRpcClient.getService().helloFuture(builder.build());
        } catch (Exception e) {
            TracerUtils.asyncCallErrorClearTracer(e);
            logger.error("hello callback future", e);

            return "false";
        }

        logger.info("rpc call start...");

        BoltResponseFuture future = (BoltResponseFuture) SofaResponseFuture.getFuture();
        Object ret = future.get();

        HelloResponseMsg msg = (HelloResponseMsg) ret;

        String result = msg.getResult();

        long end = Instant.now().toEpochMilli();
        logger.info("rpc call finish, client_elapse_time: {}, result:{}", (end - begin), result);

        return "true";
    }

    /**
     * 2. rpc调用失败后
     * <p>
     * 如果是调用时失败，则当前线程执行本地操作，executor线程执行callback
     * <p>
     * 如果是rpc执行过程中失败，则rpc回调线程执行本地操作，executor线程执行callback
     *
     * 3. rpc成功, executor线程执行callback
     */
    @Override
    public String callHelloCallback(DeferredResult<String> deferred) {
        // 指定线程池
//      callHelloCallback(deferred, Executors.newSingleThreadExecutor());

        // 未指定线程池
        callHelloCallback(deferred, null);

        return "true";
    }

    /**
     * 1. executor：如果为null, 表示在当前deferred触发线程中执行callback
     * <p>
     * 如果指定executor，则callback在executor线程触发
     */
    public void callHelloCallback(DeferredResult<String> deferred, Executor executor) {

        CompletableFuture<String> future = new CompletableFuture<>();

        // rpc异常后，执行本地战斗后，再执行callback。rpc正常返回后直接执行callback
        future.exceptionally(ex -> {

            logger.info("rpc call error, on exception", ex);

            return "exception result";
        }).thenAcceptAsync(r -> {

            logger.info("rpc call finish, on then accept async: {}", r);

            deferred.setResult(r);
        }, executor == null ? DirectExecutor.INSTANCE : executor);

        callHelloCallback(future);
    }


    public void callHelloCallback(CompletableFuture<String> future) {
        long begin = Instant.now().toEpochMilli();

        try {

            // 回调接口由：SOFA-RPC-CB线程执行
            RpcInvokeContext.getContext().setResponseCallback(new SofaTracerResponseCallback(new SofaResponseCallback() {

                /**
                 * 当客户端接收到服务端的正常返回的时候，SOFARPC 会回调这个方法。
                 * @param appResponse
                 * @param methodName
                 * @param request
                 */
                @Override
                public void onAppResponse(Object appResponse, String methodName,
                    RequestBase request) {

                    HelloResponseMsg msg = (HelloResponseMsg) appResponse;

                    String result = msg.getResult();

                    long end = Instant.now().toEpochMilli();
                    logger.info("rpc call finish, client_elapse_time: {}, result:{}", (end - begin),
                        result);

                    future.complete(result);
                }

                /**
                 * 当客户端接收到服务端的异常响应的时候，SOFARPC 会回调这个方法
                 * @param throwable
                 * @param methodName
                 * @param request
                 */
                @Override
                public void onAppException(Throwable throwable, String methodName,
                    RequestBase request) {

                    logger.error("server error ", throwable);

                    future.completeExceptionally(throwable);
                }

                /**
                 * 当 SOFARPC 本身出现一些错误，比如路由错误的时候，SOFARPC 会回调这个方法。
                 * @param sofaException
                 * @param methodName
                 * @param request
                 */
                @Override
                public void onSofaException(SofaRpcException sofaException, String methodName,
                    RequestBase request) {

                    logger.error("self error ", sofaException);

                    future.completeExceptionally(sofaException);
                }
            }));

            HelloRequestMsg.Builder builder = HelloRequestMsg.newBuilder();
            builder.setType(TYPE);
            builder.setData(DATA);
            demoRpcClient.getService().helloCallback(builder.build());
        } catch (Exception e) {
            TracerUtils.asyncCallErrorClearTracer(e);
            logger.error("hello callback error", e);

            future.completeExceptionally(e);
        }

        logger.info("rpc call start...");
    }


    private enum DirectExecutor implements Executor {
        INSTANCE;

        @Override
        public void execute(Runnable command) {
            command.run();
        }

        @Override
        public String toString() {
            return "MoreExecutors.directExecutor()";
        }
    }
}
