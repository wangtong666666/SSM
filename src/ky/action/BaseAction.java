package ky.action;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ky.util.IpGet;
import ky.util.LogUtil;
import ky.util.PageView;
import ky.util.PageViewSum;
import ky.entity.BaseResult;
import ky.entity.TSysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.MDC;

import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

public class BaseAction<T> {
    private LogUtil log = new LogUtil();
    private final String newLine = "\n";
    protected TSysUser loginUser; //當前登录用户
    protected T model;   //驱动实体

    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    HttpServletResponse response = ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();

    HttpSession session = this.request.getSession();
    TSysUser user = (TSysUser) session.getAttribute("user");
    IpGet ipget = new IpGet();

    public IpGet getIpget() {
        return ipget;
    }

    public void setIpget(IpGet ipget) {
        this.ipget = ipget;
    }

    public BaseAction() {
        if (user != null) {
            try {
//				 GeneralLog.debug("BaseAction","当前操作用户:"+user.getLoginName()+"-->当前操作IP地址:"+ipget.getIpAddr(request));
                String remoteAddr = request.getRemoteAddr();
                MDC.put("ip", remoteAddr);
                String sessionId = request.getSession().getId();
                MDC.put("se", sessionId);
                TSysUser user = (TSysUser) session.getAttribute("user");
                loginUser = user;
                MDC.put("us", user.getLoginName());


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (request.getRequestURI().contains("/output/")) {
            return;
        }

        ParameterizedType pt = (ParameterizedType) getClass()
                .getGenericSuperclass();
        Class clazz = (Class) pt.getActualTypeArguments()[0];
        try {
            model = (T) clazz.newInstance(); // 通过一个反射机制 获得当前实体的实例化对象
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public T getModel() {
        return model;
    }

    // json获取前台传来的参数
    public String getRequest(String paramName)
            throws UnsupportedEncodingException {


        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return URLDecoder.decode(request.getParameter(paramName), "UTF-8");
    }

    public String getParameter(String paramName) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getParameter(paramName);
    }

    // 向前台传递参数
    public void setAttribute(String key, Object value) {
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().setAttribute(key, value);
    }

    // 向前台传递json数据
    public void jsonArray(List list) {
        try {
            response.setContentType("text/html;charset=utf-8");
            JSONArray json = JSONArray.fromObject(list);
            System.out.println(json);
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.print(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void jsonObject(Object obj) {
        try {
            response.setContentType("text/html;charset=utf-8");
            JSONObject json = JSONObject.fromObject(obj);
            System.out.println(json);
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.print(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //返回json数据
    public void returnResult(String code, String msg, Object obj) {
        BaseResult result = new BaseResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setResult(obj);
        try {
            response.setContentType("text/html;charset=utf-8");
            JSONObject json = JSONObject.fromObject(result);
            System.out.println(json);
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.print(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * @param String url  请求url
     * @return JSONObject
     * @MethodName: getJSON
     * @description 请求微信端获取JSON返回值
     * @author zj
     * @date 2014年3月5日14:36:46
     */
    public JSONObject getJSON(String url) {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        String respStr = "";
        post.getParams().setContentCharset("utf-8");
        try {

            HttpConnectionManagerParams managerParams = client
                    .getHttpConnectionManager().getParams();
            // 设置连接超时时间(单位毫秒)
            managerParams.setConnectionTimeout(30000);
            // 设置读数据超时时间(单位毫秒)
            managerParams.setSoTimeout(120000);

            client.executeMethod(post);
            respStr = post.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放连接
            if (post != null) {
                post.releaseConnection();
            }
        }
        return JSONObject.fromObject(respStr);
    }


    // 秒转换成北京时间格式
    public String getTime(int time) {
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times = format.format(new Date(time * 1000L));
        return times;
    }

    // footer显示合计数据
    public PageViewSum pageParamSum(String page, String rows, String order, String sort, T model) {
        return new PageViewSum(Integer.parseInt(getParameter(page)), Integer.parseInt(getParameter(rows)), getParameter(order), getParameter(sort), model);
    }

    // 将数据转成json
    public void returnPageSum(PageViewSum pageView) {
        Map jsonMap = new HashMap();
        jsonMap.put("total", Integer.valueOf(pageView.getRecordCount()));
        jsonMap.put("rows", pageView.getRecordList());
        jsonMap.put("footer", pageView.getRecordSumList());
        jsonObject(jsonMap);
    }

    //封装并接收分页和排序的参数
    public PageView pageParam(String page, String rows, String order, String sort, T model) {
        return new PageView(Integer.parseInt(getParameter("" + page + "")), Integer.parseInt(getParameter("" + rows + "")), getParameter("" + order + ""), getParameter("" + sort + ""), model);
    }

    //返回分页数据到界面
    public void returnPageInfo(PageView pageView) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", pageView.getRecordCount());
        jsonMap.put("rows", pageView.getRecordList());
        jsonObject(jsonMap);
    }

    //返回修改,删除,添加的结果到界面
    public void returnU_D_S_info(int param) {
        List list = new ArrayList();
        list.add(param);
        jsonArray(list);
    }

    public void returnU_String_info(String param) {
        List list = new ArrayList();
        list.add(param);
        jsonArray(list);
    }

    //获取添加，修改，删除的数据信息集合(用于保存日志)
    public String getDateInfo(Object obj) {
        String dataInfo = "";
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            // 字段名
            String name = fields[j].getName();
            String dataVal = "";
            // 字段值
            if (fields[j].getType().getName().equals(java.lang.String.class.getName())) {
                try {
                    dataVal = fields[j].get(obj).toString();
                    if (dataVal != null && !dataVal.equals("")) {
                        dataInfo += "[" + name + "]-" + dataVal + ",";
                    }
                } catch (Exception e) {
                }
            } else if (fields[j].getType().getName().equals(java.lang.Integer.class.getName()) || fields[j].getType().getName().equals("int")) {
                try {
                    dataVal = fields[j].getInt(obj) + "";
                    if (dataVal != null && !dataVal.equals("")) {
                        dataInfo += "[" + name + "] " + dataVal + ",";
                    }
                } catch (Exception e) {
                }
            }
        }
        return dataInfo;
    }

}
