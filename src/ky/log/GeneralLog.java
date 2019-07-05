package ky.log;

import ky.log.devlog.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import ky.log.devlog.*;

//
//
//
//  @ Project : @nantian J2EE ATMP
//  @ File Name : GeneralLog.java
//  @ Date : 2007-9-26
//  @ Author : @yang yunchun
//
//


/**
 * 根据 level 来设定日志级别，level通过配置文件在sprint启动时导入，不同模板的日志格式
 * 在 configLogger中根据 传入的模块名产生 唯一的文件appender
 * 可以记录例如 通信、加密等部件的日志。
 */

/**
 * 通用的模块日志记录接口。通过配置文件来定义不同的组件级别及日志格式。
 **/
public class GeneralLog {

/*	
 通用配置
default.fileIndex = 10
default.size = 9000000
default.filepath=/was/dse_logs/log
default.pattern=%C %n %d{ISO8601} %n %l %n Message: %m %n %n
default.level=info

个别部件配置
comm.fileIndex = 10
comm.size = 9000000
comm.filepath=/was/dse_logs/log
comm.pattern=%C %n %d{ISO8601} %n %l %n Message: %m %n %n
comm.level=info

设备配置
dev.fileIndex = 10
dev.size = 9000000
dev.filepath=/was/dse_logs/log
dev.pattern=%C %n %d{ISO8601} %n %l %n Message: %m %n %n
dev.level=info

*/

    //配置模板属性参数
    /**
     * 常量：定义指向log4j的文件路径
     **/
    public static final String TAG_FILEPATH = "filePath";
    /**
     * 常量：定义指向log4j的缺省标识
     **/
    public static final String PROPERTY_DEFAULT_PREFIX = "default";
    /**
     * 常量：定义指向log4j的文件路径
     **/
    public static final String PROPERTY_FILE_PATH = ".filepath";
    /**
     * 常量：定义指向log4j的文件格式化定义
     **/
    public static final String PROPERTY_FILE_PATTERN = ".pattern";
    /**
     * 常量：定义指向log4j的日志级别
     **/
    public static final String PROPERTY_LOG_LEVEL = ".level";
    /**
     * 常量：定义指向log4j的日志大小
     **/
    public static final String PROPERTY_LOG_SIZE = ".size";
    /**
     * 常量：定义日志文件数目
     **/
    public static final String PROPERTY_LOG_FILE_INDEX = ".fileIndex";


    /**
     * 常量：定义调试级别debug
     * 日志级别从高到低依次为 fatal>error>warn>info>debug
     **/
    public static final String PROPERTY_LEVEL_DEBUG = "debug";
    public static final String PROPERTY_LEVEL_DEBUG_INT = "1";
    /**
     * 常量：定义调试级别info
     **/
    public static final String PROPERTY_LEVEL_INFO = "info";
    public static final String PROPERTY_LEVEL_INFO_INT = "2";
    /**
     * 常量：定义调试级别warn
     **/
    public static final String PROPERTY_LEVEL_WARN = "warn";
    public static final String PROPERTY_LEVEL_WARN_INT = "3";
    /**
     * 常量：定义调试级别error
     **/
    public static final String PROPERTY_LEVEL_ERROR = "error";
    public static final String PROPERTY_LEVEL_ERROR_INT = "4";
    /**
     * 常量：定义调试级别fatal
     **/
    public static final String PROPERTY_LEVEL_FATAL = "fatal";
    public static final String PROPERTY_LEVEL_FATAL_INT = "5";

    public static final String PROPERTY_LEVEL_OFF = "off";
    public static final String PROPERTY_LEVEL_OFF_INT = "0";

    /**
     * 常量：定义缺省文件大小
     **/
    public static final long DEFAULT_LOG_SIZE = 5000000L;
    /**
     * 常量：定义缺省文件个数
     **/
    public static final int DEFAULT_LOG_INDEX = 30;

//	public static final String FIELD_FILE_NAME_PREFIX = "@";

    /**
     * 常量：定义分隔符_
     **/
    public static final String FIELD_FILE_NAME_SEP = "_";

    protected String filePath, fileName;

    //日志logger
    Logger logger;

    //日志appender
    Appender appender;

//	private HashMap map=null;

    protected static HashMap establishedLoggers = new HashMap();

    //用来标识日志级别 0为不记录日志 0=off  1=debug 2=info 3= warn 4=error 5=fatal
    private int levelInt = 0;


    public static void main(String args[]) {


        if (GeneralLog.isWarnEnabled("comm"))
            GeneralLog.warn("comm", "this is a warn");
        if (GeneralLog.isInfoEnabled("comm"))
            GeneralLog.info("comm", "this is a info");


        if (GeneralLog.isErrorEnabled("comm"))
            GeneralLog.error("comm", "this is a error");
        if (GeneralLog.isDebugEnabled("comm"))
            GeneralLog.debug("comm", "this is a debug");
        if (GeneralLog.isInfoEnabled("comm"))
            GeneralLog.info("comm", "this is a info in  comm  ");
        if (GeneralLog.isWarnEnabled("tcp"))
            GeneralLog.warn("tcp", "this is a warn");
        if (GeneralLog.isInfoEnabled("tcp"))
            GeneralLog.info("tcp", "this is a info in  tcp  ");
        if (GeneralLog.isWarnEnabled("comm"))
            GeneralLog.warn("comm", "this is a warn in first comm");

        if (GeneralLog.isFatalEnabled("comm"))
            GeneralLog.fatal("comm", "this is a fatal in first comm");

        if (GeneralLog.isDebugEnabled("comm"))
            GeneralLog.debug("comm", "this is a debug test");

        GeneralLog.reloadTracelevel();

        if (GeneralLog.isDebugEnabled("comm"))
            GeneralLog.debug("comm", "this is a debug test 222");


//		GeneralLog.warn("tcp", "this is a warn");
//		GeneralLog.error("this is a error");
//		GeneralLog.warn("comm", "this is a warn");
//		GeneralLog.info("this is a info");

    }


    public GeneralLog() {
//		System.out.println("creat new logger");
    }

    public GeneralLog(String loggerName) {
        synchronized (GeneralLog.class) {
            //如果loggerName已经存在，避免重新获取
            logger = (Logger) establishedLoggers.get(loggerName);
            if (logger == null) {
                logger = Logger.getLogger(loggerName);
//				System.out.println("creat new logger ="+ loggerName+ " value=" + logger);
                establishedLoggers.put(loggerName, logger);
            }
//			System.out.println("get  logger ="+ loggerName+ " value=" + logger);
        }
    }

    /**
     * 配置日志logger参数，appender
     * @param ModelName 模块名称
     */
    protected void configLogger(String ModelName) {
        String pattern = null, logFile = null;
        String level = null;
        String currentLevel;
        long fileSize = 100000L;
        long default_fileSize = 100000L;
        int fileIndex = 30;
        int default_fileIndex = 30;

        String default_level;
        String default_pattern;
        String default_logFile;
        //获取配置模板定义的参数
        try {
            // 把配置文件读取，统一到一个HashMap中，减少文件的打开句柄
            //先读取缺省值。
            default_level = AppProperties.getProperty(PROPERTY_DEFAULT_PREFIX + PROPERTY_LOG_LEVEL);
            if (default_level == null)
                default_level = "info";
            default_pattern = AppProperties.getProperty(PROPERTY_DEFAULT_PREFIX + PROPERTY_FILE_PATTERN);
            if (default_pattern == null)
                default_pattern = "%C %n %d{ISO8601} %n %l %n Message: %m %n %n";
            default_logFile = AppProperties.getProperty(PROPERTY_DEFAULT_PREFIX + PROPERTY_FILE_PATH);
            if (default_logFile == null)
                default_logFile = "/dse_logs/log";
            String tmp = AppProperties.getProperty(PROPERTY_DEFAULT_PREFIX + PROPERTY_LOG_SIZE);
            if (tmp == null)
                default_fileSize = DEFAULT_LOG_SIZE;
            else {
                try {
                    default_fileSize = Long.parseLong(tmp);
                } catch (Exception e) {
                    default_fileSize = DEFAULT_LOG_SIZE;
                }
            }
            tmp = AppProperties.getProperty(PROPERTY_DEFAULT_PREFIX + PROPERTY_LOG_FILE_INDEX);
            if (tmp == null)
                default_fileIndex = DEFAULT_LOG_INDEX;
            else {
                try {
                    default_fileIndex = Integer.parseInt(tmp);
                } catch (Exception e) {
                    default_fileIndex = DEFAULT_LOG_INDEX;
                }
            }


            //读特殊定义部件配置文件


            //判断如果传入的字符串有"_"符号表示是设备日志 把模块名称转化为“dev”
            //所有的模块名，命名规则中不能包含"_"符号.
            int devflag = 0;//标记是否是设备日志 0 普通模块 ，1 设备；
            String ModelNamebak = null;

            if (ModelName.lastIndexOf("_") > 0) {
                ModelNamebak = ModelName;
                ModelName = "dev";
                devflag = 1;
            }


            if (ModelName.equals("dev"))
                level = getDevLogLevel(ModelNamebak);
            else
                level = getLogLevel(ModelName);

//			System.out.println(" --getLogLevel ModelName="+ModelName+" ModelNamebak="+ ModelNamebak+" level="+ level);
            if (level == null) {
                //没有获得特殊部件的日志定义，就获取缺省定义。
//				System.out.println("没有获得特殊部件的日志定义，就获取缺省定义");
                level = default_level;
            }
            pattern = AppProperties.getProperty(ModelName + PROPERTY_FILE_PATTERN);
            if (pattern == null)
                pattern = default_pattern;
            logFile = AppProperties.getProperty(ModelName + PROPERTY_FILE_PATH);
            if (logFile == null)
                logFile = default_logFile;

            tmp = AppProperties.getProperty(ModelName + PROPERTY_LOG_SIZE);
            if (tmp == null)
                fileSize = default_fileSize;
            else {
                try {
                    fileSize = Long.parseLong(tmp);
                } catch (Exception e) {
                    fileSize = default_fileSize;
                }
            }
            tmp = AppProperties.getProperty(ModelName + PROPERTY_LOG_FILE_INDEX);
            if (tmp == null)
                fileIndex = default_fileIndex;
            else {
                try {
                    fileIndex = Integer.parseInt(tmp);
                } catch (Exception e) {
                    fileIndex = default_fileIndex;
                }
            }

            if (devflag == 1)
                ModelName = ModelNamebak;

            if (logFile != null) {
                if (logFile.endsWith("/")) {
                    logFile = logFile + ModelName;
                } else
                    logFile = logFile + "/" + ModelName;
            }

            if (logFile == null || pattern == null) {
                //throw new DSEInvalidArgumentException(DSEException.critical,"addRecord","pattern or logFile not defined in log properties");
                ;
            }

            int index = logFile.lastIndexOf("/");
            if (index > 0) {
                File path = new File(logFile.substring(0, index));
                if (!path.exists())
                    path.mkdirs();
            }

            if (level == null)
                level = PROPERTY_LEVEL_INFO;
            else
                level = level.toLowerCase();

//			System.out.println("GeneralLog  ModelName=["+ModelName +"] getLogLevel level="+ level);

        } catch (Exception e) {
//			System.out.println("configLogger Exception="+e.toString());
        }

        //按照日期构建日志的文件名
        //partition the log to the date. 2007-11-03. The log file name is as: name_20071103
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//		String dateStr = df.format(new Date());
//		logFile = logFile + "_" + dateStr;

//		System.out.println("logfile = " + logFile);

        //判断是否日志周期发生改变，如是，重建appender,并删除前一日期日志appender
        appender = null;
        int lastSep = logFile.lastIndexOf("/");
        String appenderName;
        if (lastSep >= 0)
            appenderName = logFile.substring(lastSep + 1);
        else
            appenderName = logFile;

        //check if the date has shifted

        synchronized (GeneralLog.class) {
            //锁定 排斥会否 造成并发时的性能下降？？？
            //如不锁定在并发时 if(appender == null) 会造成多次创建日志，一笔日志计录多次!!!!!!!!!
            appender = logger.getAppender(appenderName);
            if (appender == null) {
//	      		System.out.println("appender == null !!!!!!!!!!!!!!!!!!!!!!!!!! " );
                PatternLayout layout = new PatternLayout(pattern);
                appender = null;
                try {
                    //appender = new FileAppender(layout,logFile,true);
                    appender = new RollingFileAppender(layout, logFile, true);
                    //add name for the appender
                    appender.setName(appenderName);

                    ((RollingFileAppender) appender).setMaximumFileSize(fileSize);
                    ((RollingFileAppender) appender).setMaxBackupIndex(fileIndex);

                } catch (Exception e) {
                    System.out.println("GeneralLog error " + e.toString());
                    //	throw new DSEInvalidArgumentException(DSEException.critical,"addRecord",e.toString());
                    return;
                }
                logger.addAppender(appender);
            }
        }

        Enumeration enumdata = null;
        try {
            enumdata = logger.getAllAppenders();
        } catch (Exception e) {
            System.out.println("Enumeration error ");
        }

        while (enumdata.hasMoreElements()) {
            Appender oldAppender = (Appender) enumdata.nextElement();
            String oldName = oldAppender.getName();
//			System.out.println("oldAppender = "+oldAppender +  "oldName= " + oldName +" appenderName=" + appenderName);
            if (oldName != null) {
                if (!oldName.equals(appenderName)) {
                    logger.removeAppender(oldAppender);
//					System.out.println("!!!!!!logger.removeAppender"  );
                } else {
                    appender = oldAppender;
//					System.out.println("!!!!!!appender = " + oldAppender );
                }
            }
        }


        if (appender == null) {
//      		System.out.println("appender == null !!!!!!!!!!!!!!!!!!!!!!!!!!--------------- " );
            PatternLayout layout = new PatternLayout(pattern);
            appender = null;
            try {
                //appender = new FileAppender(layout,logFile,true);
                appender = new RollingFileAppender(layout, logFile, true);
                //add name for the appender
                appender.setName(appenderName);

                ((RollingFileAppender) appender).setMaximumFileSize(fileSize);
                ((RollingFileAppender) appender).setMaxBackupIndex(fileIndex);

            } catch (Exception e) {
                System.out.println("GeneralLog error " + e.toString());
                return;
            }
            logger.addAppender(appender);

        }


        //设定logger级别
        if (level.equals(PROPERTY_LEVEL_DEBUG) || level.equals(PROPERTY_LEVEL_DEBUG_INT))
            logger.setLevel(Level.DEBUG);
        else if (level.equals(PROPERTY_LEVEL_WARN) || level.equals(PROPERTY_LEVEL_WARN_INT))
            logger.setLevel(Level.WARN);
        else if (level.equals(PROPERTY_LEVEL_INFO) || level.equals(PROPERTY_LEVEL_INFO_INT))
            logger.setLevel(Level.INFO);
        else if (level.equals(PROPERTY_LEVEL_ERROR) || level.equals(PROPERTY_LEVEL_ERROR_INT))
            logger.setLevel(Level.ERROR);
        else if (level.equals(PROPERTY_LEVEL_FATAL) || level.equals(PROPERTY_LEVEL_FATAL_INT))
            logger.setLevel(Level.FATAL);
        else if (level.equals(PROPERTY_LEVEL_OFF) || level.equals(PROPERTY_LEVEL_OFF_INT))
            logger.setLevel(Level.OFF);


        int levelInt = Level.INFO_INT;
        if (level.equals(PROPERTY_LEVEL_DEBUG))
            levelInt = Level.DEBUG_INT;
        else if (level.equals(PROPERTY_LEVEL_WARN))
            levelInt = Level.WARN_INT;
        else if (level.equals(PROPERTY_LEVEL_ERROR))
            levelInt = Level.ERROR_INT;
        else if (level.equals(PROPERTY_LEVEL_FATAL))
            levelInt = Level.FATAL_INT;
        else if (level.equals(PROPERTY_LEVEL_OFF))
            levelInt = Level.OFF_INT;

//		System.out.println("Level.INFO_INT ="+Level.INFO_INT +" Level.DEBUG_INT =" + Level.DEBUG_INT +" Level.WARN_INT =" +Level.WARN_INT);
//		System.out.println("levelInt ="+levelInt);
    }


    /**
     * 根据模块名称获得日志级别
     * @param ModelName 模块名称
     * @return 对应级别
     */
    protected static String getLogLevel(String ModelName) {
        String level;
        //	System.out.println(" super getLogLevel-----------");
        try {
            level = AppProperties.getProperty(ModelName + PROPERTY_LOG_LEVEL);
        } catch (Exception e) {
            return null;
        }
        if (level == null) {
            try {
                level = AppProperties.getProperty(PROPERTY_DEFAULT_PREFIX
                        + PROPERTY_LOG_LEVEL);
                return level;
            } catch (Exception e) {
                return null;
            }
        } else
            return level;
    }

    protected String getDevLogLevel(String ModelName) {

        String level;
//		System.out.println(" DevNo="+ devno +" ip=" + ip + "的日志级别，");
//		System.out.println(" getDevLogLevel by devno and ip-----------ModelName="+ ModelName);
        try {
            level = AppProperties.getProperty(ModelName);
        } catch (Exception e) {
            //System.out.println("无法获得 DevNo="+ devno +" ip=" + ip + "的日志级别，故缺省赋值为debug模式！");
            level = "1";
        }


        //这里需要和数据库的数值约定一致
        //假设 从低到高 分别为没有日志，debug info warn error fatal
        //0 没有日志 OFF_INT , 1 = DEBUG_INT,2 =INFO_INT ,3 = WARN_INT,4= ERROR_INT,5= FATAL_INT
        //
        if (level == null) {
            //	System.out.println("无法获得 DevNo="+ devno +" ip=" + ip + "的日志级别，故缺省赋值为debug模式！");
            level = "1";
        }
        return level;

    }

    protected static int getLogLevel(String devno, String ip) {

        String level;
        String name = devno + "_" + ip;
//		System.out.println(" DevNo="+ devno +" ip=" + ip + "的日志级别，");
//		System.out.println(" DevLog getLogLevel by devno and ip-----------="+name);
        try {
//			 System.out.println("开始获得 【"+ name +"】的日志级别");
            level = AppProperties.getProperty(name);
        } catch (Exception e) {
//			System.out.println("1无法获得 DevNo="+ devno +" ip=" + ip + "的日志级别，故缺省赋值为debug模式！");
            System.out.println("获得 【" + name + "】的日志级别异常 ." + e.toString());
            level = PROPERTY_LEVEL_DEBUG_INT;

//			DevLog.setLogLevel(devno,ip,1);

        }

        //这里需要和数据库的数值约定一致
        //假设 从低到高 分别为没有日志，debug info warn error fatal
        //0 没有日志 OFF_INT , 1 = DEBUG_INT,2 =INFO_INT ,3 = WARN_INT,4= ERROR_INT,5= FATAL_INT
        //
        if (level == null) {
            //System.out.println("无法获得 DevNo="+ devno +" ip=" + ip + "的日志级别，故缺省赋值为debug模式！");
//			System.out.println("无法获得 【"+ name +"】的日志级别，故缺省赋值为debug模式！");
            level = PROPERTY_LEVEL_DEBUG_INT;
        }

        if (level.equals(PROPERTY_LEVEL_OFF_INT))
            return Level.OFF_INT;
        else if (level.equals(PROPERTY_LEVEL_DEBUG_INT))
            return Level.DEBUG_INT;
        else if (level.equals(PROPERTY_LEVEL_INFO_INT))
            return Level.INFO_INT;
        else if (level.equals(PROPERTY_LEVEL_WARN_INT))
            return Level.WARN_INT;
        else if (level.equals(PROPERTY_LEVEL_ERROR_INT))
            return Level.ERROR_INT;
        else if (level.equals(PROPERTY_LEVEL_FATAL_INT))
            return Level.FATAL_INT;
        else {
//			System.out.println("DevNo="+ devno +" ip=" + ip + "的日志级别非法，故修改赋值为debug模式！！");
            return Level.DEBUG_INT;
        }
    }
    /**
     * 判断是否具有此日志级别。日志级别从高到低依次为 fatal>error>warn>info>trace
     **/
/*	public boolean isFatalEnabled(){
		if(levelInt == Level.FATAL_INT)
		 return true;
		else
		 return false;
	}
*/	
/*	public boolean isErrorEnabled(){
		if(levelInt == Level.ERROR_INT)
			 return true;
		else
			 return false;
	}
	
	public boolean isWarnEnabled(){
		if(levelInt == Level.WARN_INT)
			 return true;
		else
			 return false;
	}
	
	public boolean isInfoEnabled(){
		if(levelInt == Level.INFO_INT)
			 return true;
		else
			 return false;
	}
	
	public boolean isDebugEnabled(){
		if(levelInt == Level.DEBUG_INT)
			 return true;
		else
			 return false;
	}
*/

    /**
     * 判断是否具有此日志级别。日志级别从高到低依次为 fatal>error>warn>info>debug
     **/
    public static boolean isFatalEnabled(String ModelName) {
        String level = getLogLevel(ModelName);
        if (level == null) {
//			System.out.println("没有定义 组件【"+ModelName+"】的日志级别！！请修改日志配置文件");
            return false;
        }
        if (level.equals(PROPERTY_LEVEL_FATAL) || level.equals(PROPERTY_LEVEL_ERROR) || level.equals(PROPERTY_LEVEL_WARN) || level.equals(PROPERTY_LEVEL_INFO) || level.equals(PROPERTY_LEVEL_DEBUG))
            return true;
        else
            return false;
    }

    /**
     * 判断是否具有此日志级别。日志级别从高到低依次为 fatal>error>warn>info>debug
     **/
    public static boolean isErrorEnabled(String ModelName) {
        String level = getLogLevel(ModelName);
        if (level == null) {
//			System.out.println("没有定义 组件【"+ModelName+"】的日志级别！！请修改日志配置文件");
            return false;
        }
        if (level.equals(PROPERTY_LEVEL_ERROR) || level.equals(PROPERTY_LEVEL_WARN) || level.equals(PROPERTY_LEVEL_INFO) || level.equals(PROPERTY_LEVEL_DEBUG))
            return true;
        else
            return false;
    }

    /**
     * 判断是否具有此日志级别。日志级别从高到低依次为 fatal>error>warn>info>debug
     **/
    public static boolean isWarnEnabled(String ModelName) {
        String level = getLogLevel(ModelName);
        if (level == null) {
//			System.out.println("没有定义 组件【"+ModelName+"】的日志级别！！请修改日志配置文件");
            return false;
        }
        if (level.equals(PROPERTY_LEVEL_WARN) || level.equals(PROPERTY_LEVEL_INFO) || level.equals(PROPERTY_LEVEL_DEBUG))
            return true;
        else
            return false;
    }


    /**
     * 判断是否具有此日志级别。日志级别从高到低依次为 fatal>error>warn>info>debug
     **/
    public static boolean isInfoEnabled(String ModelName) {
        String level = getLogLevel(ModelName);
        if (level == null) {
//			System.out.println("没有定义 组件【"+ModelName+"】的日志级别！！请修改日志配置文件");
            return false;
        }
        if (level.equals(PROPERTY_LEVEL_INFO) || level.equals(PROPERTY_LEVEL_DEBUG))
            return true;
        else
            return false;
    }

    /**
     * 判断是否具有此日志级别。日志级别从高到低依次为 fatal>error>warn>info>debug
     **/
    public static boolean isDebugEnabled(String ModelName) {
        String level = getLogLevel(ModelName);
        if (level == null) {
//			System.out.println("没有定义 组件【"+ModelName+"】的日志级别！！请修改日志配置文件");
            return false;
        }
        if (level.equals(PROPERTY_LEVEL_DEBUG))
            return true;
        else
            return false;
    }

    public static void fatal(String ModelName, String messaeg) {
        if (ModelName == null)
            ModelName = "null";
        GeneralLog mylog4j;
        mylog4j = new GeneralLog(ModelName);
        mylog4j.configLogger(ModelName);
        mylog4j.dofatal(messaeg);
    }


    public static void error(String ModelName, String messaeg) {
        if (ModelName == null)
            ModelName = "null";
        GeneralLog log4j;
        log4j = new GeneralLog(ModelName);
        log4j.configLogger(ModelName);
        log4j.doerror(messaeg);
    }


    public static void warn(String ModelName, String messaeg) {
        if (ModelName == null)
            ModelName = "null";
        GeneralLog log4j;
        log4j = new GeneralLog(ModelName);
        log4j.configLogger(ModelName);
        log4j.dowarn(messaeg);
    }

    public static void info(String ModelName, String message) {
        if (ModelName == null)
            ModelName = "null";
        GeneralLog log4j;
        log4j = new GeneralLog(ModelName);
        log4j.configLogger(ModelName);
        log4j.doinfo(message);
    }

    public static void debug(String ModelName, String message) {
        if (ModelName == null)
            ModelName = "null";
        GeneralLog log4j;
        log4j = new GeneralLog(ModelName);
        log4j.configLogger(ModelName);
        log4j.dodebug(message);
    }


    protected void dofatal(String data) {
        logger.fatal(data);
    }

    protected void doerror(String data) {
        logger.error(data);
    }

    protected void dowarn(String data) {
        logger.warn(data);
    }

    protected void doinfo(String data) {
        logger.info(data);
    }

    protected void dodebug(String data) {
        logger.debug(data);
    }

    /**
     * 重新加载日志级别的配置信息
     **/
    public static void reloadTracelevel() {
        try {
            AppProperties.reloadProperty();
        } catch (Exception e) {
            System.out.println("reloadTracelevel 失败。 " + e.toString());
            return;
        }
    }

}
