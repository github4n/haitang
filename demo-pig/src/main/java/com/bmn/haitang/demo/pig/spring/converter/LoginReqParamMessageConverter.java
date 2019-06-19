package com.bmn.haitang.demo.pig.spring.converter;

import com.bmn.haitang.demo.pig.vo.LoginReqVO;
import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

/**
 * @author: zyq
 * @date: 2018/11/17
 */
public class LoginReqParamMessageConverter extends AbstractHttpMessageConverter<LoginReqVO> {

    public LoginReqParamMessageConverter() {
        super(Charset.forName("utf-8"), MediaType.APPLICATION_FORM_URLENCODED);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return LoginReqVO.class == clazz;
    }

    @Override
    protected LoginReqVO readInternal(Class<? extends LoginReqVO> clazz,
        HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
        String msg =  StreamUtils.copyToString(inputMessage.getBody(), charset);
        LoginReqVO param = new LoginReqVO();
        return param;
    }

    @Override
    protected void writeInternal(LoginReqVO payloadParam, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException {

    }

    private Charset getContentTypeCharset(MediaType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        }
        else {
            return getDefaultCharset();
        }
    }
}
