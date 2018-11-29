package com.micro.darren.common.http;

import com.micro.darren.common.constants.Constants;
import com.micro.darren.common.constants.Context;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Header;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class HttpUtilsImpl implements HttpUtils {

    private static HttpClientHelper httpClientHelper;

    static {
        httpClientHelper = new HttpClientHelper();
    }

    @Override
    public String post(Context context, String address, String charSet) {
        log.info("发送http请求 address:{},charset:{}", address, charSet);
        Stopwatch watch = new Stopwatch(context.getMsgSeq());
        try {
            if (StringUtils.isBlank(charSet)) {
                charSet = Constants.DEFAULT_CHAR_SET;
            }
            ResponseBodyCallback callback = new ResponseBodyCallback(charSet);
            try {
                int s = httpClientHelper.post(address, null, callback);
                watch.stop("httpClientHelper.post");
                if (s == 200) {
                    String ret = callback.getBody();
                    log.info("请求响应{}", ret);
                    return ret;
                } else {
                    log.error("调用POST请求状态错误，status:{},address:{}", s, address);
                }
            } catch (IOException e) {
                log.info("sequence =>" + context.getMsgSeq() + ",返回异常", e);
            }
        } finally {
            watch.log("post(context,address, charSet)");
        }
        return null;
    }

    @Override
    public String get(Context context, String address, String charSet) {
        log.info("请求网关，address:{},charset:{}", address, charSet);
        Stopwatch watch = new Stopwatch(context.getMsgSeq());
        try {
            if (StringUtils.isBlank(charSet)) {
                charSet = Constants.DEFAULT_CHAR_SET;
            }
            ResponseBodyCallback callback = null;
            callback = new ResponseBodyCallback(charSet);
            try {
                int s = httpClientHelper.post(address, null, callback);
                watch.stop("httpClientHelper.post");
                if (s == 200) {
                    String ret = callback.getBody();
                    log.info("请求响应{}", ret);
                    return ret;
                } else {
                    log.error("调用POST请求状态错误，status:{},address:{}", s, address);
                }
            } catch (IOException e) {
                log.info("sequence =>" + context.getMsgSeq() + ",返回异常", e);
            }
        } finally {
            watch.log("get(context,address,charSet)");
        }
        return null;
    }

    @Override
    public String post(Context context, String address, String body, String reqCharSet, String respCharSet,
                       String contentType) {
        Stopwatch watch = new Stopwatch(context.getMsgSeq());
        try {
            if (StringUtils.isBlank(reqCharSet)) {
                reqCharSet = Constants.DEFAULT_CHAR_SET;
            }
            if (StringUtils.isBlank(respCharSet)) {
                respCharSet = Constants.DEFAULT_CHAR_SET;
            }
            ResponseBodyCallback callback = new ResponseBodyCallback(respCharSet);
            try {
                int s = httpClientHelper.post(address, body, reqCharSet, contentType, callback);
                watch.stop("httpClientHelper.post");
                if (s == 200) {
                    String ret = callback.getBody();
                    return ret;
                } else {
                    log.info("sequence =>" + context.getMsgSeq() + ",返回异常 code =>" + s);
                }
            } catch (IOException e) {
                log.info("sequence =>" + context.getMsgSeq() + ",返回异常", e);
            }
        } finally {
            watch.log("post(context,address,body,reqCharSet,respCharSet,contentType)");
        }
        return null;
    }

    @Override
    public String post(Context context, String address, String body, String soapAction) {
        Header[] headers = null;
        if (StringUtils.isNotBlank(soapAction)) {
            headers = new Header[]{new Header(HttpHelper.HEADER_NAME_CONTENT_TYPE, "text/xml;charset=UTF-8"),
                    new Header("SOAPAction", soapAction)};
        } else {
            headers = new Header[]{new Header(HttpHelper.HEADER_NAME_CONTENT_TYPE, "text/xml;charset=UTF-8")};
        }
        try {
            return post(context, address, body.getBytes(Constants.DEFAULT_CHAR_SET), Constants.DEFAULT_CHAR_SET, headers);
        } catch (IOException e) {
            log.info("sequence =>" + context.getMsgSeq() + ",返回异常", e);
        }
        return null;
    }

    @Override
    public String post(Context context, String address, byte[] body, String charset, Header[] headers) {
        Stopwatch watch = new Stopwatch(context.getMsgSeq());
        try {
            if (StringUtils.isBlank(charset)) {
                charset = Constants.DEFAULT_CHAR_SET;
            }
            ResponseBodyCallback callback = new ResponseBodyCallback(charset);
            try {
                log.info("sequence:{}, 请求远程医院获取数据，URL:{},参数：{}", context.getMsgSeq(), address,
                        ArrayUtils.isEmpty(body) ? "" : new String(body, Constants.DEFAULT_CHAR_SET));
                int s = httpClientHelper.post(address, body, charset, null, headers, callback);
                watch.stop("httpClientHelper.post");
                if (s == 200) {
                    String ret = callback.getBody();
                    log.info("请求响应{}", ret);
                    return ret;
                }
            } catch (IOException e) {
                log.error("sequence:{}, 远程请求【{}】异常", context.getMsgSeq(), address);
            }
        } finally {
            watch.log("post(HcContext hcContext, String address, byte[] body, String charset, Header[] headers)");
        }
        return null;
    }

}