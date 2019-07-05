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
        $("#PmsPersonInfoUpdateDiv").window({
            onBeforeClose: function () { //当面板关闭之前触发的事件
                $('#PmsPersonInfoTab').datagrid('load');
                $("#update_msg").html("&nbsp;");
            }
        });
        //关闭添加界面时,刷新列表
        $('#PmsPersonInfoSaveDiv').window({
            onBeforeClose: function () { //当面板关闭之前触发的事件
                $('#PmsPersonInfoTab').datagrid('load');
                $("#add_msg").html("&nbsp;");
            }
        });


    });

    function onclick() {
        alert(0);
    }

</script>
<BODY>
<table id="PmsPersonInfoTab" class="easyui-datagrid" data-options="url:'/SSM/pmspersoninfo/selectPage.action',pageList:[10,20,22,30,40] ,
		       singleSelect:true,fit:true,fitColumns:true,toolbar:'#tb',pagination:true,idField:'',singleSelect:false,pageSize:22">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'id',sortable:true" width="20">id</th>
        <th data-options="field:'adress',sortable:true" width="20">adress</th>
        <th data-options="field:'personName',sortable:true" width="20">personName</th>
        <th data-options="field:'personNumber',sortable:true" width="20">personNumber</th>
        <th data-options="field:'userId',sortable:true" width="20">userId</th>
    </tr>
    </thead>
</table>

<!-- 搜索div,功能div -->
<div id="tb">
    <form method="post" id="PmsPersonInfoSearchForm">
        <div class="formDiv">
            <ul>
                <li>
                    personName：<INPUT class=textfield size=18 name="personName">
                </li>
                <li>
                    personNumber：<INPUT class=textfield size=18 name="personNumber">
                </li>
                <li>
                    id：<INPUT class=textfield size=18 name="id">
                </li>
                <li>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)"
                       onclick="FormData('PmsPersonInfoTab','PmsPersonInfoSearchForm')">查询</a>
                </li>
            </ul>
        </div>
    </form>
    <div class="operate">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="saveOpen('PmsPersonInfoSaveDiv','PmsPersonInfoSaveIframe')">新增</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="updateOpen('PmsPersonInfoTab','PmsPersonInfoUPdateDiv','PmsPersonInfoUPdateIframe','id')">修改(详细)</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"
           onclick="delete_info('PmsPersonInfoTab','pmspersoninfo','id')">删除</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="onclick()">按钮</a>
    </div>
</div>

<!-- 添加界面 -->
<div id="PmsPersonInfoSaveDiv" class="easyui-window" closed="true" modal="true" iconCls="icon-add" title="添加"
     style="width:530px;height:350px;text-align: center;">
    <iframe scrolling="auto" id="PmsPersonInfoSaveIframe" frameborder="0"
            style="width:97%;height:97%;margin-top: 1%;"></iframe>
</div>

<!-- 修改界面 -->
<div id="PmsPersonInfoUPdateDiv" class="easyui-window" closed="true" modal="true" iconCls="icon-edit" title="修改"
     style="width:530px;height:350px;text-align: center;">
    <iframe scrolling="auto" id="PmsPersonInfoUPdateIframe" frameborder="0"
            style="width:97%;height:97%;margin-top: 1%;"></iframe>
</div>

</BODY>
</html>

