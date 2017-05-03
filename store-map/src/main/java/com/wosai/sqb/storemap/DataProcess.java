package com.wosai.sqb.storemap;
import model.ForeignKeyModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by ycc on 17/4/11.
 */
public class DataProcess {

    Map<String, ForeignKeyModel> foreignKeyModelMap =null;
    Connection conn=DBConnection.getConn();
    String menue="";
    public DataProcess(){
        getMenu();
    }
    /**
     * 根据表名和属性名称和数据状态,将数据库中该表的数据
     * @param tableName
     * @param labels
     * @return
     */
    public List<HashMap<String,Object>> getValueByTableName(String tableName,List<String> labels,String state) {        //数据库的查询
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        Statement st=null;
        try {
            st = conn.createStatement();
            String sql = "select * from "+tableName+" where state="+state+";";
            System.out.println(sql);
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,Object> temp =new HashMap<String,Object>();
                for(String index:labels){
                    temp.put(index,rs.getObject(index));
                }
                list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<String> getAttributeByTableName(String tableName){
        Statement st=null;
        List<String> result =new ArrayList<String>();
        String sql ="select COLUMN_NAME from information_schema.COLUMNS where  table_schema='purchase' and table_name = '"+tableName+"';";
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                result.add((String)rs.getObject(1));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据表的列名,进行网页的拼接,返回属性以及对应的输入框
     * @param list
     * @return
     */
    public String drawHtmlLabel(List<String> list,String taskid,String nextid,String tablename,String taskname){
        StringBuffer sb =new StringBuffer();
        sb.append("<script type=\"text/javascript\" src=\"jquery-1.8.3.js\"></script>\n" +
                "    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/font-awesome.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/font-awesome-ie7.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"http://fonts.googleapis.com/css?family=Open+Sans:400,300\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-rtl.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-skins.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-ie.min.css\" />\n" +
                "    <script src=\"js/ace-extra.min.js\"></script>\n" +
                "    <script src=\"js/respond.min.js\"></script>");
        sb.append("<h1>"+taskname+"</h1>");
        sb.append(menue);
        sb.append("<form  name=\"createData\" action=\"/sqb-store-map/ServletStoreData\" method=\"post\">");
        for(int i=0;i<list.size()-1;i++){
            sb.append("<div class='row'><label class='col-xs-2'>"+list.get(i)+"</label> <input type=text name="+list.get(i)+ " class=col-xs-2><br> </div>  ");
        }
        sb.append("<input type=\"hidden\" name=\"tablename\" value=\""+tablename+"\">\n" +
                "    <input type=\"hidden\" name=\"nextid\" value=\""+nextid+"\">\n" +"  <input type=\"hidden\" name=\"eventid\" value="+taskid+">"+
                "    <input type=submit name=Sure value=Sure class=\"col-xs-1\" style=\"left:40px\" /><br></form>");
        return sb.toString();
    }

    /**
     * 将数据库中的数据进行展示(以输入框的形式)
     * @param data
     * @param list
     * @return
     */
    public String drawHtmlData(List<HashMap<String,Object>> data,List<String> list){
        StringBuffer sb =new StringBuffer();
        for(HashMap<String,Object> object :data){
            int index=1;
            for(String str:list){
               sb.append(str+": <input type=text name="+str+index+" value="+object.get(str)+"/><br>");
            }
        }
        return sb.toString();
    }

    /**
     * 以表格的形式
     * @param data
     * @param list
     * @return
     */
    public String drawTableData(List<HashMap<String,Object>> data,List<String> list,String nextid,String taskid,String tableName,String taskname){
        StringBuffer sb=new StringBuffer();
        sb.append("<script type=\"text/javascript\" src=\"jquery-1.8.3.js\"></script>\n" +
                "    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/font-awesome.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/font-awesome-ie7.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"http://fonts.googleapis.com/css?family=Open+Sans:400,300\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-rtl.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-skins.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-ie.min.css\" />\n" +
                "    <script src=\"js/ace-extra.min.js\"></script>\n" +
                "    <script src=\"js/respond.min.js\"></script>");
        sb.append("<h1>"+taskname+"</h1>");
        sb.append(menue);
        sb.append("<table border=2><tr>");
        for(int i=0;i<list.size();i++){//画出表头
            sb.append("<th>"+list.get(i)+"</th>");
        }
        sb.append("<th>method</th>");
        sb.append("</tr>");
        for(HashMap<String,Object> map:data){
            sb.append("<tr>");
            for(String string:list){
                if(string.toLowerCase().equals(tableName+"_id")){
                    sb.append("<td><input type=text readonly=readonly value="+map.get(string)+"></td>");
                }else{
                    sb.append("<td>"+map.get(string)+"</td>");
                }
            }
            sb.append("<td> <input type=button class=myButton value=check ></td></tr>");
        }
        sb.append("</table>");
        sb.append("<input type=hidden id=nextid value="+nextid+" >\n" + "<input type=hidden id=taskid value="+taskid+">"+ "<input type=hidden id=tableName value="+tableName+">");
        sb.append("<script>\n $(\".myButton\").click(function(){\n" +
                "        var data=$(this).parent().parent().find(\"td\").find(\"input\").val();\n" +
                "        var nextid=$(\"#nextid\").val();\n" +
                "        var taskid=$(\"#taskid\").val();\n" +
                "        var tableName=$(\"#tableName\").val();\n"+
                "        window.location.href='/sqb-store-map/ServletCheckData?dataid='+data+\"&nextid=\"+nextid+\"&taskid=\"+taskid+\"&tableName=\"+tableName;"+
                "    });\n" +
                "</script>");

        return sb.toString();
    }

    /**
     * 根据表的列名,进行网页的拼接,返回属性以及对应的输入框
     * @param list
     * @return
     */

    public String drawOutputLabel(List<String> list,String taskid,String nextid,String outputName,String dataid,String inputName,String taskname){
        StringBuffer sb =new StringBuffer();
        sb.append("<script type=\"text/javascript\" src=\"jquery-1.8.3.js\"></script>\n" +
                "    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/font-awesome.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/font-awesome-ie7.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"http://fonts.googleapis.com/css?family=Open+Sans:400,300\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-rtl.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-skins.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-ie.min.css\" />\n" +
                "    <script src=\"js/ace-extra.min.js\"></script>\n" +
                "    <script src=\"js/respond.min.js\"></script>");
        sb.append("<h1>"+taskname+"</h1>");
        sb.append(menue);
        sb.append("<form  name=\"createData\" action=\"/sqb-store-map/ServletStoreOutputData\" method=\"post\">");
        for(int i=0;i<list.size()-1;i++){
            sb.append("<div class='row'><label class='col-xs-2'>"+list.get(i)+"</label> <input type=text name="+list.get(i)+ " class=col-xs-2><br> </div>  ");
        }
        sb.append("<input type=\"hidden\" name=\"outputName\" value=\""+outputName+"\">\n" +
                "    <input type=\"hidden\" name=\"nextid\" value=\""+nextid+"\">\n" +"<input type=\"hidden\" name=\"taskid\" value="+taskid+">"+
                        "<input type=\"hidden\" name=\"dataid\" value="+dataid+">"+"<input type=\"hidden\" name=\"inputName\" value="+inputName+">"+
                "    <input type=submit name=Sure value=Sure  class=\"col-xs-1\" style=\"left:40px\"/><br></form>");
        return sb.toString();
    }

    public String drawInputTableData(List<HashMap<String,Object>> data,List<String> list,String nextid,String taskid,String outputName,String dataName,String taskname){
        StringBuffer sb=new StringBuffer();
        sb.append("<script type=\"text/javascript\" src=\"jquery-1.8.3.js\"></script>\n" +
                "    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/font-awesome.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/font-awesome-ie7.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"http://fonts.googleapis.com/css?family=Open+Sans:400,300\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-rtl.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-skins.min.css\" />\n" +
                "    <link rel=\"stylesheet\" href=\"css/ace-ie.min.css\" />\n" +
                "    <script src=\"js/ace-extra.min.js\"></script>\n" +
                "    <script src=\"js/respond.min.js\"></script>");
//        sb.append("<script type=\"text/javascript\" src=\"jquery-1.8.3.js\"></script>");
        sb.append("<h1>"+taskname+"</h1>");
        sb.append(menue);
        sb.append("<table border=2><tr>");
        for(int i=0;i<list.size();i++){//画出表头
            sb.append("<th>"+list.get(i)+"</th>");
        }
        sb.append("<th>method</th>");
        sb.append("</tr>");
        for(HashMap<String,Object> map:data){
            sb.append("<tr>");
            for(String string:list){
                if(string.toLowerCase().equals(dataName+"_id")){
                    sb.append("<td><input type=text readonly=readonly value="+map.get(string)+"></td>");
                }else{
                    sb.append("<td>"+map.get(string)+"</td>");
                }
            }
            sb.append("<td> <input type=button class=myButton value=check ></td></tr>");
        }
        sb.append("</table>");
        sb.append("<input type=hidden id=nextid value="+nextid+" >\n" + "<input type=hidden id=taskid value="+taskid+">"+ "<input type=hidden id=outputName value="+outputName+">"+ "<input type=hidden id=tableName value="+dataName+">");
        sb.append("<script>\n $(\".myButton\").click(function(){\n" +
                "        var data=$(this).parent().parent().find(\"td\").find(\"input\").val();\n" +
                "        var nextid=$(\"#nextid\").val();\n" +
                "        var taskid=$(\"#taskid\").val();\n" +
                "        var outputName=$(\"#outputName\").val();\n" +
                "        var tableName=$(\"#tableName\").val();\n" +
                "        window.location.href='/sqb-store-map/ServletInputData?dataid='+data+\"&nextid=\"+nextid+\"&taskid=\"+taskid+\"&outputName=\"+outputName+\"&tableName=\"+tableName;\n"+
                "    });\n" +
                "</script>");
        return sb.toString();
    }

    public void getForeignColumn(String databaseName){
        foreignKeyModelMap=new HashMap<String,ForeignKeyModel>();
        Statement st=null;
        String sql="SELECT k.TABLE_NAME,k.column_name,k.REFERENCED_TABLE_NAME,K.REFERENCED_COLUMN_NAME\n" +
                "FROM information_schema.table_constraints t\n" +
                "JOIN information_schema.key_column_usage k\n" +
                "USING (constraint_name,table_schema,table_name)\n" +
                "WHERE t.constraint_type='FOREIGN KEY'\n" +
                "  AND t.table_schema="+databaseName+";";
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                ForeignKeyModel model=new ForeignKeyModel();
                model.tableName=(String)rs.getObject(1);
                model.columnName=(String)rs.getObject(2);
                model.referenceTableName=(String)rs.getObject(3);
                model.referenceColumnName=(String)rs.getObject(4);
                foreignKeyModelMap.put(model.tableName+"_"+model.columnName,model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    /**
     * 根据table的名字找到某个数据库表中的所有外键信息
     * @param key
     * @return
     */
    public  List<ForeignKeyModel> likeString(String key,String dataBaseName) {
        if(foreignKeyModelMap.isEmpty()){
            getForeignColumn(dataBaseName);
        }
        List<ForeignKeyModel> list = new ArrayList<ForeignKeyModel>();
        Iterator it = foreignKeyModelMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, ForeignKeyModel> entry = (Map.Entry<String, ForeignKeyModel>)it.next();
            if (entry.getKey().indexOf(key) != -1) {
                list.add(entry.getValue());
            }
        }
        return list;
    }


    public String getMenu(){
        ProcessBpmn processBpmn=new ProcessBpmn();
        Map<String,String> map = processBpmn.getTaskIdAndName();
        StringBuffer sb =new StringBuffer();
        sb.append("<nav>");
        for(Map.Entry<String,String> entry:map.entrySet()){
            sb.append("<a href=\"hello.jsp?id="+entry.getKey()+"&condition=Yes\">"+entry.getValue()+"</a> ");
        }
        sb.append("</nav>");
        menue=sb.toString();
        return menue;
    }

}
