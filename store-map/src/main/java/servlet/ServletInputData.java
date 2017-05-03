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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by ycc on 17/4/26.
 */
//@WebServlet(name = "ServletInputData")
public class ServletInputData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charser=UTF-8");
        HttpSession session=request.getSession();
        ProcessBpmn pb=new ProcessBpmn();
        DataProcess dataProcess=new DataProcess();
        String dataid=request.getParameter("dataid");
        String nextid=request.getParameter("nextid");
        String taskid=request.getParameter("taskid");
        String outputName=request.getParameter("outputName");
        List<String> lableNames=dataProcess.getAttributeByTableName(outputName);
//        String inputName=(String)session.getAttribute("data");
        String inputName=request.getParameter("tableName");
        String htmlPage=dataProcess.drawOutputLabel(lableNames,taskid,nextid,outputName,dataid,inputName);
        PrintWriter out = response.getWriter();
        out.print("<html>"+htmlPage+"</html>");
    }
}
