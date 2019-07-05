var pname = window.location.pathname;
pname = pname.substring(0, pname.indexOf('/', 1));
$(function () {

    alert("======================");
    var height = $($("#aa").parent()).parent().height() * 0.99;
    var width = parseInt($($("#aa").parent()).parent().width());
    var rightWidth = parseInt(width) - 280;
    $("#jkTree").attr("style", "width: 260px;float:left;border: solid 1px #9CC8F7;height:" + height + "px;margin-top:-15px;margin-left:-15px;");
    $("#right").attr("style", "width: " + rightWidth + "px;float:right;border: solid 1px #9CC8F7;height:" + height + "px;margin-top:-15px;margin-right:-20px;overflow: auto;");
    $("#jKTreeMenu").attr("style", "width: 240px;border: solid 1px #AAAAAA;height:" + (height * 0.93) + "px;margin:8px;");

    //菜单点击事件
    $('#jKTreeMenu').tree({
        onClick: function (node) {
            $("#update_tsysmenu").attr("style", "display: block");
            $("#add_tsysmenu").attr("style", "display:none");
            $.post(pname + "/tsysmenu/selectPage.action?idArray=" + node.id, function (data) {
                alert(data[0].menuName + "--------" + data[0].menuHref);
                $("#up_parentId1 option:not(:first)").remove();
                $("#up_parentId2 option:not(:first)").remove();

                $("#up_menuHref").removeAttr("readonly");
                $("#up_menuName").val(data[0].menuName);
                $("#up_menuHref").val(data[0].menuHref);
                $("#up_menuId").val(data[0].menuId);
                $("#up_platformId").val(data[0].platformId);


                var obj = document.getElementById('up_menuLevel');
                var options = obj.options;
                for (var i = 0; i < options.length; i++) {
                    var opt = options[i];
                    if (data[0].menuLevel == opt.value) {
                        $(opt).attr("selected", "selected");
                    }
                }


                for (var i = 0; i < data[2].length; i++) {
                    //点击二级菜单时的操作
                    if (data[0].menuLevel == '1') {
                        $("#up_menuHref").val('');
                        $("#up_menuHref").attr("readonly", "readonly");
                        return;
                    }

                    //点击二级菜单时的操作
                    if (data[0].menuLevel == '2') {
                        for (var z = 0; z < data[2][0].length; z++) {
                            if (data[0].parentid == data[2][0][z].menuId) {
                                $("#up_parentId1").append("<option value='" + data[2][0][z].menuId + "' selected>" + data[2][0][z].menuName + "</option>");
                            } else {
                                $("#up_parentId1").append("<option value='" + data[2][0][z].menuId + "'>" + data[2][0][z].menuName + "</option>");
                            }
                        }
                        return;
                    }


                    //点击三级菜单的操作
                    if (data[0].menuLevel == '3') {
                        //添加二级菜单
                        for (var z = 0; z < data[2][1].length; z++) {
                            if (data[0].parentid == data[2][1][z].menuId) {
                                var parend = data[2][1][z].parentid;
                                for (var k = 0; k < data[2][1].length; k++) {
                                    if (data[2][1][k].parentid == parend) {
                                        if (data[0].parentid == data[2][1][k].menuId) {
                                            $("#up_parentId2").append("<option value='" + data[2][1][k].menuId + "' selected>" + data[2][1][k].menuName + "</option>");

                                            //添加一级菜单
                                            for (var q = 0; q < data[2][0].length; q++) {
                                                if (data[2][1][k].parentid == data[2][0][q].menuId) {
                                                    $("#up_parentId1").append("<option value='" + data[2][0][q].menuId + "' selected>" + data[2][0][q].menuName + "</option>");
                                                } else {
                                                    $("#up_parentId1").append("<option value='" + data[2][0][q].menuId + "'>" + data[2][0][q].menuName + "</option>");
                                                }
                                            }
                                        } else {
                                            $("#up_parentId2").append("<option value='" + data[2][1][k].menuId + "'>" + data[2][1][k].menuName + "</option>");
                                        }
                                    }
                                }
                            }
                        }
                        return;
                    }
                }
            }, 'json')
        }
    });

})

//一级菜单值变化时 重新加载二级菜单的值
function OneParentId(parentId1, parentId2) {
    $("#" + parentId2 + " option:not(:first)").remove();
    var oneParentId = $("#" + parentId1 + "").val();
    if (oneParentId != '') {
        $.post(pname + "/tsysmenu/select.action?parentId=" + oneParentId, function (data) {
            for (var q = 0; q < data.length; q++) {
                $("#" + parentId2 + "").append("<option value='" + data[q].menuId + "'>" + data[q].menuName + "</option>");
            }
        }, 'json')
    }
}

//菜单级别发生变化时 要做得操作(1,2,3级)
function clickMenuLevel() {
    var Level = $("#up_menuLevel").val();
    if (Level == 1) {
        $("#up_menuHref").val('');
        $("#up_parentId1 option:not(:first)").remove();
        $("#up_parentId2 option:not(:first)").remove();
        $("#up_menuHref").attr("readonly", "readonly");
    } else if (Level == 2) {
        menuValue();
        $("#up_menuHref").removeAttr("readonly");
        $("#up_parentId2").val('');
    } else {
        menuValue();
        $("#up_menuHref").removeAttr("readonly");
    }
}

//给修改界面的菜单下拉列表赋值
function menuValue() {


    $.post(pname + "/tsysmenu/selectPage.action", function (data) {

        $("#up_parentId1 option:not(:first)").remove();
        $("#up_parentId2 option:not(:first)").remove();
        for (var z = 0; z < data[2][0].length; z++) {
            $("#up_parentId1").append("<option value='" + data[2][0][z].menuId + "'>" + data[2][0][z].menuName + "</option>");
        }
    }, 'json')

}


function jk_selectBat(levelName) {// levelName--判断依据的id(父级菜单)
    var levelVal = $("#" + levelName + "").val();
    if (levelVal == '1') {
        var parent1 = $("#up_parentId1").val();
        if (parent1 != '') {
            $("#up_parentId1").val('');
        }
        var parent2 = $("#up_parentId2").val();
        if (parent2 != '') {
            $("#up_parentId2").val('');
        }
    }
    if (levelVal == '2') {
        var parent2 = $("#up_parentId2").val();
        if (parent2 != '') {
            $("#up_parentId2").val('');
        }
    }
}


function menu_add() {
    $("#update_tsysmenu").attr("style", "display: none");
    $("#add_tsysmenu").attr("style", "display: block");

    $.post(pname + "/tsysmenu/selectPage.action", function (data) {

        $("#parentId1 option:not(:first)").remove();
        $("#parentId2 option:not(:first)").remove();

        for (var z = 0; z < data[2][0].length; z++) {
            $("#parentId1").append("<option value='" + data[2][0][z].menuId + "'>" + data[2][0][z].menuName + "</option>");
        }

    }, 'json')
}


//保存修改菜单信息
function tsysmenu_update_save() {

    var menuLevel = $("#up_menuLevel").val();
    if (menuLevel == '1') {
        $("#up_parentId1").val('0');
        $("#up_parentId2").val('');
    } else if (menuLevel == '2') {
        $("#up_parentId2").val('');
        var parentId1 = $("#up_parentId1").val();
        if (parentId1 == '') {
            $("#up_parentId1").focus();
            messager();
            return;
        }
    } else if (menuLevel == '3') {
        var parentId2 = $("#up_parentId2").val();
        if (parentId2 == '') {
            $("#up_parentId2").focus();
            messager();
            return;
        }
    }

    var jsonParams = {};
    var actionName;
    var divName;
    for (var i = 0; i < arguments.length; i++) {
        if (i == 0) {
            actionName = arguments[i];
        } else if (i == 1) {
            divName = arguments[i];
        } else {
            var key = arguments[i].substr(3, arguments[i].length);
            jsonParams[key] = $("#" + arguments[i] + "").val();
        }
    }

    $.ajax({
        type: 'post',
        dataType: 'json',
        url: pname + "/" + actionName + "/update.action",
        data: jsonParams,
        error: function () {// 请求失败处理函数
            alert('请求失败');
        },
        success: function (data) { // 请求成功后处理函数。
            if (data == 1) {
                //重置所有文本框的值
                $("#update_msg").html("修改成功,可以继续操作");
                $("#" + divName + "").tree('reload');
            } else {
                $("#update_msg").html("修改失败,请查看修改的信息是否正确!!!");
            }
        }
    });
}


//后台菜单添加方法
function tsysmenu_add_save() {
    var menuName = $("#menuName").val();
    var menuLevel = $("#menuLevel").val();
    var href = $("#menuHref").val();
    var parentId1 = $("#parentId1").val();
    var parentId2 = $("#parentId2").val();

    if (menuName == '') {
        $("#menuName").focus();
        return;
    }

    if (menuLevel == '1') {
        $("#parentId1").val('0');
        $("#parentId2").val('');
    } else if (menuLevel == '2') {
        $("#parentId2").val('');
        var parentId1 = $("#parentId1").val();
        if (parentId1 == '') {
            $("#parentId1").focus();
            messager();
            return;
        }
    } else if (menuLevel == '3') {
        var parentId2 = $("#parentId2").val();
        if (parentId2 == '') {
            $("#parentId2").focus();
            messager();
            return;
        }
    }
    $.ajax({
        type: 'post',
        dataType: 'json',
        url: pname + "/tsysmenu/save.action",
        data: {
            "menuName": menuName,
            "menuLevel": menuLevel,
            "menuHref": href,
            "parentId1": parentId1,
            "parentId2": parentId2
        },
        error: function () {// 请求失败处理函数
            alert('请求失败');
        },
        success: function (data) { // 请求成功后处理函数。
            if (data == 1) {
                //重置所有文本框的值
                $("#add_msg").html("添加成功,可以继续操作");
                $("#jKTreeMenu").tree('reload');
            } else {
                $("#add_msg").html("添加失败,请查看修改的信息是否正确!!!");
            }
        }
    });
}

function messager() {
    $.messager.show({
        title: '信息提示',
        msg: '父级菜单不可以为空',
        timeout: 3000,
        showType: 'slide',
        style: {
            right: '',
            top: document.body.scrollTop + document.documentElement.scrollTop,
            bottom: ''
        }
    });
}

function Menu_doReset() {

    for (var i = 0; i < arguments.length; i++) {
        $("#" + arguments[i] + "").val("");
    }
    $("#add_msg").html("&nbsp;");
}
	  