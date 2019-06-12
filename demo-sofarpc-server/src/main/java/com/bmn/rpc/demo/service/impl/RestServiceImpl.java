package com.bmn.rpc.demo.service.impl;

import com.bmn.bootstrap.context.BmnApplicationContext;
import com.bmn.rpc.demo.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/12
 */
public class RestServiceImpl implements RestService {
    private final static Logger logger = LoggerFactory.getLogger(RestServiceImpl.class);

    private final BmnApplicationContext context;

    public RestServiceImpl(BmnApplicationContext context) {
        this.context = context;
    }


    /**
     * 刷新服务器
     * @return
     */
    @Override
    public String refresh() {
        logger.info("application start refresh...");
        this.context.refresh();
        logger.info("application refresh finished");
        return "refresh success";
    }

    /**
     * 关服
     * @return
     */
    @Override
    public String close() {
        logger.info("application start close...");
        this.context.close();
        logger.info("application closed");
        return "close success";
    }

}
