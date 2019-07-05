package ky.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.http.HttpStatus;

public class HttpClientOnLinelkl {

    /**
     * 发POST表单请求
     */
    public static String postRequestByFormEntity(String url, UrlEncodedFormEntity param) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(param);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK_200) {
            HttpEntity re_entity = response.getEntity();
            String res = EntityUtils.toString(re_entity, "UTF-8");
            response.close();
            client.close();
            return res;
        }
        return "error";
    }

    public static String postRequestByFormEntity(String url, StringEntity param) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(param);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK_200) {
            HttpEntity re_entity = response.getEntity();
            String res = EntityUtils.toString(re_entity, "UTF-8");
            response.close();
            client.close();
            return res;
        }
        return "error";
    }


    /**
     * @param @param  request
     * @param @param  RequestMethod
     * @param @param  output
     * @param @return
     * @return String
     * @throws
     * @Description: http方式
     * @author 王超
     * @date 2016-10-13
     */
    public static String HttpRequest(String request, String RequestMethod, String output) {
        StringBuffer buffer = new StringBuffer();
        String resMessage = "";//响应信息
        try {
            // 建立连接
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(RequestMethod);
            if (output != null) {
                OutputStream out = connection.getOutputStream();
                out.write(output.getBytes("GBK"));
                out.close();
            }
            // 流处理
            InputStream input = connection.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(input, "GBK");
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

    /**
     * @param @param  url
     * @param @param  map
     * @param @param  charset
     * @param @return
     * @return String
     * @throws
     * @Description: doPost
     * @author 王超
     * @date 2016-10-13
     */
    public static String doPost(String url, String reqXml, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;

        String result = null;
        try {
            httpClient = new SSLClientlkl();
            httpPost = new HttpPost(url);
            //设置参数
            StringEntity se = new StringEntity(reqXml);
            httpPost.setEntity(se);

            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
