package servlet;

import cache.SelfCache;
import model.TGT;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //收到客户端登出申请
        System.out.println("服务器收到登出申请");
        String token = "";
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("CAS-TGC".equals(cookie.getName())) {
                    //获取到tgtCache的key，也就是TGC
                    token= cookie.getValue();
                    cookie.setMaxAge(0); // 将cookie的存活时间设置为0，即立即过期
                    resp.addCookie(cookie); // 更新response中的cookie
                    break;
                }
            }
        }
        if(token == null||token.length() == 0){
            System.out.println("token值为零");
            return;
        }
        TGT tgt = SelfCache.tgtCache.get(token);
        //对tgt所对应的每个url依次返回注销
        for(Map.Entry<String, String> entry : tgt.safeUrl.entrySet()){
            //向客户端发送请求
            System.out.println("对每个客户端返回注销"+entry.getValue()+entry.getKey());
            SelfCache.stCache.remove(entry.getKey());
            PostMethod postMethod = new PostMethod(entry.getValue() + "/LogoutServlet");
            postMethod.addParameter("ST", entry.getKey());
            HttpClient httpClient = new HttpClient();
            try {
                httpClient.executeMethod(postMethod);
                postMethod.releaseConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String username = SelfCache.tgtCache.get(token).getUser().getUsername();
        SelfCache.web1Cache.remove(username);
        SelfCache.web2Cache.remove(username);
        // 注销当前用户对应的TGT
        if(SelfCache.tgtCache.remove(token)!=null){
            req.getSession().removeAttribute("TGC");
            req.getSession().removeAttribute("TGT");
            req.getSession().removeAttribute("user");
            System.out.println("注销成功");
        }
//        System.out.println("tgtCache大小"+SelfCache.tgtCache.size());
        String backUrl = req.getParameter("backUrl");
        System.out.println(backUrl);
        //backUrl为客户端地址
        resp.sendRedirect(backUrl);
    }
}
