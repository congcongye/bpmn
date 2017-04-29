<%--
  Created by IntelliJ IDEA.
  User: ycc
  Date: 17/4/26
  Time: 下午9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script type="text/javascript" src="jquery-1.8.3.js"></script><table border=2><tr><th>Id</th><th>Time</th><th>UserId</th><th>Number</th><th>State</th><th>method</th></tr><tr><td><input type=text readonly=readonly value=1></td><td>1</td><td>1</td><td>1</td><td>4</td><td> <input type=button class=myButton value=check ></td></tr><tr><td><input type=text readonly=readonly value=2></td><td>2</td><td>2</td><td>2</td><td>4</td><td> <input type=button class=myButton value=check ></td></tr><tr><td><input type=text readonly=readonly value=3></td><td>3</td><td>3</td><td>3</td><td>4</td><td> <input type=button class=myButton value=check ></td></tr><tr><td><input type=text readonly=readonly value=11></td><td>11</td><td>11</td><td>11</td><td>4</td><td> <input type=button class=myButton value=check ></td></tr><tr><td><input type=text readonly=readonly value=1></td><td>1</td><td>1</td><td>1</td><td>4</td><td> <input type=button class=myButton value=check ></td></tr><tr><td><input type=text readonly=readonly value=13></td><td>1</td><td>1</td><td>1</td><td>4</td><td> <input type=button class=myButton value=check ></td></tr><tr><td><input type=text readonly=readonly value=1></td><td>1</td><td>1</td><td>1</td><td>4</td><td> <input type=button class=myButton value=check ></td></tr><tr><td><input type=text readonly=readonly value=1></td><td>1</td><td>1</td><td>1</td><td>4</td><td> <input type=button class=myButton value=check ></td></tr><tr><td><input type=text readonly=readonly value=1></td><td>1</td><td>1</td><td>1</td><td>4</td><td> <input type=button class=myButton value=check ></td></tr></table><input type=hidden id=nextid value=ExclusiveGateway_2 >
<input type=hidden id=taskid value=Task_4><input type=hidden id=outputName value=packOrder><script>
    $(".myButton").click(function(){
        var data=$(this).parent().parent().find("td").find("input").val();
        var nextid=$("#nextid").val();
        var taskid=$("#taskid").val();
        var outputName=$("#outputName").val();
//        window.location.href='/sqb-store-map/ServletCheckData?dataid='+data+"&nextid="+nextid+"&taskid="+taskid;

        window.location.href='/sqb-store-map/ServletInputData?dataid='+data+"&nextid="+nextid+"&taskid="+taskid+"&outputName="+outputName;
    });
</script>
</body>
</html>
