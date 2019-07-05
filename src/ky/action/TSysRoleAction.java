
package ky.action;

import ky.entity.TSysRole;
import ky.entity.TSysUser;
import ky.service.TSysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ky.util.PageView;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.swing.JOptionPane;

/**
 * ********************************************************
 *
 * @author 生成Action类
 * @ClassName: TSysRoleAction
 * @Description: null
 * @date 2014-06-10 下午 06:52:43
 * ******************************************************
 */

@Controller
@RequestMapping("/tsysrole")
public class TSysRoleAction extends BaseAction<TSysRole> {

    @Resource
    private TSysRoleService TSysRoleSer;

    @RequestMapping("/select")
//	@Action(value = "select", results = {@Result(name = "select", location = "/pages/TSysRole/list.jsp") })
    public String selete(TSysRole model) {
        List<TSysRole> list = TSysRoleSer.selectList(model);
        this.jsonArray(list);
        return "select";
    }
//	@Action(value = "selectPage", results = {@Result(name = "selectPage", location = "/pages/TSysRole/list.jsp") })
//	public String seletePage(){
//
//		//接受前台参数，并进行数据查询
//		PageView pageView = TSysRoleSer.selectPage(this.pageParam("page", "rows","order", "sort",model ));
//		
//		//返回数据到界面
//		this.returnPageInfo(pageView);
//
//		return "selectPage";
//	}
//

//
//	@Action(value = "save", results = {@Result(name = "save", location = "/pages/TSysRole/save.jsp") })
//	public String save(){
//     System.out.println("fsafaf");
//		this.returnU_D_S_info(TSysRoleSer.save(model));
//		return "save";
//	}
//
//	@Action(value = "detele", results = {@Result(name = "detele", location = "/pages/TSysRole/list.jsp") })
//	public String detele() throws UnsupportedEncodingException{
//
//		String idArray=this.getRequest("idArray");//获得前台复选框的值
//		this.returnU_D_S_info(TSysRoleSer.delete(idArray));
//		return "detele";
//	}
//
//	@Action(value = "update", results = {@Result(name = "update", location = "/pages/TSysRole/list.jsp") })
//	public String update(){
//
//		this.returnU_D_S_info(TSysRoleSer.update(model));
//		return "update";
//	}

}

