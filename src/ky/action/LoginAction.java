package ky.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import ky.entity.TSysRole;
import ky.entity.TSysUser;
import ky.service.TSysRoleService;
import ky.service.TSysUserMenuService;
import ky.service.TSysUserService;
import ky.util.Encryption;
import ky.util.TheadUtils;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginAction extends BaseAction<TSysUser> {


    @Resource
    private TSysUserService userSer;

    @Resource
    private TSysRoleService roleser;
    Encryption md5 = new Encryption();

    @Resource
    private TSysUserMenuService TSysUserMenuSer;


    @RequestMapping("/main")
//	@Action(value="main", results={@org.apache.struts2.convention.annotation.Result(name="loginError", type="chain", location="login"), @org.apache.struts2.convention.annotation.Result(name="main", location="/admincontrol.jsp"), @org.apache.struts2.convention.annotation.Result(name="app", location="/appchangePwd.jsp")})
    public String login(TSysUser model, String code) {

        String loginmsg = "登录成功！";
//		String code = getParameter("code");
        TSysUser u = (TSysUser) this.session.getAttribute("user");

        if (u != null) {
            TheadUtils.clear();
            this.session.setAttribute("user", u);
            getRoleName();
            init();
//		  returnResult("1", "已经登录，请勿重复操作！", null);
            return "main";
        }

        // 用户密码检查
        String loginname = ((TSysUser) this.model).getLoginName();//获取登录名
        String pwd = ((TSysUser) this.model).getLoginPwd();//获取登陆密码
        TSysUser usercheck = new TSysUser();
        usercheck.setLoginName(loginname);
        usercheck.setLoginPwd(Encryption.MD5(pwd));   //暂用 （加密方式过于简单）
        List list = this.userSer.selectList(usercheck);


        if (list.size() == 1) {
            TSysUser user = (TSysUser) list.get(0);
            setAttribute("loginmsg", loginmsg);
            TheadUtils.clear();
            this.session.setAttribute("user", user);
            getRoleName();
            init();
//			returnResult("1", "登录成功！", null);
            return "main";
        }
        loginmsg = "登录失败，用户名或密码有误。";

        setAttribute("loginmsg", loginmsg);
        setAttribute("loginname", ((TSysUser) this.model).getLoginName());

//		returnResult("0", loginmsg, null);
        return "loginError";
    }

    @RequestMapping("/login")
//	@Action(value="login", results={@org.apache.struts2.convention.annotation.Result(name="app", location="/applogin.jsp")})
    public String toLogin() {
        this.session.removeAttribute("user");
        String path = getParameter("loginpath");
        if ("".equals(path) || path == null || "app".equals(path)) {
            return "app";
        } else {
            this.session.setAttribute("loginpath", path);
        }

        return path;
    }

    /*
     * 密码有效性检查，比如字母和数字混搭，位数6-20位之间
     */
    private boolean isContainNumber(String company) {

        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(company);
        if (m.find()) {
            return true;
        }
        return false;
    }

    private boolean isContainKey(String company) {

        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(company);
        if (m.find()) {
            return true;
        }
        return false;
    }

    private boolean pwdcheck(String str) {
        if (str.length() < 6 || str.length() > 18) {
            return false;
        }
        return str.matches("^[a-z0-9A-Z]+$") && isContainNumber(str) && isContainKey(str);
    }

    @RequestMapping("/updatePwd")
//	@Action(value="updatePwd")
    public void UpdatePwd() {
        TSysUser user = new TSysUser();
        user = ((TSysUser) this.userSer.selectList(user).get(0));
        String loginname = user.getLoginName();//获取登录名
        String pwd = model.getLoginPwd();//获取新的密码

        // 手机验证码验证
        String mobilesecond = (String) this.session.getAttribute(((TSysUser) this.model).getLoginName() + "mobilesecond");
        if (null != mobilesecond) {
            long time1 = Long.parseLong(mobilesecond);
            long time2 = new Date().getTime();
            long diff = time2 - time1;
            long sec = diff / 1000;
            if (sec > 60) {
                returnU_D_S_info(-1);
                return;
            }
            String mobileno = this.model.getMobileNum();
            if (mobileno == null) {
                returnU_D_S_info(-2);
                return;
            } else {
                String mobilenoy = (String) this.session.getAttribute(((TSysUser) this.model).getLoginName() + "Mobilemessage");
                if (null == mobilenoy) {
                    returnU_D_S_info(-3);
                    return;
                }
                if (!mobilenoy.equals(mobileno)) {
                    returnU_D_S_info(-4);
                    return;
                }
            }
            this.session.removeAttribute(((TSysUser) this.model).getLoginName() + "Mobilemessage");
            this.session.removeAttribute(((TSysUser) this.model).getLoginName() + "mobilesecond");
        } else {
            returnU_D_S_info(-1);
            return;
        }

        // 密码有效性检查
        if (pwdcheck(pwd) == false) {
            returnU_D_S_info(0);
            return;
        }

        user.setLoginPwd(Encryption.MD5new(loginname, pwd));//新的密文
        user.setPwdDate(new java.sql.Date(new java.util.Date().getTime()));
        returnU_D_S_info(this.userSer.updatePwe(user));
    }


    @RequestMapping("/logout")
//	@Action(value="logout", results={@org.apache.struts2.convention.annotation.Result(name="app", type="chain", location="login")})
    public String logout() {
        this.session.removeAttribute("user");
        return "app";
    }

    @RequestMapping("/updatePwd2")
//	@Action(value="updatePwd2")
    public void UpdatePwd2() {
        int ret = 0;
        try {
            TSysUser user = new TSysUser();
            user.setLoginName(getParameter("loginName"));
            String mobilesecond = (String) this.session.getAttribute(((TSysUser) this.model).getLoginName() + "mobilesecond");
            if (null != mobilesecond) {
                long time1 = Long.parseLong(mobilesecond);
                long time2 = new Date().getTime();
                long diff = time2 - time1;
                long sec = diff / 1000;
                if (sec > 60) {
                    returnU_D_S_info(-1);
                    return;
                }
                String mobileno = this.model.getMobileNum();
                if (mobileno == null) {
                    returnU_D_S_info(-2);
                    return;
                } else {
                    String mobilenoy = (String) this.session.getAttribute(((TSysUser) this.model).getLoginName() + "Mobilemessage");
                    if (null == mobilenoy) {
                        returnU_D_S_info(-3);
                        return;
                    }
                    if (!mobilenoy.equals(mobileno)) {
                        returnU_D_S_info(-4);
                        return;
                    }
                }
                this.session.removeAttribute(((TSysUser) this.model).getLoginName() + "Mobilemessage");
                this.session.removeAttribute(((TSysUser) this.model).getLoginName() + "mobilesecond");

                // 密码有效性检查
                if (pwdcheck(getParameter("password")) == false) {
                    returnU_D_S_info(0);
                    return;
                }

                this.model = ((TSysUser) this.userSer.selectList(user).get(0));
                ((TSysUser) this.model).setLoginPwd(Encryption.MD5new(getParameter("loginName"), getParameter("password")));
                ((TSysUser) this.model).setPwdDate(new java.sql.Date(new java.util.Date().getTime()));
                ret = Integer.valueOf(this.userSer.updatePwe((TSysUser) this.model));
            } else {
                ret = -5;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        returnU_D_S_info(ret);
    }


    public void init() {
        TSysUser u = (TSysUser) this.session.getAttribute("user");
        List list = this.TSysUserMenuSer.selectUserMenu(u.getId().intValue());
        setAttribute("menuList", JSONArray.fromObject(list));

//		menuAccess(list);


    }

    private Map findJspUrl(List<Map> list, Map jspMap) {
        for (Map map : list) {
            String url = (String) map.get("attributes");
            if (url != null) {
                //截取/pages后面的路径 并去除首尾空格
                url = url.substring(url.indexOf("/pages")).trim();
                // 去除参数
                if (url.indexOf("?") > -1) {
                    url = url.substring(0, url.indexOf("?"));
                }
                jspMap.put(url, "");
            }
            List<Map> childrenList = (List) map.get("children");
            if (childrenList != null && !("".equals(childrenList))) {
                jspMap = findJspUrl(childrenList, jspMap);
            }
        }
        return jspMap;
    }


    public void getRoleName() {
        HttpSession sess = this.request.getSession();
        TSysUser u = (TSysUser) this.session.getAttribute("user");
        TSysRole r = new TSysRole();
        r.setRoleId(u.getRoleId());
        r = (TSysRole) this.roleser.selectList(r).get(0);
        System.out.println(r.getRoleName());
        sess.setAttribute("RoleName", r.getRoleName());
        sess.setAttribute("Name", u.getTrueName());
        sess.setAttribute("agentNumber", u.getLoginName().substring(0, 6));
    }
}