package ky.service.Impl;

import ky.entity.TSysMenu;
import ky.entity.TSysUser;
import ky.entity.TSysUserMenu;
import ky.dao.TSysMenuDao;
import ky.dao.TSysUserDao;
import ky.dao.TSysUserMenuDao;
import ky.service.TSysUserMenuService;
import ky.service.TSysUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;

import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 生成service类
 * @ClassName: TSysUserMenuServiceImpl
 * @Description: 用户菜单关联表
 * @date 2014-06-12 下午 02:53:55
 * ******************************************************
 */
@Service
public class TSysUserMenuServiceImpl extends BaseServiceImpl implements
        TSysUserMenuService {

    @Resource
    private TSysUserMenuDao tsysusermenuDao;
    @Resource
    private TSysMenuDao tsysmenuDao;
    @Resource
    private TSysUserDao tSysUserDao;

    public PageView selectPage(PageView pageView) {
        return tsysusermenuDao.getPageView(pageView);
    }

    public List<TSysUserMenu> selectList(TSysUserMenu obj) {
        return tsysusermenuDao.selectList(obj);
    }

    public int update(TSysUserMenu obj) {
        int param = 1;
        int param1 = tsysusermenuDao.update(obj);
        if (param1 < 1)
            param = param1;
        return param;
    }

    public int save(TSysUserMenu obj) {
        int param = 1;
        int param1 = tsysusermenuDao.save(obj);
        if (param1 < 1)
            param = param1;
        return param;
    }

    public int delete(String idArray) {
        int param = 1;
        String[] id_Array = idArray.split("-");
        if (id_Array.length > 1) {
            for (int i = 0; i < id_Array.length; i++) {
                int param1 = tsysusermenuDao.delete(Integer
                        .parseInt(id_Array[i]));
                if (param1 < 1)
                    param = param1;
            }
        } else {
            int param1 = tsysusermenuDao.delete(Integer.parseInt(idArray));
            if (param1 < 1)
                param = param1;
        }
        return param;
    }

    // 删除usermenu关联表
    public int delete_add(String idArray, String userId) {
        int param = 1;
        String[] id_Array = idArray.split("-");
        TSysUser user = new TSysUser();


        user.setId(Integer.parseInt(userId));
        int role = tSysUserDao.selectList(user).get(0).getRoleId();
        param = tsysusermenuDao.delete(Integer.parseInt(userId));
        if (idArray.length() > 1) {
            TSysUserMenu tum = new TSysUserMenu();
            tum.setUserId(Integer.parseInt(userId));
            for (int i = 0; i < id_Array.length; i++) {
                int ida = Integer.parseInt(id_Array[i]);
                tum.setMenuId(ida);
                param += tsysusermenuDao.save(tum);
                if (param < 1) {
                    break;
                }
            }
        }
        return param;
    }

    // 根据登录用户ID获取后台一级菜单
    public List<TSysMenu> OneTree(int uid) {
        List<TSysMenu> newmlist = new ArrayList<TSysMenu>();// 备用集合，存储符合条件的菜单
        newmlist.clear();// 清空集合
        TSysUserMenu sum = new TSysUserMenu();
        sum.setUserId(uid);
        List<TSysUserMenu> tumlist = tsysusermenuDao.selectList(sum);// 获得关联表菜单集合
        TSysMenu menu = new TSysMenu();
        menu.setMenuLevel(1);// 一級菜單
        menu.setPlatformId(4);// 一级平台（总后台）
        List<TSysMenu> mlist = tsysmenuDao.selectList(menu);// 获得一级菜单集合
        for (int i = 0; i < mlist.size(); i++) {// 遍历一级菜单集合
            for (int j = 0; j < tumlist.size(); j++) {// 遍历该用户可访问的菜单集合
                if (tumlist.get(j).getMenuId().equals(mlist.get(i).getMenuId())) {
                    newmlist.add(mlist.get(i));
                }
            }
        }
        return newmlist;
    }

    //得到userMenu
    public List selectUserMenu(int userId) {
        TSysUserMenu um = new TSysUserMenu();
        um.setUserId(userId);
        List<TSysUserMenu> ums = tsysusermenuDao.selectList(um);
        TSysMenu menu = new TSysMenu();
        List<TSysMenu> menus = tsysmenuDao.selectList(menu);
        List myMenu = findChildMenus(0, ums, menus, new ArrayList());

        return myMenu;
    }

    public List findChildMenus(int parentId, List<TSysUserMenu> ums, List<TSysMenu> menus, List myMenu) {
        for (int i = 0; i < menus.size(); i++) {
            TSysMenu m = menus.get(i);
            if (parentId == m.getParentid().intValue()) {
                for (int j = 0; j < ums.size(); j++) {
                    if (m.getMenuId().intValue() == ums.get(j).getMenuId().intValue()) {
                        Map map = new HashMap();
                        map.put("id", m.getMenuId().toString());
                        map.put("text", m.getMenuName());
                        map.put("attributes", m.getMenuHref());
                        List l = new ArrayList();
                        map.put("children", findChildMenus(m.getMenuId().intValue(), ums, menus, l));
                        myMenu.add(map);
                    }
                }
            }
        }

        return myMenu;
    }

    //得到审件权限，附加申件功能，T0垫资功能
    public List selectUserMenuOudan(int userId, String loanType, String loanYN) {
        TSysUserMenu um = new TSysUserMenu();
        um.setUserId(userId);
        List<TSysUserMenu> ums = tsysusermenuDao.selectList(um);
        // 服务商垫资 开放审件权限
        if ("1".equals(loanYN) && "1".equals(loanType)) {
            TSysMenu tums = new TSysMenu();
            tums.setPlatformId(5);
            tums.setParam("1");
            List<TSysMenu> umlist = this.tsysmenuDao.selectList(tums);
            TSysMenu tumss = new TSysMenu();
            tumss.setPlatformId(6);
            tumss.setParam("1");
            List<TSysMenu> umlists = this.tsysmenuDao.selectList(tumss);
            umlist.addAll(umlists);
            if (umlist.size() > 0) {
                for (int n = 0; n < umlist.size(); n++) {
                    TSysUserMenu tsum = new TSysUserMenu();
                    tsum.setMenuId(umlist.get(n).getMenuId());
                    tsum.setUserId(userId);
                    ums.add(tsum);
                }
            }
        } else if ("1".equals(loanYN) && "2".equals(loanType)) {// 审件权限未开放
            TSysMenu tums = new TSysMenu();
            tums.setPlatformId(5);
            tums.setParam("1");
            List<TSysMenu> umlist = this.tsysmenuDao.selectList(tums);
            if (umlist.size() > 0) {
                for (int n = 0; n < umlist.size(); n++) {
                    TSysUserMenu tsum = new TSysUserMenu();
                    tsum.setMenuId(umlist.get(n).getMenuId());
                    tsum.setUserId(userId);
                    ums.add(tsum);
                }
            }
        } else if ("2".equals(loanYN) && "1".equals(loanType)) {// 系统垫资 审件权限开放
            TSysMenu tums = new TSysMenu();
            tums.setPlatformId(6);
            tums.setParam("1");
            List<TSysMenu> umlist = this.tsysmenuDao.selectList(tums);
            if (umlist.size() > 0) {
                for (int n = 0; n < umlist.size(); n++) {
                    TSysUserMenu tsum = new TSysUserMenu();
                    tsum.setMenuId(umlist.get(n).getMenuId());
                    tsum.setUserId(userId);
                    ums.add(tsum);
                }
            }
        }
        TSysMenu menu = new TSysMenu();
        menu.setParam("1");
        List<TSysMenu> menus = tsysmenuDao.selectList(menu);
        List myMenu =
                findChildMenus(0, ums, menus, new ArrayList());
        return myMenu;
    }
}
