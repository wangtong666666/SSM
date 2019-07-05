<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/commons.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
    <meta charset="UTF-8">
    <title>后台菜单</title>
    <link rel="stylesheet" type="text/css" href="../../css/jkMenu.css">
    <script type="text/javascript" src="../../js/jkMenu.js"></script>
</HEAD>


<script type="text/javascript">
    //加载左右div的属性


</script>
<BODY id="aa">
<!-- 左侧界面(树状结构) -->
<div id="jkTree">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="margin-left: 35px;margin-top: 10px;"
       onclick="menu_add()">添加</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cut'" style="margin-left: 20px;margin-top: 10px;"
       onclick="tree_remove('tsysmenu','jKTreeMenu')">删除</a>
    <ul id="jKTreeMenu" class="easyui-tree"
        data-options="url:'../../tsysmenu/allMenu.action',animate:true,checkbox:true"></ul>
</div>


<!-- 右侧界面 -->
<div id="right">
    <center style="display: none;" id="add_tsysmenu">
        <table class="weixinMenuTable" style="width: 600px;" ;>
            <tr>
                <td colspan="2"
                    style="border-right: 0px;text-align: center;font-size: 17px;font-weight: bolder;height: 40px;">
                    添加后台菜单
                </td>
            </tr>
            <tr>
                <td style="width: 24%;">菜单名称:</td>
                <td style="border-right:0px;width: 300px;text-align: left;">&nbsp;&nbsp;
                    <input class="easyui-validatebox" data-options="required:true,missingMessage:'字段不可以为空'"
                           id="menuName" style="height: 18px;width: 145px;">
                </td>
            </tr>
            <tr>
                <td>菜单级别:</td>
                <td style="border-right:0px;text-align: left;">&nbsp;&nbsp;
                    <select id="menuLevel" style="width: 150px;height: 25px;">
                        <option value="1">1级</option>
                        <option value="2">2级</option>
                        <option value="3">3级</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>父级菜单:</td>
                <td style="border-right:0px;text-align: left;">&nbsp;&nbsp;
                    <select id="parentId1" style="width: 150px;height: 25px;"
                            onchange="OneParentId('parentId1','parentId2')">
                        <option value=''>一级菜单</option>
                    </select>&nbsp;&nbsp;
                    <select id="parentId2" style="width: 150px;height: 25px;">
                        <option value=''>二级菜单</option>
                    </select>

                </td>
            </tr>
            <tr>
                <td>跳转路径:</td>
                <td style="border-right: 0px;text-align: left;">&nbsp;&nbsp;
                    <input style="width: 360px;height: 18px;" id="menuHref">
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td style="border-right: 0px;text-align: left;">&nbsp;&nbsp;
                    <span style="color: #E50000">例如:   ../pages/WeixinOperation/list.jsp</span>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="border-right: 0px;height: 40px;border-bottom: 0px;text-align: right;">
                    <span id="add_msg"
                          style="font-size: 14px;font-weight: bold;float: left;margin-left: 100px;color: green"></span>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                       onclick="tsysmenu_add_save()" style="margin-right: 30px;">保存</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-cut'" href="javascript:void(0)"
                       onclick="Menu_doReset('menuName','menuHref','parentId1','parentId2')"
                       style="margin-right: 50px;">重置</a>
                </td>
            </tr>
        </table>
    </center>


    <center style="display: none;" id="update_tsysmenu">
        <table class="weixinMenuTable" style="width: 600px" ;>
            <tr>
                <td colspan="2"
                    style="border-right: 0px;text-align: center;font-size: 17px;font-weight: bolder;height: 40px;">系统菜单
                </td>
            </tr>
            <tr>
                <td style="width: 24%;">菜单名称:</td>
                <td style="border-right:0px;width: 300px;text-align: left;">&nbsp;&nbsp;
                    <input class="easyui-validatebox" data-options="required:true,missingMessage:'字段不可以为空'"
                           id="up_menuName" style="height: 18px;width: 145px;">
                    <input type="hidden" id="up_menuId">
                    <input type="hidden" id="up_platformId">
                </td>
            </tr>
            <tr>
                <td>菜单级别:</td>
                <td style="border-right:0px;text-align: left;">&nbsp;&nbsp;
                    <!--  <input  id="up_menuLevel" style="height: 18px;width: 145px;" readonly="readonly" >-->
                    <select id="up_menuLevel" style="width: 150px;height: 25px;" onchange="clickMenuLevel()">
                        <option value="1">1级</option>
                        <option value="2">2级</option>
                        <option value="3">3级</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>父级菜单:</td>
                <td style="border-right:0px;text-align: left;">&nbsp;&nbsp;
                    <select id="up_parentId1" style="width: 150px;height: 25px;"
                            onchange="OneParentId('up_parentId1','up_parentId2')"
                            onclick="jk_selectBat('up_menuLevel')">
                        <option value=''>一级菜单</option>
                    </select>&nbsp;&nbsp;
                    <select id="up_parentId2" style="width: 150px;height: 25px;" onclick="jk_selectBat('up_menuLevel')">
                        <option value=''>二级菜单</option>
                    </select>

                </td>
            </tr>
            <tr>
                <td>跳转路径:</td>
                <td style="border-right: 0px;text-align: left;">&nbsp;&nbsp;
                    <input style="width: 360px;height: 18px;" id="up_menuHref">
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td style="border-right: 0px;text-align: left;">&nbsp;&nbsp;
                    <span style="color: #E50000">例如:     ../../pages/WeixinOperation/list.jsp</span>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="border-right: 0px;height: 40px;border-bottom: 0px;text-align: right;">
                    <span id="update_msg"
                          style="font-size: 14px;font-weight: bold;float: left;margin-left: 100px;color: green"></span>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="tsysmenu_update_save('tsysmenu','jKTreeMenu','up_menuId','up_menuName',
				                           'up_menuLevel','up_parentId1','up_parentId2','up_menuHref','up_platformId')"
                       style="margin-right: 50px;">修改</a>
                </td>
            </tr>
        </table>
    </center>
</div>
</BODY>
</HTML>
