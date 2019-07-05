package ky.entity;

public class BaseEntity {
    protected String loginName;//当前登录用户

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

}
