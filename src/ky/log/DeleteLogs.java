package ky.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yepeng
 * <p>
 * 过期日志删除类
 */
public class DeleteLogs {
    protected final Calendar calendar = Calendar.getInstance();
    protected Map paths;
    protected int keepDays;

    public void setKeepDays(int keepDays) {
        this.keepDays = keepDays;
    }

    public void deleteLogs() {
        try {
            paths = new HashMap();
            Iterator itr = AppProperties.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry entry = (Map.Entry) itr.next();
                String key = (String) entry.getKey();
                if (key.endsWith(GeneralLog.PROPERTY_FILE_PATH)) {
                    String value = (String) entry.getValue();
                    if (value != null) {
                        if (value.length() == 0) {
                            paths.put("Blank", value);
                        } else {
                            paths.put(value, value);
                        }
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 0 - keepDays);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(calendar.getTime());
        String osName = System.getProperty("os.name");

        if (osName.startsWith("Windows")) {
            String[] cmd = new String[3];
            cmd[0] = "cmd.exe";
            cmd[1] = "/C";

            Iterator itr = paths.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry entry = (Map.Entry) itr.next();
                String value = (String) entry.getValue();
                value = value.replace('/', '\\');

                if (value.endsWith("\\") || value.length() == 0) {
                    value += "*_" + date;
                } else {
                    value += "\\*_" + date;
                }
                cmd[2] = "del " + value;
                if (GeneralLog.isDebugEnabled("Scheduler"))
                    GeneralLog.debug("Scheduler", cmd[2]);

                Runtime rt = Runtime.getRuntime();
                Process proc;
                try {
                    proc = rt.exec(cmd);
                    InputStream stderr = proc.getErrorStream();
                    InputStreamReader isr = new InputStreamReader(stderr);
                    BufferedReader br = new BufferedReader(isr);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        if (GeneralLog.isDebugEnabled("Scheduler"))
                            GeneralLog.debug("Scheduler", line);
                    }
                    int exitVal = proc.waitFor();
                    if (GeneralLog.isDebugEnabled("Scheduler"))
                        GeneralLog.debug("Scheduler", "Process exitValue: "
                                + exitVal);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            String[] cmd = new String[3];
            cmd[0] = "sh";
            cmd[1] = "-c";

            Iterator itr = paths.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry entry = (Map.Entry) itr.next();
                String value = (String) entry.getValue();
                value = value.replace('\\', '/');

                if (value.endsWith("/") || value.length() == 0) {
                    value += "*_" + date;
                } else {
                    value += "/*_" + date;
                }
                cmd[2] = "rm -f " + value;
                if (GeneralLog.isDebugEnabled("Scheduler"))
                    GeneralLog.debug("Scheduler", cmd[2]);

                Runtime rt = Runtime.getRuntime();
                Process proc;
                try {
                    proc = rt.exec(cmd);
                    InputStream stderr = proc.getErrorStream();
                    InputStreamReader isr = new InputStreamReader(stderr);
                    BufferedReader br = new BufferedReader(isr);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        if (GeneralLog.isDebugEnabled("Scheduler"))
                            GeneralLog.debug("Scheduler", line);
                    }
                    int exitVal = proc.waitFor();
                    if (GeneralLog.isDebugEnabled("Scheduler"))
                        GeneralLog.debug("Scheduler", "Process exitValue: "
                                + exitVal);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
