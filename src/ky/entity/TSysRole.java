
package ky.entity;

import java.util.Date;

/**
 * ********************************************************
 *
 * @author 用wzl写的自动生成
 * @ClassName: TSysRole
 * @Description: null
 * @date 2014-06-10 下午 06:52:43
 * ******************************************************
 */
public class TSysRole extends BaseEntity {

    private Integer roleId;        //角色编号
    private Integer levels;        //角色等级
    private String roleName;        //角色名称

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getLevels() {
        return this.levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}

