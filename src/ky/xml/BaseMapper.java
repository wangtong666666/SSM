
package ky.xml;

import java.util.List;

import ky.util.PageView;

/**
 * ********************************************************
 *
 * @author zj
 * @ClassName: BaseMapper
 * @Description: Mapper接口提供的公用的接口
 * @date 2014-02-21 下午 05:19:47
 * ******************************************************
 */
public interface BaseMapper<T> {

    /**
     * @methodName: save
     * @description: 保存对象
     * @param: T t
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public int save(T t);

    /**
     * @methodName: update
     * @description: 修改实体信息集合
     * @param: T t
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public int update(T t);

    /**
     * @methodName: delete
     * @description: 根据ID删除实体信息
     * @param: 主键  id
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public int delete(int id);

    /**
     * @methodName: delete
     * @description: 根据实体删除信息
     * @param: 主键  id
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public int delete(T t);

    /**
     * @methodName: selectOne
     * @description: 根据条件查询实体对象信息
     * @param: T t
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public T selectOne(T t);

    /**
     * @methodName: select
     * @description: 查询方法(不分页)
     * @param: T t
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public List<T> selectList(T t);

    /**
     * @methodName: selectPage
     * @description: 查询方法(分页)查询分页列表信息集合
     * @param: T t
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public List<T> selectPage(PageView pageView);

    /**
     * @methodName: total
     * @description: 查询分页信息总条数
     * @param: 主键  id
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public int total(PageView pageView);

}

