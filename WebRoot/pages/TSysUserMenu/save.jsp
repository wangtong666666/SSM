<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/commons.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
    <meta charset="UTF-8">
    <title>后台菜单</title>
</HEAD>
<BODY>
<!-- 添加界面 -->
<div class="easyui-layout" data-options="fit:true">
    <form id="TSysUserMenuSaveForm" method="post">
        <div data-options="region:'center'" style="padding:10px;">
            <p>menuId:&nbsp;&nbsp;<input class="easyui-validatebox"
                                         data-options="required:true,missingMessage:'字段不可以为空'" name="menuId"></p>
            <p>userId:&nbsp;&nbsp;<input class="easyui-validatebox"
                                         data-options="required:true,missingMessage:'字段不可以为空'" name="userId"></p>
            <p><span id="add_msg" style="margin-left: 60px;color: green;font: 13px;font-weight: bolder;">&nbsp;</span>
            </p>
        </div>
        <div data-options="region:'south',border:false" style="text-align:right;padding:10px;padding-bottom: 5px;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
               style="margin-right: 15px " onclick="add_save('TSysUserMenuSaveForm','tsysusermenu')">添加</a>
            <a class="easyui-linkbutton" data-options="iconCls:'icon-cut'" href="javascript:void(0)"
               style="margin-right: 15px " onclick="doReset('TSysUserMenuSaveForm')">重置</a>
        </div>
    </form>
</div>

</BODY>
</html>

