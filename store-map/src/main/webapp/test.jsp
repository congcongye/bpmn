<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.wosai.sqb.storemap.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="dataService.tableAnalyzer.DBTable" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileOutputStream" %>


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="jquery-1.8.3.js"></script>
</head>
<body>
 <%
    String startEventId =request.getParameter("id");
    String condition =request.getParameter("condition");
    if(startEventId==null||startEventId.equals("")){
        startEventId="StartEvent_1";
        condition="Yes";
    }
    ProcessBpmn pb =new ProcessBpmn();
    DataProcess dp =new DataProcess();
    Map<String,Object> map = pb.getNext(startEventId,condition);
    if(map!=null) {
        String type = (String) map.get("type");
        String nextId = (String) map.get("nextid");
        String taskName=(String) map.get("taskName");
        if (type.equals("start")) {
            System.out.println("start");
        }%>
start;
<%
        request.getRequestDispatcher("test.jsp?id="+nextId+"&condition=Yes").forward(request,response);
    }
%>

</body>