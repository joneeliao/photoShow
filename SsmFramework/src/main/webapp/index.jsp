<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!-- /*Copyright @ 2016 Oracle and/or its affiliates. All rights reserved */ -->
<body>
<script type="text/javascript">
    function login() {
        var userName = $("#userName").val();
        var password = $("#password").val();
        var roleName = $("#roleName").val();
        if (userName == null || userName == "") {
            alert("用户名不能为空！");
            return;
        }
        if (password == null || password == "") {
            alert("密码不能为空！");
            return;
        }
        $("#adminlogin").submit();

    }
    function to_change(){
        var obj  = document.getElementsByName('like');
        for(var i=0;i<obj.length;i++){
            if(obj[i].checked==true){
                if(obj[i].value=='Y'){
                    window.location.href='index.html';
                }else if(obj[i].value=='N'){
                    window.location.href='index.html';
                }
            }
        }
    }
</script>
<h2>Hello Cloud World!</h2>
<p STYLE="font-size: 30px">傻叉，说<BR/>你喜不喜欢我！！！</p>
<BR/>
<span style="font-size: 50px; color: red; font-weight: bolder">很喜欢：</span><input id="likeid"   type="radio"   value="Y"    name="like"  style="width: 100px;height: 100px" onclick="to_change()"/>
不喜欢：<input id="unlikeid"   type="radio"   value="N"    name="like"  style="width: 100px;height: 100px" onclick="to_change()"/>
<!--
<form id=adminlogin method=post
      name=adminlogin action="${pageContext.request.contextPath}/user/save.do">
    <table border=0 cellSpacing=0 cellPadding=0 width="100%">
        <tr>
            <td style="height: 45px">User Name</td>
            <td style="height: 45px"><input type="text" class=input value="${user.userName }" name="userName"
                                            id="userName"></td>
        </tr>
        <tr>
            <td style="height: 45px"> Password</td>
            <td style="height: 45px"><input type="password" class=input value="${user.password }" name="password"
                                            id="password"/></td>
        </tr>
        <tr>
            <td style="height: 45px">Role Name</td>
            <td style="height: 45px"><input type="text" class=input value="${user.roleName }" name="roleName"
                                            id="roleName"/></td>
        </tr>

        <tr>
            <td style="height: 45px">
                <input style="border-right-width: 0px; border-top-width: 0px; border-bottom-width: 0px; border-left-width: 0px"
                       id=btnLogin value="submit"
                       type=submit name=btnLogin>
            </td>
            <td style="height: 45px"></td>

        </tr>
    </table>

</form>
-->

</body>
</html>
<script type=text/javascript>
    if ('${errorMsg}' != '') {
        alert('${errorMsg}');
    }
</script>
