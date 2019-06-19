package com.bmn.haitang.demo.pig.vo;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/19
 */
public class LoginReqVO {

    private String loginType;
    private String loginname;
    private String loginpwd;
    private String authcode;

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getLoginpwd() {
        return loginpwd;
    }

    public void setLoginpwd(String loginpwd) {
        this.loginpwd = loginpwd;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }
}
