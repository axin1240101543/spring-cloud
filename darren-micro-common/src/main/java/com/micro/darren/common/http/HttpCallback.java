package com.micro.darren.common.http;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpMethod;


public interface HttpCallback {

    /**
     * callback when get response. if you need headers, httpMethod.getResponseHeaders()
     * 
     * @param httpMethod
     * @param cookies
     * @throws IOException
     * @see HttpClientHelper
     */
    public void callback(int httpstatus, HttpMethod httpMethod, Cookie[] cookies) throws IOException;
}
