package servlet;

import cache.SelfCache;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/StCheckServlet")
public class StCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ST= req.getParameter("ST");
        //查询服务端中存储的ST对应的用户名
        String username = SelfCache.stCache.get(ST);
        PrintWriter writer = resp.getWriter();
        writer.write(username);
    }
}
