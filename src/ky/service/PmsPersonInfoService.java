
package ky.service;

import ky.entity.PmsPersonInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 生成service类
 * @ClassName: PmsPersonInfoService
 * @Description:
 * @date 2018-03-22 上午 11:47:27
 * ******************************************************
 */
@Service
public interface PmsPersonInfoService {

    public PageView selectPage(PageView pageView);

    public List<PmsPersonInfo> selectList(PmsPersonInfo obj);

    public int save(PmsPersonInfo obj);

    public int delete(String idArray);

    public int update(PmsPersonInfo obj);

}

