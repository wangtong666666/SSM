<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
    <TITLE>修改密码</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <script type="text/javascript" src="../jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="../js/config.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/login.css">
    <script type="text/javascript" src="../js/login.js"></script>
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

            $("#userPassword").blur(function () {

                if ($("#userPassword").val().length < 6 || $("#userPassword").val().length > 18) {
                    $("#loginmsg").html('请输入6位到18位之间的密码！').css("color", "red");
                    return;
                }
                if (!($("#userPassword").val().match(/\d/) && $("#userPassword").val().match(/[a-zA-Z]/))) {
                    $("#loginmsg").html('密码必须包含大小写字母及数字！').css("color", "red");
                    return;
                }
                if (!($("#userPassword").val().match(/^[0-9a-zA-Z]+$/))) {
                    $("#loginmsg").html('密码只能包含字母及数字！').css("color", "red");
                    return;
                }
                $("#loginmsg").html('');
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

            //向后台发送处理数据
            $.post("../mobilenumber/getnumber.action?agentNumber=" + userName, function (data) {

            });
        }

        function getCheck() {

            if ($("#userPassword").val() == null || $("#userPassword").val() == "" || $("#userPassword").val() == "请输入密码") {
                $("#loginmsg").html('请输入新密码').css("color", "red");
                return;
            } else {
                $("#loginmsg").html('');
            }

            if ($("#userPassword2").val() == null || $("#userPassword2").val() == "" || $("#userPassword2").val() == "请二次确认密码") {
                $("#loginmsg").html('请输入确认密码').css("color", "red");
                return;
            } else {
                $("#loginmsg").html('');
            }
            if ($("#userPassword").val() != $("#userPassword2").val()) {
                $("#userPassword").val('');
                $("#userPassword2").val('');
                $("#loginmsg").html('两次密码输入不一致').css("color", "red");
                return;
            }
            if ($("#checkCodeNum").val() == null || $("#checkCodeNum").val() == "" || $("#checkCodeNum").val() == "填写验证码") {
                $("#loginmsg").html('请输入手机验证码').css("color", "red");
                return;
            } else {
                $("#loginmsg").html('');
            }

            $.ajax({
                type: 'post',
                url: '../login/updatePwd2.action',
                data: $("form").serialize(),
                success: function (data) {
                    if (data == '[1]') {
                        window.location = "../login/login.action?loginpath=app";
                    } else if (data == '[-1]') {
                        $("#loginmsg").html('手机验证码已过期请重新获取').css("color", "red");
                    } else if (data == '[-2]') {
                        $("#loginmsg").html('用户未输入手机验证码').css("color", "red");
                    } else if (data == '[-3]') {
                        $("#loginmsg").html('未查询到手机验证码').css("color", "red");
                    } else if (data == '[-4]') {
                        $("#loginmsg").html('手机验证码错误').css("color", "red");
                    } else if (data == '[-5]') {
                        $("#loginmsg").html('请输入手机验证码').css("color", "red");
                    } else if (data == '[6]') {
                        $("#loginmsg").html('密码长度不能大于6位').css("color", "red");
                    } else if (data == '[10]') {
                        $("#loginmsg").html('密码长度不能小于10位').css("color", "red");
                    } else if (data == '[21]') {
                        $("#loginmsg").html('密码不能包含特殊字符').css("color", "red");
                    } else if (data == '[22]') {
                        $("#loginmsg").html('密码必须包含大小写字母及数字').css("color", "red");
                    } else {
                        $("#loginmsg").html('变更失败').css("color", "red");
                    }
                }
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
    <SCRIPT type=text/javascript>
        $(function () {
                alert("您目前使用密码过于简单或者密码已过期，请立即修改密码");
                console.log($("form").serialize());
            }
        );

        function clearmsg() {
            $("#loginmsg").html('');
        }
    </SCRIPT>
    <META content="MSHTML 6.00.2900.5880" name=GENERATOR>
    <style type="text/css">
        .login {
            margin: auto;
            width: 100%;
        }
    </style>
</HEAD>
<body style="padding: 1px;margin: 0px;">
<img src="../images/background.png" width="100%" height="50%" tyle="z-index:-100;position:absolute;left:0;top:0">
<form id="form" name="AdminLogin" action="" method="post">
    <input type="hidden" id="loginpath" name="loginpath" value="app"/>
    <div class="login">
        <table style="line-height:45px;margin:auto;;padding-top:30px;">
            <tr>
                <td></td>
            </tr>
            <tr>
                <td style="color:#FFFFFF;line-height:30px;">&nbsp;&nbsp;</td>
            </tr>
            <tr>
                <td colspan=2>
                    <input type="hidden" value="${zt}" id="zt"/>
                    <INPUT id=userName name=loginName
                           style="IME-MODE: active;\" maxlength="11" class="Input_name_unfocus"
                           onmousedown="clearmsg()" value="${loginName }"/>
                </td>
                <td align="left">
                    <IMG id="user1" src="../images/true.jpg" style="display:none;CURSOR: pointer;" border=0/>
                    <IMG id="user2" src="../images/false.jpg" onclick="clearUser()"
                         style="display:none;CURSOR: pointer;" border=0/>
                </td>
            </tr>
            <tr>
                <td></td>
            </tr>
            <tr>
                <td style="color:#FFFFFF;line-height:20px;">&nbsp;&nbsp;</td>
            </tr>
            <tr>
                <td colspan=2>
                    <input type="password" id="userPassword" name="password" title="请输入密码"
                           value="" style="display:none" onclick="PasswordOnClick();" onblur="userPasswordOnBlur()"
                           style="IME-MODE: disabled;\" maxlength="20" class="Input_password_unfocus">
                    <input type="text" id="userpwd" value="请输入密码" maxlength="20" onclick="userPasswordOnClick(this);"
                           style="display:"
                           title="请输入密码" class="Input_password_unfocus"/>
                </td>
                <td>
                    <IMG id="password1" src="../images/true.jpg" style="display:none;CURSOR: pointer;" border=0/>
                    <IMG id="password2" src="../images/false.jpg" onclick="clearPassword()"
                         style="display:none;CURSOR: pointer;" border=0/>
                </td>
            </tr>
            <tr>
                <td></td>
            </tr>
            <tr>
                <td style="color:#FFFFFF;line-height:20px;">&nbsp;&nbsp;</td>
                <td></td>
            </tr>
            <tr>
                <td></td>
            </tr>
            <tr>
                <td colspan=2>
                    <input type="password" id="userPassword2" title="请二次确认密码"
                           value="" style="display:none" onclick="PasswordOnClick2();" onblur="userPasswordOnBlur2()"
                           style="IME-MODE: disabled;\" maxlength="20" class="Input_password_unfocus">
                    <input type="text" id="userpwd2" value="请二次确认密码" maxlength="20"
                           onclick="userPasswordOnClick2(this);" style="display:"
                           title="请二次确认密码" class="Input_password_unfocus"/>
                </td>
            </tr>

            <tr>
                <td>
                    <div class="checkCode">
                        <label class="phoneIcon"></label>
                        <input id="checkCodeNum" name="mobileNum" placeholder="填写验证码" type="text" size="6" required/>
                        <button type="button" id="getCheckCode" onclick="getCheckCodeFunc()">获取验证码</button>
                    </div>
                </td>
            </tr>

            <tr>
                <td style="color:#FFFFFF;line-height:30px;"></td>
            </tr>
            <tr>
                <td style="color:#FFFFFF;line-height:30px;"></td>
            </tr>
            <tr style="height: 80px;">
                <td colspan="3">
                    <input type="button" onclick="getCheck()"
                           style="width:250px; height:36px;margin-left: 0px; background:url(../images/updatebutton.jpg);">
                    <!--	 <input onclick="getCheck()" style="width:250px; height:36px;margin-left: 0px;" type="image" src="../images/updatebutton.jpg"/>
                           <input style="width:90px; height:36px;margin-left: 60px;" type="image" src="../images/resetbtn.png" value="重置"  onclick="doReset('LoginName','LoginPassword','code');"/>-->
                </td>
            </tr>

            <tr>
                <td colspan="3" align="center">
                    <span id="loginmsg" style="color: red;">${loginmsg}</span>
                </td>
            </tr>
        </table>
    </div>
</form>
</body>
</HTML>