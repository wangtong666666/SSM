
package ky.service.Impl;

import ky.entity.TSysRoleMenu;
import ky.entity.TSysUserMenu;
import ky.dao.TSysRoleMenuDao;
import ky.service.TSysRoleMenuService;

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
 * @ClassName: TSysRoleMenuServiceImpl
 * @Description: 角色菜单关联表
 * @date 2014-06-16 下午 07:42:07
 * ******************************************************
 */
@Service
public class TSysRoleMenuServiceImpl extends BaseServiceImpl implements TSysRoleMenuService {

    @Resource
    private TSysRoleMenuDao tsysrolemenuDao;

    public PageView selectPage(PageView pageView) {
        return tsysrolemenuDao.getPageView(pageView);
    }

    public List<TSysRoleMenu> selectList(TSysRoleMenu obj) {
        return tsysrolemenuDao.selectList(obj);
    }

    public int update(TSysRoleMenu obj) {
        int param = 1;
        int param1 = tsysrolemenuDao.update(obj);
        if (param1 < 1) param = param1;
        return param;
    }

    public int save(TSysRoleMenu obj) {
        int param = 1;
        int param1 = tsysrolemenuDao.save(obj);
        if (param1 < 1) param = param1;
        return param;
    }

    public int delete(String idArray) {
        int param = 1;
        String[] id_Array = idArray.split("-");
        if (id_Array.length > 1) {
            for (int i = 0; i < id_Array.length; i++) {
                int param1 = tsysrolemenuDao.delete(Integer.parseInt(id_Array[i]));
                if (param1 < 1) param = param1;
            }
        } else {
            int param1 = tsysrolemenuDao.delete(Integer.parseInt(idArray));
            if (param1 < 1) param = param1;
        }
        return param;
    }

    // 删除并更新rolemenu关联表
    public int delete_add(String idArray, String roleId) {
        int param = 1;
        String[] id_Array = idArray.split("-");
        param = tsysrolemenuDao.delete(Integer.parseInt(roleId));
        if (idArray.length() > 1) {
            TSysRoleMenu trm = new TSysRoleMenu();
            trm.setRoleId(Integer.parseInt(roleId));
            for (int i = 0; i < id_Array.length; i++) {
                trm.setMenuId(Integer.parseInt(id_Array[i]));
                param += tsysrolemenuDao.save(trm);
                if (param < 1) {
                    break;
                }
            }
        }
        return param;
    }


}

