package com.bmn.rpc.demo.service.impl;

import com.alipay.sofa.rpc.api.future.SofaResponseFuture;
import com.alipay.sofa.rpc.codec.SerializerFactory;
import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.config.ApplicationConfig;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.message.bolt.BoltResponseFuture;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloRequestMsg;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloResponseMsg;
import com.bmn.rpc.demo.filter.SnappyClientFilter;
import com.bmn.rpc.demo.service.HelloClientService;
import com.bmn.rpc.demo.service.HelloWorldService;
import com.bmn.rpc.demo.util.TracerUtils;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/5/27
 */
@Service
public class HelloClientServiceImpl implements HelloClientService, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(HelloClientServiceImpl.class);


    private ConsumerConfig<HelloWorldService> consumerConfig;

    private HelloWorldService helloWorldService;

    @Value("${rpc.zookeeper.address}")
    private String zkAddress;

    @Value("${rpc.request.timeout}")
    private int requestTimeout;

    @Value("${rpc.connection.timeout}")
    private int connectionTimeout;
    @Value("${rpc.connection.size}")
    private int connectionSize;

    private static final String TYPE = "run";
    private static final String DATA = "{\"seed\": 1543,\"at\": [{\"units\": [{\"tid\": 326,\"pos\": 1,\"lv\": 25,\"type\": 3,\"fgType\": 1,\"sex\": 2,\"star\": 1,\"quality\": 3,\"attributes\": [{\"key\": 5,\"value\": 721},{\"key\": 18,\"value\": 416},{\"key\": 69,\"value\": 800},{\"key\": 68,\"value\": 442},{\"key\": 101,\"value\": 86},{\"key\": 203,\"value\": 1163},{\"key\": 6,\"value\": 3365},{\"key\": 4,\"value\": 721},{\"key\": 41,\"value\": 8},{\"key\": 201,\"value\": 37190},{\"key\": 30,\"value\": 120},{\"key\": 103,\"value\": 80},{\"key\": 16,\"value\": 2000},{\"key\": 102,\"value\": 72},{\"key\": 204,\"value\": 1163},{\"key\": 1,\"value\": 37190},{\"key\": 22,\"value\": 300},{\"key\": 42,\"value\": 8},{\"key\": 202,\"value\": 3365},{\"key\": 104,\"value\": 68},{\"key\": 21,\"value\": 1092},{\"key\": 19,\"value\": 400}],\"ss\": [{\"sTid\": 3261,\"giftTid\": 0},{\"sTid\": 3262,\"giftTid\": 0}],\"e\": 0},{\"tid\": 204,\"pos\": 2,\"lv\": 28,\"type\": 2,\"fgType\": 3,\"sex\": 2,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 5,\"value\": 933},{\"key\": 18,\"value\": 560},{\"key\": 69,\"value\": 500},{\"key\": 68,\"value\": 692},{\"key\": 101,\"value\": 83},{\"key\": 11,\"value\": 2800},{\"key\": 203,\"value\": 1625},{\"key\": 6,\"value\": 3379},{\"key\": 4,\"value\": 933},{\"key\": 201,\"value\": 42679},{\"key\": 30,\"value\": 70},{\"key\": 103,\"value\": 81},{\"key\": 102,\"value\": 79},{\"key\": 204,\"value\": 1625},{\"key\": 1,\"value\": 42679},{\"key\": 22,\"value\": 200},{\"key\": 202,\"value\": 3379},{\"key\": 104,\"value\": 78},{\"key\": 21,\"value\": 400},{\"key\": 19,\"value\": 1160}],\"ss\": [{\"sTid\": 2041,\"giftTid\": 0},{\"sTid\": 2042,\"giftTid\": 0},{\"sTid\": 820131,\"giftTid\": 820134}],\"persionalityva\": 0},{\"tid\": 211,\"pos\": 3,\"lv\": 25,\"type\": 3,\"fgType\": 3,\"sex\": 1,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 5,\"value\": 443},{\"key\": 18,\"value\": 480},{\"key\": 69,\"value\": 500},{\"key\": 68,\"value\": 180},{\"key\": 101,\"value\": 89},{\"key\": 11,\"value\": 2800},{\"key\": 203,\"value\": 623},{\"key\": 6,\"value\": 2180},{\"key\": 4,\"value\": 443},{\"key\": 201,\"value\": 26110},{\"key\": 30,\"value\": 70},{\"key\": 103,\"value\": 81},{\"key\": 102,\"value\": 73},{\"key\": 204,\"value\": 623},{\"key\": 1,\"value\": 26110},{\"key\": 10,\"value\": 200},{\"key\": 202,\"value\": 2180},{\"key\": 104,\"value\": 87},{\"key\": 21,\"value\": 540},{\"key\": 19,\"value\": 300}],\"ss\": [{\"sTid\": 2112,\"giftTid\": 0},{\"d\": 2111,\"t\": 0}],\"d\": 0},{\"tid\": 202,\"pos\": 4,\"lv\": 25,\"type\": 3,\"fgType\": 3,\"sex\": 2,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 5,\"value\": 605},{\"key\": 18,\"value\": 396},{\"key\": 69,\"value\": 500},{\"key\": 68,\"value\": 196},{\"key\": 101,\"value\": 80},{\"key\": 11,\"value\": 4800},{\"key\": 203,\"value\": 801},{\"key\": 6,\"value\": 2410},{\"key\": 4,\"value\": 605},{\"key\": 41,\"value\": 4},{\"key\": 201,\"value\": 24551},{\"key\": 30,\"value\": 70},{\"key\": 103,\"value\": 87},{\"key\": 16,\"value\": 2000},{\"key\": 102,\"value\": 83},{\"key\": 204,\"value\": 801},{\"key\": 1,\"value\": 24551},{\"key\": 42,\"value\": 4},{\"key\": 202,\"value\": 2410},{\"key\": 104,\"value\": 79},{\"key\": 21,\"value\": 80},{\"key\": 19,\"value\": 300}],\"ss\": [{\"sTid\": 2021,\"giftTid\": 0},{\"sTid\": 2022,\"giftTid\": 0},{\"sTid\": 820011,\"giftTid\": 820012},{\"sTid\": 820161,\"giftTid\": 820162}],\"persionalityva\": 0},{\"tid\": 310,\"pos\": 5,\"lv\": 26,\"type\": 1,\"fgType\": 1,\"sex\": 2,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 9,\"value\": 200},{\"key\": 5,\"value\": 475},{\"key\": 18,\"value\": 96},{\"key\": 69,\"value\": 800},{\"key\": 68,\"value\": 243},{\"key\": 101,\"value\": 65},{\"key\": 203,\"value\": 556},{\"key\": 6,\"value\": 2212},{\"key\": 4,\"value\": 313},{\"key\": 201,\"value\": 20415},{\"key\": 30,\"value\": 50},{\"key\": 103,\"value\": 81},{\"key\": 16,\"value\": 2000},{\"key\": 102,\"value\": 67},{\"key\": 204,\"value\": 718},{\"key\": 1,\"value\": 20415},{\"key\": 10,\"value\": 800},{\"key\": 22,\"value\": 300},{\"key\": 202,\"value\": 2212},{\"key\": 104,\"value\": 74},{\"key\": 21,\"value\": 380}],\"ss\": [{\"sTid\": 3101,\"giftTid\": 0},{\"sTid\": 3102,\"giftTid\": 0}],\"persionalityva\": 0},{\"tid\": 303,\"pos\": 6,\"lv\": 25,\"type\": 4,\"fgType\": 2,\"sex\": 1,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 5,\"value\": 450},{\"key\": 18,\"value\": 512},{\"key\": 68,\"value\": 311},{\"key\": 101,\"value\": 73},{\"key\": 11,\"value\": 400},{\"key\": 203,\"value\": 761},{\"key\": 6,\"value\": 2312},{\"key\": 4,\"value\": 450},{\"key\": 41,\"value\": 4},{\"key\": 201,\"value\": 22453},{\"key\": 30,\"value\": 70},{\"key\": 103,\"value\": 74},{\"key\": 102,\"value\": 81},{\"key\": 204,\"value\": 761},{\"key\": 1,\"value\": 22453},{\"key\": 202,\"value\": 2312},{\"key\": 104,\"value\": 76},{\"key\": 21,\"value\": 228},{\"key\": 19,\"value\": 300}],\"ss\": [{\"sTid\": 3031,\"giftTid\": 0},{\"sTid\": 3032,\"giftTid\": 0},{\"sTid\": 833031,\"giftTid\": 833033}],\"persionalityva\": 0}],\"fg\": 51114,\"senteValue\": 215,\"assistSkills\": [{\"sTid\": 0,\"sLv\": 0},{\"sTid\": 0,\"sLv\": 0}],\"partnerTidList\": [311,201],\"teamva\": 31}],\"defenders\": [{\"units\": [{\"tid\": 201,\"pos\": 1,\"lv\": 26,\"type\": 2,\"fgType\": 3,\"sex\": 1,\"star\": 1,\"quality\": 3,\"attributes\": [{\"key\": 1,\"value\": 25180},{\"key\": 6,\"value\": 3621},{\"key\": 10,\"value\": 200},{\"key\": 18,\"value\": 1072},{\"key\": 202,\"value\": 3621},{\"key\": 16,\"value\": 2400},{\"key\": 21,\"value\": 1528},{\"key\": 30,\"value\": 120},{\"key\": 104,\"value\": 83},{\"key\": 68,\"value\": 281},{\"key\": 103,\"value\": 86},{\"key\": 203,\"value\": 970},{\"key\": 5,\"value\": 689},{\"key\": 4,\"value\": 689},{\"key\": 101,\"value\": 81},{\"key\": 102,\"value\": 75},{\"key\": 201,\"value\": 25180},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 970}],\"ss\": [{\"sTid\": 2011,\"giftTid\": 0},{\"sTid\": 2012,\"giftTid\": 0}],\"persionalityva\": 0},{\"tid\": 306,\"pos\": 2,\"lv\": 26,\"type\": 3,\"fgType\": 3,\"sex\": 1,\"star\": 1,\"quality\": 3,\"attributes\": [{\"key\": 1,\"value\": 27796},{\"key\": 6,\"value\": 2403},{\"key\": 18,\"value\": 440},{\"key\": 202,\"value\": 2403},{\"key\": 16,\"value\": 400},{\"key\": 19,\"value\": 600},{\"key\": 22,\"value\": 200},{\"key\": 21,\"value\": 80},{\"key\": 30,\"value\": 120},{\"key\": 104,\"value\": 74},{\"key\": 68,\"value\": 170},{\"key\": 103,\"value\": 68},{\"key\": 203,\"value\": 717},{\"key\": 11,\"value\": 2000},{\"key\": 5,\"value\": 689},{\"key\": 4,\"value\": 547},{\"key\": 101,\"value\": 90},{\"key\": 102,\"value\": 71},{\"key\": 201,\"value\": 27796},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 859}],\"ss\": [{\"sTid\": 3061,\"giftTid\": 0},{\"sTid\": 3062,\"giftTid\": 0},{\"sTid\": 820011,\"giftTid\": 820012}],\"persionalityva\": 0},{\"tid\": 210,\"pos\": 3,\"lv\": 26,\"type\": 1,\"fgType\": 2,\"sex\": 1,\"star\": 1,\"quality\": 3,\"attributes\": [{\"key\": 1,\"value\": 30053},{\"key\": 6,\"value\": 3698},{\"key\": 18,\"value\": 1256},{\"key\": 202,\"value\": 3698},{\"key\": 16,\"value\": 400},{\"key\": 21,\"value\": 360},{\"key\": 30,\"value\": 120},{\"key\": 104,\"value\": 88},{\"key\": 68,\"value\": 291},{\"key\": 103,\"value\": 84},{\"key\": 203,\"value\": 980},{\"key\": 11,\"value\": 3400},{\"key\": 5,\"value\": 689},{\"key\": 4,\"value\": 689},{\"key\": 101,\"value\": 69},{\"key\": 102,\"value\": 72},{\"key\": 201,\"value\": 30053},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 980}],\"ss\": [{\"sTid\": 2101,\"giftTid\": 0},{\"sTid\": 2102,\"giftTid\": 0},{\"sTid\": 820161,\"giftTid\": 820162}],\"persionalityva\": 0},{\"tid\": 207,\"pos\": 4,\"lv\": 25,\"type\": 4,\"fgType\": 2,\"sex\": 2,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 1,\"value\": 19573},{\"key\": 6,\"value\": 2180},{\"key\": 18,\"value\": 440},{\"key\": 202,\"value\": 2180},{\"key\": 16,\"value\": 400},{\"key\": 19,\"value\": 300},{\"key\": 21,\"value\": 148},{\"key\": 30,\"value\": 70},{\"key\": 104,\"value\": 78},{\"key\": 9,\"value\": 200},{\"key\": 68,\"value\": 281},{\"key\": 103,\"value\": 81},{\"key\": 203,\"value\": 701},{\"key\": 11,\"value\": 400},{\"key\": 5,\"value\": 420},{\"key\": 4,\"value\": 420},{\"key\": 101,\"value\": 88},{\"key\": 102,\"value\": 70},{\"key\": 201,\"value\": 19573},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 701}],\"ss\": [{\"sTid\": 2071,\"giftTid\": 0},{\"sTid\": 2072,\"giftTid\": 0}],\"persionalityva\": 0},{\"tid\": 310,\"pos\": 5,\"lv\": 26,\"type\": 1,\"fgType\": 1,\"sex\": 2,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 1,\"value\": 21255},{\"key\": 6,\"value\": 2114},{\"key\": 18,\"value\": 140},{\"key\": 202,\"value\": 2114},{\"key\": 19,\"value\": 200},{\"key\": 22,\"value\": 300},{\"key\": 21,\"value\": 480},{\"key\": 30,\"value\": 70},{\"key\": 104,\"value\": 74},{\"key\": 68,\"value\": 392},{\"key\": 103,\"value\": 81},{\"key\": 203,\"value\": 835},{\"key\": 5,\"value\": 443},{\"key\": 4,\"value\": 443},{\"key\": 101,\"value\": 65},{\"key\": 102,\"value\": 67},{\"key\": 201,\"value\": 21255},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 835}],\"ss\": [{\"sTid\": 3101,\"giftTid\": 0},{\"sTid\": 3102,\"giftTid\": 0}],\"persionalityva\": 0},{\"tid\": 301,\"pos\": 6,\"lv\": 26,\"type\": 4,\"fgType\": 2,\"sex\": 1,\"star\": 1,\"quality\": 2,\"attributes\": [{\"key\": 1,\"value\": 25667},{\"key\": 6,\"value\": 2903},{\"key\": 18,\"value\": 408},{\"key\": 202,\"value\": 2903},{\"key\": 16,\"value\": 2400},{\"key\": 19,\"value\": 800},{\"key\": 21,\"value\": 280},{\"key\": 30,\"value\": 70},{\"key\": 104,\"value\": 68},{\"key\": 68,\"value\": 281},{\"key\": 103,\"value\": 76},{\"key\": 203,\"value\": 866},{\"key\": 11,\"value\": 400},{\"key\": 5,\"value\": 585},{\"key\": 4,\"value\": 585},{\"key\": 101,\"value\": 66},{\"key\": 102,\"value\": 63},{\"key\": 201,\"value\": 25667},{\"key\": 69,\"value\": 400},{\"key\": 204,\"value\": 866}],\"ss\": [{\"sTid\": 3011,\"giftTid\": 0},{\"sTid\": 3012,\"giftTid\": 0}],\"persionalityva\": 0}],\"fg\": 47152,\"senteValue\": 180,\"assistSkills\": [{\"sTid\": 0,\"sLv\": 0},{\"sTid\": 0,\"sLv\": 0}],\"partnerTidList\": [308],\"teamva\": 27}],\"bdMode\": 1}";


    @Override
    public String callHello() throws ExecutionException, InterruptedException {

        long begin = Instant.now().toEpochMilli();

        try {

            HelloRequestMsg.Builder builder = HelloRequestMsg.newBuilder();
            builder.setType(TYPE);
            builder.setData(DATA);

            helloWorldService.hello(builder.build());
        } catch (Exception e) {
            TracerUtils.asyncCallErrorClearTracer(e);
            logger.error("eeeror", e);
        }

        logger.info("rpc lua battle call start...");

        BoltResponseFuture future = (BoltResponseFuture) SofaResponseFuture.getFuture();
        Object ret = future.get();

        HelloResponseMsg msg = (HelloResponseMsg) ret;

        String result = msg.getResult();

        long end = Instant.now().toEpochMilli();

        String s = MDC.get("SOFA-TraceId");
        String s2 = MDC.get("SOFA-SpanId");

        logger.info("rpc lua battle call finish, client_elapse_time: {}, result:{}", (end - begin), result);

        return "true";
    }

    @Override
    public String callHelloFuture() throws ExecutionException, InterruptedException {
        try {
//            helloWorldService.helloFuture(TYPE, DATA);
        } catch (Exception e) {
            TracerUtils.asyncCallErrorClearTracer(e);
            logger.error("eeeror", e);
        }

        return "true";
    }

    @Override
    public String callHelloCallback() throws ExecutionException, InterruptedException {
        try {
//            helloWorldService.helloCallback(TYPE, DATA);
        } catch (Exception e) {
            TracerUtils.asyncCallErrorClearTracer(e);
            logger.error("eeeror", e);
        }

        return "true";
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        RegistryConfig registryConfig = new RegistryConfig()
            .setProtocol(RpcConstants.REGISTRY_PROTOCOL_ZK)
            .setAddress(zkAddress);

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setAppName("spurs");

        // rpc 接口调用配置
        consumerConfig = new ConsumerConfig<HelloWorldService>()
            .setInterfaceId(HelloWorldService.class.getName())
            .setRegistry(registryConfig)
            .setApplication(applicationConfig)
            .setProtocol(RpcConstants.PROTOCOL_TYPE_BOLT)
            .setInvokeType(RpcConstants.INVOKER_TYPE_FUTURE)
            .setSerialization(RpcConstants.SERIALIZE_PROTOBUF)
            .setConnectionNum(connectionSize)
            .setRouter(Arrays.asList("!directUrl", "!mesh"))
            .setConnectTimeout(connectionTimeout)
            .setTimeout(requestTimeout);

        consumerConfig.setFilterRef(Arrays.asList(new SnappyClientFilter()));

        /**
         * 预热加载序列化。避免请求请求超时
         *
         * 默认是首次数据序列化时才加载。加载会很长时间。
         */
        SerializerFactory.getSerializer(RpcConstants.SERIALIZE_PROTOBUF);

        helloWorldService = consumerConfig.refer();
    }


}