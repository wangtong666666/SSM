
package ky.entity;

import java.util.Date;

/**
 * ********************************************************
 *
 * @author 用wzl写的自动生成
 * @ClassName: TSysRoleMenu
 * @Description: 角色菜单关联表
 * @date 2014-06-16 下午 07:42:07
 * ******************************************************
 */
public class TSysRoleMenu extends BaseEntity {

    private Integer menuId;        //菜单编号
    private Integer srmId;        //关联表编号
    private Integer roleId;        //角色编号

    public Integer getMenuId() {
        return this.menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getSrmId() {
        return this.srmId;
    }

    public void setSrmId(Integer srmId) {
        this.srmId = srmId;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}

