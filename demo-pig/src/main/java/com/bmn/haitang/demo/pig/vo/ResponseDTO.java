package com.bmn.haitang.demo.pig.vo;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/19
 */
public class ResponseDTO {

    private Object resp;

    private boolean isError;

    public Object getResp() {
        return resp;
    }

    public void setResp(Object resp) {
        this.resp = resp;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
