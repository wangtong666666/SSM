package ky.action;

import ky.dao.TSysUserMenuDao;
import ky.entity.TSysMenu;
import ky.entity.TSysRole;
import ky.entity.TSysRoleMenu;
import ky.entity.TSysUser;
import ky.entity.TSysUserMenu;
import ky.service.TSysMenuService;
import ky.service.TSysRoleMenuService;
import ky.service.TSysRoleService;
import ky.service.TSysUserMenuService;
import ky.service.TSysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ky.util.PageView;

import org.apache.commons.collections.map.HashedMap;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

/**
 * ********************************************************
 *
 * @author 生成Action类
 * @ClassName: TSysMenuAction
 * @Description: null
 * @date 2014-06-09 下午 09:02:34
 * ******************************************************
 */

@Controller
@RequestMapping("/tsysmenu")

public class TSysMenuAction extends BaseAction<TSysMenu> {

    @Resource
    private TSysMenuService TSysMenuSer;
    @Resource
    private TSysUserMenuService tsysusermenuSer;
    @Resource
    private TSysUserService tsysUserService;
    @Resource
    private TSysRoleMenuService tsysRoleMemuService;
    @Resource
    private TSysRoleService tsysRoleService;

    //	@Action(value = "selectPage", results = { @Result(name = "selectPage", location = "/pages/TSysMenu/list.jsp") })
//	public String seletePage() {
//
//		String idArray = this.getParameter("idArray");
//		this.jsonArray(TSysMenuSer.selectPage(idArray));
//
//		return "selectPage";
//	}
//
//	@Action(value = "select", results = { @Result(name = "select", location = "/pages/TSysMenu/list.jsp") })
//	public String selete() {
//
//		String idArray = this.getParameter("idArray");
//		String parentId = this.getParameter("parentId");
//		List<TSysMenu> list = TSysMenuSer.selectList(idArray, null, parentId);
//		this.jsonArray(list);
//		return "select";
//	}
//
//	@Action(value = "save", results = { @Result(name = "save", location = "/pages/TSysMenu/list.jsp") })
//	public String save() {
//
//		String parentId1 = this.getParameter("parentId1");
//		String parentId2 = this.getParameter("parentId2");
//		this.returnU_D_S_info(TSysMenuSer.save(model, parentId1, parentId2));
//		return "save";
//	}
//
//	@Action(value = "detele", results = { @Result(name = "detele", location = "/pages/TSysMenu/list.jsp") })
//	public String detele() throws UnsupportedEncodingException {
//
//		String idArray = this.getRequest("idArray");// 获得前台复选框的值
//		this.returnU_D_S_info(TSysMenuSer.delete(idArray));
//		return "detele";
//	}
//
//	// 修改菜单
//	@Action(value = "update", results = { @Result(name = "update", location = "/pages/TSysMenu/list.jsp") })
//	public String update() throws UnsupportedEncodingException {
//		String parentId1 = this.getParameter("parentId1");
//		String parentId2 = this.getParameter("parentId2");
//		if (model.getMenuLevel() == 1) {
//			model.setParentid(0);
//			model.setMenuHref("");
//		} else if (model.getMenuLevel() == 2) {
//			model.setParentid(Integer.parseInt(parentId1));
//		} else if (model.getMenuLevel() == 3) {
//			model.setParentid(Integer.parseInt(parentId2));
//		}
//
//		int param = TSysMenuSer.update(model);
//		this.returnU_D_S_info(param);
//		return null;
//	}
//
//	// 获取后台子集菜单(一级以下)
//	@Action(value = "childMenu", results = { @Result(name = "childMenu", location = "/admincontrol.jsp") })
//	public String childMenu() {
//
//		String id = this.getParameter("id");
//		// 返回二,三级菜单的集合
//		this.jsonArray(TSysMenuSer.childMenu(id));
//
//		return "childMenu";
//	}
//	
//	// 获取后台用户能访问的子集菜单(一级以下)
//	@Action(value = "childMenu1", results = { @Result(name = "childMenu", location = "/admincontrol.jsp") })
//	public String childMenu1() {
//
//		String id = this.getParameter("id");
//
//		HttpSession sess = request.getSession();
//		TSysUser u = (TSysUser) session.getAttribute("user");//获得登录的用户信息
//		
//		
//		// 返回二,三级菜单的集合
//		this.jsonArray(TSysMenuSer.getchildMenu(id,u.getId()));
//
//		return "childMenu";
//	}
//
//
//	// 获取后台全部的菜单集合,以树状结构显示
//	@Action(value = "allMenu", results = { @Result(name = "allMenu", location = "/pages/TSysMenu/list.jsp") })
//	public String allMenu() {
//
//		List list = TSysMenuSer.allMenu();
//		this.jsonArray(list);
//
//		return "allMenu";
//	}
//
    // 获取后台全部的菜单集合，根据用户所拥有的权限默认选中,以树状结构显示
    @RequestMapping("/allMenuById")
//	@Action(value = "allMenuById", results = { @Result(name = "allMenuById", location = "/pages/TSysUser/edit.jsp") })
    public String allMenuById() {
        TSysUserMenu usermenu = new TSysUserMenu();
        List<TSysUserMenu> usermenuList = null;
        List<TSysMenu> mlist = null;
        String userId = this.getParameter("userId"); // 获得员工的id
        usermenu.setUserId(Integer.parseInt(userId));
        usermenuList = tsysusermenuSer.selectList(usermenu); // 根据id查询出用户的


        TSysUser user = new TSysUser();
        user.setId(Integer.parseInt(userId));
        List<TSysUser> users = tsysUserService.selectList(user);


        int roleId = users.get(0).getRoleId();

        if (roleId == 2 || roleId == 3) {
            mlist = new ArrayList<TSysMenu>();
            for (int i = 0; i < usermenuList.size(); i++) {
                int mid = usermenuList.get(i).getMenuId();
                TSysMenu tmenu = new TSysMenu();
                tmenu.setMenuId(mid);
                List list = TSysMenuSer.getMenus(tmenu);
                if (list.size() != 0) {
                    mlist.addAll(list);
                }
            }
            List list = TSysMenuSer.allMenu2(mlist);
            this.jsonArray(list);
            return "allMenuById";
        } else {
            mlist = new ArrayList<TSysMenu>();
            for (int i = 0; i < usermenuList.size(); i++) {
                int mid = usermenuList.get(i).getMenuId();
                TSysMenu tmenu = new TSysMenu();
                tmenu.setMenuId(mid);
                List list = TSysMenuSer.getMenus(tmenu);
                if (list.size() != 0) {
                    mlist.addAll(list);
                }
            }
            List list = TSysMenuSer.allMenu1(mlist);
            this.jsonArray(list);
            return "allMenuById";
        }
    }

//	// 获取后台全部的菜单集合，根据角色所拥有的权限默认选中,以树状结构显示
//	@Action(value = "allMenuByRoleId", results = { @Result(name = "allMenuByRoleId", location = "/pages/TSysRole/edit.jsp") })
//	public String allMenuByRoleId() {
//		TSysRoleMenu rolemenu = new TSysRoleMenu();
//		List<TSysRoleMenu> rolemenuList = null;
//		List<TSysMenu> mlist = null;
//		String roleId = this.getParameter("roleId"); // 获得角色id
//		rolemenu.setRoleId(Integer.parseInt(roleId));
//		rolemenuList = tsysRoleMemuService.selectList(rolemenu); // 根据id查询角色的
//		TSysRole role = new TSysRole();
//		role.setRoleId(Integer.parseInt(roleId));
//		if (roleId.equals("2") || roleId.equals("3")) {				
//			mlist = new ArrayList<TSysMenu>();
//			for (int i = 0; i < rolemenuList.size(); i++) {
//				int mid = rolemenuList.get(i).getMenuId();
//				TSysMenu tmenu = new TSysMenu();
//				tmenu.setMenuId(mid);
//				List list = TSysMenuSer.getMenus(tmenu);
//				if (list.size() != 0) {
//					mlist.addAll(list);
//				}
//			}
//			List list = TSysMenuSer.allMenu2(mlist);
//			this.jsonArray(list);
//			return "allMenuByRoleId";
//		} else {
//			mlist = new ArrayList<TSysMenu>();
//			for (int i = 0; i < rolemenuList.size(); i++) {
//				int mid = rolemenuList.get(i).getMenuId();
//				TSysMenu tmenu = new TSysMenu();
//				tmenu.setMenuId(mid);
//				List list = TSysMenuSer.getMenus(tmenu);
//				if (list.size() != 0) {
//					mlist.addAll(list);
//				}
//				System.out.println(mlist.get(i).getMenuName());
//			}
//			List list = TSysMenuSer.allMenu1(mlist);
//			this.jsonArray(list);
//			return "allMenuByRoleId";
//		}
//	}
}