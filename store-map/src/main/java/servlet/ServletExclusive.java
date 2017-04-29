package servlet;

import com.wosai.sqb.storemap.ProcessBpmn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by ycc on 17/4/18.
 */
//@WebServlet(name = "ServletExclusive")
public class ServletExclusive extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charser=UTF-8");
        String eventid=request.getParameter("eventid");
        String condition=request.getParameter("condition");
        ProcessBpmn pb=new ProcessBpmn();
        Map<String,Object> map=pb.getNext(eventid,condition);
        String nextid=(String)map.get("nextid");
        String type=(String)map.get("type");
        request.getRequestDispatcher("hello.jsp?id="+nextid+"&type="+type).forward(request,response);
    }
}
