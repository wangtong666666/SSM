
package ky.dao.Impl;

import ky.dao.TSysUserMenuDao;
import ky.entity.TSysUserMenu;
import ky.xml.TSysUserMenuMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ********************************************************
 *
 * @author 用wzl写的自动生成daoImpl包
 * @ClassName: TSysUserMenuDaoImpl
 * @Description: 用户菜单关联表
 * @date 2014-06-17 下午 04:27:26
 * ******************************************************
 */
@Repository
public class TSysUserMenuDaoImpl extends BaseDaoImpl<TSysUserMenu> implements TSysUserMenuDao {

    @Autowired
    private TSysUserMenuMapper umMapper;

    public int deletemenu_id(int menuId) {
        return umMapper.deletemenu_id(menuId);
    }
}

