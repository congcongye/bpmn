<%--
  Created by IntelliJ IDEA.
  User: ycc
  Date: 17/4/10
  Time: 下午5:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.wosai.sqb.storemap.*" %>
<%@ page import="java.util.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <script type="text/javascript" src="jquery-1.8.3.js"></script>
</head>
<body>
<%--<label><input name="condition" type="radio" value="Yes" />Yes </label>--%>
<%--<label><input name="conditon" type="radio" value="No" />No </label><br>--%>
<%--<button onclick="exclusive()"  width="100px" height="40px">next</button>--%>

<div class="controls">
   <input id='radio-1' type="radio" name='condition' value ="Yes" checked='checked' /><label for="radio-1">Yes</label>
   <input id='radio-2' type="radio" name='condition' value = "No"/><label for="radio-2">No</label><br>
   <button onclick="exclusive()"  width="100px" height="40px">next</button>
</div>

<script>
    function exclusive() {
        var temp=$('input:radio:checked').val();
        var eventid ="<%=request.getParameter("id")%>";
       window.location.href='/sqb-store-map/ServletExclusive?eventid='+eventid+"&condition="+temp;

    }
</script>
</body>
</html>
