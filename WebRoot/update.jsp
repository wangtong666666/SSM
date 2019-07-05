<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
    <meta charset="UTF-8">
    <title>后台菜单</title>
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.2/themes/metro_blue/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.2/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.2/themes/demo.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <script type="text/javascript" src="jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/config.js"></script>
    <script type="text/javascript">
        $(function () {

            $("#userName").blur(function () {
                var userName = $("#userName").val();
                var times = getTimes(userName);
                if (times == 0) {
                    return;
                }
                Countdown(times);
            });
        })

        var InterValObj;
        var count = 60;
        var curCount;

        function getCheckCodeFunc() {
            var userName = $("#userName").val();
            if (userName == '请输入用户名') {
                alert("请输入用户名");
                return;
            }

            setTimes(userName + "-" + getTimestamp());         // 设置该用户倒计时时间
            Countdown(count);					//开始倒计时

            //向后台/发送处理数据
            $.post("/LKLM_AGENT/mobilenumber/getnumber.action?agentNumber=" + userName, function (data) {

            });
        }

        //开始倒计时
        function Countdown(times) {
            curCount = times;
            $("#getCheckCode").attr("disabled", "true");
            $("#getCheckCode").val(curCount + "秒内填写");
            InterValObj = window.setInterval(SetRemainTime, 1000);
            console.log(InterValObj);
        }

        //设置某用户时间
        function setTimes(str) {

            var times = getCookieTimes();

            if (times == '') {
                document.cookie = "times=" + str;
            } else {
                document.cookie = "times=" + times + "," + str;
            }
        }

        //获取某用户剩余倒计时秒数
        function getTimes(userName) {
            var t = 0;
            var timestamp = 0;
            var times = getCookieTimes();

            var arrTimes = times.split(",");
            for (var i = 0; i < arrTimes.length; i++) {
                var arr2 = arrTimes[i].split("-");
                if (arr2[0] == userName) {
                    if (arr2[1] > timestamp) {
                        timestamp = arr2[1];
                    }
                }
            }

            var s = parseInt((parseInt(getTimestamp()) - parseInt(timestamp)) / 1000);

            if (s < 200) {
                t = count - s;
            }
            return t;
        }

        //获取 cookie名字为 times的值
        function getCookieTimes() {
            var times = '';
            //获取cookie字符串
            var strCookie = document.cookie;
            //将多cookie切割为多个名/值对
            var arrCookie = strCookie.split(";");
            //遍历cookie数组，处理每个cookie对
            for (var i = 0; i < arrCookie.length; i++) {
                var arr = arrCookie[i].split("=");
                //找到名称为times的cookie，并返回它的值
                if ("times" == arr[0]) {
                    times = arr[1];
                    break;
                }
            }
            return times;
        }

        function SetRemainTime() {
            console.log(curCount);
            if (curCount == 0) {
                window.clearInterval(InterValObj);
                $("#getCheckCode").removeAttr("disabled");
                $("#getCheckCode").text("重新获取验证码");
            } else {
                curCount--;
                $("#getCheckCode").text(curCount + "秒内填写");
            }
        }

        //获取当前时间戳
        function getTimestamp() {
            var timestamp = new Date().getTime();
            return timestamp;
        }
    </script>
    <script type="text/javascript">
        function checkPwd() {
            $("#update_msg").val("");
            var pwd = $('#LoginPassword').val();
            var pwd2 = $('#code').val();

            if (pwd != pwd2) {
                $("#update_msg").text("两次密码输入不一致");
            }
        }

        function clearPwd() {
            $("#update_msg").text("");
        }

        function inputcheck() {

            $("#update_msg").val("");
            var pwd = $('#LoginPassword').val();
            var pwd2 = $('#code').val();

            if (pwd == null || pwd == "") {
                $("#update_msg").text("请输入密码");
                return;
            }

            if (pwd.length < 6 || pwd.length > 18) {
                $("#update_msg").text("请输入6位到18位之间的密码！");
                return;
            }
            if (!(pwd.match(/\d/) && pwd.match(/[a-zA-Z]/))) {
                $("#update_msg").text("您输入的密码至少包含一个数字和一个字母！");
                return;
            }
            if (!(pwd.match(/^[0-9a-zA-Z]+$/))) {
                $("#update_msg").text("密码只能包含字母及数字！！");
                return;
            }
            if (pwd2 == null || pwd2 == "") {
                $("#update_msg").text("请输入密码确认");
                return;
            }
            if (pwd != pwd2) {
                $("#update_msg").text("两次密码输入不一致");
                return;
            }

            updatePwd_save('LoginUpdateForm', 'login');
        }

    </script>
</HEAD>
<BODY>
<!-- 修改界面 -->
<div>
    <form id="LoginUpdateForm" method="post">
        <div data-options="region:'center'" style="padding:10px; text-align: center;">
            <input type='hidden' name="id">
            <br/>
            <p>&nbsp;&nbsp;用户名称:&nbsp;&nbsp;<input id="userName" class="easyui-validatebox" readonly="readonly"
                                                   name="loginName" value="${user.loginName}"/><span
                    id="required">*</span></p>

            <p>&nbsp;&nbsp;&nbsp;新&nbsp;密&nbsp;码:&nbsp;&nbsp;<input class="easyui-validatebox"
                                                                    data-options="required:true,missingMessage:'不可以为空'"
                                                                    name="newPwd" id="LoginPassword"
                                                                    type="password"/><span id="required">*</span></p>
            <p>&nbsp;确认密码:&nbsp;&nbsp;<input class="easyui-validatebox"
                                             data-options="required:true,missingMessage:'不可以为空'" name="loginPwd"
                                             onblur="checkPwd()" onfocus="clearPwd()" id="code" type="password"/><span
                    id="required" class="required">*</span></p>
            <p>
                <font style="color:red">注：密码需同时包含字母和数字且长度在6到18位之间</font>
            </p>
            <p>
            <div class="checkCode">
                <label class="phoneIcon"></label>
                <input id="checkCodeNum" name="mobileNum" placeholder="填写验证码" type="text" size="6" required/>
                <button type="button" id="getCheckCode" onclick="getCheckCodeFunc()">获取验证码</button>
            </div>
            <p>
            <p><span id="update_msg" style="margin-left: 60px;color: green;font: 13px;font-weight: bolder;"></span></p>
            <div align="right" style="padding-right: 80px; margin-bottom: 10px;"><em style="color: red;">*</em>为必填项
            </div>
            <div data-options="region:'south',border:false" style="text-align:center;padding:25px;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                   onclick="inputcheck()">修改</a>
            </div>
        </div>
    </form>
</div>
</BODY>
</html>

