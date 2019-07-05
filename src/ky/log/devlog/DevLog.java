package ky.log.devlog;

import java.util.HashMap;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ky.log.AppProperties;
import ky.log.GeneralLog;


//
//
//  @ Project : 2007-09-17
//  @ File Name : DevLog.java
//  @ Date : 2007-9-27
//	@ Author : @yang yunchun
//
//


public class DevLog extends GeneralLog {

    public static final String ModelName = "Dev";
    //��־logger
//	Logger logger ;

    //��־appender
//	Appender appender;

//	protected static HashMap  DevLevelMap = new HashMap();

    public DevLog(String loggerName) {
        super(loggerName);
    }

    public static void main(String args[]) {
        //	DevLog.isFatalEnabled("B0001","10.50.100.65");

        DevLog.setLogLevel("B0001", "10.50.100.65", 1);

        if (DevLog.isFatalEnabled("B0001", "10.50.100.65"))
            DevLog.fatal("B0001", "10.50.100.65", "this is a fatal");

        if (DevLog.isWarnEnabled("B0001", "10.50.100.65"))
            DevLog.warn("B0001", "10.50.100.65", "this is a warn");

        DevLog.setLogLevel("B0001", "10.50.100.65", 2);
        if (DevLog.isInfoEnabled("B0001", "10.50.100.65"))
            DevLog.info("B0001", "10.50.100.65", "this is a info");
        if (DevLog.isErrorEnabled("B0001", "10.50.100.65"))
            DevLog.error("B0001", "10.50.100.65", "this is a error");
        if (DevLog.isDebugEnabled("B0001", "10.50.100.65"))
            DevLog.debug("B0001", "10.50.100.65", "this is a debug");

        DevLog.setLogLevel("B0001", "10.50.100.65", 3);
        if (DevLog.isInfoEnabled("B0001", "10.50.100.65"))
            DevLog.info("B0001", "10.50.100.65", "this is a info");

        DevLog.setLogLevel("B0001", "10.50.100.65", 1);
        if (DevLog.isInfoEnabled("B0001", "10.50.100.65"))
            DevLog.info("B0001", "10.50.100.65", "this is a info2");
		
		
/*		if(DevLog.isDebugEnabled("B0001","10.50.100.65"))
			DevLog.debug("B0001","10.50.100.65","this is a debug  2");
		if(DevLog.isInfoEnabled("B0001","10.50.100.65"))
			DevLog.info("B0001","10.50.100.65","this is a info in  comm  ");
		if(DevLog.isWarnEnabled("B0001","10.50.100.65"))
			DevLog.warn("B0002","10.50.100.65", "this is a warn");
		if(DevLog.isInfoEnabled("B0001","10.50.100.65"))
			DevLog.info("B0002","10.50.100.65","this is a info in  tcp  ");
		if(DevLog.isWarnEnabled("B0001","10.50.100.65"))
			DevLog.warn("B0001","10.50.100.65", "this is a warn in first comm");
		
*/

    }

    /**
     * ���ö�Ӧ��־�������ĳ̨�豸û�ж�����־������ȱʡΪDebug����
     *
     * @param devno �豸��
     * @param ip    ip��ַ
     * @param level ��Ч��־����
     * @return void
     */
    public static void setLogLevel(String devno, String ip, int level) {

        try {
            AppProperties.setProperty(devno + "_" + ip, String.valueOf(level));
        } catch (Exception e) {
            System.out.println("DevNo=" + devno + " ip=" + ip + " setLogLevel ʧ�ܡ� " + e.toString());
            return;
        }
    }


    public static void fatal(String devno, String ip, String messaeg) {
        String ModelName = "null";
        ModelName = DevLog.ModelName;

//		if(devno != null &&  ip != null)
//			ModelName =devno + "_" +ip;

        DevLog mylog4j;
        mylog4j = new DevLog(ModelName);
        mylog4j.configLogger(ModelName);
        //mylog4j.dofatal(messaeg);

        mylog4j.dofatal("devno:" + devno + "(ip:" + ip + ")," + messaeg);
    }


    public static void error(String devno, String ip, String messaeg) {
        String ModelName = "null";
        ModelName = DevLog.ModelName;

//		if(devno != null &&  ip != null)
//			ModelName =devno + "_" +ip;
        DevLog log4j;
        log4j = new DevLog(ModelName);
        log4j.configLogger(ModelName);
//		log4j.doerror(messaeg);

        log4j.doerror("devno:" + devno + "(ip:" + ip + ")," + messaeg);
    }


    public static void warn(String devno, String ip, String messaeg) {
        String ModelName = "null";
        ModelName = DevLog.ModelName;

//		if(devno != null &&  ip != null)
//			ModelName =devno + "_" +ip;
        DevLog log4j;
        log4j = new DevLog(ModelName);
        log4j.configLogger(ModelName);
//		log4j.dowarn(messaeg);

        log4j.dowarn("devno:" + devno + "(ip:" + ip + ")," + messaeg);
    }

    public static void info(String devno, String ip, String messaeg) {
        String ModelName = "null";
        ModelName = DevLog.ModelName;

//		if(devno != null &&  ip != null)
//			ModelName =devno + "_" +ip;
        DevLog log4j;
        log4j = new DevLog(ModelName);
        log4j.configLogger(ModelName);
//		log4j.doinfo(messaeg);

        log4j.doinfo("devno:" + devno + "(ip:" + ip + ")," + messaeg);
    }

    public static void debug(String devno, String ip, String messaeg) {
        String ModelName = "null";
        ModelName = DevLog.ModelName;

//		if(devno != null &&  ip != null)
//			ModelName =devno + "_" +ip;
        DevLog log4j;
        log4j = new DevLog(ModelName);
        log4j.configLogger(ModelName);
//		System.out.println("debug out-------------messaeg="+ messaeg);
//		log4j.dodebug(messaeg);

        log4j.dodebug("devno:" + devno + "(ip:" + ip + ")," + messaeg);
    }

    public static boolean isFatalEnabled(String devno, String ip) {
        int levelInt = getLogLevel(devno, ip);
        if (levelInt == Level.DEBUG_INT || levelInt == Level.WARN_INT ||
                levelInt == Level.ERROR_INT || levelInt == Level.FATAL_INT ||
                levelInt == Level.INFO_INT)
            return true;
        else
            return false;
    }

    public static boolean isErrorEnabled(String devno, String ip) {
        int levelInt = getLogLevel(devno, ip);
        if (levelInt == Level.DEBUG_INT || levelInt == Level.WARN_INT ||
                levelInt == Level.ERROR_INT || levelInt == Level.INFO_INT)
            return true;
        else
            return false;
    }

    public static boolean isWarnEnabled(String devno, String ip) {
        int levelInt = getLogLevel(devno, ip);
        if (levelInt == Level.DEBUG_INT || levelInt == Level.WARN_INT ||
                levelInt == Level.INFO_INT)
            return true;
        else
            return false;
    }

    public static boolean isInfoEnabled(String devno, String ip) {
        int levelInt = getLogLevel(devno, ip);
        if (levelInt == Level.DEBUG_INT || levelInt == Level.INFO_INT)
            return true;
        else
            return false;
    }

    public static boolean isDebugEnabled(String devno, String ip) {
        int levelInt = getLogLevel(devno, ip);
        if (levelInt == Level.DEBUG_INT)
            return true;
        else
            return false;
    }
}
