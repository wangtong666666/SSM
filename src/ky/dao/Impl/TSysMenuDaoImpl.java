
package ky.dao.Impl;

import ky.entity.TSysMenu;
import ky.dao.TSysMenuDao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 用wzl写的自动生成daoImpl包
 * @ClassName: TSysMenuDaoImpl
 * @Description: null
 * @date 2014-06-09 下午 09:02:34
 * ******************************************************
 */
@Repository
public class TSysMenuDaoImpl extends BaseDaoImpl<TSysMenu> implements TSysMenuDao {
    //查詢所有菜單的级别
    public List<Integer> menuLevelGroup() {
        return session.selectList(this.getPath("menuLevelGroup"));
    }
}

