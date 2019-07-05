
package ky.xml;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ky.entity.TSysMenu;
import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 数据查询接口
 * @ClassName: TSysMenuMapper
 * @Description: null
 * @date 2014-06-09 下午 09:02:33
 * ******************************************************
 */
public interface TSysMenuMapper extends BaseMapper<TSysMenu> {
    public List<Integer> menuLevelGroup();//查询所有的菜单级别(按级别分组)
}

