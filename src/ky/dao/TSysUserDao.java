
package ky.dao;

import ky.entity.TSysUser;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 用wzl写的自动生成dao包
 * @ClassName: TSysUserDao
 * @Description: 用户表
 * @date 2014-06-09 下午 06:42:03
 * ******************************************************
 */
@Repository
public interface TSysUserDao extends BaseDao<TSysUser> {
    public int deletep(String id);

    public int update1(TSysUser obj);
}

