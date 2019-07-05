
package ky.service;

import ky.entity.TSysUser;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author 生成service类
 * @ClassName: TSysUserService
 * @Description: 用户表
 * @date 2014-06-09 下午 06:42:03
 * ******************************************************
 */
@Service
public interface TSysUserService {

    public PageView selectPage(PageView pageView);

    public List<TSysUser> selectList(TSysUser obj);

    public int save(TSysUser obj);

    public int delete(String idArray);

    public int update(TSysUser obj);

    public int deletep(String idArray);

    public int updatePwe(TSysUser obj);

    public List<Map<String, String>> selectOnlineUser(Map<String, HttpSession> map, String path);
}

