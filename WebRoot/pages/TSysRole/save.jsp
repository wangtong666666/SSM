<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
    <meta charset="UTF-8">
    <title>后台菜单</title>
    <link rel="stylesheet" type="text/css" href="../../jquery-easyui-1.3.2/themes/metro_blue/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../jquery-easyui-1.3.2/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../jquery-easyui-1.3.2/themes/demo.css">
    <link rel="stylesheet" type="text/css" href="../../css/main.css">
    <script type="text/javascript" src="../../jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="../../jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/config.js"></script>
    <script type="text/javascript">
        function check() {
            if ($("#levels").val() == 0) {
                alert("请选中等级!!");
                $("#levels").focus();
            }
        }
    </script>
</HEAD>
<BODY>
<!-- 添加界面 -->
<div class="easyui-layout" data-options="fit:true">
    <form id="TSysRoleSaveForm" method="post">
        <div data-options="region:'center'" style="padding:10px;">
            <p>角色等级:&nbsp;&nbsp;
                <select id="levels" name="levels">
                    <option value="0">请选择</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                </select>
            </p>
            <p>角色名称:&nbsp;&nbsp;<input onfocus="check()" class="easyui-validatebox"
                                       data-options="required:true,missingMessage:'字段不可以为空'" name="roleName"></p>
            <p><span id="add_msg" style="margin-left: 60px;color: green;font: 13px;font-weight: bolder;">&nbsp;</span>
            </p>
        </div>
        <div data-options="region:'south',border:false" style="text-align:right;padding:10px;padding-bottom: 5px;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
               style="margin-right: 15px " onfocus="check()" onclick="add_save('TSysRoleSaveForm','tsysrole')">添加</a>
            <a class="easyui-linkbutton" data-options="iconCls:'icon-cut'" href="javascript:void(0)"
               style="margin-right: 15px " onclick="doReset('TSysRoleSaveForm')">重置</a>
        </div>
    </form>
</div>

</BODY>
</html>

