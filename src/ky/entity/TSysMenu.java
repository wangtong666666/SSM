
package ky.entity;

import java.util.Date;

/**
 * ********************************************************
 *
 * @author 用wzl写的自动生成
 * @ClassName: TSysMenu
 * @Description: null
 * @date 2014-06-09 下午 09:02:34
 * ******************************************************
 */
public class TSysMenu extends BaseEntity {

    private Integer menuId;        // 菜单id
    private Integer platformId;        //null
    private String menuName;        //菜单名称
    private Integer menuLevel;        //菜单等级
    private Integer parentid;        //父级id
    private String menuHref;        //点击跳转路径
    private String param;        //null

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getMenuId() {
        return this.menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getPlatformId() {
        return this.platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuLevel() {
        return this.menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public Integer getParentid() {
        return this.parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getMenuHref() {
        return this.menuHref;
    }

    public void setMenuHref(String menuHref) {
        this.menuHref = menuHref;
    }

}

