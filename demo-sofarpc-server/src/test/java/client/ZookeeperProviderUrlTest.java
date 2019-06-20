package client;

import com.alipay.sofa.rpc.client.ProviderHelper;
import com.alipay.sofa.rpc.client.ProviderInfo;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.junit.Test;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/5/29
 */
public class ZookeeperProviderUrlTest {

    /**
     * bolt://169.254.131.141:12200?
     * version=1.0
     * &accepts=100000
     * &weight=100
     * &language=java
     * &pid=8868
     * &interface=com.playcrab.achilles.spurs.battle.service.SpursBattleService
     * &timeout=0
     * &serialization=protobuf
     * &protocol=bolt
     * &delay=-1
     * &dynamic=true
     * &startTime=1559093123831
     * &id=rpc-cfg-0
     * &uniqueId=
     * &rpcVer=50503
     * @throws UnsupportedEncodingException
     */
    @Test
    public void urlDecoder() throws UnsupportedEncodingException {
        String v = "bolt%3A%2F%2F169.254.131.141%3A12200%3Fversion%3D1.0%26accepts%3D100000%26weight%3D100%26language%3Djava%26pid%3D8868%26interface%3Dcom.playcrab.achilles.spurs.battle.service.SpursBattleService%26timeout%3D0%26serialization%3Dprotobuf%26protocol%3Dbolt%26delay%3D-1%26dynamic%3Dtrue%26startTime%3D1559093123831%26id%3Drpc-cfg-0%26uniqueId%3D%26rpcVer%3D50503";

        // 加入恢度参数
        v = "bolt%3A%2F%2F169.254.131.141%3A13200%3Fversion%3D1.0%26accepts%3D100000%26weight%3D100%26language%3Djava%26pid%3D12808%26interface%3Dcom.bmn.rpc.demo.service.HelloWorldService%26timeout%3D0%26grayscale%3D1%26serialization%3Dhessian2%26protocol%3Dbolt%26delay%3D-1%26dynamic%3Dtrue%26startTime%3D1561019410446%26id%3Drpc-cfg-0%26uniqueId%3D%26rpcVer%3D50505";

        String r  = URLDecoder.decode(v, "utf-8");
        System.out.println(r);

        ProviderInfo info = ProviderHelper.toProviderInfo(r);
    }


    /**
     * 加入恢度参数
     * bolt://169.254.131.141:13200
     * ?version=1.0
     * &accepts=100000
     * &weight=100
     * &language=java
     * &pid=12808
     * &interface=com.bmn.rpc.demo.service.HelloWorldService
     * &timeout=0
     * &grayscale=1  自定义恢度参数
     * &serialization=hessian2
     * &protocol=bolt&
     * delay=-1&
     * dynamic=true
     * &startTime=1561019410446
     * &id=rpc-cfg-0
     * &uniqueId=
     * &rpcVer=50505
     *
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void urlGrayscaleDecoder() throws UnsupportedEncodingException {

        // 加入恢度参数
        String v = "bolt%3A%2F%2F169.254.131.141%3A13200%3Fversion%3D1.0%26accepts%3D100000%26weight%3D100%26language%3Djava%26pid%3D12808%26interface%3Dcom.bmn.rpc.demo.service.HelloWorldService%26timeout%3D0%26grayscale%3D1%26serialization%3Dhessian2%26protocol%3Dbolt%26delay%3D-1%26dynamic%3Dtrue%26startTime%3D1561019410446%26id%3Drpc-cfg-0%26uniqueId%3D%26rpcVer%3D50505";

        String r  = URLDecoder.decode(v, "utf-8");
        System.out.println(r);

        ProviderInfo info = ProviderHelper.toProviderInfo(r);
    }
}
