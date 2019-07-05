package ky.log;


import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
//import java.util.Iterator;


/**
 * 日志配置文件操作类
 **/
public class AppProperties {

    private static HashMap map = null;
    private static AppProperties appProperties = null;


    private AppProperties() throws IOException {
        Properties prop = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        URL file = classLoader.getResource("log.cfg");
        if (file != null) {
            prop.load(file.openStream());
            Enumeration e = prop.keys();
            if (map == null)
                map = new HashMap();
//				System.out.println("new map------------------------");
            synchronized (map) {
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    map.put(key, prop.getProperty(key));
                }
            }
//				System.out.println("初始化 app.properties 参数");
        } else {
            throw new IOException("AppProperties.java-AppProperties()构造方法:  Load  log.cfg  error");
        }
    }

    private boolean Reload() throws IOException {
        Properties prop = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        URL file = classLoader.getResource("log.cfg");
        if (file != null) {
            prop.load(file.openStream());
            Enumeration e = prop.keys();

            synchronized (map) {
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    map.put(key, prop.getProperty(key));
                }
            }
            return true;
        } else {
            throw new IOException("AppProperties.java-Reload()重载方法:  ReLoad  log.cfg  error");

        }
    }

    public static AppProperties getInstance() {
        if (appProperties == null) {

            try {
                //System.out.println("getInstance 时 appProperties==null  !!!!!!" );
                appProperties = new AppProperties();
            } catch (Exception e) {
                //System.out.println("获得 AppProperties getInstance时 构造AppProperties异常,error=" + e.toString());
            }

        }
        return appProperties;

    }

    /**
     * 获取对应名称的参数
     *
     * @param key 参数名称
     * @return 对应名称参数值
     */
    public static String getProperty(String key) throws IOException {
        if (null == appProperties) {
            //System.out.println("getProperty 时 appProperties==null  !!!!!!" );
            appProperties = new AppProperties();
        }
        return appProperties.getproperty(key);

    }

    private String getproperty(String key) throws IOException {
			/*
			if(null==appProperties) 
				{
				//System.out.println("getProperty 时 appProperties==null  !!!!!!" );
				appProperties=new AppProperties();
				}
				*/
        if (null != appProperties.map) {
            //			System.out.println("getProperty 时 key="+key );
            String sbuf;
            synchronized (appProperties.map) {
                sbuf = (String) appProperties.map.get(key);
            }
//				System.out.println("getProperty 时 key="+key + " value=" +sbuf);
            return sbuf;
        } else {
            //System.out.println("getProperty 时 appProperties.map==null  !!!!!!" );
            return null;
        }

    }

    /**
     * 刷新参数配置，重新加载外部参数
     */
    public static void reloadProperty() throws IOException {
        if (null == appProperties) {
            //System.out.println("reloadProperty 时 appProperties==null  !!!!!!" );
            appProperties = new AppProperties();
        }
        if (null != appProperties.map)
            appProperties.Reload();

    }


    /**
     * 模糊方式获取对应名称的参数
     * @param pattern 参数名称
     * @return 对应名称参数值
     */
/*		public static String getFromProp(String pattern) throws IOException
		{
			if(null==appProperties) appProperties=new AppProperties();
			if(null!= appProperties.map){
				String ret = null;
				Iterator e = appProperties.map.keySet().iterator();
				while(e.hasNext()){
					String key = (String)e.next();
					if(key.indexOf(pattern) >= 0)
						return (String)appProperties.map.get(key);
				}
				return ret;
			}else 
				return null;
		}
*/
    /**
     * 模糊方式获取获取匹配名称的参数(包含两种参数)
     * @param pattern1 参数1名称
     * @param pattern2 参数2名称
     * @return 对应名称参数值
     */
/*		public static String getFromProp(String pattern1,String pattern2) throws IOException
		{
			if(null==appProperties) appProperties=new AppProperties();
			if(null!= appProperties.map){
				String ret = null;
				Iterator e = appProperties.map.keySet().iterator();
				while(e.hasNext()){
					String key = (String)e.next();
					if(key.indexOf(pattern1) >= 0 && key.indexOf(pattern2) >= 0)
						return (String)appProperties.map.get(key);
				}
				return ret;
			}else 
				return null;
		}
*/

    /**
     * 设置参数
     *
     * @param pattern1 对应变量
     * @param key      对应值
     * @return void
     */
    public static void setProperty(String pattern1, String key) throws IOException {
        if (null == appProperties) {
            //System.out.println("setProperty 时 appProperties==null  !!!!!!" );
            appProperties = new AppProperties();
        }

        if (null != appProperties.map) {
            appProperties.setproperty(pattern1, key);
        }
    }


    private void setproperty(String pattern1, String key) throws IOException {
        if (null != appProperties.map) {
            synchronized (appProperties.map) {
//					System.out.println("setproperty ["+pattern1+"] key=["+key+"]");
                String oldvalue = (String) map.put(pattern1, key);
//				 	System.out.println("setproperty oldvalue=["+oldvalue+"] ");
            }
        }

    }

    public static Set entrySet() throws IOException {
        if (null == appProperties) {
            appProperties = new AppProperties();
        }

        return appProperties.entryset();
    }

    private Set entryset() {
        if (null != map) {
            synchronized (map) {
                return map.entrySet();
            }
        } else
            return null;
    }
}

