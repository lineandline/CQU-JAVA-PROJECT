package servlet;

import util.GetWebUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RefreshInfoServlet")
public class RefreshInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("客户端信息更新");
        GetWebUser webUser = new GetWebUser();
        webUser.GetWebUser();
        //判断用户是否登出
//        req.getRequestDispatcher("/index.jsp").forward(req, resp);
//        String url = URLEncoder.encode(req.getRequestURL().toString().replace(req.getRequestURI(),req.getContextPath())+"/index.jsp","utf-8");
//        resp.sendRedirect(url);
        resp.sendRedirect(req.getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
