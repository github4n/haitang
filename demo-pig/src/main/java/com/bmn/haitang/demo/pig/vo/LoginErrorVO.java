package com.bmn.haitang.demo.pig.vo;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/19
 */
public class LoginErrorVO {

    private String username;
    private String pwd;

    private String emptyAuthcode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmptyAuthcode() {
        return emptyAuthcode;
    }

    public void setEmptyAuthcode(String emptyAuthcode) {
        this.emptyAuthcode = emptyAuthcode;
    }
}
