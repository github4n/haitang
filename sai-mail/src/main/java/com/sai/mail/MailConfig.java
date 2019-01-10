package com.sai.mail;

import com.sai.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * 发件服务配置或收件服务配置
 *
 * @author: zyq
 * @date: 2018/12/29
 */
public class MailConfig {
    // 邮件服务器地址：sender:smap.xx; receiver:imap|pop3.xx
    private String host;
    // 邮件服务器端口
    private int port;
    // 邮件协议：store=pop3,imap. transport=smap
    private String protocol;
    // 属主邮箱：如果是收件服务配置，则表示收件人；如果是发件服务配置，则表示发件人
    private String username;
    // 属主邮箱密码
    private String passwd;
    // 收件人列表：发件服务配置使用
    private String[] to;
    // 抄送人列表：发件服务配置使用
    private String[] cc;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public boolean set(String name, String v) {
        switch(name) {
            case "host":
                this.host = v;
                return true;
            case "port":
                this.port = Integer.parseInt(v);
                return true;
            case "protocol":
                this.protocol = v;
                return true;
            case "username":
                this.username = v;
                return true;
            case "passwd":
                this.passwd = v;
                return true;
            case "to":
                String[] tvl = StringUtils.tokenizeToStringArray(v, ",");
                this.to = tvl;
                return true;
            case "cc":
                String[] cvl = StringUtils.tokenizeToStringArray(v, ",");
                this.to = cvl;
                return true;
            default:
        }

        return false;
    }

    @Override
    public String toString() {
        return "MailConfig{" +
            "host='" + host + '\'' +
            ", port=" + port +
            ", protocol='" + protocol + '\'' +
            ", username='" + username + '\'' +
            ", passwd='" + passwd + '\'' +
            ", to=" + Arrays.toString(to) +
            ", cc=" + Arrays.toString(cc) +
            '}';
    }
}
