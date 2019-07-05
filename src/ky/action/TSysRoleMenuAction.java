
package ky.action;

import ky.entity.TSysRoleMenu;
import ky.service.TSysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

/**
 * ********************************************************
 *
 * @author 生成Action类
 * @ClassName: TSysRoleMenuAction
 * @Description: 角色菜单关联表
 * @date 2014-06-16 下午 07:42:07
 * ******************************************************
 */

@Controller
@RequestMapping("/tsysrolemenu")
public class TSysRoleMenuAction extends BaseAction<TSysRoleMenu> {

    @Resource
    private TSysRoleMenuService TSysRoleMenuSer;
//	@Action(value = "selectPage", results = {@Result(name = "selectPage", location = "/pages/TSysRoleMenu/list.jsp") })
//	public String seletePage(){
//
//		//接受前台参数，并进行数据查询
//		PageView pageView = TSysRoleMenuSer.selectPage(this.pageParam("page", "rows","order", "sort",model ));
//
//		//返回数据到界面
//		this.returnPageInfo(pageView);
//
//		return "selectPage";
//	}
//
//	@Action(value = "select", results = {@Result(name = "select", location = "/tsysrolemenu.jsp") })
//	public String selete(){
//
//		List<TSysRoleMenu> list = TSysRoleMenuSer.selectList(model);
//		this.jsonArray(list);
//		return "select";
//	}
//
//	@Action(value = "save", results = {@Result(name = "save", location = "/tsysrolemenu.jsp") })
//	public String save(){
//
//		this.returnU_D_S_info(TSysRoleMenuSer.save(model));
//		return "save";
//	}
//
//	@Action(value = "detele", results = {@Result(name = "detele", location = "/tsysrolemenu.jsp") })
//	public String detele() throws UnsupportedEncodingException{
//
//		String idArray=this.getRequest("idArray");//获得前台复选框的值
//		this.returnU_D_S_info(TSysRoleMenuSer.delete(idArray));
//		return "detele";
//	}
//
//	@Action(value = "update", results = {@Result(name = "update", location = "/tsysrolemenu.jsp") })
//	public String update(){
//
//		this.returnU_D_S_info(TSysRoleMenuSer.update(model));
//		return "update";
//	}
//	
//	@Action(value = "delete_add", results = {@Result(name = "delete_add", location = "/pages/TSysRoleMenu/list.jsp") })
//	public String delete_add() throws UnsupportedEncodingException{
//		String idArray=this.getRequest("idArray");//获得前台复选框的值
//		String roleId=this.getRequest("roleId");
//		this.returnU_D_S_info(TSysRoleMenuSer.delete_add(idArray,roleId));
//		return "delete_add";
//	}

}

