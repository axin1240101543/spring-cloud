package com.micro.darren.common.http;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpMethod;

/**
 * 获取http返回内容
 */
public class ResponseBodyCallback implements HttpCallback {

    private String body = null;

    private String charset = null;

    private Integer httpstaus4collect = null;

    private int httpStatus = -1;

    /**
     * 
     */
    public ResponseBodyCallback() {
        super();
    }

    /**
     * 指定返回結果編碼
     * 
     * @param charset
     */
    public ResponseBodyCallback(String charset) {
        super();
        this.charset = charset;
    }

    /**
     * 
     * @param charset
     *            指定返回結果編碼
     * @param httpstaus4collect
     *            当返回的状态码等于该值时采集response数据
     */
    public ResponseBodyCallback(String charset, int httpstaus4collect) {
        super();
        this.charset = charset;
        this.httpstaus4collect = httpstaus4collect;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.iqiyi.ugc.utils.http.CommonHttpCallback#callback(org.apache.commons
     * .httpclient.HttpMethod, org.apache.commons.httpclient.HttpState)
     */
    @Override
    public void callback(int httpstaus, HttpMethod httpMethod, Cookie[] cookies)
            throws IOException {
        this.httpStatus = httpstaus;
        if (this.httpstaus4collect == null
                || this.httpstaus4collect.intValue() == httpstaus) {
            if (this.charset == null) {
                this.body = httpMethod.getResponseBodyAsString();
            } else {
                this.body = new String(httpMethod.getResponseBody(),
                        this.charset);
            }
        }
    }

    public String getBody() {
        return this.body;
    }

    public int getHttpStatus() {
        return this.httpStatus;
    }
}