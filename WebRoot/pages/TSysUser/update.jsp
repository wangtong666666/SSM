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
            url: '../../tsysuser/select.action',
            data: {'id':${param.pkName}},
            error: function () {// 请求失败处理函数
                alert('请求失败');
            },
            success: function (data) { // 请求成功后处理函数。
                $('#roleId').combobox({
                    valueField: 'roleId',
                    textField: 'roleName',
                    panelHeight: 100,
                    url: '../../tsysrole/select.action'
                });

                $('#departmentId').combobox({
                    valueField: 'departmentNum',
                    textField: 'departmentName',
                    url: '../../pmsdepartment/select.action',
                    onSelect: function (rec) {
                        var url = '../../pmsperson/select.action?departmentId=' + rec.departmentNum;
                        $('#tname').combobox('clear');
                        $('#tname').combobox('reload', url);
                    }

                });

                var inputValArray = $('#TSysUserUpdateForm').serialize().split('&');
                for (var i = 0; i < inputValArray.length; i++) {
                    var inputName = inputValArray[i].split('=')[0];
                    if (inputName == 'trueName') {
                        $('#tname').combobox('setValue', row_info(data[0], inputName));
                    }
                    $("#TSysUserUpdateForm [name='" + inputName + "']").val(row_info(data[0], inputName));
                }
            }
        });
    });
</script>
<BODY>
<!-- 修改界面 -->
<div class="easyui-layout" data-options="fit:true">
    <form id="TSysUserUpdateForm" method="post">
        <div data-options="region:'center'" style="padding:10px;" align="center">
            <p>角色名称:&nbsp;&nbsp;
                <input class="easyui-combobox" name="roleId" id="roleId" style="width: 130px;" editable="false"
                       disabled="disabled">
            </p>
            <p>电子邮件:&nbsp;&nbsp;<input class="easyui-validatebox" id="email"
                                       data-options="required:true,missingMessage:'字段不可以为空'" name="email"
                                       readonly="readonly" style="color: #808080;"></p>
            <p>真实姓名:&nbsp;&nbsp;
                <input id="tname" name="trueName" editable="false" class="easyui-combobox"
                       data-options="valueField:'personName',textField:'personName'" disabled="disabled"/>
            </p>
            <p>登 录 名:&nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-validatebox" style="color: #808080;"
                                                    data-options="required:true,missingMessage:'字段不可以为空'"
                                                    name="loginName" readonly="readonly"></p>
            <p>用户状态:&nbsp;&nbsp;&nbsp;<select class="easyui-validatebox" style="width: 130px;" size="20px"
                                              data-options="required:true,missingMessage:'字段不可以为空'" name="userStatus"
                                              disabled="disabled">
                <option value="0">开通</option>
                <option value="1">无效</option>
            </select></p>
            <p>手 机 号:&nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-validatebox" style="color: #808080;" id="mobileno"
                                                    data-options="required:true,missingMessage:'字段不可以为空'"
                                                    name="mobileno" readonly="readonly"></p>

        </div>
    </form>
</div>
</BODY>
</html>

