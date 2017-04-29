<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="jquery-1.8.3.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="css/font-awesome.min.css" />
    <link rel="stylesheet" href="css/font-awesome-ie7.min.css" />
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />
    <link rel="stylesheet" href="css/ace.min.css" />
    <link rel="stylesheet" href="css/ace-rtl.min.css" />
    <link rel="stylesheet" href="css/ace-skins.min.css" />
    <link rel="stylesheet" href="css/ace-ie.min.css" />
    <script src="js/ace-extra.min.js"></script>
    <script src="js/respond.min.js"></script>
</head>
<body>

    <form  name="createData" action="/sqb-store-map/ServletStoreData" method="post" class="form-group">
        <div class="row" center>
        <lable class="col-xs-1">Id</lable>  <input type=text name=Id class="col-xs-2"><br>
        </div>
        <div class="row">
        <lable class="col-xs-1"> Time </lable> <input type=text name=Time class="col-xs-2"><br>
        </div>
        <%--UserId  <input type=text name=UserId><br>--%>
        <%--Number  <input type=text name=Number><br>--%>
        <input type="hidden" name="tablename" value="bpmnOrder">
        <input type="hidden" name="nextid" value="Task_3">
        <input type="hidden" name="eventid" value="">
        <input type=submit name=Sure value=Sure class="col-xs-2" style="left:40px"/><br>

    </form>



</body>


</html>
