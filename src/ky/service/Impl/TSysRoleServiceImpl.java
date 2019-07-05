
package ky.service.Impl;

import ky.entity.TSysRole;
import ky.dao.TSysRoleDao;
import ky.service.TSysRoleService;

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
 * @ClassName: TSysRoleServiceImpl
 * @Description: null
 * @date 2014-06-10 下午 06:52:43
 * ******************************************************
 */
@Service
public class TSysRoleServiceImpl extends BaseServiceImpl implements TSysRoleService {

    @Resource
    private TSysRoleDao tsysroleDao;

    public PageView selectPage(PageView pageView) {
        return tsysroleDao.getPageView(pageView);
    }

    public List<TSysRole> selectList(TSysRole obj) {
        return tsysroleDao.selectList(obj);
    }

    public int update(TSysRole obj) {
        int param = 1;
        int param1 = tsysroleDao.update(obj);
        if (param1 < 1) param = param1;
        return param;
    }

    public int save(TSysRole obj) {
        int param = 1;
        int param1 = tsysroleDao.save(obj);
        if (param1 < 1) param = param1;
        return param;
    }

    public int delete(String idArray) {
        int param = 1;
        String[] id_Array = idArray.split("-");
        if (id_Array.length > 1) {
            for (int i = 0; i < id_Array.length; i++) {
                int param1 = tsysroleDao.delete(Integer.parseInt(id_Array[i]));
                if (param1 < 1) param = param1;
            }
        } else {
            int param1 = tsysroleDao.delete(Integer.parseInt(idArray));
            if (param1 < 1) param = param1;
        }
        return param;
    }
    //查询某个角色信息

    public String selectRoleName(TSysRole role) {
        String roleName = "";
        List<TSysRole> trole = tsysroleDao.selectList(role);
        if (trole.size() > 0) {
            roleName = trole.get(0).getRoleName();
        }
        return roleName;
    }

}
