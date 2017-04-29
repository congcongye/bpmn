package servlet;

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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by ycc on 17/4/26.
 */
//@WebServlet(name = "ServletStoreOutputData")
public class ServletStoreOutputData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     this.doGet(request,response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charser=UTF-8");
        HttpSession session=request.getSession();
        String inputName=request.getParameter("inputName");
        String nextid=request.getParameter("nextid");
        String taskid=request.getParameter("taskid");
        String outputName=request.getParameter("outputName");
        String dataid=request.getParameter("dataid");
        DataProcess dataProcess=new DataProcess();
        ProcessBpmn pb=new ProcessBpmn();
        List<String> list =dataProcess.getAttributeByTableName(outputName);
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<list.size()-1;i++){
            sb.append(request.getParameter(list.get(i))+",");
        }
        String data="";
        String state=pb.getStateByTaskId(taskid);
        data=sb.append(state).toString();
        Statement st=null;
        Connection conn= DBConnection.getConn();
        try {
            st = conn.createStatement();
            String sql = "insert into "+outputName+" values ("+data+");";
            String str="update "+inputName+" set state = "+state +" where id="+dataid;
            int result = st.executeUpdate(sql);
            if(result>0){ //数据插入数据库成功
                //更新session,同时跳转到hello.jsp页面
                session.setAttribute("data",outputName);
                session.setAttribute("state",state);
                st.executeUpdate(str);
                request.getRequestDispatcher("hello.jsp?id="+nextid+"&condition=Yes").forward(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
