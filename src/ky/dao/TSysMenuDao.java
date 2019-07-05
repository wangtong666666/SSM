
package ky.dao;

import ky.entity.TSysMenu;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 用wzl写的自动生成dao包
 * @ClassName: TSysMenuDao
 * @Description: null
 * @date 2014-06-09 下午 09:02:34
 * ******************************************************
 */
@Repository
public interface TSysMenuDao extends BaseDao<TSysMenu> {
    public List<Integer> menuLevelGroup();//查询所有的菜单级别(按级别分组)
}

