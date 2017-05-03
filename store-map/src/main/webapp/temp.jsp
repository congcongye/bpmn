










<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="jquery-1.8.3.js"></script>
</head>
<body>

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
   <h1 class="center">Submit Paper</h1>
   <div class="row">
       <div class="col-xs-2">
       <ul style="left-margin:0 ;left-padding:0 ; list-style-type:none"><li><a href="hello.jsp?id=Task_1&condition=Yes">Submit Paper</a></li>
           <li><a href="hello.jsp?id=Task_5&condition=Yes">Accept paper</a></li> <li><a href="hello.jsp?id=Task_4&condition=Yes">Review Paper</a></li>
           <li><a href="hello.jsp?id=Task_3&condition=Yes">Reject Paper</a></li> <li><a href="hello.jsp?id=Task_2&condition=Yes">Check Paper</a></li>
           <li><a href="hello.jsp?id=Task_7&condition=Yes">Publish Paper</a></li>
       </ul>
   </div>
       <div class="col-xs-10">
           <form  name="createData" action="/sqb-store-map/ServletStoreData" method="post">
               <div class='row'><label class='col-xs-3'>paper_information_id</label><input type=text name=paper_information_id class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>authorl</label><input type=text name=authorl class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>email</label> <input type=text name=email class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>address</label> <input type=text name=address class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>author2</label> <input type=text name=author2 class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>author3</label> <input type=text name=author3 class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>author4</label> <input type=text name=author4 class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>author5</label> <input type=text name=author5 class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>author6</label> <input type=text name=author6 class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>title</label> <input type=text name=title class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>key_words</label> <input type=text name=key_words class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>abstract</label> <input type=text name=abstract class=col-xs-4><br> </div>
               <div class='row'><label class='col-xs-3'>reference</label> <input type=text name=reference class=col-xs-4><br> </div>
               <input type="hidden" name="tablename" value="paper_information">
    <input type="hidden" name="nextid" value="Task_2">
    <input type="hidden" name="eventid" value=Task_1>    <input type=submit name=Sure value=Sure class="col-xs-1" style="left:40px" /><br></form>
       </div>
   </div>



</body>