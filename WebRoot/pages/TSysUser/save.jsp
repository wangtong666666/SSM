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
        $(function () {
            var date = new Date();
            var strDate = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
            $("#pdate").val(strDate);

            $('#email').blur(function () {
                var email = $("#email").val();
                //对电子邮件的验证
                var Myemail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
                if (!Myemail.test(email)) {
                    $.messager.alert('错误信息!', '请输入正确的E_mail!', 'info');
                    $('#email').val('');
                }
            });

            $('#mobileno').blur(function () {
                var num = $("#mobileno").val();
                var partten = /^1[3,5,8]\d{9}$/;
                if (!partten.test(num)) {
                    $.messager.alert('错误信息!', '请填写正确的手机号!', 'info');
                    $('#mobileno').val('');
                }
            });

        });

    </script>
</HEAD>
<BODY>
<!-- 添加界面 -->
<div class="easyui-layout" data-options="fit:true">
    <form id="TSysUserSaveForm" method="post">
        <div data-options="region:'center'" style="padding:10px;">
            <p>角色名称:&nbsp;&nbsp;
                <input class="easyui-combobox" id="id" name="roleId" style="width: 130px" editable="false"
                       data-options="
					        	valueField: 'roleId',    
					        	textField: 'roleName',
					        	panelHeight:110,    
					        	url: '/ky_oem/tsysrole/select.action',    
					        	onLoadSuccess:function(){
			    					$(this).combobox('setText','请选择');
			    				}">
                </input></p>
            <p id="ss">密码创建时间:&nbsp;&nbsp;<input class="easyui-validatebox" id="pdate"
                                                 data-options="required:true,missingMessage:'字段不可以为空'" name="pwdDate">
            </p>
            <p>电子邮件:&nbsp;&nbsp;<input class="easyui-validatebox" id="email"
                                       data-options="required:true,missingMessage:'字段不可以为空'" name="email"></p>


            <p>部门名称:&nbsp;&nbsp;
                <input id="departmentNum" class="easyui-combobox" name="departmentId" editable="false" data-options="
        					valueField: 'departmentNum',   
        					textField: 'departmentName',   
       						url:'/ky_oem/pmsdepartment/select.action', 
       						onLoadSuccess:function(){
			    					$(this).combobox('setText','请选择');
			    				},  
        					onSelect: function(rec){   
        					var url='/ky_oem/pmsperson/select.action?departmentId='+rec.departmentNum;    				
        					$('#tname').combobox('clear');  
         					$('#tname').combobox('reload', url);   	 
      							 }"/>
            </p>
            <p>真实姓名:&nbsp;&nbsp;
                <input id="tname" name="trueName" class="easyui-combobox" editable="false"
                       data-options="valueField:'personName',textField:'personName'"/>
            </p>

            <p>登录名:&nbsp;&nbsp;<input class="easyui-validatebox" data-options="required:true,missingMessage:'字段不可以为空'"
                                      name="loginName"></p>
            <p>用户状态:&nbsp;&nbsp;<select class="easyui-validatebox" style="width: 100px;"
                                        data-options="required:true,missingMessage:'字段不可以为空'" name="userStatus">
                <option value="0">开通</option>
                <option value="1">无效</option>
            </select></p>
            <p>登录密码:&nbsp;&nbsp;<input class="easyui-validatebox" data-options="required:true,missingMessage:'字段不可以为空'"
                                       name="loginPwd"></p>
            <p>手机号:&nbsp;&nbsp;<input class="easyui-validatebox" id="mobileno"
                                      data-options="required:true,missingMessage:'字段不可以为空'" name="mobileno"></p>
            <p><span id="add_msg" style="margin-left: 60px;color: green;font: 13px;font-weight: bolder;">&nbsp;</span>
            </p>
        </div>
        <div data-options="region:'south',border:false" style="text-align:right;padding:10px;padding-bottom: 5px;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
               style="margin-right: 15px " onclick="add_save()">添加</a>
            <a class="easyui-linkbutton" data-options="iconCls:'icon-cut'" href="javascript:void(0)"
               style="margin-right: 15px " onclick="doReset('TSysUserSaveForm')">重置</a>
        </div>
    </form>
</div>
<script type="text/javascript">
    function add_save() {
        var id = $("#id").combobox('getText');
        var num = $("#departmentNum").combobox('getText');
        if (id == "请选择") {
            alert("请选择角色名称！");
            $('#id').combobox('clear');
        }
        if (num == "请选择") {
            alert("请选择部门名称！");
            $('#departmentNum').combobox('clear');
        } else {
            $("#TSysUserSaveForm").form('submit', {
                url: "/ky_oem/tsysuser/save.action",
                dataType: 'text',
                data: $("#tsysuser").serialize(),
                success: function (data) {
                    var newData = $.parseJSON(data);
                    if (newData == 1) {
                        $("#TSysUserSaveForm input").val('');
                        $("#add_msg").html("添加成功,可以继续添加");
                    } else {
                        $("#add_msg").html("添加失败,请查看添加信息是否正确!!!");
                    }
                }
            });
        }
    }
</script>
</BODY>
</html>

