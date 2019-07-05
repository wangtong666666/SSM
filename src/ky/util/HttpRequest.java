package ky.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {


    public static String httpRequest(String request, String RequestMethod,
                                     String param) {
        StringBuffer buffer = new StringBuffer();
        String resMessage = "";// 响应信息
        try {
            // 建立连接
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(RequestMethod);
            if (param != null) {
                OutputStream out = connection.getOutputStream();
                out.write(param.getBytes("UTF-8"));
                out.close();
            }
            // 流处理
            InputStream input = connection.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
            BufferedReader reader = new BufferedReader(inputReader);
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            // 关闭连接、释放资源
            reader.close();
            inputReader.close();
            input.close();
            input = null;
            connection.disconnect();
            resMessage = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resMessage;
    }

    public static boolean isActive(String url, int timeout) {
        URL urltest;
        try {
            urltest = new URL(url);
            URLConnection myurlcon = urltest.openConnection();
            myurlcon.setConnectTimeout(timeout);
            myurlcon.setReadTimeout(timeout);
            new InputStreamReader(myurlcon.getInputStream(), "UTF-8");
            System.out.println(url + "连接可用");
            return true;
        } catch (Exception e1) {
            System.out.println(url + "连接打不开!");
            urltest = null;
        }

        return false;
    }

    public static void main(String[] args) {
        String ret = HttpRequest.httpRequest("url", "POST", "pwd=123&login=aa");
    }
}
