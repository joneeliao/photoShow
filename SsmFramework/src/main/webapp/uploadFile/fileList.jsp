<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Test</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body style="margin: 1px">

<script type="text/javascript">
    var url;
    $(function () {
        loadData();
    });
    //参数pars(Json格式)可以当做额外参数(搜索条件)传递到服务端
    function loadData(pars) {
        $('#tt').datagrid({   //#tt是展示数据的表格ID
            //这里需要接收【总行数total】和【数据组rows】的【JSON格式】的数据{total:23,rows:[{},{}]}
            url: '${pageContext.request.contextPath}/files/list.do',   //MVC路由，可以不是MVC格式
            title: '文件管理',
            width: 1000,
            height: 400,
            fitColumns: true, //列自适应
            nowrap: false,
            idField: 'picId',//主键列的列名
            loadMsg: '正在加载文件信息...',
            pagination: true,//是否有分页
            singleSelect: false,//是否单行选择
            pageSize: 5,//页大小，一页多少条数据
            pageNumber: 1,//当前页，默认的
            pageList: [5, 10, 15],   //可以设置的页大小的列表
            queryParams: pars,  //往后台额外传递的参数，(搜索条件)(Json格式)
            columns: [[//picId
                {field: 'ck', checkbox: true, align: 'left', width: 50},  //额外添加的checkBox的列
                {field: 'picId', title: '编号', width: 80},    //field属性必须和UserInfo的属性名对应。
                {field: 'fileName', title: '文件名', width: 120},
                {field: 'fileType', title: '文件类型', width: 120},
                {field: 'fileTitle', title: '文件标题', width: 120},
                {field: 'fileDescription', title: '文件说明', width: 120},
                {field: 'fileLocation', title: '文件地址', width: 120},

                {
                    field: 'creationDate', title: '创建日期', width: 120, align: 'right',
                    formatter: function (value, row, index) {
                        return ChangeDateFormat(value)
                    }
                }

            ]],
            toolbar: [
                {   //工具条，按钮
                    id: 'btnDelete',
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        deletefFile();
                    }
                },
                {  //按钮未用到
                    id: 'btnAdd',
                    text: '添加',
                    iconCls: 'icon-add',
                    handler: function () {
                        newFile();
                        //self.location.href="${pageContext.request.contextPath}/uploadFile/uploadFile.jsp"
                    }
                },
                {
                    id: 'btnEidt',
                    text: '编辑',
                    iconCls: 'icon-edit',
                    handler: function () {
                        editUser();
                    }
                }],
        });
    }

    //将序列化成json格式后日期(毫秒数)转成日期格式(未用到)
    function ChangeDateFormat(cellval) {
        //alert(JSON.stringify(cellval))
        var y = cellval.year;
        var m = cellval.month + 1;
        var d = cellval.date;

        return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    }

    function deletefFile() {
        var selectedRows = $("#tt").datagrid('getSelections');
        if (null == selectedRows || "undefined" == typeof(selectedRows) || "" == selectedRows) {
            $.messager.alert("系统提示", "请选择要删除的数据！");
            return;
        }
        var strIds = [];
        //alert(JSON.stringify(selectedRows))
        for (var i = 0; i < selectedRows.length; i++) {
            strIds.push(selectedRows[i].picId);
        }
        var ids = strIds.join(",");

        $.messager.confirm("系统提示", "您确定要删除这<font color=red>"
            + selectedRows.length + "</font>条数据吗？", function (r) {
            if (r) {
                $.post("${pageContext.request.contextPath}/files/delete.do", {
                    ids: ids
                }, function (result) {
                    if (result.success) {
                        $.messager.alert("系统提示", "数据已成功删除！");
                        $("#tt").datagrid("reload");
                    } else {
                        $.messager.alert("系统提示", "数据删除失败，请联系系统管理员！");
                    }
                }, "json");

            }
        });

    }

    function newFile() {
        $('#addFile').dialog('open').dialog('setTitle', '新增');
        $('#addFm').form('clear');
        url = '${pageContext.request.contextPath}/files/save.do';
    }

    function saveUser() {
        $('#fm').form('submit', {
            url: url,
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.success) {
                    $.messager.show({
                        title: '提示',
                        msg: '保存成功.'
                    });
                    $('#dlg').dialog('close');        // close the dialog
                    $("#tt").datagrid("reload");
                } else {
                    $.messager.show({
                        title: '提示',
                        msg: result.message
                    });
                }
            }
        });
    }

    function addFile() {
        $('#addFm').form('submit', {
            url: url,
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {

                var result = eval('(' + result + ')');
                if (result.success) {
                    $.messager.show({
                        title: '提示',
                        msg: '保存成功.'
                    });
                    $('#addFile').dialog('close');        // close the dialog
                    $("#tt").datagrid("reload");
                } else {
                    $.messager.show({
                        title: '提示',
                        msg: result.message
                    });
                }
            }
        });
    }

    function searchUser() {
        $("#tt").datagrid('load', {
            "fileName" : $("#fileName").val(),
            "fileDescription" : $("#fileDescription").val()
        });
    }

    function editUser(){
        var row = $('#tt').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('setTitle','修改');
            $('#fm').form('load',row);
            url = '${pageContext.request.contextPath}/files/update.do';
        }
    }
</script>
<div>
    文件名:<input type="text" id="fileName" onkeydown="if(event.keyCode == 13)searchUser()"/>  
    文件说明:<input type="text" id="fileDescription" onkeydown="if(event.keyCode == 13)searchUser()"/>
    <a href="javascript:searchUser()"
       class="easyui-linkbutton"
       data-options="iconCls:'icon-search'"
       style="width:80px"
       id="btnSearch">Search</a>

    <table id="tt" style="width: 100%;" title="标题，可以使用代码进行初始化，也可以使用这种属性的方式" iconcls="icon-edit">
    </table>

    <div id="dlg" class="easyui-dialog" style="width:500px;height:300px;padding:10px 20px"
         closed="true" buttons="#dlg-buttons">
        <div class="ftitle">文件上传</div>
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <input name="picId" type="hidden" class="easyui-validatebox" />
                <label>文件名称:</label>
                <input name="fileName" class="easyui-textbox" style="width: 350px" />
            </div>
            <div class="fitem" hidden="true">
                <label>文件类型:</label>
                <input name="fileType" class="easyui-textbox" />
            </div>
            <div class="fitem">
                <label>文件标题:</label>
                <input name="fileTitle" class="easyui-textbox" style="width: 350px" />
            </div>
            <div class="fitem">
                <label>文件说明:</label>
                <input name="fileDescription" class="easyui-textbox" data-options="multiline:true" style="height:120px; width: 350px" />
            </div>
            <div class="fitem" style="" hidden="true">
                <label>文件地址:</label>
                <input name="fileLocation" class="" required="true" />
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">提交</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
           onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
    <div id="addFile" class="easyui-dialog" style="width:500px;height:510px;padding:10px 20px"
         closed="true" buttons="#addFile-buttons">
        <div class="ftitle">文件上传</div>
        <form id="addFm" method="post" enctype="multipart/form-data" novalidate>
            <div class="fitem">
                <label>图片说明:</label>
                <input name="desc1" class="easyui-textbox" data-options="multiline:true" style="height:100px; width: 350px" />
            </div>
            <div class="fitem">
                <label>选择图片:</label>
                <input class="easyui-filebox" name="image1" data-options="buttonText:'浏览',prompt:'仅支持jpg、gif、png等格式的图片'">
            </div>
            <div class="fitem">
                <label>图片说明:</label>
                <input name="desc2" class="easyui-textbox" data-options="multiline:true" style="height:100px; width: 350px" />
            </div>
            <div class="fitem">
                <label>选择图片:</label>
                <input class="easyui-filebox" name="image2" data-options="buttonText:'浏览',prompt:'仅支持jpg、gif、png等格式的图片'">
            </div>
            <div class="fitem">
                <label>图片说明:</label>
                <input name="desc3" class="easyui-textbox" data-options="multiline:true" style="height:100px; width: 350px"/>
            </div>
            <div class="fitem">
                <label>选择图片:</label>
                <input class="easyui-filebox" name="image3" data-options="buttonText:'浏览',prompt:'仅支持jpg、gif、png等格式的图片'">
            </div>
        </form>
    </div>
    <div id="addFile-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="addFile()">提交</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
           onclick="javascript:$('#addFile').dialog('close')">取消</a>
    </div>
</div>
</body>
</html>
