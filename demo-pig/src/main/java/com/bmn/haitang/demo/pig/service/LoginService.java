package com.bmn.haitang.demo.pig.service;

import com.bmn.haitang.demo.pig.vo.ResponseDTO;

/**
 * Created by Administrator on 2017/7/27.
 */
public interface LoginService {

    ResponseDTO login(String loginname, String password);

}
