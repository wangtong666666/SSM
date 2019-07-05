package ky.dao.Impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.annotation.Resource;

import ky.dao.BaseDao;
import ky.util.PageView;
import ky.util.PageViewSum;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * ********************************************************
 *
 * @author zj
 * @ClassName: BaseDaoImpl
 * @Description: BaseDaoImpl
 * @date 2014年4月23日15:35:34
 * ******************************************************
 */

public class BaseDaoImpl<T> implements BaseDao<T> {

    @Resource(name = "sqlSessionTemplate")
    protected SqlSessionTemplate session;
    private final String path = "ky.xml.";

    protected Class<T> clazz;

    public BaseDaoImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        clazz = (Class) pt.getActualTypeArguments()[0];
    }

    //	//获取Mapper的路径
    public String getPath(String methodType) {
        System.out.println(path + clazz.getSimpleName() + "Mapper." + methodType);
        return path + clazz.getSimpleName() + "Mapper." + methodType;
    }

    /**
     * @methodName: save
     * @description: 保存对象
     * //session.insert("cn.xxx.mapper.save",user)里，
     * 第一个参数是mapper.xml里的namespace+方法名,第二个参数就是传入对象
     * @param: DealerNotbindingWeixin obj
     * @author zjj
     * @time 2014年4月23日12:06:07
     */

    public int save(T t) {
        return session.insert(getPath("save"), t);
    }

    /**
     * @methodName: delete
     * @description: 删除
     * @param: 主键  id
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public int delete(int id) {
        return session.delete(getPath("delete"), id);
    }

    /**
     * @methodName: update
     * @description: 更新对象
     * @param: DealerNotbindingWeixin obj
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public int update(T t) {
        return session.update(getPath("update"), t);
    }

    /**
     * @methodName: selectOne
     * @description: 根据条件查询对象实体信息
     * @param:
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public T selectOne(T t) {
        return session.selectOne(getPath("selectOne"), t);
    }

    /**
     * @methodName: selectList
     * @description: 根据条件查询对象信息集合
     * @param:
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public List<T> selectList(T t) {
        return session.selectList(getPath("selectList"), t);
    }

    /**
     * @methodName: getPageView
     * @description: 查询分页展示信息集合
     * @param: 分页对象PageView pageView
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public PageView getPageView(PageView pageView) {
        int num = pageView.getPageNum();         // 显示的页数
        int size = pageView.getPageSize();        //显示的条数
        pageView.setPageNum((num - 1) * size);

        return new PageView(Integer.parseInt(session.selectList(getPath("total"), pageView).get(0).toString()),
                session.selectList(getPath("selectPage"), pageView));
    }

    /**
     * @methodName: getPageViewSum
     * @description: footer显示合计数据
     * @param: 分页对象PageViewSum pageViewSum
     * @author zjj
     * @time 2014年4月23日12:06:07
     */
    public PageViewSum getPageViewSum(PageViewSum pageViewSum) {
        return new PageViewSum(Integer.parseInt(this.session.selectList(getPath("total"), pageViewSum).get(0).toString()),
                this.session.selectList(getPath("selectPage"), pageViewSum), this.session.selectList(getPath("selectPageSum"), pageViewSum));
    }

}

