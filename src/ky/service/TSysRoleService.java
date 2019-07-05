
package ky.service;

import ky.entity.TSysRole;

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
 * @ClassName: TSysRoleService
 * @Description: null
 * @date 2014-06-10 下午 06:52:43
 * ******************************************************
 */
@Service
public interface TSysRoleService {

    public PageView selectPage(PageView pageView);

    public List<TSysRole> selectList(TSysRole obj);

    public int save(TSysRole obj);

    public int delete(String idArray);

    public int update(TSysRole obj);

    //查询某个角色信息
    public String selectRoleName(TSysRole role);

}

