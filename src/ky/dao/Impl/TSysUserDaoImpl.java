
package ky.dao.Impl;

import ky.entity.TSysUser;
import ky.dao.TSysUserDao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 用wzl写的自动生成daoImpl包
 * @ClassName: TSysUserDaoImpl
 * @Description: 用户表
 * @date 2014-06-09 下午 06:42:03
 * ******************************************************
 */
@Repository
public class TSysUserDaoImpl extends BaseDaoImpl<TSysUser> implements TSysUserDao {
    public int deletep(String id) {
        return session.delete(getPath("deletep"), id);
    }

    public int update1(TSysUser obj) {
        return session.update(getPath("update1"), obj);
    }
}

