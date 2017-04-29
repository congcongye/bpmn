<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.wosai.sqb.storemap.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.HashMap"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="jquery-1.8.3.js"></script>
</head>
<body>
<div id="createData" >

</div>
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
        if (type.equals("start")) {%>
        start;
       <%
          request.getRequestDispatcher("hello.jsp?id="+nextId+"&condition=Yes").forward(request,response);
      }else if(type.equals("end")){%>
           process end
          <script>alert('process ends')</script>
      <%} else if(type.equals("exclusive")){
          request.getRequestDispatcher("exclusive.jsp?id="+startEventId).forward(request,response);
      }else if(type.equals("task")){
            Collection<String> inputs =pb.getInputData(startEventId,1);
            Collection<String> outputs =pb.getInputData(startEventId,2);
           if(inputs.size()==0&&outputs.size()==0){ //没有输入,没有输出
               String data=(String) session.getAttribute("data");//获得当前tablename
               String state=(String) session.getAttribute("state");//数据当前的状态
               if(data==null){%>
                   alert("bpmn 文件格式有问题");
               <% }else{
                   List<String> label=dp.getAttributeByTableName(data);
                   List<HashMap<String,Object>> tableValues=dp.getValueByTableName(data,label,state);
                   String result=dp.drawTableData(tableValues,label,nextId,startEventId);
               %>
                <script>
                myWindow =window.open('','','width=600,height=600')
                myWindow.document.write("<%=result%>")
                </script>
                <%
               }
           }
           if(inputs.size()==0&&outputs.size()!=0){ //没有输入,有输出,分成两种情况,session有data属性和session中没有data属性
               String data=(String)session.getAttribute("data");
               if(data==null){
                   List<String> list =new ArrayList<String>();
                   list.addAll(dp.getAttributeByTableName("bpmnOrder"));//等数据库建好后,用后面注释的替换该句
//                   for(String string:outputs){
//                       list.addAll(dp.getAttributeByTableName(string));
//                   }
                   String result=dp.drawHtmlLabel(list,startEventId,nextId,"bpmnOrder");//bpmnOrder以后替换成outputs.get(0);
                   %>
                   <script>
                       myWindow =window.open('','','width=300,height=600')
                       myWindow.document.write("<%=result%>")
                   </script>

              <% }else{
                  String tablename="bpmnOrder";//data;
                  String state=(String)session.getAttribute("state");
                  List<String> label=dp.getAttributeByTableName(tablename);
                  List<HashMap<String,Object>> tableValues=dp.getValueByTableName(tablename,label,state);
                  String result=dp.drawInputTableData(tableValues,label,nextId,startEventId,"packOrder");//将packOrder换成outputs.iterator().next()
                  %>
                <script>
                   myWindow =window.open('400','300','width=600,height=600')
                   myWindow.document.write("<%=result%>")
                </script>
              <%
               }
           }
      }
    }

%>
</body>

</html>