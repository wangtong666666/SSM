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
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: '../../tsysrole/select.action',
            data: {'roleId':${param.pkName}},
            error: function () {// 请求失败处理函数
                alert('请求失败');
            },
            success: function (data) { // 请求成功后处理函数。
                var inputValArray = $('#TSysRoleUpdateForm').serialize().split('&');
                for (var i = 0; i < inputValArray.length; i++) {
                    var inputName = inputValArray[i].split('=')[0];
                    $("#TSysRoleUpdateForm [name='" + inputName + "']").val(row_info(data[0], inputName));
                }
            }
        });
    });

    function check() {
        if ($("#levels").val() == 0) {
            alert("请选中等级");
            $("#levels").focus();
        }
    }
</script>
<BODY>
<!-- 修改界面 -->
<div class="easyui-layout" data-options="fit:true">
    <form id="TSysRoleUpdateForm" method="post">
        <div data-options="region:'center'" style="padding:10px;">
            <input type='hidden' name="roleId">
            <p>角色等级:&nbsp;&nbsp;
                <select id="levels" name="levels">
                    <option value="0">请选择</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                </select>
            </p>
            <p>角色名称:&nbsp;&nbsp;<input onblur="check()" class="easyui-validatebox"
                                       data-options="required:true,missingMessage:'字段不可以为空'" name="roleName"></p>
            <p><span id="update_msg"
                     style="margin-left: 60px;color: green;font: 13px;font-weight: bolder;">&nbsp;</span></p>
        </div>
        <div data-options="region:'south',border:false" style="text-align:right;padding:10px;padding-bottom: 5px;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
               onclick="update_save('TSysRoleUpdateForm','tsysrole')">修改</a>
        </div>
    </form>
</div>

</BODY>
</html>

