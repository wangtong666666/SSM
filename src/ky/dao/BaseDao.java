
package ky.dao;

import java.util.List;

import ky.util.PageView;
import ky.util.PageViewSum;

/**
 * ********************************************************
 *
 * @author zj
 * @InterfaceName: BaseDao
 * @Description: baseDao
 * @date 2014年4月23日15:26:47
 * ******************************************************
 */

public interface BaseDao<T> {

    /**
     * @methodName: save
     * @description: 保存对象
     * @param: T t
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public int save(T t);

    /**
     * @methodName: delete
     * @description: 删除
     * @param: 主键  id
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public int delete(int id);

    /**
     * @methodName: update
     * @description: 更新对象
     * @param: T t
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public int update(T t);

    /**
     * @methodName: selectOne
     * @description: 查询对象信息
     * @param: T t
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public T selectOne(T t);

    /**
     * @methodName: selectList
     * @description: 查询对象集合
     * @param: T t
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public List<T> selectList(T t);

    /**
     * @methodName: getPageView
     * @description: 查询分页展示信息集合
     * @param: 分页对象PageView pageView
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public PageView getPageView(PageView pageView);

    /**
     * @methodName: getPageViewSum
     * @description: 用于footer显示合计
     * @param: 分页对象PageView pageView
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public abstract PageViewSum getPageViewSum(PageViewSum paramPageView);


}

