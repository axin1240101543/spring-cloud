package com.micro.darren.common.http;

import com.micro.darren.common.constants.Context;
import org.apache.commons.httpclient.Header;

public interface HttpUtils {

    /**
     * 发送http post请求
     *
     * @param address 请求地址
     * @param charSet 返回内容字符集
     * @return
     */
    String post(Context context, String address, String charSet);

    /**
     * @param address 请求地址
     * @param charSet 返回内容字符集
     * @return http请求回来的数据必须是xml或者json
     */
    String get(Context context, String address, String charSet);

    /**
     * 通过post请求获取数据
     *
     * @param context
     * @param address 访问地址
     * @param body post的消息体
     * @param reqCharSet post消息体的编码 可null 默认utf-8
     * @param respCharSet 返回加过的编码 可null 默认utf-8
     * @param contentType http content-type 可null 默认text/plain
     * @return
     */
    String post(Context context, String address, String body, String reqCharSet, String respCharSet, String contentType);

    /**
     *
     * 调用远程webservice. 默认编码格式UTF-8，XML格式<br/>
     *
     * @param context 上下文
     * @param address 访问地址
     * @param body 访问参数
     * @param soapAction webservice的SOAPAction
     * @return
     */
    String post(Context context, String address, String body, String soapAction);

    /**
     * post请求. <br/>
     *
     * @param context 上下文信息
     * @param address 访问url
     * @param body body参数
     * @param charset 编码格式
     * @param headers 头信息
     * @return
     */
    String post(Context context, String address, byte[] body, String charset, Header[] headers);

}
