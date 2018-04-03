<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Test</title>
</head>
<body>
<h1>上传文件</h1>

<form method="post" action="${pageContext.request.contextPath}/files/save.do">

    <table border=0 cellSpacing=0 cellPadding=0 width="100%">
        <tr>
            <td style="height: 45px">File Name</td>
            <td style="height: 45px"><input type="text" class=input value="${xxctFiles.fileName }" name="fileName"
                                            id="fileName"></td>
        </tr>
        <tr>
            <td style="height: 45px">File Type</td>
            <td style="height: 45px"><input type="text" class=input value="${xxctFiles.fileType }" name="fileType"
                                            id="fileType"></td>
        </tr>
        <tr>
            <td style="height: 45px">File Title</td>
            <td style="height: 45px"><input type="text" class=input value="${xxctFiles.fileTitle }" name="fileTitle"
                                            id="fileTitle"></td>
        </tr>
        <tr>
            <td style="height: 45px">File Description</td>
            <td style="height: 45px"><input type="text" class=input value="${xxctFiles.fileDescription }"
                                            name="fileDescription"
                                            id="fileDescription"></td>
        </tr>
    </table>
    <input type="submit" value="上传文件"/>
</form>
</body>
</html>