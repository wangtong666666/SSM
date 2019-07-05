<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
    <TITLE>信息管理后台</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <script type="text/javascript" src="../jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="../js/config.js"></script>
    <script type="text/javascript" src="../js/login.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/login.css">
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
        var count = 200;
        var curCount;
        var codeLength = 6;

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
            var zt = $("#zt").val();
            if (zt == '1') {
                alert('密码修改成功,请重新登陆！');
            }
            if (zt == '0') {
                alert('密码修改失败！！！');
            }

            //	    var w=$(document).width();
            //	    var h=$(document).height();
            //	    $("#bodyimg").width(1919);
            //	    $("#bodyimg").height(945);
            //	    $(".login").offset({ top: 473});
        })

        //点击变幻验证码图片函数
        function changeImg() {
            var imgSrc = $("#imgObj");
            var src = imgSrc.attr("src");
            imgSrc.attr("src", chgUrl(src));
        }

        //时间戳
        //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
        function chgUrl(url) {
            var timestamp = (new Date()).valueOf();
            url = url + "?timestamp=" + timestamp;
            return url;
        }

        $(function () {
                $('#form').submit(
                    function () {
                        var temp = /^\d+(\.\d+)?$/;
                        var s = document.getElementById("userName");
                        if (temp.test(s.value) == false) {
                            $("#loginmsg").html('输入的账号不符合').css("color", "blue");
                            return false;
                        } else {
                            $("#loginmsg").html('');
                        }
                        if ($("#userName").val() == null || $("#userName").val() == "" || $("#userName").val() == "请输入用户名") {
                            $("#loginmsg").html('用户名不可为空').css("color", "blue");
                            return false;
                        } else {
                            $("#loginmsg").html('');
                        }
                        if ($("#userPassword").val() == null || $("#userPassword").val() == "" || $("#userPassword").val() == "请输入密码") {
                            $("#loginmsg").html('密码不可为空').css("color", "blue");
                            return false;
                        } else {
                            $("#loginmsg").html('');
                        }
                        if ($("#code").val() == null || $("#code").val() == "" || $("#code").val() == "验证码") {
                            $("#loginmsg").html('验证码不可为空').css("color", "blue");
                            return false;
                        }
                        //		if($("#checkCodeNum").val()==null || $("#checkCodeNum").val()==""|| $("#checkCodeNum").val()=="验证码"){
                        //			$("#loginmsg").html('验证码不可为空').css("color","blue");
                        //			return false;
                        //		}
                        return true;
                    }
                );
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
            margin-top: 5%;
            width: 100%;
        }
    </style>
</HEAD>
<body style="width:100%;height:100%;">
<img src="../images/background.png" width="100%" height="50%" style="z-index:-100;position:absolute;left:0;top:0">


<form id="form" name=AdminLogin action="../login/main.action" method="post">
    <input type="hidden" id="loginpath" name="loginpath" value="app"/>
    <input type="hidden" value="${zt}" id="zt"/>
    <div class="login">
        <table style="line-height:45px;margin:auto;;padding-top:370px;">
            <tr>
                <td></td>
            </tr>
            <tr>
                <td style="color:#FFFFFF;line-height:30px;">&nbsp;&nbsp;</td>
            </tr>
            <tr>
                <c:if test="${not empty loginname}">
                    <td colspan=2>
                        <INPUT id=userName name=loginName
                               onblur="userNameOnBlur()"
                               style="IME-MODE: active;\" maxlength="11" class="Input_name_unfocus"
                               onmousedown="clearmsg()" value="${loginName }"/>
                    </td>
                </c:if>
                <c:if test="${empty loginname}">
                    <td colspan=2>
                        <INPUT id=userName name=loginName
                               onblur="userNameOnBlur()"
                               style="IME-MODE: active;\" value="请输入用户名" maxlength="11" class="Input_name_unfocus"
                               onmousedown="clearmsg()" value="${loginName }"/>
                    </td>
                </c:if>
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
                    <input type="password" id="userPassword" name="loginPwd" title="请输入密码"
                           value="" style="display:none" onblur="userPasswordOnBlur()" onmousedown="clearmsg()"
                           style="IME-MODE: disabled;\" maxlength="20" class="Input_password_unfocus">
                    <input type="text" id="userpwd" value="请输入密码" maxlength="20" onclick="userPasswordOnClick(this);"
                           style="display:"
                           title="请输入密码" class="Input_password_unfocus" onmousedown="clearmsg()"/>
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
                <td><%--
							<INPUT id=code maxLength=5 name=code
								title="请输入验证码" value="验证码"  maxLength=5  onblur="codeOnBlur()"  onmousedown="clearmsg()"
                                 style="IME-MODE: disabled;" class="Input_code_unfocus" value="${code}">
                                 </td>
                             <td>
								<div style="width:55px; float:left;">
									<IMG id="imgObj" onclick="changeImg()" alt="点击刷新" src="${pageContext.request.contextPath}/verifycode/verifyCode.action" border=0 />
								</div>
							</td> --%>

            </tr>


            <!--<tr>
                <td>
                    <div class="checkCode">
                        <label class="phoneIcon"></label>
                        <input id="checkCodeNum" name="mobileNum" placeholder="填写验证码" type="text" size="6" required/>
                        <button type="button" id="getCheckCode" onclick="getCheckCodeFunc()">获取验证码</button>
                    </div>
                </td>
            </tr>
            -->
            <tr>
                <td style="color:#FFFFFF;line-height:30px;"></td>
            </tr>

            <tr>
                <td style="color:#FFFFFF;line-height:30px;"></td>
            </tr>
            <tr style="height: 80px;">
                <td colspan="3">
                    <input style="width:250px; height:36px;margin-left: 0px;" type="image"
                           src="../images/bluebutton.jpg"/>
                    <!--  	 <input style="width:90px; height:36px;margin-left: 60px;" type="image" src="../images/resetbtn.png" value="重置"  onclick="doReset('LoginName','LoginPassword','code');"/>-->
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