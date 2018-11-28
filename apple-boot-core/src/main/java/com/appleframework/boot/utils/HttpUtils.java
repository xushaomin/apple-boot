package com.appleframework.boot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求
 * 
 * @author cruise.xu
 *
 */
public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
	/**
	 * post请求 ，超时默认20秒
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */

	public static String post(String url, Map<String, String> params) {
		return post(url, params, 20);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout 超时时间，秒
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params, int timeout) {
		logger.info("request url is " + url);
		long begin = System.currentTimeMillis();
		String retVal = null;
		if (null != params && params.size() > 0) {
			String param = UrlEncodeUtils.getUrlParamsByMap(params, false, false);
			retVal = sendPost(url, param);
        }
		else {
			retVal = sendPost(url, null);
		}
		long end = System.currentTimeMillis();
		logger.info("consume millis end time is " + (end - begin));
		logger.info("return result is " + retVal);
		return retVal;
	}
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            if(null != param) {
            	out.print(param);
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
		} catch (Exception e) {
			logger.error("发送 POST 请求出现异常！" + e);
		} finally {// 使用finally块来关闭输出流、输入流
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error("发送 POST 请求出现异常！" + ex.getMessage());
			}
		}
		return result;
    }
    	
}