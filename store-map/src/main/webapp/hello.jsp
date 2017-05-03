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
    ProcessBpmn pb =new ProcessBpmn();
    DataProcess dp =new DataProcess();
    String tempId=pb.getStartEvent().get(0);
    if(startEventId==null||startEventId.equals("")){
        startEventId=tempId;
        condition="Yes";
    }

    Map<String,Object> map = pb.getNext(startEventId,condition);
    if(map!=null) {
        String type = (String) map.get("type");
        String nextId = (String) map.get("nextid");
        String taskName=(String) map.get("taskName");
        if (type.equals("start")) {%>
            start;
        <%request.getRequestDispatcher("hello.jsp?id="+nextId+"&condition=Yes").forward(request,response);
        }else if(type.equals("end")){%>
            process end
        <script>alert('process ends')</script>
        <%} else if(type.equals("exclusive")){
            request.getRequestDispatcher("exclusive.jsp?id="+startEventId).forward(request,response);
        }else if(type.equals("task")){
            Collection<String> inputs =pb.getInputData(startEventId);
            Collection<String> outputs =pb.getOutPutData(startEventId);
            if(inputs.size()==0&&outputs.size()==0){ //没有输入,没有输出
         %>
                alert("bpmn 文件格式有问题");
         <%}
            if(inputs.size()==0&&outputs.size()!=0){ //没有输入,有输出,开始task
                List<String> list =new ArrayList<String>();
                for(String string:outputs){
                list.addAll(dp.getAttributeByTableName(pb.escape(string)));
                }
                String tableName =pb.escape(outputs.iterator().next());
                String result=dp.drawHtmlLabel(list,startEventId,nextId,tableName,taskName);

        %>
       <script>
        myWindow =window.open('','','width=300,height=600')
        myWindow.document.write("<%=result%>")
        </script>
        <%
        } if(inputs.size()!=0&&outputs.size()==0){ //check operation
            String tableName=pb.escape(inputs.iterator().next());
            List<String> label=dp.getAttributeByTableName(tableName);
            String preTaskId=pb.getPreId(startEventId);//preview task id
            String state =pb.getStateByTaskId(preTaskId);
            List<HashMap<String,Object>> tableValues=dp.getValueByTableName(tableName,label,state);
            String result=dp.drawTableData(tableValues,label,nextId,startEventId,tableName,taskName);
        %>
        <script>
             myWindow =window.open('','','width=300,height=600')
             myWindow.document.write("<%=result%>")
        </script>
        <%
        }  if(inputs.size()!=0&& outputs.size()!=0){  //check + input
            String tablename=pb.escape(inputs.iterator().next());
            String preTaskId=pb.getPreId(startEventId);//preview task id
            String state =pb.getStateByTaskId(preTaskId);
            List<String> label=dp.getAttributeByTableName(tablename);
            List<HashMap<String,Object>> tableValues=dp.getValueByTableName(tablename,label,state);
            String outputTableName =pb.escape(outputs.iterator().next());
            String result=dp.drawInputTableData(tableValues,label,nextId,startEventId,outputTableName,tablename,taskName);//将packOrder换成outputs.iterator().next()

        %>
        <script>
            myWindow =window.open('400','300','width=600,height=600')
            myWindow.document.write("<%=result%>")
        </script>
        <%
        }
        }
        }
        else{%>
          alert("next task is null");
        <%}%>

</body>