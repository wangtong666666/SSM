
package ky.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ky.entity.TSysRole;
import ky.entity.TSysRoleMenu;
import ky.entity.TSysUser;
import ky.entity.TSysUserMenu;
import ky.dao.TSysRoleDao;
import ky.dao.TSysRoleMenuDao;
import ky.dao.TSysUserDao;
import ky.dao.TSysUserMenuDao;
import ky.service.TSysUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;

import ky.util.DateFormat;
import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 生成service类
 * @ClassName: TSysUserServiceImpl
 * @Description: 用户表
 * @date 2014-06-09 下午 06:42:03
 * ******************************************************
 */
@Service
public class TSysUserServiceImpl extends BaseServiceImpl implements TSysUserService {

    @Resource
    private TSysUserDao tsysuserDao;
    @Resource
    private TSysUserMenuDao tsysusermenuDao;
    @Resource
    private TSysRoleDao tsysroleDao;

    @Resource
    private TSysRoleMenuDao tsysRoleMenuDao;

    public PageView selectPage(PageView pageView) {
        try {
            PageView pv = tsysuserDao.getPageView(pageView);
            return pv;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<TSysUser> selectList(TSysUser obj) {
        List<TSysUser> list = tsysuserDao.selectList(obj);
        return list;
    }

    public int update(TSysUser obj) {
        int param = 1;
        int roleId = obj.getRoleId();
        int userId = obj.getId();
        tsysusermenuDao.delete(userId);
        TSysRoleMenu rolemenu = new TSysRoleMenu();
        rolemenu.setRoleId(roleId);
        List<TSysRoleMenu> list = tsysRoleMenuDao.selectList(rolemenu);
        for (int i = 0; i < list.size(); i++) {
            TSysUserMenu usermenu = new TSysUserMenu();
            usermenu.setUserId(userId);
            usermenu.setMenuId(list.get(i).getMenuId());
            int param1 = tsysusermenuDao.save(usermenu);
        }
        tsysuserDao.update(obj);
        return param;

    }

    public int updatePwe(TSysUser obj) {
        return tsysuserDao.update1(obj);

    }

    public int save(TSysUser obj) {
        int param = 1;
        tsysuserDao.save(obj);
        int roleId = obj.getRoleId();
        int userId = obj.getId() + 1;
        TSysRoleMenu rolemenu = new TSysRoleMenu();
        rolemenu.setRoleId(roleId);
        TSysUserMenu usermenu = new TSysUserMenu();
        List<TSysRoleMenu> list = tsysRoleMenuDao.selectList(rolemenu);
        for (int i = 0; i < list.size(); i++) {
            usermenu.setUserId(userId);
            usermenu.setMenuId(list.get(i).getMenuId());
            param = tsysusermenuDao.save(usermenu);
        }
        return param;
    }

    public int delete(String idArray) {
        int param = 1;
        String[] id_Array = idArray.split("-");
        for (int i = 0; i < id_Array.length; i++) {
            int shu = tsysusermenuDao.delete(Integer.parseInt(id_Array[i]));
            int param1 = tsysuserDao.delete(Integer.parseInt(id_Array[i]));
            if (param1 < 1) param = param1;
        }
        return param;
    }

    public int deletep(String idArray) {
        int param = 1;
        String[] id_Array = idArray.split("-");
        for (int i = 0; i < id_Array.length; i++) {

            int param1 = tsysuserDao.deletep(id_Array[i]);

            if (param1 < 1) param = param1;
        }
        return param;
    }

    //查询在线用户的信息
    public List<Map<String, String>> selectOnlineUser(Map<String, HttpSession> map, String path) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            HttpSession se = map.get(it.next());
            String p = (String) se.getAttribute("loginpath");
            if (path != null && !path.equals(p)) {
                continue;
            }
            Object obj = se.getAttribute("user");
            if (obj == null) {
                continue;//当用户未登录时，跳过
            }
            TSysUser user = (TSysUser) obj;
            Map<String, String> m = new HashMap<String, String>();
            m.put("loginName", user.getLoginName());
            m.put("trueName", user.getTrueName());
            TSysRole role = new TSysRole();
            role.setRoleId(user.getRoleId());
            List<TSysRole> roleList = tsysroleDao.selectList(role);
            if (roleList.size() == 1) {
                role = roleList.get(0);
            }
            String roleName = role.getRoleName();
//				m.put("roleName", roleName);
//				if(user.getDepartmentId()!=null){
////					PmsDepartment dep=new PmsDepartment();
////					dep.setDepartmentNum(user.getDepartmentId());
////					List<PmsDepartment> depList = pmsdepartmentDao.selectList(dep);
////					if(depList.size()==1){
////						dep=depList.get(0);
////					}
////					m.put("depName", dep.getDepartmentName());
////					m.put("agentName", "");//当有部门id存在时，必定属于总后台或总服务商，所属服务商为空
////					if(dep.getOemNumber().equals("1")){
////						m.put("oemName", "快易总部");
////					}else{
////						PmsAgentInfo pai=new PmsAgentInfo();
////						pai.setAgentNumber(dep.getOemNumber());
////						List<PmsAgentInfo> oemList = pmsAgentInfoDao.selectList(pai);
////						if(oemList.size()==1){
////							pai=oemList.get(0);
////						}
////						m.put("oemName", pai.getAgentName());
////					}
//				}else {
//					m.put("depName", "");
//					if(roleName.equals("销售员")){
//						PospSellerInfo psi=new PospSellerInfo();
//						psi.setSellerNo(user.getLoginName());
//						List<PospSellerInfo> sellerList = pospSellerInfoDao.selectList(psi);
//						if(sellerList.size()==1){
//							psi=sellerList.get(0);
//							String agentNumber = psi.getAgentNumber();
//							PmsAgentInfo pai=new PmsAgentInfo();
//							pai.setAgentNumber(agentNumber);
//							List<PmsAgentInfo> agentList = pmsAgentInfoDao.selectList(pai);
//							if(agentList.size()==1){
//								pai=agentList.get(0);
//							}
//							m.put("agentName", pai.getAgentName());
//							pai=new PmsAgentInfo();
//							pai.setAgentNumber(agentNumber.substring(0,6));
//							agentList = pmsAgentInfoDao.selectList(pai);
//							if(agentList.size()==1){
//								pai=agentList.get(0);
//							}
//							m.put("oemName", pai.getAgentName());
//						}
//					}else if(roleName.equals("一级服务商")||roleName.equals("二级服务商")||roleName.equals("三级服务商")){
//						String agentNumber = user.getLoginName();
//						PmsAgentInfo pai=new PmsAgentInfo();
//						pai.setAgentNumber(agentNumber);
//						List<PmsAgentInfo> agentList = pmsAgentInfoDao.selectList(pai);
//						if(agentList.size()==1){
//							pai=agentList.get(0);
//						}
//						m.put("agentName", pai.getAgentName());
//						pai=new PmsAgentInfo();
//						pai.setAgentNumber(agentNumber.substring(0,6));
//						agentList = pmsAgentInfoDao.selectList(pai);
//						if(agentList.size()==1){
//							pai=agentList.get(0);
//						}
//						m.put("oemName", pai.getAgentName());
//					}
//					
//				}
            m.put("loginTime", DateFormat.getString(new Date(se.getCreationTime())));
            list.add(m);
        }
        return list;
    }
}

