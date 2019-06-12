package com.bmn.rpc.demo.service;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/12
 */
@Resource
@Path("battle/api")
@Consumes
public interface RestService {

    /**
     * 刷新配置
     * @return
     */
    @GET
    @Path(value = "/refresh")
    String refresh();

    /**
     * 关服
     * @return
     */
    @GET
    @Path(value = "/close")
    String close();

}