
package ky.entity;

/**
 * ********************************************************
 *
 * @author 用wzl写的自动生成
 * @ClassName: TSysUserMenu
 * @Description: 用户菜单关联表
 * @date 2014-06-17 下午 04:27:26
 * ******************************************************
 */
public class TSysUserMenu extends BaseEntity {

    private Integer sumId;        //关联表编号
    private Integer menuId;        //菜单编号
    private Integer userId;        //用户编号

    public Integer getSumId() {
        return this.sumId;
    }

    public void setSumId(Integer sumId) {
        this.sumId = sumId;
    }

    public Integer getMenuId() {
        return this.menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}

