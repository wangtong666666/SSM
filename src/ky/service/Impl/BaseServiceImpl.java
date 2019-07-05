package ky.service.Impl;

import ky.service.BaseService;
import ky.util.LogUtil;


import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class BaseServiceImpl implements BaseService {

    private LogUtil log = new LogUtil();

    private final String newLine = "\n";


    public void saveLog(String menu, String tableName, String operateType, String dataInfo) {
        String logMsg = "";


        String userName = "";
        logMsg += "[UserName] " + userName + newLine;
        logMsg += "[MenuName] " + menu + newLine;
        logMsg += "[TableName] " + tableName + newLine;
        logMsg += "[OperateType] " + operateType + newLine;
        logMsg += "[DataInfo] " + dataInfo + newLine;

        // 记录添加信息
        log.savelogUtil(logMsg);
    }


    //获取添加，修改，删除的数据信息集合
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


    public int returnParam(int param) {
        if (param < 1) {

        }
        return 0;
    }

}

