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
</HEAD>
<script type="text/javascript">
    $(function () {
        //关闭修改界面时,刷新列表
        $("#TSysRoleUPdateDiv").window({
            onBeforeClose: function () { //当面板关闭之前触发的事件
                $('#TSysRoleTab').datagrid('load');
                $("#update_msg").html("&nbsp;");
            }
        });
        //关闭添加界面时,刷新列表
        $('#TSysRoleSaveDiv').window({
            onBeforeClose: function () { //当面板关闭之前触发的事件
                $('#TSysRoleTab').datagrid('load');
                $("#add_msg").html("&nbsp;");
            }
        });
    });
</script>
<BODY>
<table id="TSysRoleTab" class="easyui-datagrid" data-options="url:'../../tsysrole/selectPage.action',pageList:[10,20,22,30,40] ,
		       singleSelect:true,fit:true,fitColumns:true,toolbar:'#tb',pagination:true,idField:'roleId',singleSelect:false,pageSize:22">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'roleId',sortable:true" width="20">角色编号</th>
        <th data-options="field:'levels',sortable:true" width="20">角色等级</th>
        <th data-options="field:'roleName',sortable:true" width="20">角色名称</th>
    </tr>
    </thead>
</table>

<!-- 搜索div,功能div -->
<div id="tb">
    <form method="post" id="TSysRoleSearchForm">
        <div class="formDiv">
            <ul>
                <li>
                    角色编号：<INPUT class=textfield size=18 name="roleId">
                </li>
                <li>
                    角色名称：<INPUT class=textfield size=18 name="roleName">
                </li>

                <li>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)"
                       onclick="FormData('TSysRoleTab','TSysRoleSearchForm')">查询</a>
                </li>
            </ul>
        </div>
    </form>
    <div class="operate">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="saveOpen('TSysRoleSaveDiv','TSysRoleSaveIframe')">新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="updateOpen('TSysRoleTab','TSysRoleUPdateDiv','TSysRoleUPdateIframe','roleId')">修改(详细)</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"
           onclick="delete_info('TSysRoleTab','tsysrole','roleId')">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="editOpen1('TSysRoleTab','TSysRoleEditDiv','TSysRoleEditIframe','roleId')">编辑权限</a>
    </div>
</div>

<!-- 添加界面 -->
<div id="TSysRoleSaveDiv" class="easyui-window" closed="true" modal="true" iconCls="icon-add" title="添加"
     style="width:530px;height:350px;text-align: center;">
    <iframe scrolling="auto" id="TSysRoleSaveIframe" frameborder="0"
            style="width:97%;height:97%;margin-top: 1%;"></iframe>
</div>

<!-- 修改界面 -->
<div id="TSysRoleUPdateDiv" class="easyui-window" closed="true" modal="true" iconCls="icon-edit" title="修改"
     style="width:530px;height:350px;text-align: center;">
    <iframe scrolling="auto" id="TSysRoleUPdateIframe" frameborder="0"
            style="width:97%;height:97%;margin-top: 1%;"></iframe>
</div>
<!-- 编辑权限界面 -->
<div id="TSysRoleEditDiv" class="easyui-window" closed="true" modal="true" iconCls="icon-edit" title="编辑权限"
     style="width:336px;height:550px;text-align: center;">
    <iframe scrolling="auto" id="TSysRoleEditIframe" frameborder="0"
            style="width:97%;height:97%;margin-top: 1%;"></iframe>
</div>
</BODY>
</html>

