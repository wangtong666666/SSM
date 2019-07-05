
package ky.action;

import ky.entity.PmsPersonInfo;
import ky.service.PmsPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import ky.util.PageView;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

/**
 * ********************************************************
 *
 * @author 生成Action类
 * @ClassName: PmsPersonInfoAction
 * @Description:
 * @date 2018-03-22 上午 11:47:27
 * ******************************************************
 */

@Controller
@RequestMapping("/pmspersoninfo")
public class PmsPersonInfoAction extends BaseAction<PmsPersonInfo> {

    @Resource
    private PmsPersonInfoService PmsPersonInfoSer;

    @RequestMapping("/selectPage")
//	@Action(value = "selectPage", results = {@Result(name = "selectPage", location = "/pages/PmsPersonInfo/list.jsp") })
    public String seletePage() {

        //接受前台参数，并进行数据查询
        PageView pageView = PmsPersonInfoSer.selectPage(this.pageParam("page", "rows", "order", "sort", model));

        //返回数据到界面
        this.returnPageInfo(pageView);

        return "selectPage";
    }

    @RequestMapping("/select")
//	@Action(value = "select",results = {@Result(name = "select", location = "/pages/PmsPersonInfo/list.jsp") })
    public String selete() {
        List<PmsPersonInfo> list = PmsPersonInfoSer.selectList(model);
        this.returnResult("1", "查询成功", list);
        return "select";
    }

    @RequestMapping("/save")
//	@Action(value = "save", results = {@Result(name = "save", location = "/pages/PmsPersonInfo/list.jsp") })
    public String save() {
        this.returnU_D_S_info(PmsPersonInfoSer.save(model));
        return "save";
    }

    @RequestMapping("/delete")
//	@Action(value = "delete", results = {@Result(name = "detele", location = "/pages/PmsPersonInfo/list.jsp") })
    public String detele() throws UnsupportedEncodingException {

        String idArray = this.getRequest("idArray");//获得前台复选框的值
        this.returnU_D_S_info(PmsPersonInfoSer.delete(idArray));
        return "detele";
    }

    @RequestMapping("/update")
//	@Action(value = "update", results = {@Result(name = "update", location = "/pages/PmsPersonInfo/list.jsp") })
    public String update() {

        this.returnU_D_S_info(PmsPersonInfoSer.update(model));
        return "update";
    }

}

