<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>forms | mvc-showcase</title>
    <link href="<c:url value="/resources/form.css" />" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<c:url value="/resources/jquery/1.6/jquery.js" />"></script>
</head>
<body>

<div id="response" style="border: solid 1px black">
</div>

<script type="text/javascript">
    function ajaxPostWithRequestBody() {
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/mapping/requestBody",
            dataType: "json",
            contentType: 'application/json; charset=utf-8',
            data: ({
                email: "test@email.com"
            }),
            success: function (res) {
                $("#response").text(res.data);
            }
        });
    }

    function ajaxPostWithRequestParam() {
        var param = "test@test.com";
        $.ajax({
            type: "GET",
            url: "${pageContext.request.contextPath}/mapping/requestParam",
            dataType: "json",
            contentType: 'application/json; charset=utf-8',
            data: "email=" + param,
            success: function (res) {
                $("#response").text(res.data);
            },
            error: function(res) {
                $("#response").text(res.responseText);
            }
        });
    }
</script>

<br/>

<a href="#" onclick="ajaxPostWithRequestBody();return false;">ajax post with return body</a>

<br/>
<a href="#" onclick="ajaxPostWithRequestParam();return false;">ajax post with return param</a>

<br/>

</body>
</html>
