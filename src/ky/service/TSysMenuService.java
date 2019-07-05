
package ky.service;

import ky.entity.TSysMenu;

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
 * @ClassName: TSysMenuService
 * @Description: null
 * @date 2014-06-09 下午 09:02:34
 * ******************************************************
 */
@Service
public interface TSysMenuService {

    public List selectPage(String idArray);

    public List<TSysMenu> selectList(String idArray, String menuLevel, String parentId);

    public int save(TSysMenu obj, String parentId1, String parentId2);

    public int delete(String idArray);

    public int update(TSysMenu obj);

    // 获取后台一级菜单
    public List<TSysMenu> OneTree();

    // 获取后台子集菜单(一级以下)
    public List childMenu(String id);

    // 获取后台全部的菜单集合,以树状结构显示
    public List allMenu();

    //查询Menu
    public List<TSysMenu> getMenus(TSysMenu obj);

    // 获取后台全部的菜单集合,以树状结构显示
    public List allMenu1(List<TSysMenu> mlist);

    public List allMenu2(List<TSysMenu> mlist);

    //获取后台子集菜单(一级以下)
    public List getchildMenu(String id, int uid);

}

