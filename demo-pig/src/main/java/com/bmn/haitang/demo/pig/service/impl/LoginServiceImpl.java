package com.bmn.haitang.demo.pig.service.impl;

import com.bmn.gm.app.LoginApp;
import com.bmn.gm.doman.LoginConnect;
import com.bmn.gm.util.StringUtil;
import com.bmn.gm.vo.LoginResult;
import com.bmn.haitang.demo.pig.service.LoginService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/27.
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Override
    public LoginResult login(long loginId, String loginname, String password, String authcode) {
        LoginConnect connect = loginMap.get(loginId);
        if(connect == null) {
            preLogin(loginId);
            connect = loginMap.get(loginId);
        }

        LoginResult result = new LoginResult();

        if (StringUtil.isEmplty(loginname) || !loginname.equals(admin)) {
            result.setUsername("账户名不存在，请重新输入");

            failLogin(connect, result);
            return result;
        }

        if(StringUtil.isEmplty(password) || !password.equals(pwd)) {
            result.setPwd("账户名与密码不匹配，请重新输入");

            failLogin(connect, result);
            return result;
        }

        String verifyAuthcode = connect.getAuthCode();
        if(!StringUtil.isEmplty(verifyAuthcode)) {
            if (StringUtil.isEmplty(authcode) || !authcode.equalsIgnoreCase(verifyAuthcode)) {
                result.setEmptyAuthcode("验证码不正确或验证码已过期");
                return result;
            }
        }

        result.setSuccess("hello");
        return result;
    }


}
