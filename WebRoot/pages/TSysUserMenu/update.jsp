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
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: '../../tsysusermenu/select.action',
            data: {'sumId':${param.pkName}},
            error: function () {// 请求失败处理函数
                alert('请求失败');
            },
            success: function (data) { // 请求成功后处理函数。
                var inputValArray = $('#TSysUserMenuUpdateForm').serialize().split('&');
                for (var i = 0; i < inputValArray.length; i++) {
                    var inputName = inputValArray[i].split('=')[0];
                    $("#TSysUserMenuUpdateForm [name='" + inputName + "']").val(row_info(data[0], inputName));
                }
            }
        });
    });
</script>
<BODY>
<!-- 修改界面 -->
<div class="easyui-layout" data-options="fit:true">
    <form id="TSysUserMenuUpdateForm" method="post">
        <div data-options="region:'center'" style="padding:10px;">
            <input type='hidden' name="sumId">
            <p>menuId:&nbsp;&nbsp;<input class="easyui-validatebox"
                                         data-options="required:true,missingMessage:'字段不可以为空'" name="menuId"></p>
            <p>userId:&nbsp;&nbsp;<input class="easyui-validatebox"
                                         data-options="required:true,missingMessage:'字段不可以为空'" name="userId"></p>
            <p><span id="update_msg"
                     style="margin-left: 60px;color: green;font: 13px;font-weight: bolder;">&nbsp;</span></p>
        </div>
        <div data-options="region:'south',border:false" style="text-align:right;padding:10px;padding-bottom: 5px;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
               onclick="update_save('TSysUserMenuUpdateForm','tsysusermenu')">修改</a>
        </div>
    </form>
</div>

</BODY>
</html>

