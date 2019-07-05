package ky.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import ky.entity.PmsMessageRetuen;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

public class MessageAuthenticationCodeGet {
    /**
     * 短信发送工具
     *
     * @param mobilephone 手机号
     * @param mercName    商户名称
     * @param status      认证结果
     * @throws Exception
     */
    public String sendMessage(String mobilephone, String mercName) throws Exception {
        String interfaceId = "1";//发起请求平台编号
        String modelNo = "1"; //短信模板（默认认证失败）
        String context = mercName;//短信内容
        URL url = new URL("http://172.16.7.104:8003/msg_manager/zilianyunfu/sendMessage.action");
        //URL url = new URL("http://xxxxxxxxxxxxx:xxxx/msg_manager/pmsmessage/sendMessage.action");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(120000);//2分钟
        connection.setReadTimeout(120000);
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        String content = "interfaceId=" + URLEncoder.encode(interfaceId, "utf-8");//暂时设置为1,发起请求平台编号
        content += "&phoneNumber=" + URLEncoder.encode(mobilephone, "utf-8");//正确的11位手机号码
        content += "&context=" + URLEncoder.encode(context, "utf-8");//消息内容
        content += "&modelNo=" + URLEncoder.encode(modelNo, "utf-8");//短信模板编号
        content += "&channelNo=" + URLEncoder.encode("clwh", "utf-8");//短信上游
        out.writeBytes(content);
        out.flush();
        out.close();
        //接受响应的参数
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        String line;
        String response = "";
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        if (null != buffer && !"".equals(buffer)) {
            //解析短信上游返回的信息
//			Gson gson = new Gson();
//			JsonReader jsonReader = new JsonReader(new StringReader(buffer.toString()));
            JSONObject json = JSONObject.fromObject(buffer.toString());
            PmsMessageRetuen smsChannelInfo = (PmsMessageRetuen) JSONObject.toBean(json, PmsMessageRetuen.class);
            String responseState = smsChannelInfo.getResponse();
            String searchId = smsChannelInfo.getSearchID();
            response = responseState.equals("00") ? "发送成功" : "发送失败";
            System.out.println(response + "," + searchId);
        }
        reader.close();
        connection.disconnect();
        return response;
    }

}
