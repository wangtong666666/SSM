
package ky.action;

import ky.entity.TSysUser;
import ky.service.TSysRoleService;
import ky.service.TSysUserMenuService;
import ky.service.TSysUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ky.util.Encryption;
import ky.util.PageView;
import ky.util.SessionListener;
import com.opensymphony.xwork2.ActionContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * ********************************************************
 *
 * @author 生成Action类
 * @ClassName: TSysUserAction
 * @Description: 用户表
 * @date 2014-06-09 下午 06:42:03
 * ******************************************************
 */


@Controller
@RequestMapping("/tsysuser")
public class TSysUserAction extends BaseAction<TSysUser> {

    @Resource
    private TSysUserService TSysUserSer;
    @Resource
    private TSysRoleService TSysRoleSer;
    @Resource
    private TSysUserMenuService TSysRoleMenuSer;
    @Resource
    private TSysUserMenuService TSysUserMenuSer;

    @RequestMapping("/selectPage")
//	@Action(value = "selectPage", results = {@Result(name = "selectPage", location = "/pages/TSysUser/list.jsp") })
    public String seletePage() {


        //接受前台参数，并进行数据查询
        PageView pageView = TSysUserSer.selectPage(this.pageParam("page", "rows", "order", "sort", model));
        //返回数据到界面
        this.returnPageInfo(pageView);
        return "selectPage";
    }

    @RequestMapping("/select")
//	@Action(value = "select", results = {@Result(name = "select", location = "/pages/TSysUser/list.jsp") })
    public String selete(TSysUser model, String person) {
        String dept = person;
        if (dept != null && dept != "") {
            model.setLoginName(dept);
        }
        List<TSysUser> list = TSysUserSer.selectList(model);
        System.out.println(list.size());
        this.jsonArray(list);
        return "select";
    }

    @RequestMapping("/save")
//	@Action(value = "save", results = {@Result(name = "save", location = "/pages/TSysUser/list.jsp") })
    public String save(TSysUser model) {
        Encryption md5 = new Encryption();
        model.setLoginPwd(md5.MD5(model.getLoginPwd()));
        this.returnU_D_S_info(TSysUserSer.save(model));
        return "save";

    }

//	@Action(value = "detele", results = {@Result(name = "detele", location = "/pages/TSysUser/list.jsp") })
//	public String detele() throws UnsupportedEncodingException{
//		String idArray=this.getRequest("idArray");//获得前台复选框的值
//		this.returnU_D_S_info(TSysUserSer.delete(idArray));
//		return "detele";
//	}
//	
//	@Action(value = "detelep", results = {@Result(name = "detelep", location = "/pages/TSysUser/list.jsp") })
//	public String detelep() throws UnsupportedEncodingException{
//		String idArray=this.getRequest("idArray");//获得前台复选框的值
//		this.returnU_D_S_info(TSysUserSer.deletep(idArray));
//		return "detelep";
//	}
//
//	@Action(value = "update", results = {@Result(name = "update", location = "/pages/TSysUser/list.jsp") })
//	public String update(){
//		
//	    this.returnU_D_S_info(TSysUserSer.update(model));
//		return "update";
//	}
//	
//	@Action(value = "selectUserOnline", results = {@Result(name = "selectUserOnline", location = "/pages/TSysUser/userOnlineList.jsp") })
//	public String selectUserOnline(){
//		Map<String, HttpSession> map = SessionListener.getSessionOnline();
//		TSysUser user=(TSysUser) session.getAttribute("user");
//		String path = null;
//		if(!user.getLoginName().equals("admin")){
//			path = (String) session.getAttribute("loginpath");
//		}
//		List<Map<String, String>> list = TSysUserSer.selectOnlineUser(map,path);
//		Map<String, Object> m=new HashMap<String, Object>();
//		m.put("userOnline",list);
//		m.put("userNum", list.size());
//		try {
//			response.setContentType("text/html;charset=utf-8");
//			response.setCharacterEncoding("utf-8");
//			PrintWriter out = response.getWriter();
//			response.setContentType("text/html");
//			out.print(JSONObject.fromObject(m));
//			out.flush();
//			out.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		return "selectUserOnline";
//	}

}

