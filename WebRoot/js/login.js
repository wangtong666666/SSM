$(function () {
    $('#userpwd').focus(function ()//
    {
        document.getElementById("userpwd").style.display = "none";
        document.getElementById("userPassword").style.display = "";
        document.getElementById("userPassword").focus();
        document.getElementById("password1").style.display = "none";
        document.getElementById("password2").style.display = "none";
        $("#userPassword").removeClass().addClass("Input_password");
    })
    $('#userName').focus(function ()//
    {
        var txtUserName = $("#userName");
        document.getElementById("user1").style.display = "none";
        document.getElementById("user2").style.display = "none";
        if (txtUserName.val() == "请输入用户名") {
            $("#userName").val('');
            $("#userName").removeClass().addClass("Input_name");
        }
        if ($("#userName").attr("class") == "Input_name_error") {
            document.getElementById("userpwd").style.display = "";
            document.getElementById("userPassword").style.display = "none";
            document.getElementById("password1").style.display = "none";
            document.getElementById("password2").style.display = "none";
            $("#userPassword").val('');
        }
    })
    $('#code').focus(function ()//
    {
        var txtcode = $("#code");
        if (txtcode.val() == "验证码") {
            $("#code").val('');
        }
        $("#code").removeClass().addClass("Input_code");
    })
    $('#userpwd2').focus(function ()//
    {
        document.getElementById("userpwd2").style.display = "none";
        document.getElementById("userPassword2").style.display = "";
        document.getElementById("userPassword2").focus();
        document.getElementById("password1").style.display = "none";
        document.getElementById("password2").style.display = "none";
        $("#userPassword2").removeClass().addClass("Input_password");
    })
})

function clearmsg() {//清除提示信息
    $("#loginmsg").html('');
}

function clearUser() {//清除用户信息
    $("#userName").val('');
    $("#userName").focus();
    document.getElementById("user1").style.display = "none";
    document.getElementById("user2").style.display = "none";
}

function clearPassword() {//清除密码信息
    $("#userPassword").val('');
    $("#userPassword").focus();
    document.getElementById("password1").style.display = "none";
    document.getElementById("password2").style.display = "none";
}

function changeImg() {//变换验证码
    $("#code").val('');
    $("#code").focus();
    $("#code").removeClass().addClass("Input_code");
    var imgSrc = $("#imgObj");
    var src = imgSrc.attr("src");
    imgSrc.attr("src", chgUrl(src));
}

function chgUrl(url) {//变换验证码
    var timestamp = (new Date()).valueOf();
    url = url + "?timestamp=" + timestamp;
    return url;
}

function reset_checkeds() {//重置复选按钮
    $("#rmbPassword").attr('checked', true);
    $("#autoLogin").attr('checked', false);
}

function resetOnClick() {//重置选项和输入框
    document.getElementById("userpwd").style.display = "";
    document.getElementById("userPassword").style.display = "none";
    document.getElementById("user1").style.display = "none";
    document.getElementById("user2").style.display = "none";
    document.getElementById("password1").style.display = "none";
    $("#userpwd").removeClass().addClass("Input_password_unfocus");
    $("#userName").removeClass().addClass("Input_name_unfocus");
    $("#code").removeClass().addClass("Input_code_unfocus");
    $("#userPassword").val('');
    $("#userName").val('请输入用户名');
    $("#code").val('验证码');
}

function userNameOnClick() {//用户名点击事件
    var txtUserName = $("#userName");
    document.getElementById("user1").style.display = "none";
    document.getElementById("user2").style.display = "none";
    if (txtUserName.val() == "请输入用户名") {
        $("#userName").val('');
        $("#userName").removeClass().addClass("Input_name");
    }
    if ($("#userName").attr("class") == "Input_name_error") {
        document.getElementById("userpwd").style.display = "";
        document.getElementById("userPassword").style.display = "none";
        document.getElementById("password1").style.display = "none";
        document.getElementById("password2").style.display = "none";
        $("#userPassword").val('');
    }
}

function userNameOnBlur() {//用户名离开事件
    var txtUserName = $("#userName").val();
    if (txtUserName == "" || txtUserName == null) {
        $("#userName").val('请输入用户名');
        $("#userName").removeClass().addClass("Input_name_unfocus");
    }
}

function userPasswordOnClick(obj) {//密码点击事件
    obj.style.display = "none";
    document.getElementById("userPassword").style.display = "";
    document.getElementById("userPassword").focus();
    document.getElementById("password1").style.display = "none";
    document.getElementById("password2").style.display = "none";
    $("#userPassword").removeClass().addClass("Input_password");
}

function PasswordOnClick() {//密码点击事件
    document.getElementById("password1").style.display = "none";
    document.getElementById("password2").style.display = "none";
}

function userPasswordOnBlur() {//密码离开事件
    var txtUserPassword = $("#userPassword");
    if (txtUserPassword.val() == "" || txtUserPassword.val() == null) {
        document.getElementById("userPassword").style.display = "none";
        document.getElementById("userpwd").style.display = "";
        $("#userpwd").removeClass().addClass("Input_password_unfocus");
    }
}

function codeOnClick() {//验证码点击事件
    clearmsg();
    var txtcode = $("#code");
    if (txtcode.val() == "验证码") {
        $("#code").val('');
    }
    $("#code").removeClass().addClass("Input_code");
}

function codeOnBlur() {//验证码离开事件
    var txtcode = $("#code");
    if (txtcode.val() == "" || txtcode.val() == null) {
        $("#code").val('验证码');
        $("#code").removeClass().addClass("Input_code_unfocus");
    }
}

function userPasswordOnClick2(obj) {//密码点击事件
    obj.style.display = "none";
    document.getElementById("userPassword2").style.display = "";
    document.getElementById("userPassword2").focus();
    document.getElementById("password1").style.display = "none";
    document.getElementById("password2").style.display = "none";
    $("#userPassword2").removeClass().addClass("Input_password");
}

function PasswordOnClick2() {//密码点击事件
    document.getElementById("password1").style.display = "none";
    document.getElementById("password2").style.display = "none";
}

function userPasswordOnBlur2() {//密码离开事件
    var txtUserPassword = $("#userPassword2");
    if (txtUserPassword.val() == "" || txtUserPassword.val() == null) {
        document.getElementById("userPassword2").style.display = "none";
        document.getElementById("userpwd2").style.display = "";
        $("#userpwd2").removeClass().addClass("Input_password_unfocus");
    }
}