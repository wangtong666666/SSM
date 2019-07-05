
package ky.service;

import ky.entity.TSysMenu;
import ky.entity.TSysUserMenu;

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
 * @ClassName: TSysUserMenuService
 * @Description: 用户菜单关联表
 * @date 2014-06-12 下午 02:53:55
 * ******************************************************
 */
@Service
public interface TSysUserMenuService {

    public PageView selectPage(PageView pageView);

    public List<TSysUserMenu> selectList(TSysUserMenu obj);

    public int save(TSysUserMenu obj);

    public int delete(String idArray);

    public int update(TSysUserMenu obj);

    // 根据登录用户ID获取后台一级菜单
    public List<TSysMenu> OneTree(int uid);

    public int delete_add(String idArray, String userId);

    public List selectUserMenu(int userId);

    public List selectUserMenuOudan(int userId, String loanType, String loanYN);

}

