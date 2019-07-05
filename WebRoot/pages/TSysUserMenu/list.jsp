<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/commons.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
    <meta charset="UTF-8">
    <title>后台菜单</title>
</HEAD>
<script type="text/javascript">
    $(function () {
        //关闭修改界面时,刷新列表
        $("#TSysUserMenuUpdateDiv").window({
            onBeforeClose: function () { //当面板关闭之前触发的事件
                $('#TSysUserMenuTab').datagrid('load');
                $("#update_msg").html("&nbsp;");
            }
        });
        //关闭添加界面时,刷新列表
        $('#TSysUserMenuSaveDiv').window({
            onBeforeClose: function () { //当面板关闭之前触发的事件
                $('#TSysUserMenuTab').datagrid('load');
                $("#add_msg").html("&nbsp;");
            }
        });
    });
</script>
<BODY>
<table id="TSysUserMenuTab" class="easyui-datagrid" data-options="url:'../../tsysusermenu/selectPage.action',pageList:[10,20,22,30,40] ,
		       singleSelect:true,fit:true,fitColumns:true,toolbar:'#tb',pagination:true,idField:'sumId',singleSelect:false,pageSize:22">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'sumId',sortable:true" width="20">sumId</th>
        <th data-options="field:'menuId',sortable:true" width="20">menuId</th>
        <th data-options="field:'userId',sortable:true" width="20">userId</th>
    </tr>
    </thead>
</table>

<!-- 搜索div,功能div -->
<div id="tb">
    <form method="post" id="TSysUserMenuSearchForm">
        <div class="formDiv">
            <ul>
                <li>
                    字段：<INPUT class=textfield size=18 name="">
                </li>
                <li>
                    字段：<INPUT class=textfield size=18 name="">
                </li>
                <li>
                    字段：<INPUT class=textfield size=18 name="">
                </li>
                <li>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)"
                       onclick="FormData('TSysUserMenuTab','TSysUserMenuSearchForm')">查询</a>
                </li>
            </ul>
        </div>
    </form>
    <div class="operate">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="saveOpen('TSysUserMenuSaveDiv','TSysUserMenuSaveIframe')">新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="updateOpen('TSysUserMenuTab','TSysUserMenuUPdateDiv','TSysUserMenuUPdateIframe','sumId')">修改(详细)</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"
           onclick="delete_info('TSysUserMenuTab','tsysusermenu','sumId')">删除</a>
    </div>
</div>

<!-- 添加界面 -->
<div id="TSysUserMenuSaveDiv" class="easyui-window" closed="true" modal="true" iconCls="icon-add" title="添加"
     style="width:530px;height:350px;text-align: center;">
    <iframe scrolling="auto" id="TSysUserMenuSaveIframe" frameborder="0"
            style="width:97%;height:97%;margin-top: 1%;"></iframe>
</div>

<!-- 修改界面 -->
<div id="TSysUserMenuUPdateDiv" class="easyui-window" closed="true" modal="true" iconCls="icon-edit" title="修改"
     style="width:530px;height:350px;text-align: center;">
    <iframe scrolling="auto" id="TSysUserMenuUPdateIframe" frameborder="0"
            style="width:97%;height:97%;margin-top: 1%;"></iframe>
</div>

</BODY>
</html>

