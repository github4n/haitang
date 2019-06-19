package com.bmn.haitang.demo.pig.service.impl;

import com.bmn.haitang.demo.pig.doman.Admin;
import com.bmn.haitang.demo.pig.manager.OnlineAdminManager;
import com.bmn.haitang.demo.pig.service.LoginService;
import com.bmn.haitang.demo.pig.vo.LoginErrorVO;
import com.bmn.haitang.demo.pig.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/27.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Value("${admin}")
    private String admins;
    @Override
    public ResponseDTO login(String loginName, String password) {
        ResponseDTO dto = new ResponseDTO();

        if (!admins.equals(loginName)) {
            LoginErrorVO vo = new LoginErrorVO();
            vo.setUsername(loginName);
            dto.setError(true);
            dto.setResp(vo);
            return dto;
        }

        Admin admin = new Admin();
        admin.setLoginName(loginName);

        OnlineAdminManager.INSTANCE.online("123", admin);
        return dto;
    }


}
