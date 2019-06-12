## 说明


### 启动参数
 ``` 
    -Dlog4j.configurationFile=config/log4j2-test.xml
    -Dlogging.path=logs
    -Dbolt.netty.buffer.pooled=true
    # 本地测试时使用
    -Dlogger.impl=com.alipay.sofa.rpc.log.SLF4JLoggerImpl
 ```