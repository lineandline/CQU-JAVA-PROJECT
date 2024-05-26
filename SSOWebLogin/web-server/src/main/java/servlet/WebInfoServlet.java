package servlet;

import cache.SelfCache;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/WebInfoServlet")
public class WebInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json");
        String jsonstr = null;
        String backUrl = req.getParameter("backUrl");
        Gson gson = new Gson();
        if(backUrl.equals("web1")){
            jsonstr = gson.toJson(SelfCache.web1Cache);
        } else if(backUrl.equals("web2")){
            jsonstr = gson.toJson(SelfCache.web2Cache);
        } else{
            System.out.println("请求来源获取错误");
            return;
        }
        OutputStream outputStream = resp.getOutputStream();
        outputStream.write(jsonstr.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
