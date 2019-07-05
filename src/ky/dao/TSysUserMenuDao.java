
package ky.dao;

import ky.entity.TSysUserMenu;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 用wzl写的自动生成dao包
 * @ClassName: TSysUserMenuDao
 * @Description: 用户菜单关联表
 * @date 2014-06-17 下午 04:27:26
 * ******************************************************
 */
@Repository
public interface TSysUserMenuDao extends BaseDao<TSysUserMenu> {
    public int deletemenu_id(int menuId);
}

