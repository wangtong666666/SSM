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

        //关闭添加界面时,刷新列表
        $('#TSysUserSaveDiv').window({
            onBeforeClose: function () { //当面板关闭之前触发的事件
                $('#TSysUserTab').datagrid('reload');
                $("#add_msg").html("&nbsp;");
            }
        });

        //关闭修改界面时,刷新列表
        $('#TSysUserUPdateDiv').window({
            onBeforeClose: function () { //当面板关闭之前触发的事件
                $('#TSysUserTab').datagrid('reload');

            }
        });
    });
</script>
<BODY>
<table id="TSysUserTab" class="easyui-datagrid" data-options="pageList:[10,20,22,30,40] ,
		       singleSelect:true,fit:true,fitColumns:true,toolbar:'#tb',pagination:true,idField:'id',singleSelect:false,pageSize:22">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'id',sortable:true" width="20">编号</th>
        <th data-options="field:'loginName',sortable:true" width="20">登录名</th>
        <th data-options="field:'trueName',sortable:true" width="20">真实姓名</th>
        <th data-options="field:'roleName',sortable:true" width="20">角色名称</th>
        <th data-options="field:'email',sortable:true" width="20">电子邮件</th>
        <th data-options="field:'userStatus',sortable:true,formatter:function(userStatus){if(userStatus=='0'){return '开通';}if(userStatus=='1'){return '无效';}}"
            width="20">用户状态
        </th>
        <th data-options="field:'mobileno',sortable:true" width="20">手机号</th>
    </tr>
    </thead>
</table>

<!-- 搜索div,功能div -->
<div id="tb">

    <form method="post" id="TSysUserSearchForm">
        <div class="formDiv">
            <ul>
                <li>
                    编号：<INPUT class=textfield size=18 name="id"><INPUT type="hidden" id="use"
                                                                       value="${user.loginName }">
                </li>
                <li>
                    姓名：<INPUT class=textfield size=18 name="trueName">
                </li>
                <li>
                    登录名：<INPUT class=textfield size=18 name="loginName">
                </li>
                <li>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)"
                       onclick="FormData2('TSysUserTab','TSysUserSearchForm','../../tsysuser/selectPage.action')">查询</a>
                </li>
            </ul>
        </div>
    </form>
    <div class="operate">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true"
           onclick="updateOpens('TSysUserTab','TSysUserUPdateDiv','TSysUserUPdateIframe','id')">查看(详细)</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="editOpen('TSysUserTab','TSysUserEditDiv','TSysUserEditIframe','id')">编辑权限</a>
    </div>
</div>

<!-- 查看界面 -->
<div id="TSysUserUPdateDiv" class="easyui-window" closed="true" modal="true" iconCls="icon-edit" title="查看"
     style="width:530px;height:450px;text-align: center;">
    <iframe scrolling="auto" id="TSysUserUPdateIframe" frameborder="0"
            style="width:97%;height:97%;margin-top: 1%;"></iframe>
</div>
<!-- 编辑权限界面 -->
<div id="TSysUserEditDiv" class="easyui-window" closed="true" modal="true" iconCls="icon-edit" title="编辑权限"
     style="width:336px;height:500px;text-align: center;">
    <iframe scrolling="auto" id="TSysUserEditIframe" frameborder="0"
            style="width:97%;height:97%;margin-top: 1%;"></iframe>
</div>
</BODY>
</html>

