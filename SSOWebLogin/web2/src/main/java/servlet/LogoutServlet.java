package servlet;


import cache.SelfCache;
import httpconstants.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取ST
        String st = request.getParameter("ST");
        String url = URLEncoder.encode(request.getContextPath()+"/logoutsuccess.jsp", "UTF-8");
        //发出logout请求
        if (st == null || "".equals(st)){
            System.out.println("发出Logout请求");
            response.sendRedirect(Constants.SERVER_LOGIN_URL +"/Logout?backUrl=" + url);
        }else {
            //当其他服务器发出登出请求，server向当前服务器发出Logout通知
            //获取当前ST，并清空Cache
            HttpSession nowSession = SelfCache.sessionCache.get(st);
            SelfCache.sessionCache.clear();
            SelfCache.userLoginMessage.clear();
            System.out.println("session large"+SelfCache.sessionCache.size());
            //清除当前session
            nowSession.removeAttribute("user");
            nowSession.removeAttribute("ST");
            System.out.println("now session id "+nowSession.getId());
            nowSession.invalidate();
            System.out.println("web1登出！！！");
//            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }

    }
}
