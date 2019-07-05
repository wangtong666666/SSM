<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
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
    <script type="text/javascript" src="../../js/jkMenu1.js"></script>
    <script type="text/javascript">
        function menu_add() {
            var nodes = [];
            $("#jKTreeMenu").find('.tree-checkbox2').each(function () {
                var node = $(this).parent();
                nodes.push($.extend({}, $.data(node[0], 'tree-node'), {
                    target: node[0],
                    checked: node.find('.tree-checkbox').hasClass('tree-checkbox2')
                }));
            });
            var idArray = '';
            for (var i = 0; i < nodes.length; i++) {
                idArray += nodes[i].id + '-';
            }
            var nodes1 = $("#jKTreeMenu").tree('getChecked');
            for (var i = 0; i < nodes1.length; i++) {
                idArray += nodes1[i].id + '-';
            }
            idArray = idArray.substr(0, idArray.length - 1);
            var roleId = $('#roleId').val();
            $.ajax({
                url: '../../tsysrolemenu/delete_add.action?idArray=' + idArray + '&roleId=' + roleId,
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    if (data != null) {
                        $.messager.alert('信息提示', '菜单添加成功', 'info');
                    }
                }
            });
        }

    </script>
</HEAD>

<BODY id="aa">
<!-- 左侧界面(树状结构) -->
<div id="jkTree">
    <ul id="jKTreeMenu" class="easyui-tree"
        data-options="url:'../../tsysmenu/allMenuByRoleId.action?roleId=${param.pkName}',animate:true,checkbox:true"></ul>
    <input type="hidden" id="roleId" value="${param.pkName}"/>
</div>
<div style="width: 90%; height: 50px;">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="margin-top:10px;margin-left: 105px;"
       onclick="menu_add()">添加</a>
</div>
</BODY>
</HTML>
