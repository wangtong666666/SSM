//form表单查询的公用方法
var pname = window.location.pathname;
pname = pname.substring(0, pname.indexOf('/', 1));

function FormData(tableId, searchFormId) {
    $("#" + tableId + "").datagrid('clearSelections');
    var params = {};
    var inputValArray = $("#" + searchFormId + "").serialize();
    inputValArray = inputValArray.replace(/\+/g, " ");
    inputValArray = decodeURIComponent(inputValArray, true);

    inputValArray = inputValArray.split("&");
    for (var i = 0; i < inputValArray.length; i++) {
        var eachInput = inputValArray[i].split("=");
        eachInput[1] = eachInput[1].replace(/\s+/g, "");
        params[eachInput[0]] = eachInput[1];
    }
    $("#" + tableId + "").datagrid('load', params);
}

document.write('<script type="text/javascript" src="/APP/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>');

//打开tab
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

}

// 在父级页面中打开Tab
function addTabInParent(title, url) {
    if (parent.$("#centre").tabs("exists", title)) {
        parent.$("#centre").tabs("close", title);
    }
    var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:99.5%;height:98.5%;margin:3px;"></iframe>';
    parent.$("#centre").tabs("add", {
        title: title,
        content: content,// 这里的content里放你想要显示的东西
        closable: true
    });
}

//关闭打开的dialog窗体
function closeDialog(dialogid) {
    $("#" + dialogid + "").dialog('close');
}

//删除数据的公用方法
function delete_info(tableId, actionName, pkName) {
    var rows = $("#" + tableId + "").datagrid("getSelections");    // 获取所有选中的
    var flag = false;
    if (rows.length == 0) {
        $.messager.alert('信息提示', '请选择删除信息!!!', 'error');
    } else {
        var idArray = "";
        for (var i = 0; i < rows.length; i++) {
            var id = row_info(rows[i], pkName);
            idArray += id + "-";
        }

        idArray = idArray.substr(0, idArray.length - 1);
        var message;

        message = "确定删除选中的信息吗?"
        $.messager.confirm('删除提示', message, function (r) {
            if (r) {
                $.post("../../" + actionName + "/detele.action?idArray=" + idArray, function (data) {
                    if (data == 1) {
                        $.messager.alert('信息提示', '数据删除成功', 'info');
                        deleteRow(rows, tableId);
                    } else {
                        $.messager.alert('信息提示', '数据删除失败', 'error');
                    }
                }, 'json')
            }
        });
    }
}

//根据行id删除数据表中的行
function deleteRow(rows, tableId) {
    for (var z = 0; z < rows.length; z++) {
        var copyRows = [];
        for (var j = 0; j < rows.length; j++) {
            copyRows.push(rows[j]);
        }
        for (var i = 0; i < copyRows.length; i++) {
            var index = $("#" + tableId + "").datagrid('getRowIndex', copyRows[i]);
            $("#" + tableId + "").datagrid('deleteRow', index);
        }
    }
}

//删除公用方法(tree树状图)
function tree_remove() {
    var actionName;
    var divName;
    for (var i = 0; i < arguments.length; i++) {
        if (i == 0) {
            actionName = arguments[i];
        } else {
            divName = arguments[i];
        }
    }

    var nodes = $("#" + divName + "").tree('getChecked');
    var idArray = '';
    for (var i = 0; i < nodes.length; i++) {
        if (idArray != '') idArray += '-';
        idArray += nodes[i].id;
    }
    if (idArray == '') {
        $.messager.alert('信息提示', '请选择删除菜单的信息!!!', 'error');
    } else {
        $.messager.confirm('删除提示', "你确定删除id是:&nbsp;&nbsp;" + idArray + "&nbsp;&nbsp;&nbsp;的数据信息吗?", function (r) {
            if (r) {
                $.post("../../" + actionName + "/detele.action?idArray=" + idArray, function (data) {
                    if (data == 1) {
                        $.messager.alert('信息提示', '数据删除成功', 'info');
                        $("#" + divName + "").tree('reload');
                    } else {
                        $.messager.alert('信息提示', '数据删除失败', 'error');
                    }
                }, 'json')
            }
        });
    }
}

//获取数据表中每行的信息(key和value的值)
function row_info(row, pkName) {
    for (k in row) {
        if (pkName == k) {
            return row[k];
        }
    }
}

//打开添加界面（window界面）
function saveOpen(divName, iframeName) {
    $("#" + divName + "").dialog('open');
    $("#" + iframeName + "")[0].src = 'save.jsp';
}

//添加的公用方法
function add_save(addFormName, actionName) {

    $("#add_msg").html("保存数据中，请稍后...");
    $("#" + addFormName + "").form('submit', {
        url: "../../" + actionName + "/save.action",
        dataType: 'text',
        data: $("#" + addFormName + "").serialize(),
        success: function (data) {
            var newData = $.parseJSON(data);
            //alert("newData:"+newData);
            if (newData == 1) {
                $("#" + addFormName + " input").val('');
                $("#" + addFormName + " input:checked").attr("checked", false);
                $("#add_msg").html("添加成功,可以继续添加");
            } else {
                $("#add_msg").html("添加失败,请查看添加信息是否正确!!!");
            }
        }
    });
}

//添加菜单界面的重置函数
function doReset(formName) {
    $("#" + formName + " input").val('');
    $("#add_msg").html("&nbsp;");
    $("#" + formName + " input:checked").attr("checked", false);
}

function updateOpen(tableName, updateDivName, iframeName, pkName) {
    var rows = $("#" + tableName + "").datagrid("getSelections");
    if (rows.length < 1) {
        $.messager.alert('信息提示', '请选择修改信息!!!', 'error');
    } else if (rows.length > 1) {
        $.messager.alert('信息提示', '只能选择一条修改信息', 'error');
    } else {
        var row = rows[0];//获取类表中的选中的列
        $("#" + updateDivName + "").dialog('open');

        $("#" + iframeName + "")[0].src = "update.jsp?pkName=" + row_info(row, pkName) + "";

    }
}

function updateOpens(tableName, updateDivName, iframeName, pkName) {
    var rows = $("#" + tableName + "").datagrid("getSelections");
    if (rows.length < 1) {
        $.messager.alert('信息提示', '请选择查看信息!!!', 'error');
    } else if (rows.length > 1) {
        $.messager.alert('信息提示', '只能选择一条信息查看', 'error');
    } else {
        var row = rows[0];//获取类表中的选中的列
        $("#" + updateDivName + "").dialog('open');
        $("#" + iframeName + "")[0].src = "update.jsp?pkName=" + row_info(row, pkName) + "";
    }
}

//编辑用户菜单权限
function editOpen(tableName, editDivName, iframeName, pkName) {
    var use = $("input:hidden").val();
    var rows = $("#" + tableName + "").datagrid("getSelections");
    if (rows.length < 1) {
        $.messager.alert('信息提示', '请选择编辑信息!!!', 'error');
    } else if (rows.length > 1) {
        $.messager.alert('信息提示', '只能选择一条编辑信息!!!', 'error');
    } else if (rows[0].roleId == 1) {
        $.messager.alert('信息提示', '管理员不能编辑权限!!!', 'error');
    } else if (rows[0].loginName.length == 6 && use != "admin") {
        $.messager.alert('信息提示', '总服务商不能编辑权限!!!', 'error');
    } else {
        var row = rows[0];//获取类表中的选中的列
        $("#" + editDivName + "").dialog('open');
        $("#" + iframeName + "")[0].src = "edit.jsp?pkName=" + row_info(row, pkName) + "";
    }
}

//编辑角色菜单权限
function editOpen1(tableName, editDivName, iframeName, pkName) {

    var rows = $("#" + tableName + "").datagrid("getSelections");
    if (rows.length < 1) {
        $.messager.alert('信息提示', '请选择编辑信息!!!', 'error');
    } else if (rows.length > 1) {
        $.messager.alert('信息提示', '只能选择一条编辑信息!!!', 'error');
    } else if (rows[0].roleId == 1) {
        $.messager.alert('信息提示', '管理员不能编辑权限!!!', 'error');
    } else if (rows[0].roleId == 21) {
        $.messager.alert('信息提示', '普通员工不能编辑权限!!!', 'error');
    } else {
        var row = rows[0];//获取类表中的选中的列
        $("#" + editDivName + "").dialog('open');
        $("#" + iframeName + "")[0].src = "edit.jsp?pkName=" + row_info(row, pkName) + "";
    }
}

//保存密码修改的信息
function updatePwd_save(updateFormName, actionName) {
    $.ajax({
        type: 'post',
        dataType: 'json',
        url: pname + "/" + actionName + "/updatePwd.action",
        data: $("#" + updateFormName + "").serialize(),
        error: function () {// 请求失败处理函数
            alert('请求失败...');
        },
        success: function (data) { // 请求成功后处理函数。
            if (data == 1) {
                //重置所有文本框的值
                $("#update_msg").html("修改成功,可以继续操作");
            } else if (data < 0) {
                $("#update_msg").html("手机验证码不正确");
            } else {
                $("#update_msg").html("修改失败,请查看修改的信息是否正确!!!");
            }
        }
    });
}

//保存修改的信息
function update_save(updateFormName, actionName) {
    $.ajax({
        type: 'post',
        dataType: 'json',
        url: "../../" + actionName + "/update.action",
        data: $("#" + updateFormName + "").serialize(),
        error: function () {// 请求失败处理函数
            alert('请求失败');
        },
        success: function (data) { // 请求成功后处理函数。
            if (data == 1) {
                //重置所有文本框的值
                $("#update_msg").html("修改成功,可以继续操作");
            } else {
                $("#update_msg").html("修改失败,请查看修改的信息是否正确!!!");
            }
        }
    });
}

//保存修改的信息
function update1_save(updateFormName, actionName) {
    if ($("#loginName").val() == $("#agentNumber").val()) {
        $.messager.alert("提示信息", "您无法修改自己的信息", "info");
        return false;
    }
    $.ajax({
        type: 'post',
        dataType: 'json',
        url: "../../" + actionName + "/update.action",
        data: $("#" + updateFormName + "").serialize(),
        error: function () {// 请求失败处理函数
            alert('请求失败');
        },
        success: function (data) { // 请求成功后处理函数。
            if (data == 1) {
                //重置所有文本框的值
                $("#update_msg").html("修改成功,可以继续操作");
            } else {
                $("#update_msg").html("修改失败,请查看修改的信息是否正确!!!");
            }
        }
    });
}


function getDate() {
    var d = new Date();
    var vYear = d.getFullYear();
    var vMon = d.getMonth() + 1;
    var vDay = d.getDate();
    var sys = vYear + "/" + (vMon < 10 ? "0" + vMon : vMon) + "/" + (vDay < 10 ? "0" + vDay : vDay);
    return sys;
}


//遮罩
function EasyUILoad() {
    $("<div class=\"datagrid-mask\"></div>").css({
        display: "block",
        width: "100%",
        height: "auto !important"
    }).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html("  正在处理，请稍候。。。").appendTo("body").css({
        display: "block",
        left: ($(document.body).outerWidth(true) - 190) / 2,
        top: ($(window).height() - 45) / 2
    });
}

//删除遮罩
function dispalyEasyUILoad() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}

//导出表格
function outputExcel(tableId, searchFormId, actionName) {
    EasyUILoad();
    var params = "?";
    var inputValArray = $("#" + searchFormId + "").serialize();
    inputValArray = inputValArray.replace(/\+/g, " ");
    inputValArray = decodeURIComponent(inputValArray, true);
    inputValArray = inputValArray.split("&");
    for (var i = 0; i < inputValArray.length; i++) {
        var eachInput = inputValArray[i].split("=");
        eachInput[1] = eachInput[1].replace(/\s+/g, "");
        params = params + eachInput[0] + "=" + eachInput[1] + "&"
    }
    var str = ":";
    $("th[data-options^='field:']").each(function (index, domEle) {
        var d = $(domEle).attr("data-options");
        str = str + "," + d.substr(7, d.indexOf(',') - 8) + ":" + $(domEle).html() + "";
    });
    params = params + "namelist=" + str;

    $.post("../../" + actionName + "/excelDoc.action" + params, function (data) {
        if (data != 0) {
            window.location = "../../output/excel.action?file=" + data;
            dispalyEasyUILoad();
        } else {
            $.messager.alert("信息提示", "导出失败！", "info");
            dispalyEasyUILoad();
        }
    }, 'json');

}

function FormData2(tableId, searchFormId, url) {
    $("#" + tableId + "").datagrid('clearSelections');
    var params = {};
    var inputValArray = $("#" + searchFormId + "").serialize();
    inputValArray = inputValArray.replace(/\+/g, " ");
    inputValArray = decodeURIComponent(inputValArray, true);
    inputValArray = inputValArray.split("&");
    var pp = false;
    for (var i = 0; i < inputValArray.length; i++) {
        var eachInput = inputValArray[i].split("=");
        eachInput[1] = eachInput[1].replace(/\s+/g, "");
        params[eachInput[0]] = eachInput[1];
        if (eachInput[1] != "" && eachInput[1] != null) {
            pp = true;
        }
    }
    if (pp == true) {
        params['param2'] = '1';
    }
    $("#" + tableId + "").datagrid({url: url, queryParams: params});
}

