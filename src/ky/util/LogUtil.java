package ky.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * ********************************************************
 *
 * @author 路人
 * @ClassName: LogUtil
 * @Description: txt日志工具类
 * @date 2013-5-25 下午03:10:25
 * ******************************************************
 */
public class LogUtil {

    /**
     * ********************************************************
     *
     * @param msg
     * @return boolean
     * @throws FileNotFoundException
     * @Title: saveerrorlog
     * @Description: 数据操作日志
     * @date 2013-5-25 下午03:08:41
     * *******************************************************
     */


    public boolean savelogUtil(String msg) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            String path = LogUtil.class.getResource("/").getPath();
            String logUrl = path.substring(1, path.length()) + "Log/" + formatdate(2) + ".txt";
            File file = new File(logUrl);
            fos = new FileOutputStream(file, true);
            osw = new OutputStreamWriter(fos, "gbk");
            bw = new BufferedWriter(osw);
            bw.write("[Time] " + formatdate(1) + "\n" + msg);
            bw.newLine();
            bw.newLine();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * ********************************************************
     *
     * @param style
     * @return String
     * @Title: formatdate
     * @Description: 格式化时间
     * @date 2013-5-25 下午03:08:51
     * *******************************************************
     */
    public String formatdate(int style) {
        String type = "yyyyMMdd";
        if (style == 1)
            type = "yyyy-MM-dd HH:mm:ss";
        if (style == 2)
            type = "yyyyMMdd";
        if (style == 3)
            type = "yyyyMMddhhmmss";

        SimpleDateFormat sdf = new SimpleDateFormat(type);
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * ********************************************************
     *
     * @return String
     * @Title: getPath
     * @Description: 获得项目所在路径
     * @date 2013-5-25 下午03:09:09
     * *******************************************************
     */
    public String getPath() {
        // return "d:\\a";//System.getProperty("user.dir");
        // return System.getProperty("user.dir");

        String addr[] = new File(LogUtil.class.getName()).getAbsolutePath().replace("%20", " ").split("\\\\");
        String address = ""; // 项目绝对路径
        for (int i = 0; i < addr.length - 1; i++) {
            address += addr[i] + "\\";
        }
        return address + "src\\Log\\"; // newEntity 类 路径
    }


}
