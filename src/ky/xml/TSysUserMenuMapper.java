
package ky.xml;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ky.entity.TSysUserMenu;
import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 数据查询接口
 * @ClassName: TSysUserMenuMapper
 * @Description: 用户菜单关联表
 * @date 2014-06-17 下午 04:27:25
 * ******************************************************
 */
public interface TSysUserMenuMapper extends BaseMapper<TSysUserMenu> {

    public int deletemenu_id(int menuId);

}

