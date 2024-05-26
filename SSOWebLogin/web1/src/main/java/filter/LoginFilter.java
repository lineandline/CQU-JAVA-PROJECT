package filter;

import cache.SelfCache;
import httpconstants.Constants;
import util.CheckUtil;
import util.GetWebUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebFilter(urlPatterns = { "/index.jsp"})
public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Login Filter...");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //用户未登录情况
        if(request.getSession().getAttribute("user")==null){
            System.out.println("user=null,判断用户未登录");
            //获取服务端签发的service ticket
            String st = request.getParameter("ST");
            //不存在令牌时重定向到登录界面
            if(st == null||st.length()==0){
                System.out.println("不存在令牌，转向登录界面");
                getLogin(request,response);
                return;
            } else {//若存在令牌则检测令牌是否真实
                System.out.println("令牌为"+st);
                String username = new CheckUtil().checkST(st);
                System.out.println("检测令牌真实性");
                if(username == null||username.length()==0){
                    System.out.println("令牌失效");
                    getLogin(request,response);
                } else {
                    //存入客户端sessionCache
                    System.out.println("令牌真实");
                    if(!SelfCache.sessionCache.containsKey(st)) {
                        SelfCache.sessionCache.put(st,request.getSession());
                    }
                    //判断令牌真实，由server返回在线用户列表
                    GetWebUser webUser = new GetWebUser();
                    webUser.GetWebUser();
                    System.out.println("跳转到主页面");
                    request.getSession().setAttribute("user",username);
                    request.getSession().setAttribute("ST",st);
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
            }

        } else {
            System.out.println("user存在值，判断用户存在");
            filterChain.doFilter(request,response);
        }
    }

    public void destroy() {

    }

    public void getLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = URLEncoder.encode(request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()),"utf-8");
        System.out.println(Constants.SERVER_LOGIN_URL +"/index.jsp?backUrl=" + url);
        response.sendRedirect(Constants.SERVER_LOGIN_URL +"/LoginPage?backUrl=" + url);
    }
}