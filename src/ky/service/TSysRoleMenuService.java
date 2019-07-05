
package ky.service;

import ky.entity.TSysRoleMenu;

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
 * @ClassName: TSysRoleMenuService
 * @Description: 角色菜单关联表
 * @date 2014-06-16 下午 07:42:07
 * ******************************************************
 */
@Service
public interface TSysRoleMenuService {

    public PageView selectPage(PageView pageView);

    public List<TSysRoleMenu> selectList(TSysRoleMenu obj);

    public int save(TSysRoleMenu obj);

    public int delete(String idArray);

    public int update(TSysRoleMenu obj);

    public int delete_add(String idArray, String roleId);

}

