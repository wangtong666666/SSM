
package ky.service.Impl;

import ky.entity.PmsPersonInfo;
import ky.dao.PmsPersonInfoDao;
import ky.service.PmsPersonInfoService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 生成service类
 * @ClassName: PmsPersonInfoServiceImpl
 * @Description:
 * @date 2018-03-22 上午 11:47:27
 * ******************************************************
 */
@Service
public class PmsPersonInfoServiceImpl extends BaseServiceImpl implements PmsPersonInfoService {

    @Resource
    private PmsPersonInfoDao pmspersoninfoDao;

    public PageView selectPage(PageView pageView) {
        return pmspersoninfoDao.getPageView(pageView);
    }

    public List<PmsPersonInfo> selectList(PmsPersonInfo obj) {
        return pmspersoninfoDao.selectList(obj);
    }

    public int update(PmsPersonInfo obj) {
        int param = 1;
        int param1 = pmspersoninfoDao.update(obj);
        if (param1 < 1) param = param1;
        return param;
    }

    public int save(PmsPersonInfo obj) {
        int param = 1;
        int param1 = pmspersoninfoDao.save(obj);
        if (param1 < 1) param = param1;
        return param;
    }

    public int delete(String idArray) {
        int param = 1;
        String[] id_Array = idArray.split("-");
        if (id_Array.length > 1) {
            for (int i = 0; i < id_Array.length; i++) {
                int param1 = pmspersoninfoDao.delete(Integer.parseInt(id_Array[i]));
                if (param1 < 1) param = param1;
            }
        } else {
            int param1 = pmspersoninfoDao.delete(Integer.parseInt(idArray));
            if (param1 < 1) param = param1;
        }
        return param;
    }

}

