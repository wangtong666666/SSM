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
        getData();
    });

    function getData() {
        $.post("../../tsysuser/selectUserOnline.action",
            function (data) {
                $("#mydg").datagrid({loadFilter: pagerFilter}).datagrid('loadData', data.userOnline);
                $("#userNum").html(data.userNum);
            }, 'json');
    }

    function pagerFilter(data) {
        if (typeof data.length == 'number' && typeof data.splice == 'function') {
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage: function (pageNum, pageSize) {
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                pager.pagination('refresh', {
                    pageNumber: pageNum,
                    pageSize: pageSize
                });
                dg.datagrid('loadData', data);
            }
        });
        if (!data.originalRows) {
            data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
    }

</script>

<BODY>
<table id="mydg" class="easyui-datagrid"
       data-options="rownumbers:true,pageSize:22,singleSelect:true,fit:true,fitColumns:true,toolbar:'#tb',pagination:true">
    <thead>
    <tr>
        <th field="loginName" width="80">登录账号</th>
        <th field="trueName" width="80">真实姓名</th>
        <th field="roleName" width="80">角色</th>
        <th field="depName" width="80">部门</th>
        <th field="agentName" width="80">所属服务商</th>
        <th field="oemName" width="80">所属总服务商</th>
        <th field="loginTime" width="80">登录时间</th>
    </tr>
    </thead>
</table>

<!-- 搜索div,功能div -->
<div id="tb">
    <div class="formDiv">
        <ul>
            <li>
                当前在线人数:&nbsp;&nbsp;&nbsp;&nbsp;<span id="userNum" style="color: green;">0</span>&nbsp;&nbsp;&nbsp;&nbsp;人。
            </li>
        </ul>
    </div>
    <div class="operate">
        <br/><br/><br/>
    </div>
</div>
</BODY>
</html>

