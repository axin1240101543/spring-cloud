package com.micro.darren.common.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class HttpClientHelper implements HttpHelper {

    private static final Log logger = LogFactory.getLog(HttpClientHelper.class);
    private static final String ENV_PROXY_KEY = "HTTP_PROXY";
    private HttpClient client;
    private MultiThreadedHttpConnectionManager manager;
    private int connectionTimeout = 2000;
    private int soTimeout = 5000;
    private int maxTotalConnections = 4096;
    private int defaultMaxConnectionsPerHost = 1024;

    @Override
    public void destroy() {
        this.manager.shutdown();
    }

    /**
     * @return the connectionTimeout
     */
    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    /**
     * @param connectionTimeout the connectionTimeout to set
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.manager.getParams().setConnectionTimeout(connectionTimeout);
    }

    /**
     * @return the soTimeout
     */
    public int getSoTimeout() {
        return this.soTimeout;
    }

    /**
     * @param soTimeout the soTimeout to set
     */
    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
        this.manager.getParams().setSoTimeout(soTimeout);
    }

    /**
     * @return the maxTotalConnections
     */
    public int getMaxTotalConnections() {
        return this.maxTotalConnections;
    }

    /**
     * @param maxTotalConnections the maxTotalConnections to set
     */
    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
        this.manager.getParams().setMaxTotalConnections(maxTotalConnections);
    }

    /**
     * @return the defaultMaxConnectionsPerHost
     */
    public int getDefaultMaxConnectionsPerHost() {
        return this.defaultMaxConnectionsPerHost;
    }

    /**
     * @param defaultMaxConnectionsPerHost the defaultMaxConnectionsPerHost to
     *            set
     */
    public void setDefaultMaxConnectionsPerHost(int defaultMaxConnectionsPerHost) {
        this.defaultMaxConnectionsPerHost = defaultMaxConnectionsPerHost;
        this.manager.getParams().setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost);
    }

    public HttpClientHelper(boolean initProxy) {
        this();
        if (initProxy) {
            try {
                // check is system properties include proxy info
                String proxyHost = HttpClientHelper.trimToNull(System.getenv(HttpClientHelper.ENV_PROXY_KEY));
                proxyHost = System.getProperty(HttpClientHelper.ENV_PROXY_KEY, proxyHost);
                String proxyPort = null;
                if (proxyHost != null) {
                    int idx = proxyHost.indexOf(":");
                    proxyPort = proxyHost.substring(idx + 1);
                    proxyHost = proxyHost.substring(0, idx);
                    this.setProxy(proxyHost, Integer.parseInt(proxyPort));
                } else {
                    HttpClientHelper.logger.warn("HttpClientHelper no proxy to set");
                }
            } catch (Exception e) {
                HttpClientHelper.logger.warn("use proxy fail", e);
            }
        }
    }

    public HttpClientHelper(String proxyHost, int proxyPort) {
        this();
        this.setProxy(proxyHost, proxyPort);
    }

    public HttpClientHelper() {
        this.manager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams paras = new HttpConnectionManagerParams();
        paras.setConnectionTimeout(this.connectionTimeout);
        paras.setSoTimeout(this.soTimeout);
        paras.setMaxTotalConnections(this.maxTotalConnections);
        paras.setDefaultMaxConnectionsPerHost(this.defaultMaxConnectionsPerHost);
        this.manager.setParams(paras);
        this.client = new HttpClient(this.manager);
    }

    private void setProxy(String proxyHost, int port) {
        proxyHost = HttpClientHelper.trimToNull(proxyHost);
        if (proxyHost != null) {
            this.client.getHostConfiguration().setProxy(proxyHost, port);
        }
    }

    @Override
    public int exec(HttpMethod method, HttpState httpState, HttpCallback callback) throws IOException {
        try {
            if (httpState == null) {
                httpState = new HttpState();
            }
            int r = this.client.executeMethod(null, method, httpState);
            if (callback != null) {
                callback.callback(r, method, httpState.getCookies());
            }
            return r;
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
    }

    protected HttpState setHttpState(HttpState httpState, Map<String, String> cookies, String url) throws IOException {
        if (httpState == null) {
            httpState = new HttpState();
        }
        if (cookies != null && cookies.size() != 0) {
            String n;
            String host = this.getHost(url);
            for (Entry<String, String> en : cookies.entrySet()) {
                n = HttpClientHelper.trimToNull(en.getKey());
                if (n != null) {
                    httpState.addCookie(new Cookie(host, n, en.getValue(), "/", null, false));
                }
            }
        }
        return httpState;
    }

    protected String getHost(String url) throws MalformedURLException {
        return new URL(url).getHost();
    }

    protected HttpState setHttpState(HttpState httpState, Cookie[] cookies, String url) throws IOException {
        if (httpState == null) {
            httpState = new HttpState();
        }
        if (cookies != null && cookies.length > 0) {
            String host = this.getHost(url);
            for (Cookie c : cookies) {
                if (c != null) {
                    if (c.getDomain() == null) {
                        c.setDomain(host);
                    }
                    if (c.getPath() == null) {
                        c.setPath("/");
                    }
                    httpState.addCookie(c);
                }
            }
        }
        return httpState;
    }

    protected <T> HttpMethod getGetMethod(String uri, Map<String, T> data, String charset, Header[] headers) {
        return this.setParameter(this.getGetMethod(uri, charset, headers), data, charset);
    }

    protected <T> HttpMethod getGetMethod(String uri, String queryStr, String charset, Header[] headers) {
        return this.setParameter(this.getGetMethod(uri, charset, headers), queryStr);
    }

    protected <T> HttpMethod getGetMethod(String uri, Map<String, T> data, String charset, Map<String, String> headers) {
        return this.getGetMethod(uri, data, charset, this.createHeaders(headers));
    }

    protected GetMethod getGetMethod(String uri, String charset, Header[] headers) {
        GetMethod getMethod = new GetMethod(uri);
        getMethod.setFollowRedirects(true);
        if (charset == null) {
            charset = HttpHelper.DEFAULT_CHARSET;
        }
        getMethod.getParams().setContentCharset(charset);
        if (headers != null && headers.length != 0) {
            for (Header h : headers) {
                if (h != null) {
                    getMethod.addRequestHeader(h);
                }
            }
        }
        return getMethod;
    }

    protected <T> GetMethod setParameter(GetMethod getMethod, Map<String, T> data, String charset) {
        if (charset == null) {
            charset = HttpHelper.DEFAULT_CHARSET;
        }
        if (data != null && data.size() != 0) {
            List<NameValuePair> paras = new ArrayList<NameValuePair>(data.size());
            for (Entry<String, T> entry : data.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    if (value instanceof String) {
                        paras.add(new NameValuePair(entry.getKey(), (String) value));
                    } else if (value instanceof Iterable) {
                        for (Object v : ((Iterable<?>) value)) {
                            if (v != null) {
                                paras.add(new NameValuePair(entry.getKey(), String.valueOf(v)));
                            }
                        }
                    } else if (value.getClass().isArray()) {
                        int len = Array.getLength(value);
                        for (int i = 0; i < len; i++) {
                            Object v = Array.get(value, i);
                            if (v != null) {
                                paras.add(new NameValuePair(entry.getKey(), String.valueOf(v)));
                            }
                        }
                    } else {
                        paras.add(new NameValuePair(entry.getKey(), String.valueOf(value)));
                    }
                }
            }
            if (paras.size() != 0) {
                String qStr = HttpClientHelper.trimToNull(getMethod.getQueryString());
                if (qStr != null) {
                    getMethod.setQueryString(
                            qStr + "&" + EncodingUtil.formUrlEncode(paras.toArray(new NameValuePair[paras.size()]), charset));
                } else {
                    getMethod.setQueryString(EncodingUtil.formUrlEncode(paras.toArray(new NameValuePair[paras.size()]), charset));
                }
            }
        }
        return getMethod;
    }

    protected <T> GetMethod setParameter(GetMethod getMethod, String queryStr) {
        if (StringUtils.isBlank(queryStr)) {
            return getMethod;
        }
        String qStr = HttpClientHelper.trimToNull(getMethod.getQueryString());
        if (qStr != null) {
            getMethod.setQueryString(qStr + "&" + queryStr);
        } else {
            getMethod.setQueryString(queryStr);
        }
        return getMethod;
    }

    public static String trimToNull(String str) {
        if (str != null) {
            str = str.trim();
        }
        return (str == null || str.length() == 0) ? null : str;
    }

    protected Header[] createHeaders(Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            List<Header> hs = new ArrayList<Header>(headers.size());
            for (Entry<String, String> en : headers.entrySet()) {
                hs.add(new Header(en.getKey(), en.getValue()));
            }
            return hs.toArray(new Header[hs.size()]);
        }
        return null;
    }

    protected HttpMethod getPostMethod(String uri, String charset, Map<String, String> headers, InputStream data) {
        return this.getPostMethod(uri, charset, this.createHeaders(headers), data);
    }

    protected HttpMethod getPostMethod(String uri, String charset, Header[] headers, InputStream data) {
        return this.setParameter(this.getPostMethod(uri, charset, headers, "application/octet-stream"), data);
    }

    protected <T> HttpMethod getPostMethod(String uri, String charset, Header[] headers, Map<String, T> data) {
        return this.setParameter(this.getPostMethod(uri, charset, headers, HttpClientHelper.DEFAULT_POST_CONTENT_TYPE), data);
    }

    protected <T> HttpMethod getPostMethod(String uri, String charset, Map<String, String> headers, Map<String, T> data) {
        return this.getPostMethod(uri, charset, this.createHeaders(headers), data);
    }

    protected PostMethod getPostMethod(String uri, String charset, Header[] headers, String contentType) {
        PostMethod postMethod = new PostMethod(uri);
        if (charset == null) {
            charset = HttpHelper.DEFAULT_CHARSET;
        }
        postMethod.getParams().setContentCharset(charset);
        boolean ct = false;
        if (headers != null && headers.length != 0) {
            for (Header h : headers) {
                ct = ct || HttpHelper.HEADER_NAME_CONTENT_TYPE.equalsIgnoreCase(h.getName());
                postMethod.addRequestHeader(h);
            }
        }
        if (!ct) {
            postMethod.addRequestHeader(HttpHelper.HEADER_NAME_CONTENT_TYPE, contentType);
        }
        return postMethod;
    }

    protected PostMethod setParameter(PostMethod postMethod, InputStream data) {
        if (data != null) {
            postMethod.setRequestEntity(new InputStreamRequestEntity(data));
        }
        return postMethod;
    }

    protected <T> PostMethod setParameter(PostMethod postMethod, Map<String, T> data) {
        if (data != null && data.size() > 0) {
            for (Entry<String, T> entry : data.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    if (value instanceof String) {
                        postMethod.addParameter(entry.getKey(), (String) value);
                    } else if (value instanceof Iterable) {
                        for (Object v : ((Iterable<?>) value)) {
                            if (v != null) {
                                postMethod.addParameter(entry.getKey(), String.valueOf(v));
                            }
                        }
                    } else if (value.getClass().isArray()) {
                        int len = Array.getLength(value);
                        for (int i = 0; i < len; i++) {
                            Object v = Array.get(value, i);
                            if (v != null) {
                                postMethod.addParameter(entry.getKey(), String.valueOf(v));
                            }
                        }
                    } else {
                        postMethod.addParameter(entry.getKey(), String.valueOf(value));
                    }
                }
            }
        }
        return postMethod;
    }

    public final static String DEFAULT_POST_CONTENT_TYPE = "application/x-www-form-urlencoded";

    @Override
    public <T> int get(String address, Map<String, T> data) throws IOException {
        return this.get(address, data, null);
    }

    @Override
    public <T> int get(String address, Map<String, T> data, HttpCallback callback) throws IOException {
        return this.get(address, data, HttpHelper.DEFAULT_CHARSET, (Cookie[]) null, (Header[]) null, callback);
    }

    @Override
    public <T> int get(String address, Map<String, T> data, String charset, Cookie[] cookies, Header[] headers,
            HttpCallback callback) throws IOException {
        return this.exec(this.getGetMethod(address, data, charset, headers), this.setHttpState(null, cookies, address), callback);
    }

    @Override
    public <T> int get(String address, Map<String, T> data, String charset, Map<String, String> cookies,
            Map<String, String> headers, HttpCallback callback) throws IOException {
        return this.exec(this.getGetMethod(address, data, charset, headers), this.setHttpState(null, cookies, address), callback);
    }

    @Override
    public <T> int get(String address, String queryStr, String charset, Map<String, String> cookies, Header[] headers,
            HttpCallback callback) throws IOException {
        return this.exec(this.getGetMethod(address, queryStr, charset, headers), this.setHttpState(null, cookies, address),
                callback);
    }

    @Override
    public <T> int post(String address, Map<String, T> data) throws IOException {
        return this.post(address, data, null);
    }

    @Override
    public <T> int post(String address, Map<String, T> data, HttpCallback callback) throws IOException {
        return this.post(address, data, HttpHelper.DEFAULT_CHARSET, (Cookie[]) null, (Header[]) null, callback);
    }

    @Override
    public <T> int post(String address, Map<String, T> data, String charset, Map<String, String> cookies,
            Map<String, String> headers, HttpCallback callback) throws IOException {
        return this.exec(this.getPostMethod(address, charset, headers, data), this.setHttpState(null, cookies, address),
                callback);
    }

    @Override
    public <T> int post(String address, Map<String, T> data, String charset, Cookie[] cookies, Header[] headers,
            HttpCallback callback) throws IOException {
        return this.exec(this.getPostMethod(address, charset, headers, data), this.setHttpState(null, cookies, address),
                callback);
    }

    @Override
    public int post(String address, InputStream data, String charset, Cookie[] cookies, Header[] headers, HttpCallback callback)
            throws IOException {
        return this.exec(this.getPostMethod(address, charset, headers, data), this.setHttpState(null, cookies, address),
                callback);
    }

    @Override
    public int post(String address, InputStream data, String charset, Map<String, String> cookies, Map<String, String> headers,
            HttpCallback callback) throws IOException {
        return this.exec(this.getPostMethod(address, charset, headers, data), this.setHttpState(null, cookies, address),
                callback);
    }

    @Override
    public int post(String address, byte[] data, String charset, Map<String, String> cookies, Header[] headers,
            HttpCallback callback) throws IOException {
        return this.exec(this.getPostMethod(address, charset, headers, new ByteArrayInputStream(data)),
                this.setHttpState(null, cookies, address), callback);
    }

    @Override
    public int post(String address, String data, String charset, String contentType, HttpCallback callback) throws IOException {
        Header[] headers = new Header[1];
        if (StringUtils.isNotBlank(contentType)) {
            headers[0] = new Header(HttpHelper.HEADER_NAME_CONTENT_TYPE, contentType);
        } else {
            headers[0] = new Header(HttpHelper.HEADER_NAME_CONTENT_TYPE, "text/plain");
        }
        return post(address, getBytes(data, charset), charset, null, headers, callback);
    }

    protected byte[] getBytes(String data, String charset) throws UnsupportedEncodingException {
        if (data == null) {
            return new byte[0];
        }
        if (charset == null) {
            charset = HttpHelper.DEFAULT_CHARSET;
        }
        return data.getBytes(charset);
    }

    @Override
    public <T> int put(String address, Map<String, T> data) throws IOException {
        return this.put(address, data, null);
    }

    @Override
    public <T> int put(String address, Map<String, T> data, HttpCallback callback) throws IOException {
        return this.put(address, data, HttpHelper.DEFAULT_CHARSET, (Cookie[]) null, (Header[]) null, callback);
    }

    @Override
    public <T> int put(String address, Map<String, T> data, String charset, Cookie[] cookies, Header[] headers,
            HttpCallback callback) throws IOException {
        return this.exec(this.getPutMethod(address, charset, headers, data), this.setHttpState(null, cookies, address), callback);
    }

    @Override
    public int put(String address, InputStream data, String charset, Cookie[] cookies, Header[] headers, HttpCallback callback)
            throws IOException {
        return this.exec(this.getPutMethod(address, charset, headers, data), this.setHttpState(null, cookies, address), callback);
    }

    @Override
    public int put(String address, byte[] data, String charset, Cookie[] cookies, Header[] headers, HttpCallback callback)
            throws IOException {
        return this.exec(this.getPutMethod(address, charset, headers, data), this.setHttpState(null, cookies, address), callback);
    }

    @Override
    public int put(String address, String data, String charset, String contentType, HttpCallback callback) throws IOException {
        Header[] headers = new Header[1];
        if (StringUtils.isNotBlank(contentType)) {
            headers[0] = new Header(HttpHelper.HEADER_NAME_CONTENT_TYPE, contentType);
        } else {
            headers[0] = new Header(HttpHelper.HEADER_NAME_CONTENT_TYPE, "text/plain");
        }
        return this.exec(this.getPutMethod(address, charset, headers, contentType, data),
                this.setHttpState(null, (Cookie[]) null, address), callback);
    }

    protected HttpMethod getPutMethod(String uri, String charset, Header[] headers, InputStream data) {
        return this.setEntity(this.getPutMethod(uri, charset, headers, "application/octet-stream"), data);
    }

    protected <T> HttpMethod getPutMethod(String uri, String charset, Header[] headers, Map<String, T> data)
            throws UnsupportedEncodingException {
        return this.setEntity(this.getPutMethod(uri, charset, headers, HttpClientHelper.DEFAULT_POST_CONTENT_TYPE), data,
                charset);
    }

    protected HttpMethod getPutMethod(String uri, String charset, Header[] headers, byte[] data)
            throws UnsupportedEncodingException {
        return this.setEntity(this.getPutMethod(uri, charset, headers, HttpClientHelper.DEFAULT_POST_CONTENT_TYPE), data);
    }

    protected HttpMethod getPutMethod(String uri, String charset, Header[] headers, String contentType, String data)
            throws UnsupportedEncodingException {
        return this.setEntity(this.getPutMethod(uri, charset, headers, contentType), data, contentType, charset);
    }

    protected PutMethod getPutMethod(String uri, String charset, Header[] headers, String contentType) {
        PutMethod putMethod = new PutMethod(uri);
        if (charset == null) {
            charset = HttpHelper.DEFAULT_CHARSET;
        }
        putMethod.getParams().setContentCharset(charset);
        boolean ct = false;
        if (headers != null && headers.length != 0) {
            for (Header h : headers) {
                ct = ct || HttpHelper.HEADER_NAME_CONTENT_TYPE.equalsIgnoreCase(h.getName());
                putMethod.addRequestHeader(h);
            }
        }
        if (!ct) {
            putMethod.addRequestHeader(HttpHelper.HEADER_NAME_CONTENT_TYPE, contentType);
        }
        return putMethod;
    }

    protected PutMethod setEntity(PutMethod putMethod, InputStream data) {
        if (data != null) {
            putMethod.setRequestEntity(new InputStreamRequestEntity(data));
        }
        return putMethod;
    }

    protected PutMethod setEntity(PutMethod putMethod, byte[] data) {
        if (data != null) {
            putMethod.setRequestEntity(new ByteArrayRequestEntity(data));
        }
        return putMethod;
    }

    protected PutMethod setEntity(PutMethod putMethod, String data, String contentType, String charset)
            throws UnsupportedEncodingException {
        if (data != null) {
            putMethod.setRequestEntity(new StringRequestEntity(data, contentType, charset));
        }
        return putMethod;
    }

    protected <T> PutMethod setEntity(PutMethod putMethod, Map<String, T> data, String charset)
            throws UnsupportedEncodingException {
        if (data != null && data.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (Entry<String, T> entry : data.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    if (value instanceof String) {
                        builder.append(URLEncoder.encode(entry.getKey(), charset)).append("=")
                                .append(URLEncoder.encode((String) value, charset)).append("&");
                    } else if (value instanceof Iterable) {
                        builder.append(URLEncoder.encode(entry.getKey(), charset)).append("=");
                        StringBuilder temp = new StringBuilder();
                        for (Object v : ((Iterable<?>) value)) {
                            if (v != null) {
                                temp.append(String.valueOf(v)).append(",");
                            }
                        }
                        if (',' == temp.charAt(temp.length() - 1)) {
                            temp.deleteCharAt(temp.length() - 1);
                        }
                        builder.append(URLEncoder.encode(temp.toString(), charset)).append("&");
                    } else if (value.getClass().isArray()) {
                        builder.append(URLEncoder.encode(entry.getKey(), charset)).append("=");
                        StringBuilder temp = new StringBuilder();
                        int len = Array.getLength(value);
                        for (int i = 0; i < len; i++) {
                            Object v = Array.get(value, i);
                            if (v != null) {
                                temp.append(String.valueOf(v)).append(",");
                            }
                        }
                        if (',' == temp.charAt(temp.length() - 1)) {
                            temp.deleteCharAt(temp.length() - 1);
                        }
                        builder.append(URLEncoder.encode(temp.toString(), charset)).append("&");
                    } else {
                        builder.append(URLEncoder.encode(entry.getKey(), charset)).append("=")
                                .append(URLEncoder.encode(String.valueOf(value), charset)).append("&");
                    }
                }
            }
        }
        return putMethod;
    }

}
