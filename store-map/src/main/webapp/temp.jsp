










<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="jquery-1.8.3.js"></script>
</head>
<body>
<h1>task name dfad</h1>
<script>
    myWindow =window.open('400','300','width=600,height=600')
    myWindow.document.write("<script type="text/javascript" src="jquery-1.8.3.js"></script>
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
<nav><a href="hello.jsp?id=Task_4&condition=Yes">Package Order</a> <a href="hello.jsp?id=Task_3&condition=Yes">Check Credit</a>
    <a href="hello.jsp?id=Task_2&condition=Yes">Create Sales Order</a> <a href="hello.jsp?id=Task_6&condition=Yes">Ship Order</a> </nav>
<table border=2><tr><th>packing_list_id</th><th>totals</th><th>consignee</th><th>__warehouse_id</th><th>sales_order_id</th><th>__receive_date</th>
    <th>containers</th><th>packing_date</th><th>state</th><th>method</th></tr></table><input type=hidden id=nextid value=EndEvent_2 >
<input type=hidden id=taskid value=Task_6><input type=hidden id=outputName value=delivery_order><input type=hidden id=tableName value=packing_list>

<script>
    $(".myButton").click(function(){
        var data=$(this).parent().parent().find("td").find("input").val();
        var nextid=$("#nextid").val();
        var taskid=$("#taskid").val();
        var outputName=$("#outputName").val();
        var tableName=$("#tableName").val();
        window.location.href='/sqb-store-map/ServletInputData?dataid='+data+"&nextid="+nextid+"&taskid="+taskid+"&outputName="+outputName+"&tableName="+tableName;
    });
</script>")
</script>


</body>