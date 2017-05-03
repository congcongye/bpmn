package servlet;

import com.wosai.sqb.storemap.DBConnection;
import com.wosai.sqb.storemap.ProcessBpmn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ycc on 17/4/15.
 */
//@WebServlet(name = "ServletCheckData")
public class ServletCheckData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        window.location.href='/sqb-store-map/ServletCheckData?dataid='+data+"&nextid="+nextid+"&taskid="+taskid;

        response.setContentType("text/html;charser=UTF-8");
        ProcessBpmn pb=new ProcessBpmn();
//        HttpSession session=request.getSession();
        String dataid=request.getParameter("dataid");
        String nextid=request.getParameter("nextid");
        String taskid=request.getParameter("taskid");
        String tableName=request.getParameter("tableName");
//        String state=(String)session.getAttribute("state");
        String state=pb.getStateByTaskId(taskid);
        //改变数据状态
        Statement st=null;
        Connection conn= DBConnection.getConn();
        try {
            st = conn.createStatement();
            String sql = "update "+tableName+" set state = "+state +" where "+tableName+"_id="+dataid;
            System.out.println("sql: "+sql);
            int result = st.executeUpdate(sql);
            if(result>0){
//                session.setAttribute("data",tableName);
//                session.setAttribute("state",state);
                request.getRequestDispatcher("hello.jsp?id="+nextid+"&condition=Yes").forward(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
