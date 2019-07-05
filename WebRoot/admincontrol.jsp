<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>主界面</title>
</head>
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.2/themes/metro_blue/easyui.css">
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.2/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.2/themes/demo.css">
<link rel="stylesheet" type="text/css" href="../css/main.css">
<script type="text/javascript" src="../jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/config.js"></script>

<script type="text/javascript">
    $(function () {

        window.setInterval("checkuser()", 30 * 60 * 1000 + 1000);//session默认30分钟失效，每过30分零1秒验证一次
//	          $("#LoginUPdateDiv").dialog({
        //	       		onBeforeClose: function () { //当面板关闭之前触发的事件
//		         		window.location.href="../login/login.action?loginpath=${loginpath}";
        //	       		}
//		 		});
        setInterval("getCurrentTime()", "1000");//获得系统时间
        $('#menu ul').each(function () {
            $(this).tree({
                data: $.parseJSON($(this).attr('url')),
                onClick: function (node) {
                    temp = node;
                    if ($('#menu ul').tree('isLeaf', temp.target)) {
                        //最内层的菜单点击事件时,在主页面中加载tab
                        addTab(temp.text, temp.attributes);
                    }
                }
            })
        });
        //	$.ajax({
        //			type : 'post',
        //		dataType : 'json',
        //		url:'${pageContext.request.contextPath}/noticeinfo/select.action?platform=2',
        //			error : function() {// 请求失败处理函数
        //					alert('请求失败');
        //			},
        //			success : function(data) { // 请求成功后处理函数。
        //				for(var i =0;i<data.length;i++){
        //						$("#table").prepend($("<div>"+data[i].content+"</div><br/>"));
        //				}
        //			}
        //		});
    });

    //验证session是否失效
    function checkuser() {
        $.post("../login/checkUser.action",
            function (data) {
                if (data == 0) {
                    alert("由于您长时间未进行操作，为了保障您的数据安全，系统将为您自动跳转到登录页面，请重新登录！");
                    var loginpath = $("#loginpath").val();
                    window.location = "../login/login.action?loginpath=" + loginpath;
                }
            }, "json");
    }

    //获得系统时间
    function getCurrentTime() {
        $("#timeInfo").html(new Date().toLocaleString() + '  星期' + '日一二三四五六'.charAt(new Date().getDay()));
    }

    function changeWindow() {
        $("#maindiv").width(1920).height(946).resize();

    }

    function addTab(title, url) {
        if ($("#centre").tabs("exists", title)) {
            $("#centre").tabs("close", title);
        }
        var content = '<iframe scrolling="no" frameborder="0"  src="' + url + '" style="width:99%;height:98%;margin:3px;"></iframe>';
        $("#centre").tabs("add", {
            title: title,
            content: content,// 这里的content里放你想要显示的东西
            closable: true
        });
        tabClose();
    }

    $(function () {
        tabClose();
        tabCloseEven();
    })

    function tabClose() {
        /*双击关闭TAB选项卡*/
        $(".tabs-inner").dblclick(function () {
            var subtitle = $(this).children("span").text();
            $('#centre').tabs('close', subtitle);
        })

        $(".tabs-inner").bind('contextmenu', function (e) {
            $('#mm').menu('show', {
                left: e.pageX,
                top: e.pageY,
            });

            var subtitle = $(this).children("span").text();
            $('#mm').data("currtab", subtitle);

            return false;
        });
    }

    //绑定右键菜单事件
    function tabCloseEven() {
        //关闭当前
        $('#mm-tabclose').click(function () {
            var currtab_title = $('#mm').data("currtab");
            $('#centre').tabs('close', currtab_title);
        })
        //全部关闭
        $('#mm-tabcloseall').click(function () {
            $('.tabs-inner span').each(function (i, n) {
                var t = $(n).text();
                $('#centre').tabs('close', t);
            });
        });
        //关闭除当前之外的TAB
        $('#mm-tabcloseother').click(function () {
            var currtab_title = $('#mm').data("currtab");
            $('.tabs-inner span').each(function (i, n) {
                var t = $(n).text();
                if (t != currtab_title)
                    $('#centre').tabs('close', t);
            });
        });

        //关闭当前右侧的TAB
        $('#mm-tabcloseright').click(function () {
            var nextall = $('.tabs-selected').nextAll();
            if (nextall.length == 0) {
                return false;
            }
            nextall.each(function (i, n) {
                var t = $('a:eq(0) span', $(n)).text();
                $('#centre').tabs('close', t);
            });
            return false;
        });
        //关闭当前左侧的TAB
        $('#mm-tabcloseleft').click(function () {
            var prevall = $('.tabs-selected').prevAll();
            if (prevall.length == 0) {
                return false;
            }
            prevall.each(function (i, n) {
                var t = $('a:eq(0) span', $(n)).text();
                $('#centre').tabs('close', t);
            });
            return false;
        });
    }
</script>

<body style="margin: 0px;padding: 0px;font-family: '宋体';">
<div id="maindiv" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false"
         style="height:80px;background: url('../images/${agentNumber}.png') no-repeat;background-color:#DAEEF5;padding:0px;">
        <div style="float: right;margin-top: 35px;margin-right: 10px;" id="timeInfo"></div>
        <div style="float: right;margin-top: 35px;margin-right: 20px;">欢迎你:
            <span id="userName">${user.loginName}</span>&nbsp;<span>[${Name }]</span>
        </div>
        <input type="hidden" value="${loginpath}" id="loginpath"/>
        <div style="float: right;margin-top: 30px;margin-right: 20px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-exit" plain="true" onclick="exit()"
               id="exit">退出</a>
        </div>
        <!-- 修改密码  -->
        <div style="float: right;margin-top: 29px;margin-right: 15px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-editpassword" plain="true"
               onclick="openDiv()" id="changepwd">修改密码</a>
        </div>
        <c:if test="${loanYN==1}">
            <div style="float: right;margin-top: 35px;margin-right: 20px;"><span style="color:red;">账户余额:
			    [${balance}]元</span>
            </div>
        </c:if>
    </div>
    <div data-options="region:'west',split:true" title="系统功能列表" style="width:250px;">
        <div class="easyui-accordion" data-options="fit:true,border:false" id="menu">
            <c:forEach items="${menuList}" var="menu">
                <div style="padding-top: 10px;" title="${menu.text}">
                    <ul id="tree${menu.id}" class="easyui-tree" data-options="animate:true" url='${menu.children}'>
                    </ul>
                </div>
            </c:forEach>
        </div>
    </div>


    <div id="LoginUPdateDiv" class="easyui-dialog" closed="true" modal="true" iconCls="icon-edit" title="修改密码"
         style="width:600px;height:430px;text-align: center;">
        <iframe scrolling="auto" id="LoginUPdateIframe" frameborder="0" src="../update.jsp"
                style="width:97%;height:97%; margin-top: 1%;"></iframe>
    </div>

    <div data-options="region:'south',border:false"
         style="height:50px;background:#DAEEF5;padding:10px;text-align: center;color: #0F61DC;overflow: hidden;">
        <p style="margin-top: -2px;">信息技术管理平台 </p>
    </div>

    <div data-options="region:'center',iconCls:'icon-ok'"
         style=" background-image: url('jquery-easyui-1.3.2/themes/metro_blue/images/top.jpg');" id="mainCenter">
        <div class="easyui-tabs" data-options="fit:true,border:false,plain:true" id="centre">
            <div title="主界面" style="padding:10px" data-options="closable:true">
                <table class="tables" id="table1" style="padding-left:100px;padding-top:30px; ">
                </table>
                <table class="tables" id="table" style="padding-left:100px; ">
                </table>
            </div>
        </div>
        <div id="mm" class="easyui-menu" style="width:150px;">
            <div id="mm-tabclose">关闭</div>
            <div id="mm-tabcloseall">全部关闭</div>
            <div id="mm-tabcloseother">除此之外全部关闭</div>
            <div class="menu-sep"></div>
            <div id="mm-tabcloseright">当前页右侧全部关闭</div>
            <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
        </div>
        <script type="text/javascript">
            function exit() {
                window.location.href = "../login/logout.action?loginpath=${loginpath}&date=" + new Date().toString();
            }

            function openDiv() {
                $('#LoginUPdateDiv').dialog({
                    closed: false
                });
            }

            changeWindow();
        </script>
    </div>
</div>
</body>
</html>