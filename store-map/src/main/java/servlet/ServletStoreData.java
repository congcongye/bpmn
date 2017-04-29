package servlet;

import com.google.gson.Gson;
import com.wosai.sqb.storemap.DBConnection;
import com.wosai.sqb.storemap.DataProcess;
import com.wosai.sqb.storemap.ProcessBpmn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by ycc on 17/4/14.
 */
//@WebServlet(name = "ServletStoreData",urlPatterns ="sqb-store-map/ServletStoreData")
public class ServletStoreData extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charser=UTF-8");
        HttpSession session=request.getSession();
        String tableName=request.getParameter("tablename");
        String nextid=request.getParameter("nextid");
        String eventid=request.getParameter("eventid");
        DataProcess dataProcess=new DataProcess();
        List<String> list =dataProcess.getAttributeByTableName(tableName);
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<list.size()-1;i++){
            sb.append(request.getParameter(list.get(i))+",");
        }
        String data="";
        ProcessBpmn pb=new ProcessBpmn();
        String state=pb.getStateByTaskId(eventid);
        data=sb.append(state).toString();
        Statement st=null;
        Connection conn= DBConnection.getConn();
        try {
            st = conn.createStatement();
            String sql = "insert into "+tableName+" values ("+data+");";
//            System.out.println("sql: "+sql);
            int result = st.executeUpdate(sql);
            if(result>0){ //数据插入数据库成功
                //更新session,同时跳转到hello.jsp页面
                session.setAttribute("data",tableName);
                session.setAttribute("state",state);
//                System.out.println("text");
                request.getRequestDispatcher("hello.jsp?id="+nextid+"&condition=Yes").forward(request,response);
//                response.sendRedirect("hello.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
