package xxct.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Leo on 18/4/8.
 */
public class XxctHttpRequest {
    public static String sendGet(String url, String param, String charset) {
        String result = "";
        String line;
        StringBuffer sb = new StringBuffer();
        BufferedReader in = null;
        String urlNameString;
        try {
            if (param != null || (!"".equals(param)))
                urlNameString = url + "?" + param;
            else
                urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("contentType", charset);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            //conn.setConnectTimeout(60);
            //conn.setReadTimeout(60);
            // 建立实际的连接
            conn.connect();
            // 定义 BufferedReader输入流来读取URL的响应,设置接收格式
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), charset));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
            result = sb.toString();
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String args[]) {
        String url = "https://api.douban.com";
        String param = "/v2/movie/in_theaters?start=0&count=6";
        String charset = "utf-8";
        String result = sendGet(url+param, null, charset);

    }
}
