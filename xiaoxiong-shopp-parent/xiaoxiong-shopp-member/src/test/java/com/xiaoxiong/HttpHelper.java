package com.xiaoxiong;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpHelper {

    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).setConnectionRequestTimeout(60000).build();
    private static Type wxType = new TypeToken<HashMap<String, String>>() {
    }.getType();
    private static Gson gson = new Gson();

    public static String httpPost(String v_url, String str, String header, int cycles) throws Exception {
//        log.info(String.format("[T-TEST-HIS-IN][%s]", str));
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost postreq = new HttpPost(v_url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(300000).setConnectTimeout(300000).build();
        postreq.setConfig(requestConfig);
        JSONArray headerArray = JSONArray.parseArray(header);
        for (int i = 0; i < headerArray.size(); i++) {
            postreq.addHeader(headerArray.getJSONObject(i).getString("key"),
                    headerArray.getJSONObject(i).getString("value"));
        }
        StringEntity requestEntity = null;
        String resultStr = null;
        try {
            requestEntity = new StringEntity(str, ContentType.APPLICATION_JSON);
            postreq.setEntity(requestEntity);
            int i = 0;
            while (i < cycles) {
                response = client.execute(postreq, new BasicHttpContext());
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    resultStr = EntityUtils.toString(entity, "utf-8");
                }
                if (response.getStatusLine().getStatusCode() != 200) {
                    log.error(resultStr);
                    String errMessage = "请求url=" + v_url + ",请求失败,http返回code:"
                            + response.getStatusLine().getStatusCode();
                    log.error(errMessage);
                    i++;
                    if (i == cycles) {
                    }
                } else {
                    break;
                }
            }
//            log.info(String.format("[T-TEST-HIS-OUT][%s]", resultStr));
            return resultStr;
        } catch (Exception e) {
            log.info(String.format("[HTTP请求出现异常][%s]", e.getMessage()));
            e.getStackTrace();
            String errMessage = "请求url=" + v_url + ",请求失败,http返回code:"
                    + response.getStatusLine().getStatusCode();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return resultStr;
    }

    public static String httpPostForSoap(String v_url, String str, String tradeCode) throws Exception {
        log.info(String.format("[T-%s-HIS-IN][%s]", tradeCode, str));

        // Response err_response = new Response();
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost postreq = new HttpPost(v_url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();
        postreq.setConfig(requestConfig);
        postreq.setHeader("Content-Type", "text/xml; charset=utf-8");

        if ("4004".equals(tradeCode)) {
            postreq.setHeader("SOAPAction", "http://tempuri.org/IAnJiRMYY/GetReportList");
        }
        if ("4005".equals(tradeCode)) {
            postreq.setHeader("SOAPAction", "http://tempuri.org/IAnJiRMYY/GetReportDetail");
        }

        StringEntity requestEntity = null;
        try {
            requestEntity = new StringEntity(str, "utf-8");
            postreq.setEntity(requestEntity);
            response = client.execute(postreq, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {
                String errMessage = "request url failed, http code=" + response.getStatusLine().getStatusCode() + ", 请求url=" + v_url;
//                throw new Exception(
//                        "request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + v_url);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                log.info(String.format("[T-%s-HIS-OUT][%s]", tradeCode, resultStr.replaceAll("&quot;", "\"")));
                return resultStr;
            }
        } catch (IOException e) {
            log.info(String.format("[HTTP请求出现异常][%s]", e.getMessage()));
            e.getStackTrace();
            String errMessage = "请求url=" + v_url + ", 返回异常编号=" + e.getMessage();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    log.info(String.format("[response关闭出现异常][%s]", e.getMessage()));
                    e.printStackTrace();
                    String errMessage = "请求url=" + v_url + ", 返回异常编号=" + e.getMessage();
                }
            }
        }
        return null;
    }

    private static String httpsGet(String url) {
        String responseContent = null;
        try (CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setDefaultRequestConfig(requestConfig).build()) {
            responseContent = null;
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse httpResponse = httpClient.execute(get);
            if (httpResponse.getStatusLine().getStatusCode() >= 300) {
                String s1 = String.format("error:request url failed, http code = %d , url = %s ", httpResponse.getStatusLine().getStatusCode(), url);
                log.error(s1);
                return s1;
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, Consts.UTF_8);
            }
            httpClient.close();
        } catch (Exception e) {
            responseContent = String.format("error:request has Exception, url = %s  ,errorMsg = %s", url, e.getMessage());
            log.error(responseContent, e);
        }
        return responseContent;
    }


    public static String wxOpenId(String appId, String secret, String code) {
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", appId, secret, code);
        String s = httpsGet(url);
        log.info(s);
        Map<String, String> map = gson.fromJson(s, wxType);
        return map.get("openid");
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            //信任所有
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        //ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
        return new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null,
                NoopHostnameVerifier.INSTANCE);
    }
}