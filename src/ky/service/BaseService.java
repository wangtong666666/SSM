package ky.service;

import java.io.IOException;

public interface BaseService {
    // 添加数据的模板日志
    public void saveLog(String menu, String tableName, String operateType, String dataInfo);

    //获取 添加，修改删除的参数
    public String getDateInfo(Object obj);

    //获取修改 添加 删除时的返回值
    public int returnParam(int param);

}
