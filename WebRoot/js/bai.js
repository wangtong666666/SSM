//白如玉.js
//验证身份证号
function checkidcard() {
    var idcard = $('#idcard').val();
    if (idcard.length > 0) {
        var partten = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (!partten.test(idcard)) {
            $.messager.alert('错误信息!', '请输入正确身份证号!', 'info');
            $('#idcard').val('');
        }
    }
}

// 当失去焦点时判断
function checktelephone() {
    var telephone = $('#telephone').val();
    if (telephone.length > 0) {
        var partten = /^(1[3,5,8]\d{9})$/;
        if (!partten.test(telephone)) {
            $.messager.alert('错误信息!', '请输入正确的号码!', 'info');
            $('#telephone').val('');
        }
    }
}

function checkphone() {
    var phone = $('#phone').val();
    if (phone.length > 0) {
        var partten = /^(13[0-9]{9})|(18[0-9]{9}) |(15[0-9]{9})$/;
        if (!partten.test(phone)) {
            $.messager.alert('错误信息!', '请输入正确的号码!', 'info');
            $('#phone').val('');
        }
    }
}


function checkreservePhone() {
    var reservePhone = $('#rservePhone').val();
    if (reservePhone.length > 0) {
        var partten = /^(13[0-9]{9})|(18[0-9]{9}) |(15[0-9]{9})$/;
        if (!partten.test(reservePhone)) {
            $.messager.alert('错误信息!', '请输入正确的号码!', 'info');
            $('#rservePhone').val('');
        }
    }
}

// 当失去焦点时判断
function checkzipCode() {
    var zipCode = $('#zipCode').val();
    if (zipCode.length > 0) {
        var partten = /^[1-9][0-9]{5}$/;
        if (!partten.test(zipCode)) {
            $.messager.alert('错误信息!', '请输入正确邮政编码!', 'info');
            $('#zipCode').val('');
        }
    }
}

function checkemail() {
    var email = $('#email').val();
    if (email.length > 0) {
        // 对电子邮件的验证
        var Myemail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if (!Myemail.test(email)) {
            $.messager.alert('错误信息!', '请输入正确的E_mail!', 'info');
            $('#email').val('');
        }
    }
}

function checktime() {
    var reigntime = $('#reigntime').datebox('getValue');
    var entrytime = $('#entrytime').datebox('getText');
    var time1 = new Date(reigntime);
    var time2 = new Date(entrytime);
    if (time1 - time2 <= 0) {
        $.messager.alert('提示信息', '离职时间不能小于入职时间', 'info');
        $('#state').combobox('setValue', '1');
        $('#reigntime').datebox('setValue', null);
        return false;
    } else {
        return true;
    }
}

// 验证编号是否存在
function checkId() {
    var $val = $("#Num").val();
    var code;
    for (var i = 0; i < $val.length; i++) {
        var code = $val.charAt(i).charCodeAt(0);
        if (code < 48 || code > 57) {
            $.messager.alert('提示信息', '编号只能输入数字', 'info');
            $("#Num").val('');
            break;
        }
    }
}

function getDate() {
    var d = new Date();
    var vYear = d.getFullYear();
    var vMon = d.getMonth() + 1;
    var vDay = d.getDate();
    var sys = vYear + '/' + vMon + '/' + vDay;
    return sys;
}

// 入库库撤销
function delete_info1() {
    var rows = $("#PmsBusinessPosTab").datagrid("getSelections");    // 获取所有选中的行
    if (rows.length == 0) {
        $.messager.alert('信息提示', '请选择入库撤销信息!!!', 'error');
    } else {
        var statusArray = "";
        var idArray = "";
        for (var i = 0; i < rows.length; i++) {
            var status = row_info(rows[i], 'status');
            var id = row_info(rows[i], 'id');
            statusArray += status;
            idArray += id + "-";
        }
        idArray = idArray.substr(0, idArray.length - 1);
        if (statusArray.indexOf('2') >= 0) {
            $.messager.alert('信息提示', '您选择的终端含有出库信息,无法进行入库撤销', 'error');
        } else {
            $.messager.confirm('撤销提示', "确定撤销选中的入库信息吗?", function (falg) {
                if (falg) {
                    $.post("../../pmsbusinesspos/detele.action?idArray=" + idArray, function (data) {
                        if (data != '' && data != null) {
                            $.messager.alert('信息提示', '入库撤销成功', 'info');
                            deleteRow(rows, 'PmsBusinessPosTab');
                        }
                    });
                }
            }, 'json');
        }
    }
}

// 出库撤销
function update_info() {
    var rows = $("#PmsBusinessPosTab").datagrid("getSelections");    // 获取所有选中的行
    if (rows.length == 0) {
        $.messager.alert('信息提示', '请选择出库撤销信息!!!', 'error');
    } else {
        var statusArray = "";
        var idArray = "";
        for (var i = 0; i < rows.length; i++) {
            var status = row_info(rows[i], 'status');
            var id = row_info(rows[i], 'id');
            statusArray += status;
            idArray += id + "-";
        }
        idArray = idArray.substr(0, idArray.length - 1);
        if (statusArray.indexOf('1') >= 0) {
            $.messager.alert('信息提示', '您选择的终端含有入库信息,无法进行出库撤销', 'error');
        } else {
            if (rows[0].posopenstatus == 2) {
                $.messager.alert('信息提示', '您选择的终端含已经使用,无法进行出库撤销', 'error');
            } else {
                $.messager.confirm('撤销提示', "确定撤销选中的出库信息吗?", function (falg) {
                    if (falg) {
                        $.post("../../pmsbusinesspos/update.action?idArray=" + idArray, function (data) {
                            if (data == 1) {
                                $.messager.alert('信息提示', '出库撤销成功', 'info');
                                $('#PmsBusinessPosTab').datagrid('load');
                            }
                        }, 'json');
                    }
                });
            }
        }
    }
}

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

// 获取数据表中每行的信息(key和value的值)
function row_info(row, pkName) {
    for (k in row) {
        if (pkName == k) {
            return row[k];
        }
    }
}

// 终端出库
function updateOpen1() {
    var rows = $("#PmsBusinessPosTab").datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert('信息提示', '请选择出库信息!!!', 'error');
    } else {
        var idArray = new Array();
        var posnumArray = "";
        for (var i = 0; i < rows.length; i++) {
            if (rows[i]['status'] == '2') {
                $.messager.alert('信息提示', '所选信息存在已出库终端，请重新选择!!', 'error');
                break;
            }
            var id = row_info(rows[i], 'id');
            var posnum = row_info(rows[i], "posnum");
            idArray += id + ",";
            posnumArray += posnum + ",";
        }
        idArray = idArray.substr(0, idArray.length - 1);
        posnumArray = posnumArray.substr(0, posnumArray.length - 1);
        $.messager.confirm('撤销提示', "确定出库所选信息吗?", function (r) {
            if (r) {
                $("#PmsBusinessPosUPdateDiv").dialog('open');
                $("#PmsBusinessPosUPdateIframe")[0].src = 'update.jsp?idArray=' + idArray + '&posnumArray=' + posnumArray;
            }
        });

    }
}

function update_save() {
    var departmentnum = $('#departmentnum').combobox('getValue');
    var personnum = $('#personnum').combobox('getValue');
    if (departmentnum == '请选择') {
        $.messager.alert('信息提示', '请选择操作部门', 'error');
    } else if (personnum == '') {
        $.messager.alert('信息提示', '请选择操作人', 'error');
    } else {
        var idArray = '1';
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: "../../pmsbusinesspos/update.action?idArray=" + idArray,
            data: $("#PmsBusinessPosUpdateForm").serialize(),
            error: function () {// 请求失败处理函数
                alert('请求失败');
            },
            success: function (data) { // 请求成功后处理函数。
                if (data == 1) {
                    // 重置所有文本框的值
                    $("#update_msg").html("出库成功");
                } else {
                    $("#update_msg").html("出库未成功,请返回界面重新操作");
                }
            }
        });
    }
}