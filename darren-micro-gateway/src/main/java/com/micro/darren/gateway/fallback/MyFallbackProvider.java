package com.micro.darren.gateway.fallback;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 配置zuul fallback
 */
@Component
public class MyFallbackProvider implements FallbackProvider {

    /**
     * String: 表示spring.application.name
     * String = *  表示代理所有微服务
     * @return
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String s, Throwable throwable) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.GATEWAY_TIMEOUT;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.GATEWAY_TIMEOUT.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase();
            }

            /**
             * 关闭。。。
             */
            @Override
            public void close() {

            }

            /**
             * 设置返回报文
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                JSONObject json = new JSONObject();
                json.put("data", "服务不可用，请稍后再试。");
                return new ByteArrayInputStream(json.toJSONString().getBytes("UTF-8"));
            }

            /**
             * 设置返回的headers
             * @return
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }


}