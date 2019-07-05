
package ky.service.Impl;

import ky.entity.TSysMenu;
import ky.entity.TSysUser;
import ky.entity.TSysUserMenu;
import ky.dao.TSysMenuDao;
import ky.dao.TSysUserMenuDao;
import ky.service.TSysMenuService;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;

import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 生成service类
 * @ClassName: TSysMenuServiceImpl
 * @Description: null
 * @date 2014-06-09 下午 09:02:34
 * ******************************************************
 */
@Service
public class TSysMenuServiceImpl extends BaseServiceImpl implements TSysMenuService {

    @Resource
    private TSysMenuDao tsysmenuDao;
    @Resource
    private TSysUserMenuDao tSysUserMenuDao;
    @Resource
    private TSysMenuDao tSysMenuDao;

    @Resource
    private TSysUserMenuDao TSysUserMenuDao;

    public List selectPage(String idArray) {
        List list = new ArrayList();

        TSysMenu obj = selectList(idArray, null, null).get(0);//单条菜单信息
        List twoList = tsysmenuDao.menuLevelGroup();//数据库中所有的菜单级别
        List threeList = new ArrayList();//所有的父级菜单

        for (int i = 0; i < twoList.size() - 1; i++) {

            List childThreeList = new ArrayList();
            List<TSysMenu> objList = selectList(null, twoList.get(i).toString(), null);
            for (int z = 0; z < objList.size(); z++) {
                childThreeList.add(objList.get(z));
            }
            threeList.add(childThreeList);
        }
        list.add(obj);
        list.add(twoList);
        list.add(threeList);

        return list;
    }

    public List<TSysMenu> selectList(String idArray, String menuLevel, String parentId) {
        TSysMenu obj = new TSysMenu();
        if (idArray != null && idArray != "") {
            obj.setMenuId(Integer.parseInt(idArray));
        }
        if (menuLevel != null && menuLevel != "") {
            obj.setMenuLevel(Integer.parseInt(menuLevel));
        }
        if (parentId != null && parentId != "") {
            obj.setParentid(Integer.parseInt(parentId));
        }
        List<TSysMenu> list = tsysmenuDao.selectList(obj);
        return list;
    }

    public int update(TSysMenu obj) {
        int param = 1;
        int param1 = tsysmenuDao.update(obj);
        if (param1 < 1) param = param1;
        return param;
    }

    public int save(TSysMenu obj, String parentId1, String parentId2) {

        TSysMenu tm = new TSysMenu();

        int param = 1;
        obj.setPlatformId(4);//总后台
        if (obj.getMenuLevel() == 1) {
            obj.setParentid(0);
            tm.setParentid(0);
        } else if (obj.getMenuLevel() == 2) {
            obj.setParentid(Integer.parseInt(parentId1));
            tm.setParentid(Integer.parseInt(parentId1));
        } else if (obj.getMenuLevel() == 3) {
            obj.setParentid(Integer.parseInt(parentId2));
            tm.setParentid(Integer.parseInt(parentId2));
        }
        int param1 = tsysmenuDao.save(obj);
        tm.setMenuHref(obj.getMenuHref());
        tm.setMenuName(obj.getMenuName());
        TSysMenu tmenu = tsysmenuDao.selectList(tm).get(0);

        TSysUserMenu um = new TSysUserMenu();
        um.setMenuId(tmenu.getMenuId());
        um.setUserId(1);
        TSysUserMenuDao.save(um);

        if (param1 < 1) {
            param = param1;
        }

        return param;
    }

    public int delete(String idArray) {
        int param = 1;
        String[] id_Array = idArray.split("-");
        if (id_Array.length > 1) {
            for (int i = 0; i < id_Array.length; i++) {
                TSysUserMenuDao.deletemenu_id(Integer.parseInt(id_Array[i]));
                int param1 = tsysmenuDao.delete(Integer.parseInt(id_Array[i]));
                if (param1 < 1) param = param1;
            }
        } else {
            TSysUserMenuDao.deletemenu_id(Integer.parseInt(idArray));
            int param1 = tsysmenuDao.delete(Integer.parseInt(idArray));
            if (param1 < 1) param = param1;
        }
        return param;
    }

    // 获取后台一级菜单
    public List<TSysMenu> OneTree() {
        TSysMenu menu = new TSysMenu();
        menu.setMenuLevel(1);//一級菜單
        menu.setPlatformId(4);//总后台
        return tsysmenuDao.selectList(menu);
    }

    // 获取后台一级菜单2
    public List<TSysMenu> OneTree2() {
        TSysMenu menu = new TSysMenu();
        menu.setLoginName(((TSysUser) (ActionContext.getContext().getSession()).get("user")).getLoginName());
        menu.setMenuLevel(1);//一級菜單
        menu.setPlatformId(4);//平台2
        return tsysmenuDao.selectList(menu);
    }

    // 获取后台子集菜单(一级以下)
    public List childMenu(String id) {
        List list = new ArrayList();

        // 二级菜单
        TSysMenu twojm = new TSysMenu();
        twojm.setLoginName(((TSysUser) (ActionContext.getContext().getSession()).get("user")).getLoginName());
        twojm.setParentid(Integer.parseInt(id));
        //--------------------------------
        List<TSysMenu> twoMenu = tsysmenuDao.selectList(twojm);
        if (twoMenu.size() > 0) {
            for (int i = 0; i < twoMenu.size(); i++) {
                Map twoMap = new HashMap();
                twoMap.put("id", twoMenu.get(i).getMenuId());
                twoMap.put("text", twoMenu.get(i).getMenuName());

                // 三级菜单
                TSysMenu threejm = new TSysMenu();
                threejm.setLoginName(((TSysUser) (ActionContext.getContext().getSession()).get("user")).getLoginName());
                threejm.setParentid(twoMenu.get(i).getMenuId());

                List<TSysMenu> threeMenu = tsysmenuDao.selectList(threejm);
                List threeList = new ArrayList();
                if (threeMenu.size() > 0) {
                    for (int z = 0; z < threeMenu.size(); z++) {
                        Map threeMap = new HashMap();
                        threeMap.put("id", threeMenu.get(z).getMenuId());
                        threeMap.put("text", threeMenu.get(z).getMenuName());
                        threeList.add(threeMap);

                        // 四级菜单
                        TSysMenu fourjm = new TSysMenu();
                        fourjm.setLoginName(((TSysUser) (ActionContext.getContext().getSession()).get("user")).getLoginName());
                        fourjm.setParentid(threeMenu.get(z).getMenuId());

                        List<TSysMenu> fourMenu = tsysmenuDao.selectList(
                                fourjm);
                        List fourList = new ArrayList();
                        if (fourMenu.size() > 0) {
                            for (int k = 0; k < fourMenu.size(); k++) {
                                Map fourMap = new HashMap();
                                fourMap.put("id", fourMenu.get(k).getMenuId());
                                fourMap.put("text", fourMenu.get(k)
                                        .getMenuName());
                                fourList.add(fourMap);
                            }
                        }
                        threeMap.put("children", fourList);

                    }
                }
                twoMap.put("children", threeList);
                list.add(twoMap);
            }
        }
        // 返回二,三级菜单的集合
        return list;
    }

    // 获取后台全部的菜单集合,以树状结构显示
    public List allMenu() {
        List list = new ArrayList();

        // 获取一级菜单
        List<TSysMenu> oneList = OneTree();
        for (int i = 0; i < oneList.size(); i++) {
            TSysMenu jm = oneList.get(i);
            Map oneMap = new HashMap();
            oneMap.put("id", jm.getMenuId());
            oneMap.put("text", jm.getMenuName());
            oneMap.put("children", childMenu(jm.getMenuId() + ""));
            oneMap.put("state", "closed");
            list.add(oneMap);
        }
        return list;
    }

    public List<TSysMenu> getMenus(TSysMenu obj) {
        List<TSysMenu> list = tsysmenuDao.selectList(obj);
        return list;
    }

    /**
     * 获得全部菜单1
     */
    @SuppressWarnings("unchecked")
    public List allMenu1(List<TSysMenu> mlist) {
        List list = new ArrayList();

        // 获取一级菜单
        List<TSysMenu> oneList = OneTree();
        for (int i = 0; i < oneList.size(); i++) {
            TSysMenu jm = oneList.get(i);
            Map oneMap = new HashMap();
            oneMap.put("id", jm.getMenuId());
            oneMap.put("text", jm.getMenuName());
            oneMap.put("state", "closed");
            oneMap.put("children", childMenu1(jm.getMenuId() + "", mlist));
            list.add(oneMap);
        }
        return list;
    }

    /**
     * 获得全部菜单2
     */
    @SuppressWarnings("unchecked")
    public List allMenu2(List<TSysMenu> mlist) {
        List list = new ArrayList();

        // 获取一级菜单
        List<TSysMenu> oneList = OneTree2();
        for (int i = 0; i < oneList.size(); i++) {
            TSysMenu jm = oneList.get(i);
            Map oneMap = new HashMap();
            oneMap.put("id", jm.getMenuId());
            oneMap.put("text", jm.getMenuName());
            oneMap.put("state", "closed");
            oneMap.put("children", childMenu1(jm.getMenuId() + "", mlist));
            list.add(oneMap);
        }
        return list;
    }

    public List<TSysMenu> OneTree1() {
        TSysMenu menu = new TSysMenu();
        menu.setLoginName(((TSysUser) (ActionContext.getContext().getSession()).get("user")).getLoginName());
        menu.setMenuLevel(1);//一級菜單
        return tsysmenuDao.selectList(menu);
    }

    // 获取后台子集菜单(一级以下)
    @SuppressWarnings("unchecked")
    public List childMenu1(String id, List<TSysMenu> mlist) {
        List list = new ArrayList();

        // 二级菜单
        TSysMenu twojm = new TSysMenu();
        twojm.setLoginName(((TSysUser) (ActionContext.getContext().getSession()).get("user")).getLoginName());
        twojm.setParentid(Integer.parseInt(id));
        //--------------------------------
        List<TSysMenu> twoMenu = tsysmenuDao.selectList(twojm);
        if (twoMenu.size() > 0) {
            for (int i = 0; i < twoMenu.size(); i++) {
                Map twoMap = new HashMap();
                twoMap.put("id", twoMenu.get(i).getMenuId());
                twoMap.put("text", twoMenu.get(i).getMenuName());
                for (int j = 0; j < mlist.size(); j++) {
                    if (mlist.get(j).getMenuId().equals(twoMenu.get(i).getMenuId())) {
                        twoMap.put("checked", "true");
                    }
                }

                // 三级菜单
                TSysMenu threejm = new TSysMenu();
                threejm.setLoginName(((TSysUser) (ActionContext.getContext().getSession()).get("user")).getLoginName());
                threejm.setParentid(twoMenu.get(i).getMenuId());

                List<TSysMenu> threeMenu = tsysmenuDao.selectList(threejm);
                List threeList = new ArrayList();
                if (threeMenu.size() > 0) {
                    for (int z = 0; z < threeMenu.size(); z++) {
                        Map threeMap = new HashMap();
                        threeMap.put("id", threeMenu.get(z).getMenuId());
                        threeMap.put("text", threeMenu.get(z).getMenuName());
                        for (int j = 0; j < mlist.size(); j++) {
                            if (mlist.get(j).getMenuId().equals(threeMenu.get(z).getMenuId())) {
                                threeMap.put("checked", "true");
                            }
                        }
                        threeList.add(threeMap);

                        // 四级菜单
                        TSysMenu fourjm = new TSysMenu();
                        fourjm.setLoginName(((TSysUser) (ActionContext.getContext().getSession()).get("user")).getLoginName());
                        fourjm.setParentid(threeMenu.get(z).getMenuId());

                        List<TSysMenu> fourMenu = tsysmenuDao.selectList(
                                fourjm);
                        List fourList = new ArrayList();
                        if (fourMenu.size() > 0) {
                            for (int k = 0; k < fourMenu.size(); k++) {
                                Map fourMap = new HashMap();
                                fourMap.put("id", fourMenu.get(k).getMenuId());
                                fourMap.put("text", fourMenu.get(k)
                                        .getMenuName());
                                for (int j = 0; j < mlist.size(); j++) {
                                    if (mlist.get(j).getMenuId().equals(fourMenu.get(k).getMenuId())) {
                                        fourMap.put("checked", "true");
                                    }
                                }
                                fourList.add(fourMap);


                            }
                        }
                        threeMap.put("children", fourList);

                    }
                }
                twoMap.put("children", threeList);
                list.add(twoMap);
            }
        }
        // 返回二,三级菜单的集合
        return list;
    }


    /**
     * @param id 一级菜单id，uid   登录用户的id
     * @author Administrator
     */
    //获取该用户可以访问的所有后台子集菜单(一级以下)------------------ttttt--------------------------------
    public List getchildMenu(String id, int uid) {
        //查询用户的所有菜单，包括所有级别
        List list = new ArrayList();

        TSysUserMenu tum = new TSysUserMenu();
        tum.setUserId(uid);
        List<TSysUserMenu> tumList = tSysUserMenuDao.selectList(tum);    //根据用户的ID查询用户菜单关联表

        //查询所有级别的二级菜单
        for (int i = 0; i < tumList.size(); i++) {
            //获取用户菜单表的      菜单id
            int menuid = tumList.get(i).getMenuId();
            TSysMenu m = new TSysMenu();            //菜单表
            m.setMenuId(menuid);                //获取到的id
            m.setMenuLevel(2);                    //二级菜单
            m.setParentid(Integer.parseInt(id));   //一级菜单id

            //拿到二级菜单，循环查三级
            List twoList = new ArrayList();
            List<TSysMenu> tmlist = tSysMenuDao.selectList(m);        //根据条件查询 所有菜单
            for (int z = 0; z < tmlist.size(); z++) {

                Map twoMap = new HashMap();
                twoMap.put("id", tmlist.get(z).getMenuId());
                twoMap.put("text", tmlist.get(z).getMenuName());
                twoList.add(twoMap);

                TSysMenu threejm = new TSysMenu();
                threejm.setMenuLevel(3);            //查询三级菜单
                //=============
//					if(i+1<=tumList.size()){
//						menuid = tumList.get(i+1).getMenuId();
//					}
//					threejm.setMenuId(menuid);

                threejm.setParentid(tmlist.get(z).getMenuId());        //父菜单id

                //拿到三级菜单   循环查 4级
                List<TSysMenu> threeMenu = new ArrayList<TSysMenu>();
                threeMenu = tsysmenuDao.selectList(threejm);
                List threeList = new ArrayList();
                for (int k = 0; k < threeMenu.size(); k++) {

                    Map threeMap = new HashMap();
                    threeMap.put("id", threeMenu.get(k).getMenuId());
                    threeMap.put("text", threeMenu.get(k).getMenuName());
                    threeList.add(threeMap);

                    TSysMenu fourjm = new TSysMenu();
                    fourjm.setMenuLevel(4);                    //查询四级菜单
//							//==================
//							if(i+2<=tumList.size()){
//								menuid = tumList.get(i+2).getMenuId();
//							}
//							fourjm.setMenuId(menuid);

                    fourjm.setParentid(threeMenu.get(k).getMenuId());
                    List<TSysMenu> fourMenu = tsysmenuDao.selectList(fourjm);
                    List fourList = new ArrayList();
                    for (int y = 0; y < fourMenu.size(); y++) {
                        Map fourMap = new HashMap();
                        fourMap.put("id", fourMenu.get(y).getMenuId());
                        fourMap.put("text", fourMenu.get(y).getMenuName());
                        fourList.add(fourMap);
                    }
                    threeMap.put("children", fourList);
                }
                twoMap.put("children", threeList);
                list.add(twoMap);

            }

        }
        return list;
    }

}