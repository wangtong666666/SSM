
package ky.entity;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ********************************************************
 *
 * @author 用wzl写的自动生成
 * @ClassName: TSysUser
 * @Description: 用户表
 * @date 2014-06-09 下午 06:42:03
 * ******************************************************
 */
public class TSysUser extends BaseEntity {

    private Integer id;        //用户表ID
    private Integer roleId;        //用户的角色编号
    private Date pwdDate;        //密码创建时间
    private String email;        //电子邮件
    private String trueName;        //真实姓名
    private String loginName;        //登录名
    private Integer userStatus;        //用户状态（0开通1失效）
    private String loginPwd;        //登录密码
    private String mobileno;        //手机号码
    private String roleName;        //角色名字
    private String departmentId;
    private Integer parentId;        //普通员工所属O单的角色编号
    private String mobileNum;


    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getPwdDate() {
        return this.pwdDate;
    }

    public void setPwdDate(Date pwdDate) {
        this.pwdDate = pwdDate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTrueName() {
        return this.trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getLoginPwd() {
        return this.loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getMobileno() {
        return this.mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


}

